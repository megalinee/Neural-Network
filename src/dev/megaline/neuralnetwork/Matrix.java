package dev.megaline.neuralnetwork;

class Matrix {
    private int rows;
    private int cols;
    private double[][] data;

    Matrix(int rows, int cols) {
        this.data = new double[rows][cols];
        this.rows = rows;
        this.cols = cols;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = Math.random() * 2 - 1;
            }
        }
    }

    double getData(int row, int col) {
        return data[row][col];
    }

    void setData(int row, int col, double data) {
        this.data[row][col] = data;
    }

    int getRowLength() {
        return rows;
    }

    int getColLength() {
        return cols;
    }

}
