package solver;

import java.util.Arrays;

public class LinearEquation {

    public static double[][] solveLinearEquation(double[][] matrix, int numofrows){
        //first part of algorithm
        for(int i = 0; i < numofrows; i++){
            //System.out.println(Arrays.deepToString(matrix));
            Row row = new Row();
            matrix = row.rowManipulation(matrix, i);
        }
        //backSubstitution
        if(Matrix.str.equals("")) {
            for (int i = numofrows - 1; i > 0; i--) {
                //System.out.println(Arrays.deepToString(matrix));
                matrix = backSub(matrix, i);
            }
            if(Row.columnSwaps.size() != 0){
                matrix = Row.swapback(matrix);
            }
        }
        Matrix.checkSolutions(matrix);
        return matrix;
    }

    private static double[][] backSub(double[][] matrix, int i){
        double[] row = matrix[i];

        for(int a = i - 1; a >= 0; a--) {
            double[] prevrow = matrix[a];
            double[] temprow = new double[row.length];
            double coeff = -prevrow[i];
            for (int n = 0; n < row.length; n++) {
                temprow[n] = row[n] * coeff;
            }
            for (int n = 0; n < row.length; n++) {
                prevrow[n] += temprow[n];
            }
            matrix[a] = prevrow;
            System.out.println(coeff + " * R" + (i + 1) + " + R" + (a+1) + " -> R" + (a+1));
        }
        return matrix;
    }
}
