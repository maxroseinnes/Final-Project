public class Util {
    public static double map(double input, double inputLowerBound, double inputUpperBound, double outputLowerBound, double outputUpperBound) {
        return outputLowerBound + (input - inputLowerBound) / (inputUpperBound - inputLowerBound) * (outputUpperBound - outputLowerBound);
    }

    public static double sigmoid(double input) {
        return 1 / (1 + Math.exp(-input));
    }
}

