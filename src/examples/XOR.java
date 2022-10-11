package examples;

import dev.megaline.neuralnetwork.*;
import dev.megaline.time.*;
import dev.megaline.time.NeuralNetworkTimer.TimeID;

/*
    * -------
    *   XOR
    * -------
    * - This Neural Network is able to determine the XOR of an input.
    * - A XOR is a basic gate meaning "exclusive or"
    * - It's an OR gate but doesn't include AND
    * 
    * -- INPUT --
    *  The two inputs correspond to the XOR inputs.
    *  1 = TRUE
    *  0 = FALSE
    * 
    * -- OUTPUT --
    *  The output corresponds to whether the output is true or false.
    *  1 = TRUE
    *  0 = FALSE
    * 
*/

public class XOR {
    public static void main(String[] args) {

        // Initialize Neural Network Timer
        NeuralNetworkTimer timer = new NeuralNetworkTimer(TimeID.DATA_PARSE_START);

        // Creates the input and output training data. Each index in input corresponds
        // to the index for the output.
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

        // Logs Neural Network start time
        timer.setTime(TimeID.NEURAL_NETWORK_START);

        /*
         * Corresponds to layers in Neural Network.
         * - First number will always be number of inputs
         * - Last number will always be number of outputs
         * - Each number in between will corresponds to a
         * hidden network and the amount of nodes in each.
         */
        int[] layers = { 2, 5, 5, 1 };

        // Starts neural network.
        // Sets learning rate & epochs (Amount of data to consume)
        NeuralNetwork nn = new NeuralNetwork(layers, .1);
        nn.fit(inputData, outputData, 13000);

        // Outputs total time elapsed for each period.
        timer.printTimeInfo(TimeID.NEURAL_NETWORK_END);

        double[][] input = {
                { 0, 0 }, // Should be false
                { 0, 1 }, // Should be true
                { 1, 0 }, // Should be true
                { 1, 1 } // Should be false
        };
        for (double[] d : input) {
            System.out.println(nn.predict(d).toString());
        }

    }
}
