package examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

import dev.megaline.neuralnetwork.NeuralNetwork;

public class PlayTicTacToe {
    static double[] board;
    static int turn;
    static NeuralNetwork player1;
    static int xCount;
    static int tieCount;

    // CheckWinner method will
    // decide the combination
    // of three box given below.
    static String checkWinner() {
        for (int a = 0; a < 8; a++) {
            String line = "";

            switch (a) {
                case 0:
                    line = "" + board[0] + board[1] + board[2];
                    break;
                case 1:
                    line = "" + board[3] + board[4] + board[5];
                    break;
                case 2:
                    line = "" + board[6] + board[7] + board[8];
                    break;
                case 3:
                    line = "" + board[0] + board[3] + board[6];
                    break;
                case 4:
                    line = "" + board[1] + board[4] + board[7];
                    break;
                case 5:
                    line = "" + board[2] + board[5] + board[8];
                    break;
                case 6:
                    line = "" + board[0] + board[4] + board[8];
                    break;
                case 7:
                    line = "" + board[2] + board[4] + board[6];
                    break;
            }
            // For X winner
            if (line.equals("1.01.01.0")) {
                xCount++;
                return "X";

            }

            // For O winner
            else if (line.equals("2.02.02.0")) {
                return "O";
            }
        }

        for (int a = 0; a < 9; a++) {
            if (board[a] == 0) {
                break;
            } else if (a == 8) {
                tieCount++;
                return "draw";
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int[] layers = { 9, 6, 6, 1 };
        player1 = new NeuralNetwork(layers, .1);
        int epochs = 10000;

        for (int i = 0; i < epochs; i++) {
            if (i % 1000 == 0) {
                if ((xCount + tieCount) >= 999) {
                    break;
                }
                xCount = 0;
                tieCount = 0;

            }

            if (i > epochs - 100) {
                autoGameplayLoop(true);
            } else {
                autoGameplayLoop(false);
            }

        }
        System.out.println("WINS: " + xCount);
        System.out.println("TIES: " + tieCount);
        System.out.println("TOTAL: " + (xCount + tieCount) + " / " + 1000);
        while (true) {
            playerGameplayLoop();
        }

    }

    public static void playerGameplayLoop() {
        Scanner in = new Scanner(System.in);
        board = new double[9];
        List<double[]> boardHistory = new ArrayList<>();
        turn = 1;
        String winner = null;

        for (int a = 0; a < 9; a++) {
            board[a] = 0.0;
        }

        System.out.println("Welcome to 3x3 Tic Tac Toe.");
        printBoard(board);

        System.out.println(
                "X will play first. Enter a slot number to place X in:");

        while (winner == null) {
            int numInput;
            boardHistory = new ArrayList<>();

            // Exception handling.
            // numInput will take input from user like from 1 to 9.
            // If it is not in range from 1 to 9.
            // then it will show you an error "Invalid input."

            // This game has two player x and O.
            // Here is the logic to decide the turn.

            if (turn == 1.0) {
                double[] bestPotentialBoard = Arrays.copyOf(board, 9);

                double bestPotentialBoardScore = -1;
                double[] boardPredictions = new double[9];
                for (int i = 0; i < 9; i++) {
                    if (isValidMove(board, i)) {
                        double[] potentialBoard = Arrays.copyOf(board, 9);
                        potentialBoard[i] = turn;
                        List<Double> potentialBoardScore = player1.predict(potentialBoard);
                        boardPredictions[i] = potentialBoardScore.get(0);
                        if (potentialBoardScore.get(0) > bestPotentialBoardScore) {
                            bestPotentialBoard = Arrays.copyOf(potentialBoard, 9);
                            bestPotentialBoardScore = potentialBoardScore.get(0);
                        }
                    } else {
                        boardPredictions[i] = -1;
                    }
                }
                printBoard(boardPredictions);
                board = Arrays.copyOf(bestPotentialBoard, 9);
            } else {

                try {
                    numInput = in.nextInt();
                    if (!(numInput > 0 && numInput <= 9)) {
                        System.out.println(
                                "Invalid input; re-enter slot number:");
                        continue;
                    }
                } catch (InputMismatchException e) {
                    System.out.println(
                            "Invalid input; re-enter slot number:");
                    continue;
                }
                if (board[numInput - 1] == 0) {
                    board[numInput - 1] = turn;

                } else {
                    System.out.println(
                            "Slot already taken; re-enter slot number:");
                }
            }
            if (turn == 1.0) {
                turn = 2;
            } else {
                turn = 1;
            }

            printBoard(board);
            boardHistory.add(board);
            winner = checkWinner();
        }

        // If no one win or lose from both player x and O.
        // then here is the logic to print "draw".
        if (winner.equalsIgnoreCase("draw")) {
            System.out.println(
                    "It's a draw! Thanks for playing.");
        }

        // For winner -to display Congratulations! message.
        else {
            if (winner.equals("X")) {
                for (int i = 0; i < boardHistory.size() - 1; i++) {
                    player1.train(boardHistory.get(i), new double[] { 1 });
                    player1.train(invertBoard(boardHistory.get(i)), new double[] { 0 });
                }
            } else {
                for (int i = 0; i < boardHistory.size() - 1; i++) {
                    player1.train(boardHistory.get(i), new double[] { 0 });
                    player1.train(invertBoard(boardHistory.get(i)), new double[] { 1 });
                }
            }
            System.out.println(
                    "Congratulations! " + winner
                            + "'s have won! Thanks for playing.");
        }
    }

    public static void autoGameplayLoop(boolean isPrintingBoard) {
        board = new double[9];
        turn = 1;

        List<double[]> boardHistory = new ArrayList<>();

        String winner = null;

        for (int a = 0; a < 9; a++) {
            board[a] = 0;
        }

        while (winner == null) {

            if (turn == 1.0) {
                double[] bestPotentialBoard = Arrays.copyOf(board, 9);

                double bestPotentialBoardScore = -1;
                for (int i = 0; i < 9; i++) {
                    if (isValidMove(board, i)) {
                        double[] potentialBoard = Arrays.copyOf(board, 9);
                        potentialBoard[i] = turn;
                        List<Double> potentialBoardScore = player1.predict(potentialBoard);
                        if (potentialBoardScore.get(0) > bestPotentialBoardScore) {
                            bestPotentialBoard = Arrays.copyOf(potentialBoard, 9);
                            bestPotentialBoardScore = potentialBoardScore.get(0);
                        }
                    }
                }
                board = Arrays.copyOf(bestPotentialBoard, 9);
            } else {
                board[findRandomMove(board)] = 2.0;
            }

            if (turn == 1) {
                turn = 2;
            } else {
                turn = 1;
            }
            if (isPrintingBoard)
                printBoard(board);
            boardHistory.add(board);
            winner = checkWinner();

        }

        // If no one win or lose from both player x and O.
        // then here is the logic to print "draw".
        if (winner.equalsIgnoreCase("draw")) {
            for (int i = 0; i < boardHistory.size() - 1; i++) {
                player1.train(boardHistory.get(i), new double[] { 1 });
                // player2.train(boardHistory.get(i), boardHistory.get(i + 1));
            }
            printBoard(board);
            System.out.println(
                    "It's a draw! Thanks for playing!!");
        }

        // For winner -to display Congratulations! message.
        else {
            if (winner.equals("X")) {
                for (int i = 0; i < boardHistory.size() - 1; i++) {
                    player1.train(boardHistory.get(i), new double[] { 1 });
                    // player1.train(invertBoard(boardHistory.get(i)), new double[] { 0 });
                }
            } else {
                for (int i = 0; i < boardHistory.size() - 1; i++) {
                    // player1.train(invertBoard(boardHistory.get(i)), new double[] { 1 });
                    player1.train(boardHistory.get(i), new double[] { 0 });
                }
            }
            printBoard(board);
            System.out.println(
                    "Congratulations! " + winner
                            + "'s have won! Thanks for playing.");
        }
    }

    public static boolean isValidMove(double[] board, int indexOfMove) {
        if (board[indexOfMove] == 0.0) {
            return true;
        }
        return false;
    }

    public static void printBoard(double[] board) {
        System.out.println("|-----|-----|-----|");
        System.out.println("| " + board[0] + " | "
                + board[1] + " | " + board[2]
                + " |");
        System.out.println("|-----------------|");
        System.out.println("| " + board[3] + " | "
                + board[4] + " | " + board[5]
                + " |");
        System.out.println("|-----------------|");
        System.out.println("| " + board[6] + " | "
                + board[7] + " | " + board[8]
                + " |");
        System.out.println("|-----|-----|-----|");
        System.out.println();
    }

    public static int findRandomMove(double[] board) {
        List<Integer> availableIndex = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (isValidMove(board, i)) {
                availableIndex.add(i);
            }
        }
        return availableIndex.get((int) (Math.random() * availableIndex.size()));
    }

    public static double[] invertBoard(double[] board) {
        double[] newBoard = board = new double[9];
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 1) {
                newBoard[i] = 2;
            } else if (board[i] == 2) {
                newBoard[i] = 1;
            } else {
                newBoard[i] = 0;
            }
        }
        return newBoard;
    }
}
