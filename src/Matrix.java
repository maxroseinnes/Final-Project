public class Matrix {
    double[][] contents;

    public Matrix(double[][] contents) {
        this.contents = contents;
    }

    public Matrix(int rows, int columns, boolean initializeRandomly) {
        contents = new double[rows][columns];
        if (initializeRandomly) {
            for (int i = 0; i < contents.length; i++) {
                for (int j = 0; j < contents[0].length; j++) {
                    contents[i][j] = Util.map(Math.random(), 0, 1, -1, 1);
                }
            }
        }
    }

    public double getValue(int row, int column) {
        return contents[row][column];
    }

    public double setValue(double value, int row, int column) {
        contents[row][column] = value;
    }

    public int getRows() {
        return contents.length;
    }

    public int getColumns() {
        return contents[0].length;
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        if (a.getColumns() != b.getRows()) {
            System.out.println("Matrix 1 must have the same number of columns as the number of rows in matrix 2.");
            return null;
        }
        Matrix toReturn = new Matrix(a.getRows(), b.getColumns(), false);

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

    }
}
