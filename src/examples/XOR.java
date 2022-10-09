package examples;

import java.time.Duration;
import java.time.Instant;

import dev.megaline.neuralnetwork.*;

/*
    * -------
    *   XOR
    * -------
    * - A XOR is a basic gate meaning "exclusive or"
    * - It's an OR gate but doesn't include AND
    * 
    *  1 = TRUE
    *  0 = FALSE
    * 
*/

public class XOR {
        public static void main(String[] args) {

                // Logs data parsing start time
                Instant dataStart = Instant.now();

                double[][] inputData = {
                                { 0, 0 },
                                { 1, 0 },
                                { 0, 1 },
                                { 1, 1 }
                };
                double[][] outputData = {
                                { 0 },
                                { 1 },
                                { 1 },
                                { 0 }
                };

                // Logs neural network start time
                Instant neuralStart = Instant.now();

                int[] layers = { 2, 5, 5, 1 };
                NeuralNetwork nn = new NeuralNetwork(layers, .1);
                nn.fit(inputData, outputData, 10000);

                // Logs neural network end time
                Instant neuralEnd = Instant.now();
                System.out
                                .println("TOTAL TIME ELAPSED: "
                                                + Duration.between(dataStart, neuralEnd).toString().substring(2) + ".");
                System.out.println(
                                "TIME FOR DATA PARSING: "
                                                + Duration.between(dataStart, neuralStart).toString().substring(2)
                                                + ".");
                System.out.println(
                                "TIME FOR NEURAL NETWORK TRAINING:"
                                                + Duration.between(neuralStart, neuralEnd).toString().substring(2)
                                                + ".");

                double[][] input = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } };
                for (double[] d : input) {
                        System.out.println(nn.predict(d).toString());
                }

        }
}
