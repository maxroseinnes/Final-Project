public class NeuralNetwork {
    public int[] nodeCounts;
    double[][] biases;
    double[][][] weights;

    public NeuralNetwork(int[] neuronCounts) {
        this.nodeCounts = neuronCounts;
        weights = new double[neuronCounts.length - 1][][];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = new double[neuronCounts[i]][neuronCounts[i + 1]];
            for (int j = 0; j < weights[i].length; j++) {
                for (int k = 0; k < weights[i][0].length; k++) {
                    weights[i][j][k] = Math.random();
                }
            }
        }
        biases = new double[neuronCounts.length - 1][];
        for (int i = 0; i < biases.length; i++) {
            biases[i] = new double[neuronCounts[i + 1]];
            for (int j = 0; j < biases[i].length; j++) {
                biases[i][j] = Math.random();
            }
        }
    }
}
