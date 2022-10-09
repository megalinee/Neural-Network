package learn;

/*
    * --------------
    *   Perceptron
    * --------------
    * - A perceptron is a very basic interpretation of a Neural Network.
    * - There is no hidden layers, it goes directly from input to output.
    * - Since there are no hidden layers it's only able to process linear
    *   data, making it incapable of something like a XOR gate.
    * - This following code is an example is of an OR gate.
    * 
    *  1 = TRUE
    *  0 = FALSE
    * 
*/

public class Perceptron {

    public static void main(String[] args) {
        double learningRate = .1;
        int bias = 0;
        double[] weights = { Math.random(), Math.random(), Math.random() };

        for (int i = 0; i < 30; i++) {
            perceptronTraining(1, 1, 1, learningRate, bias, weights);
            perceptronTraining(0, 1, 1, learningRate, bias, weights);
            perceptronTraining(1, 0, 1, learningRate, bias, weights);
            perceptronTraining(0, 0, 0, learningRate, bias, weights);
        }

        System.out.println(calculateOutput(1, 1, bias, weights)); // This should be true (1)
        System.out.println(calculateOutput(1, 0, bias, weights)); // This should be true (1)
        System.out.println(calculateOutput(0, 1, bias, weights)); // This should be true (1)
        System.out.println(calculateOutput(0, 0, bias, weights)); // This should be false (0)
    }

    public static double[] perceptronTraining(int input1, int input2, int output, double learningRate, int bias,
            double[] weights) {
        double estimatedOutput = calculateOutput(input1, input2, bias, weights);
        double error = output - estimatedOutput;

        weights[0] += error * input1 * learningRate;
        weights[1] += error * input2 * learningRate;
        weights[2] += error * bias * learningRate;

        return weights;
    }

    public static double calculateOutput(int input1, int input2, int bias, double[] weights) {
        double estimatedOutput = input1 * weights[0] + input2 * weights[1] + bias * weights[2];
        if (estimatedOutput > 0.5) {
            estimatedOutput = 1;
        } else {
            estimatedOutput = 0;
        }
        return estimatedOutput;
    }
}