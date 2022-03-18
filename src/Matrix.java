import java.util.Arrays;

public class Matrix {
    private double[][] contents;

    public Matrix(int rows, int columns) {
        if (rows < 1 || columns < 1) {
            throw new IllegalArgumentException("Invalid matrix shape.");
        }
        contents = new double[rows][columns];
    }

    public int getRows() {
        return contents.length;
    }

    public int getColumns() {
        return contents[0].length;
    }

    public double getValue(int row, int column) {
        if (row < 0 || row >= getRows()) {
            throw new IllegalArgumentException("Invalid row or column.");
        }

        return contents[row][column];
    }

    public void setValue(double value, int row, int column) {
        if (row < 0 || row >= getRows() || column < 0 || column >= getColumns()) {
            throw new IllegalArgumentException("Invalid row or column.");
        }

        contents[row][column] = value;
    }

    public double[][] getContents() {
        return contents;
    }

    public Matrix fromArray(double[][] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i].length != array[0].length) {
                throw new IllegalArgumentException("Inconsistent row lengths.");
            }
        }

        Matrix toReturn = new Matrix(array.length, array[0].length);
        for (int i = 0; i < toReturn.getRows(); i++) {
            for (int j = 0; j < toReturn.getColumns(); j++) {
                toReturn.setValue(array[i][j], i, j);
            }
        }

        return toReturn;
    }

    public Matrix transposition() {
        Matrix toReturn = new Matrix(getColumns(), getRows());

        for (int i = 0; i < toReturn.getRows(); i++) {
            for (int j = 0; j < toReturn.getColumns(); j++) {
                toReturn.setValue(getValue(j, i), i, j);
            }
        }

        return toReturn;
    }

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

    public void multiplyBy(Matrix m) {
        if (getColumns() != m.getRows()) {
            throw new IllegalArgumentException("Incompatible matrix shapes.");
        }

        Matrix product = new Matrix(getRows(), m.getColumns());

        for (int i = 0; i < product.getRows(); i++) {
            for (int j = 0; j < product.getColumns(); j++) {
                double sum = 0;
                for (int k = 0; k < getColumns(); k++) {
                    sum += getValue(i, k) * m.getValue(k, j);
                }

                product.setValue(sum, i, j);
            }
        }

        this.contents = product.getContents();
    }

    public void multiplyBy(double scalar) {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                setValue(scalar * getValue(i, j), i, j);
            }
        }
    }

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

    public void add(Matrix toAdd) {
        if (getRows() != toAdd.getRows() || getColumns() != toAdd.getColumns()) {
            throw new IllegalArgumentException("Incompatible matrix shapes");
        }

        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                setValue(getValue(i, j) + toAdd.getValue(i, j), i, j);
            }
        }
    }

    public void subtract(Matrix toSubtract) {
        if (getRows() != toSubtract.getRows() || getColumns() != toSubtract.getColumns()) {
            throw new IllegalArgumentException("Incompatible matrix shape");
        }

        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                setValue(getValue(i, j) - toSubtract.getValue(i, j), i, j);
            }
        }
    }

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

    public static Matrix difference(Matrix a, Matrix b) {
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

    public void sigmoid() {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                setValue(1 / (1 + Math.exp(-getValue(i, j))), i, j);
            }
        }
    }

    public static Matrix sigmoid(Matrix matrix) {
        Matrix toReturn = new Matrix(matrix.getRows(), matrix.getColumns());

        for (int i = 0; i < toReturn.getRows(); i++) {
            for (int j = 0; j < toReturn.getColumns(); j++) {
                toReturn.setValue(1 / (1 + Math.exp(-matrix.getValue(i, j))), i, j);
            }
        }

        return toReturn;
    }

    public void dSigmoid() {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                setValue(getValue(i, j) * (1 - getValue(i, j)), i, j);
            }
        }
    }

    public static Matrix dSigmoid(Matrix sigmoidOutputs) {
        Matrix toReturn = new Matrix(sigmoidOutputs.getRows(), sigmoidOutputs.getColumns());

        for (int i = 0; i < toReturn.getRows(); i++) {
            for (int j = 0; j < toReturn.getColumns(); j++) {
                toReturn.setValue(sigmoidOutputs.getValue(i, j) * (1 - sigmoidOutputs.getValue(i, j)), i, j);
            }
        }

        return toReturn;
    }

    public void printContents() {
        for (int i = 0; i < getRows(); i++) {
            System.out.println(Arrays.toString(contents[i]));
        }
    }
}
