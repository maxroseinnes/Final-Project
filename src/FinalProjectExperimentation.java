import java.util.Arrays;

public class FinalProjectExperimentation {
    public static void main(String[] args) {
        new FinalProjectExperimentation();
    }

    public FinalProjectExperimentation() {
        ThreeLayerNeuralNetwork[] networks = new ThreeLayerNeuralNetwork[100];
        for (int i = 0; i < networks.length; i++) {
           networks[i] = new ThreeLayerNeuralNetwork(2, 3, 1);
        }

        double[][] testData = new double[1000][networks[0].inputLayerNeurons];
        for (int i = 0; i < testData.length; i++) {
            for (int j = 0; j < testData[0].length; j++) {
                testData[i][j] = 2 * Math.random() - 1;
            }
        }

        int[] scores = new int[networks.length];
        for (int i = 0; i < networks.length; i++) {
            for (int j = 0; j < testData.length; j++) {
                int guess = networks[i].feedForward(testData[j])[0] >= 0.5 ? 1 : -1;
                int target = Math.pow(testData[j][0], 2) + Math.pow(testData[j][1], 2) <= 1 ? 1 : -1;

                if (guess == target) {
                    scores[i]++;
                }
            }
        }

        int bestIndex = 1;
        for (int i = 1; i < scores.length; i++) {
            if (scores[i] > scores[bestIndex]) {
                bestIndex = i;
            }
        }

        ThreeLayerNeuralNetwork bestNetwork = networks[bestIndex];
        int resolutionX = 100;
        int resolutionY = 100;
        int[][] bestNetworkTestData = new int[resolutionX][resolutionY];
        for (int i = 0; i <= resolutionX; i++) {
            for (int j = 0; j <= resolutionY; j++) {
                double x = 0.02 * i - 1;
                double y = 0.02 * j - 1;

                bestNetworkTestData[i][j] = bestNetwork.feedForward(new double[] {x, y})[0] >= 0.5 ? 1 : -1;
            }
        }
    }
}

