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

    public double getRowSum(int row) {
        double toReturn = 0;
        for (int i = 0; i < contents[0].length; i++) {
            toReturn += contents[row][i];
        }

        return toReturn;
    }

    public void setValue(double value, int row, int column) {
        contents[row][column] = value;
    }

    public Matrix transcribe() {
        Matrix toReturn = new Matrix(getColumns(), getRows());

        for (int i = 0; i < toReturn.getRows(); i++) {
            for (int j = 0; j < toReturn.getColumns(); j++) {
                toReturn.setValue(getValue(j, i), i, j);
            }
        }

        return toReturn;
    }

    public Matrix scalarProduct(double scalar) {
        Matrix toReturn = new Matrix(getRows(), getColumns());

        for (int i = 0; i < toReturn.getRows(); i++) {
            for (int j = 0; j < toReturn.getColumns(); j++) {
                toReturn.setValue(scalar * getValue(i, j), i, j);
            }
        }

        return toReturn;
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

    public static Matrix hadamard(Matrix a, Matrix b) {
        if (a.getRows() != b.getRows() || a.getColumns() != b.getColumns()) {
            System.out.println("Matrices must have identical dimensions.");
            return null;
        }

        Matrix toReturn = new Matrix(a.getRows(), b.getColumns());
        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j < b.getRows(); j++) {
                toReturn.setValue(a.getValue(i, j) * b.getValue(i, j), i, j);
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

    public static Matrix subtract(Matrix a, Matrix b) {
        if (a.getRows() != b.getRows() || a.getColumns() != b.getColumns()) {
            System.out.println("Matrix A must have the same dimensions as matrix B.");
            return null;
        }

        Matrix toReturn = new Matrix(a.getRows(), a.getColumns());
        for (int i = 0; i < toReturn.getRows(); i++) {
            for (int j = 0; j < toReturn.getColumns(); j++) {
                toReturn.setValue(a.getValue(i, j) - b.getValue(i, j), i, j);
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
