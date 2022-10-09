package examples;

import dev.megaline.neuralnetwork.*;
import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) throws Exception {

        // Logs data parsing start time
        Instant dataStart = Instant.now();

        List<List<String>> data = new ArrayList<>();

        File ticTacToeData = new File("./src/examples/data/tictactoedata.csv");
        try (Scanner scanner = new Scanner(ticTacToeData);) {
            while (scanner.hasNextLine()) {
                data.add(getCSVLine(scanner.nextLine()));
            }
        }

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

        // Logs neural network start time
        Instant neuralStart = Instant.now();

        int[] layers = { 9, 20, 20, 20, 1 };
        NeuralNetwork nn = new NeuralNetwork(layers, .01);
        nn.fit(inputArray, outputArray, 200000);

        // Logs neural network end time
        Instant neuralEnd = Instant.now();
        System.out
                .println("TOTAL TIME ELAPSED: " + Duration.between(dataStart, neuralEnd).toString().substring(2) + ".");
        System.out.println(
                "TIME FOR DATA PARSING: " + Duration.between(dataStart, neuralStart).toString().substring(2) + ".");
        System.out.println(
                "TIME FOR NEURAL NETWORK TRAINING: "
                        + Duration.between(neuralStart, neuralEnd).toString().substring(2) + ".");

        double[][] input = {
                {
                        -1, -1, 1,
                        1, 1, -1,
                        -1, 1, 1
                },
                {
                        1, 1, 1,
                        0, -1, -1,
                        -1, 0, 1
                },
                {
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
