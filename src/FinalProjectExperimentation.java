import java.util.Arrays;
import java.util.Random;

public class FinalProjectExperimentation {
    Random random = new Random();

    public static void main(String[] args) {
        new FinalProjectExperimentation();
    }

    public FinalProjectExperimentation() {
        Perceptron perceptron = new Perceptron(2);

        double mTemplate = 10 * Math.random() - 5;
        double bTemplate = 10 * Math.random() - 5;
        double noise = 10 * Math.sqrt(1 + mTemplate * mTemplate); // Maximum vertical distance from template line
        int resolution = 10;

        double[] xValues = new double[10];
        double[] yValues = new double[xValues.length];

        double xLowerBound = -10;
        double xUpperBound = 10;

        double yLowerBound = Math.min(mTemplate * xLowerBound + bTemplate - noise, mTemplate * xUpperBound + bTemplate - noise);
        double yUpperBound = Math.max(mTemplate * xLowerBound + bTemplate + noise, mTemplate * xUpperBound + bTemplate + noise);

        System.out.println(mTemplate + " " + bTemplate);

        double[][] points = new double[resolution + 1][2];
        for (int i = 0; i < points.length; i++) {
            points[i][0] = xLowerBound + i * (xUpperBound - xLowerBound) / resolution;
            points[i][1] = mTemplate * points[i][0] + bTemplate + Math.random() * 2 * noise - noise;

            System.out.println("(" + points[i][0] + "," + points[i][1] + ")");
        }

//        for (int i = 0; i < 1000000; i++) {
//            Matrix inputs = new Matrix(2, 1);
//            for (int j = 0; j < 2; j++) {
//                inputs.setValue(Math.random(), j, 0);
//            }
//
//            double target = Math.signum(inputs.getValue(0, 0) - inputs.getValue(1, 0));
//            perceptron.backPropagate(inputs, target, 0.01);
//        }

        //perceptron.printData();
    }
}
