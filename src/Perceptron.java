public class Perceptron {
    double weight1, weight2, bias;

    public Perceptron() {
        this.weight1 = 2 * Math.random() - 1;
        this.weight2 = 2 * Math.random() - 1;
        this.bias = 2 * Math.random() - 1;
    }

    double feedForward(double input1, double input2) {
        return sigmoid(input1 * weight1 + input2 * weight2 + bias);
    }

    void mutate() {

    }

    double sigmoid(double input) {
        return Math.exp(input) / (Math.exp(input) + 1);
    }
}
