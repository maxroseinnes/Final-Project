public class Perceptron {
    double[] weights;
    double bias;
    double mutationChance;
    double mutationAmount;

    public Perceptron(int inputCount, double mutationChance, double mutationAmount) {
        weights = new double[inputCount];
        for (int i = 0; i < inputCount; i++) {
            weights[i] = 2 * Math.random() - 1;
        }
        this.bias = 2 * Math.random() - 1;
        this.mutationChance = mutationChance;
        this.mutationAmount = mutationAmount;
    }

    double feedForward(double[] inputs) {
        double sum = bias;
        for (int i = 0; i < weights.length; i++) {
            sum += inputs[i] * weights[i];
        }
        return sum;
    }

    Perceptron mutate() {
        Perceptron toReturn = new Perceptron(weights.length, mutationChance, mutationAmount);

        for (int i = 0; i < weights.length; i++) {
            toReturn.weights[i] = weights[i];
            if (Math.random() < mutationChance) {
                toReturn.weights[i] += (2 * Math.random() * mutationAmount) - mutationAmount;
            }
        }

        toReturn.bias = bias;
        if (Math.random() < mutationChance) {
            toReturn.bias += (2 * Math.random() * mutationAmount) - mutationAmount;
        }

        return toReturn;
    }

    double sigmoid(double input) {
        return Math.exp(input) / (Math.exp(input) + 1);
    }

    int sign(double input) {
        return input >= 0 ? 1 : -1;
    }
}
