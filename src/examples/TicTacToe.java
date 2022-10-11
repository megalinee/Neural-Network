package examples;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dev.megaline.neuralnetwork.*;
import dev.megaline.time.*;
import dev.megaline.time.NeuralNetworkTimer.TimeID;

/*
    * ---------------
    *   Tic Tac Toe
    * ---------------
    * - This Neural Network is able to predict if X will win in any given tic tac toe end game.
    * 
    * -- INPUT --
    *  The input corresponds to a Tic Tac Toe board.
    *  {Top Left, Top Middle, Top Right, Middle Left, Middle, Middle Right, Bottom Left, Bottom Middle, Bottom Right}
    *  1 = X
    *  0 = Blank
    *  -1 = O
    * 
    * -- OUTPUT --
    *  The output corresponds to the percentage likelihood of X winning in end game.
    *  EX: .08 = 8%
    * 
*/

public class TicTacToe {
    public static void main(String[] args) throws Exception {

        // Initialize Neural Network Timer
        NeuralNetworkTimer timer = new NeuralNetworkTimer(TimeID.DATA_PARSE_START);

        // Retrieves data from CSV file
        List<List<String>> data = new ArrayList<>();
        File ticTacToeData = new File("./src/examples/data/tictactoedata.csv");
        try (Scanner scanner = new Scanner(ticTacToeData);) {
            while (scanner.hasNextLine()) {
                data.add(getCSVLine(scanner.nextLine()));
            }
        }

        // Converts List of String List into 2 2D arrays for Neural Network class.
        double[][] inputArray = new double[data.size()][9];
        double[][] outputArray = new double[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            for (int j = 1; j < data.get(i).size(); j++) {
                switch (data.get(i).get(j)) {
                    case "x":
                        inputArray[i][j - 1] = 1.0;
                        break;
                    case "o":
                        inputArray[i][j - 1] = -1.0;
                        break;
                    case "b":
                        inputArray[i][j - 1] = 0.0;
                        break;
                    case "positive":
                        outputArray[i][0] = 1.0;
                        break;
                    case "negative":
                        outputArray[i][0] = 0.0;
                        break;
                }
            }
        }

        // Logs Neural Network start time
        timer.setTime(TimeID.NEURAL_NETWORK_START);

        /*
         * Corresponds to layers in Neural Network.
         * - First number will always be number of inputs
         * - Last number will always be number of outputs
         * - Each number in between will corresponds to a
         * hidden network and the amount of nodes in each.
         */
        int[] layers = { 9, 8, 8, 1 };

        // Starts neural network.
        // Sets learning rate & epochs (Amount of data to consume)
        NeuralNetwork nn = new NeuralNetwork(layers, .1);
        nn.fit(inputArray, outputArray, 100000);

        // Outputs total time elapsed for each period.
        timer.printTimeInfo(TimeID.NEURAL_NETWORK_END);

        // Input data to test if Neural Network worked.
        double[][] input = {
                { // Should be not likely
                        -1, -1, 1,
                        1, 1, -1,
                        -1, 1, 1
                },
                { // Should be very likely
                        1, 1, 1,
                        0, -1, -1,
                        -1, 0, 1
                },
                { // Should be not likely
                        -1, -1, -1,
                        1, 1, 0,
                        1, 0, 0
                }
        };
        for (double[] d : input) {
            System.out.println(nn.predict(d).toString());
        }
    }

    public static List<String> getCSVLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }
}
