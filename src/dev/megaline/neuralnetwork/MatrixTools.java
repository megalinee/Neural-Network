package dev.megaline.neuralnetwork;

import java.util.ArrayList;
import java.util.List;

class MatrixTools {
    Matrix add(Matrix mat, double scaler) {
        Matrix temp = new Matrix(mat.getRowLength(), mat.getColLength());

        for (int i = 0; i < mat.getRowLength(); i++) {
            for (int j = 0; j < mat.getColLength(); j++) {
                temp.setData(i, j, mat.getData(i, j) + scaler);
            }
        }
        return temp;
    }

    static Matrix add(Matrix mat1, Matrix mat2) {
        if (mat1.getColLength() != mat2.getColLength() || mat1.getRowLength() != mat2.getRowLength()) {
            System.out.println("Shape Mismatch");
            return null;
        }

        Matrix temp = new Matrix(mat1.getRowLength(), mat1.getColLength());

        for (int i = 0; i < mat1.getRowLength(); i++) {
            for (int j = 0; j < mat1.getColLength(); j++) {
                temp.setData(i, j, mat1.getData(i, j) + mat2.getData(i, j));
            }
        }
        return temp;
    }

    static Matrix subtract(Matrix mat1, Matrix mat2) {
        Matrix temp = new Matrix(mat1.getRowLength(), mat1.getColLength());

        for (int i = 0; i < mat1.getRowLength(); i++) {
            for (int j = 0; j < mat1.getColLength(); j++) {
                temp.setData(i, j, mat1.getData(i, j) - mat2.getData(i, j));
            }
        }
        return temp;
    }

    static Matrix transpose(Matrix mat1) {
        Matrix temp = new Matrix(mat1.getColLength(), mat1.getRowLength());

        for (int i = 0; i < mat1.getRowLength(); i++) {
            for (int j = 0; j < mat1.getColLength(); j++) {
                temp.setData(j, i, mat1.getData(i, j));
            }
        }
        return temp;
    }

    static Matrix dotProduct(Matrix mat1, Matrix mat2) {
        Matrix temp = new Matrix(mat1.getRowLength(), mat2.getColLength());

        for (int i = 0; i < temp.getRowLength(); i++) {
            for (int j = 0; j < temp.getColLength(); j++) {
                double sum = 0;
                for (int k = 0; k < mat1.getColLength(); k++) {
                    sum += mat1.getData(i, k) * mat2.getData(k, j);
                }
                temp.setData(i, j, sum);
            }
        }
        return temp;
    }

    static Matrix multiply(Matrix mat1, Matrix mat2) {
        Matrix temp = new Matrix(mat1.getRowLength(), mat1.getColLength());

        for (int i = 0; i < mat1.getRowLength(); i++) {
            for (int j = 0; j < mat1.getColLength(); j++) {
                temp.setData(i, j, mat1.getData(i, j) * mat2.getData(i, j));
            }
        }
        return temp;
    }

    static Matrix multiply(Matrix mat, double scaler) {
        Matrix temp = new Matrix(mat.getRowLength(), mat.getColLength());

        for (int i = 0; i < mat.getRowLength(); i++) {
            for (int j = 0; j < mat.getColLength(); j++) {
                temp.setData(i, j, mat.getData(i, j) * scaler);
            }
        }
        return temp;
    }

    static Matrix sigmoid(Matrix mat) {
        Matrix temp = new Matrix(mat.getRowLength(), mat.getColLength());

        for (int i = 0; i < mat.getRowLength(); i++) {
            for (int j = 0; j < mat.getColLength(); j++) {
                temp.setData(i, j, 1 / (1 + Math.exp(-mat.getData(i, j))));
            }
        }
        return temp;
    }

    static Matrix derivSigmoid(Matrix mat) {
        Matrix temp = new Matrix(mat.getRowLength(), mat.getColLength());

        for (int i = 0; i < mat.getRowLength(); i++) {
            for (int j = 0; j < mat.getColLength(); j++) {
                temp.setData(i, j, mat.getData(i, j) * (1 - mat.getData(i, j)));
            }
        }
        return temp;
    }

    static Matrix fromArray(double[] x) {
        Matrix temp = new Matrix(x.length, 1);
        for (int i = 0; i < x.length; i++)
            temp.setData(i, 0, x[i]);
        return temp;

    }

    static List<Double> toArray(Matrix mat) {
        List<Double> temp = new ArrayList<>();

        for (int i = 0; i < mat.getRowLength(); i++) {
            for (int j = 0; j < mat.getColLength(); j++) {
                temp.add(mat.getData(i, j));
            }
        }
        return temp;
    }
}
