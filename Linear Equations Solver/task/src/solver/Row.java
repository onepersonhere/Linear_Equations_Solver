package solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Row {
    private List<Integer> columnSwaps = new ArrayList<>();
    public Row(){
        //a list to hold all column swaps
    }

    public double[][] rowManipulation(double[][] matrix, int i){
        double[] row = matrix[i];
        matrix = checkCoefficient(matrix, i);
        //System.out.println(Arrays.toString(row));
        row = simplify(row, i); //simplfies the row
        matrix = rowEchelon(matrix, row, i);
        return matrix;
    }

    private double[] rowMultiplication(double[] row, double coeff){
        for(int i = 0; i < row.length; i++){
            if(row[i] != 0) {
                row[i] *= coeff;
            }
        }
        return row;
    }

    private double[] simplify(double[] row, int i){
        if(row[i] != 1 && row[i] != 0){
            double coeff = 1/row[i];
            row = rowMultiplication(row, coeff);
            System.out.println(coeff + " * R" + (i+1) + " -> R" + (i+1));
        }
        return row;
    }

    private double[][] rowEchelon(double[][] matrix, double[] row, int i){

        for(int a = i + 1; a < matrix.length; a++) {
            double[] nextrow = matrix[a];
            //System.out.println(Arrays.toString(nextrow));
            if (row[i] != nextrow[i]) {
                double[] temprow = new double[row.length];
                double coeff = -nextrow[i];
                for (int n = 0; n < row.length; n++) {
                    temprow[n] = row[n] * coeff;
                }
                for (int n = 0; n < row.length; n++) {
                    nextrow[n] += temprow[n];
                }
                matrix[a] = nextrow;
                System.out.println(coeff + " * R" + (i + 1) + " + R" + (a+1) + " -> R" + (a+1));
            }
        }
        return matrix;
    }
    private double[][] checkCoefficient(double[][] matrix, int i){
        //row 0, elem 0 != 0 else swap
        //row 1, elem 1 != 0
        //if down row, all = 0, swap with next right column (entire column is swapped)
        //if down row 0, right column = 0, find first non-zero elem in bottom, swap
        //column swaps must swap back in the end
        //if no such element, end first part of algo
        //check if no solutions
        //amount of variables & amt of equations don't need to be equal
        //significant eqns = non zero rows in linear system
        //significant vars = number of all columns in the linear system
        //determine if the linear system has an infinite amount of solutions or a single.
        //variants:
        //number of significant equations = the number of significant variables.
        //number of significant equations < the number of significant variables.
        //(infinite number of solutions.)
        //here can't be a case in which the number of significant equations is greater than the number of significant variables
        //because in this case there would be a contradiction
        //we've handled in the previous steps of the algorithm.
    }
}
