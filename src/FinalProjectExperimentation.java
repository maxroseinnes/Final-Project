public class FinalProjectExperimentation {
    public static void main(String[] args) {
        new FinalProjectExperimentation();
    }

    public FinalProjectExperimentation() {
        double m = Math.random() * 20 - 10;
        double b = Math.random() * 20 - 10;
        System.out.println("m: " + m + ", b: " + b);

        Perceptron[] agents = new Perceptron[10000];
        int[] scores = new int[agents.length];

        for (int i = 0; i < agents.length; i++) {
            agents[i] = new Perceptron(2, 0, 0);
        }

        for (int i = 0; i < agents.length; i++) {
            int score = 0;
            for (int j = 0; j < 10000; j++) {
                double[] inputs = new double[2];
                for (int k = 0; k < inputs.length; k++) {
                    inputs[k] = Math.random();
                }

                int guess = (int) agents[i].feedForward(inputs);
                int target = inputs[1] >= m * inputs[0] + b ? 1 : -1;

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

        System.out.println(agents[bestIndex].weights[0] + ", " + agents[bestIndex].weights[1] + ", " + agents[bestIndex].bias + ", " + scores[bestIndex]);
    }
}
