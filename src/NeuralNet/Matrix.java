package NeuralNet;

import java.util.Arrays;

public class Matrix {
    private double[][] contents;

    // Creates a matrix with a specified number of rows and columns.
    public Matrix(int rows, int columns) {
        if (rows < 1 || columns < 1) {
            throw new IllegalArgumentException("Invalid matrix shape.");
        }
        contents = new double[rows][columns];
    }

    // Creates a matrix with
    public Matrix(double[][] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i].length != array[0].length) {
                throw new IllegalArgumentException("Inconsistent row lengths.");
            }
        }

        contents = array;
    }

    // Returns the amount of rows in this matrix.
    public int getRows() {
        return contents.length;
    }

    // Returns the amount of columns in this matrix.
    public int getColumns() {
        return contents[0].length;
    }

    // Returns the value at a specified row and column in this matrix.
    public double getValue(int row, int column) {
        if (row < 0 || row >= getRows()) {
            throw new IllegalArgumentException("Invalid row or column.");
        }

        return contents[row][column];
    }

    public Matrix copy() {
        Matrix toReturn = new Matrix(getRows(), getColumns());
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                toReturn.setValue(contents[i][j], i, j);
            }
        }

        return toReturn;
    }

    // Sets a specified value
    public void setValue(double value, int row, int column) {
        if (row < 0 || row >= getRows() || column < 0 || column >= getColumns()) {
            throw new IllegalArgumentException("Invalid row or column.");
        }

        contents[row][column] = value;
    }

    // Returns the transposition matrix of this matrix.
    public Matrix transposition() {
        Matrix toReturn = new Matrix(getColumns(), getRows());

        for (int i = 0; i < toReturn.getRows(); i++) {
            for (int j = 0; j < toReturn.getColumns(); j++) {
                toReturn.setValue(getValue(j, i), i, j);
            }
        }

        return toReturn;
    }

    // Returns the matrix product of two matrices.
    public static Matrix product(Matrix a, Matrix b) {
        if (a.getColumns() != b.getRows()) {
            throw new IllegalArgumentException("Incompatible matrix shapes.");
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

    // Multiplies the contents of this matrix by a scalar product.
    public void scale(double scalar) {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                setValue(scalar * getValue(i, j), i, j);
            }
        }
    }

    // Returns the hadamard product of two matrices.
    public static Matrix hadamard(Matrix a, Matrix b) {
        if (a.getRows() != b.getRows() || a.getColumns() != b.getColumns()) {
            throw new IllegalArgumentException("Incompatible matrix dimensions.");
        }

        Matrix toReturn = new Matrix(a.getRows(), b.getColumns());
        for (int i = 0; i < toReturn.getRows(); i++) {
            for (int j = 0; j < toReturn.getColumns(); j++) {
                toReturn.setValue(a.getValue(i, j) * b.getValue(i, j), i, j);
            }
        }

        return toReturn;
    }

    // Returns the sum of two specified matrix.
    public static Matrix sum(Matrix a, Matrix b) {
        if (a.getRows() != b.getRows() || a.getColumns() != b.getColumns()) {
            throw new IllegalArgumentException("Incompatible matrix shapes.");
        }

        Matrix toReturn = new Matrix(a.getRows(), a.getColumns());
        for (int i = 0; i < toReturn.getRows(); i++) {
            for (int j = 0; j < toReturn.getColumns(); j++) {
                toReturn.setValue(a.getValue(i, j) + b.getValue(i, j), i, j);
            }
        }

        return toReturn;
    }

    // Returns the difference of two matrices.
    public static Matrix subtract(Matrix a, Matrix b) {
        if (a.getRows() != b.getRows() || a.getColumns() != b.getColumns()) {
            throw new IllegalArgumentException("Incompatible matrix shapes.");
        }

        Matrix toReturn = new Matrix(a.getRows(), a.getColumns());

        for (int i = 0; i < toReturn.getRows(); i++) {
            for (int j = 0; j < toReturn.getColumns(); j++) {
                toReturn.setValue(a.getValue(i, j) - b.getValue(i, j), i, j);
            }
        }

        return toReturn;
    }

    public void modify(int function) {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                switch (function) {
                    case Function.SIGMOID:
                        setValue(Function.sigmoid(getValue(i, j)), i, j);
                        break;
                    case Function.D_SIGMOID:
                        setValue(Function.dSigmoid(getValue(i, j)), i, j);
                        break;
                }
            }
        }
    }

    public String toString() {
        String toReturn = "";

        for (int i = 0; i < getRows(); i++) {
            toReturn += Arrays.toString(contents[i]);
            if (i < getRows() - 1) {
                toReturn += "\n";
            }
        }

        return toReturn;
    }
}