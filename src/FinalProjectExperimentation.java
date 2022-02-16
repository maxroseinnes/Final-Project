import java.util.Arrays;

public class FinalProjectExperimentation {
    public static void main(String[] args) {
        new FinalProjectExperimentation();
    }

    public FinalProjectExperimentation() {
        ThreeLayerNeuralNetwork[] networks = new ThreeLayerNeuralNetwork[100];
        for (int i = 0; i < networks.length; i++) {
           networks[i] = new ThreeLayerNeuralNetwork(2, 2, 1);
        }

        int resolutionX = 101;
        int resolutionY = 101;
        double[][] testData = new double[resolutionX * resolutionY][2];
        for (int i = 0; i < resolutionY; i++) {
            for (int j = 0; j < resolutionX; j++) {
                testData[i * resolutionX + j][0] = Util.map(j, 0, resolutionX - 1, 0, 1);
                testData[i * resolutionX + j][1] = Util.map(i, 0, resolutionY - 1, 0, 1);
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

        networks[bestIndex].printWeightsAndBiases();

        for (int i = resolutionY - 1; i >= 0; i--) {
            for (int j = 0; j < resolutionX; j++) {
                int guess = networks[bestIndex].feedForward(testData[i * resolutionX + j])[0] >= 0.5 ? 1 : -1;
                System.out.print(guess == 1 ? "@" : " ");
            }
            System.out.println();
        }

    }
}

