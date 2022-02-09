public class FinalProjectExperimentation {
    public static void main(String[] args) {
        new FinalProjectExperimentation();
    }

    public FinalProjectExperimentation() {
        Perceptron[] agents = new Perceptron[1000000];
        int[] scores = new int[agents.length];

        for (int i = 0; i < agents.length; i++) {
            agents[i] = new Perceptron(2, 0, 0);
        }

        for (int i = 0; i < agents.length; i++) {
            int score = 0;
            for (int j = 0; j < 100; j++) {
                double[] inputs = new double[2];
                for (int k = 0; k < inputs.length; k++) {
                    inputs[k] = Math.random();
                }
                int guess = (int) agents[i].feedForward(inputs);
                int target = inputs[0] >= inputs[1] ? 1 : -1;

                if (guess == target) {
                    score++;
                }
            }

            scores[i] = score;
        }

        int bestIndex = 0;
        for (int i = 1; i < scores.length; i++) {
            if (scores[i] > scores[bestIndex]) {
                bestIndex = i;
            }
        }
    }
}
