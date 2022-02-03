public class FinalProjectExperimentation {
    public static void main(String[] args) {
        new FinalProjectExperimentation();
    }

    public FinalProjectExperimentation() {
        NeuralNetwork neuralNetwork = new NeuralNetwork(new int[] {3, 4, 2});
        int totalWeights = 0;
        for (int i = 0; i < neuralNetwork.weights.length; i++) {
            totalWeights += neuralNetwork.weights[i].length * neuralNetwork.weights[i][0].length;
        }
        System.out.println(totalWeights);
    }
}
