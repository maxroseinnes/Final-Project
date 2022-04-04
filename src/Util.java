public class Util {
    public static double sigmoid(double x) {
        return 1 / Math.exp(-x);
    }

    public static double dSigmoid(double y) {
        return y * (1 - y);
    }
}
