package dev.megaline.neuralnetwork;

import java.util.List;

public class NeuralNetwork {
    // Creates the matrix for the hidden layer's weights and biases.
    private Matrix[] hiddenWeights;
    private Matrix[] hiddenBias;
    private double l_rate;

    public NeuralNetwork(int[] layers, double learnRate) {
        // Sets learn rate for learning. Important to tune this so it doesn't go too
        // slow or overcompensate.
        this.l_rate = learnRate;

        // Initialize the layer's weights and biases matrix with correctly sized
        // matrixes.
        this.hiddenWeights = new Matrix[layers.length];
        this.hiddenBias = new Matrix[layers.length];
        for (int i = 1; i < hiddenWeights.length; i++) {
            hiddenWeights[i] = new Matrix(layers[i], layers[i - 1]);
            hiddenBias[i] = new Matrix(layers[i], 1);
        }
    }

    public List<Double> predict(double[] inputValues) {

        // Iterates through each hidden layer and applies needed dot product and biases.
        Matrix previousLayer = MatrixTools.fromArray(inputValues);
        for (int i = 1; i < hiddenWeights.length; i++) {
            previousLayer = MatrixTools.dotProduct(hiddenWeights[i], previousLayer);
            previousLayer = MatrixTools.add(previousLayer, hiddenBias[i]);
            previousLayer = MatrixTools.sigmoid(previousLayer);
        }

        return MatrixTools.toArray(previousLayer);
    }

    public void train(double[] inputValues, double[] outputValues) {
        Matrix[] layers = new Matrix[hiddenWeights.length];
        layers[0] = MatrixTools.fromArray(inputValues);
        for (int i = 1; i < hiddenWeights.length; i++) {
            layers[i] = MatrixTools.dotProduct(hiddenWeights[i], layers[i - 1]);
            layers[i] = MatrixTools.add(layers[i], hiddenBias[i]);
            layers[i] = MatrixTools.sigmoid(layers[i]);
        }

        Matrix target = MatrixTools.fromArray(outputValues);

        Matrix error = MatrixTools.subtract(target, layers[layers.length - 1]);
        Matrix gradient = MatrixTools.derivSigmoid(layers[layers.length - 1]);
        gradient = MatrixTools.multiply(gradient, error);
        gradient = MatrixTools.multiply(gradient, l_rate);

        for (int i = hiddenWeights.length - 1; i > 0; i--) {
            Matrix hiddenTranspose = MatrixTools.transpose(layers[i - 1]);
            Matrix outputDelta = MatrixTools.dotProduct(gradient, hiddenTranspose);

            hiddenWeights[i] = MatrixTools.add(hiddenWeights[i], outputDelta);
            hiddenBias[i] = MatrixTools.add(hiddenBias[i], gradient);

            Matrix outputTranspose = MatrixTools.transpose(hiddenWeights[i]);
            error = MatrixTools.dotProduct(outputTranspose, error);

            gradient = MatrixTools.derivSigmoid(layers[i - 1]);
            gradient = MatrixTools.multiply(gradient, error);
            gradient = MatrixTools.multiply(gradient, l_rate);
        }
    }

    public void fit(double[][] input, double[][] output, int epochs) {
        for (int i = 0; i < epochs; i++) {
            int sampleN = (int) (Math.random() * input.length);
            this.train(input[sampleN], output[sampleN]);
        }
    }
}
