import java.util.Arrays;
import java.util.Random;

public class Matrix {
    private static Random random = new Random();
    private double[][] contents;

    public Matrix(double[][] contents) {
        this.contents = contents;
    }

    public Matrix(int rows, int columns) {
        contents = new double[rows][columns];
    }

    public void setContentsRandomly() {
        for (int i = 0; i < contents.length; i++) {
            for (int j = 0; j < contents[0].length; j++) {
                contents[i][j] = random.nextGaussian();
            }
        }
    }

    public int getRows() {
        return contents.length;
    }

    public int getColumns() {
        return contents[0].length;
    }

    public double getValue(int row, int column) {
        return contents[row][column];
    }

    public double[][] getContents() {
        return contents;
    }

    public void setValue(double value, int row, int column) {
        contents[row][column] = value;
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        if (a.getColumns() != b.getRows()) {
            System.out.println("Matrix A must have the same number of columns as the number of rows in matrix B.");
            return null;
        }
        Matrix toReturn = new Matrix(a.getRows(), b.getColumns());

        for (int i = 0; i < toReturn.getRows(); i++) {
            for (int j = 0; j < toReturn.getColumns(); j++) {
                double sum = 0;
                for (int k = 0; k < a.getColumns(); k++) {
                    sum += a.getValue(i, k) * b.getValue(k, j);
                }

                toReturn.setValue(sum, i, j);
            }
        }

        return toReturn;
    }

    public static Matrix add(Matrix a, Matrix b) {
        if (a.getRows() != b.getRows() || a.getColumns() != b.getColumns()) {
            System.out.println("Matrix A must have the same dimensions as matrix B.");
            return null;
        }

        Matrix toReturn = new Matrix(a.getRows(), a.getColumns());
        for (int i = 0; i < toReturn.getRows(); i++) {
            for (int j = 0; j < toReturn.getColumns(); j++) {
                toReturn.setValue(a.getValue(i, j) + b.getValue(i, j), i, j);
            }
        }

        return toReturn;
    }

    public static Matrix sigmoid(Matrix input) {
        Matrix toReturn = new Matrix(input.getRows(), input.getColumns());
        for (int i = 0; i < toReturn.getRows(); i++) {
            for (int j = 0; j < toReturn.getColumns(); j++) {
                toReturn.setValue(Util.sigmoid(input.getValue(i, j)), i, j);
            }
        }

        return toReturn;
    }

    public void printContents() {
        for (int i = 0; i < contents.length; i++) {
            System.out.println(Arrays.toString(contents[i]));
        }
    }
}
