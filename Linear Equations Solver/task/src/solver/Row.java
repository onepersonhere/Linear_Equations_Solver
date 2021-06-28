package solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Row {
    public static List<int[]> columnSwaps = new ArrayList<>();
    private static int numofrows = Matrix.numofrows;
    private static int numofcolumns = Matrix.numofcolumns;
    public Row(){
        //a list to hold all column swaps
    }

    public double[][] rowManipulation(double[][] matrix, int i){
        matrix = checkCoefficient(matrix, i);
        double[] row = matrix[i];
        //System.out.println(Arrays.toString(row));
        if(Matrix.str.equals("")) {
            row = simplify(row, i); //simplfies the row
            matrix = rowEchelon(matrix, row, i);
            //Matrix.checkSolutions(matrix);
        }
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
            if(nextrow[i] != 0) {
                double[] temprow = new double[row.length];
                double coeff = -nextrow[i];
                for (int n = 0; n < row.length; n++) {
                    temprow[n] = row[n] * coeff;
                }
                for (int n = 0; n < row.length; n++) {
                    nextrow[n] += temprow[n];
                }
                matrix[a] = nextrow;
                //System.out.println(Arrays.toString(nextrow));
                System.out.println(coeff + " * R" + (i + 1) + " + R" + (a + 1) + " -> R" + (a + 1));
            }
        }
        return matrix;
    }
    private double[][] checkCoefficient(double[][] matrix, int i){

        //row i, elem i != 0 else swap
        matrix = swaprow(matrix, i);
        //if down row, all = 0, swap with next right column (entire column is swapped)
        matrix = swapcol(matrix, i);
        //if down row 0, right column = 0, find first non-zero elem in bottom, swap
        if(matrix[i][i] == 0){
            double[][] tempmatrix = matrix;
            int a = i;
            int b = i;
            for(; a < numofrows; a++){
                for(; b < numofcolumns; b++){
                    if(matrix[a][b] != 0){
                        break;
                    }
                }
            }
            if(a != numofrows){
                //swaprow
                matrix[i] = matrix[a];
                matrix[a] = tempmatrix[i];
                System.out.println("R" + (i + 1) + " <-> R" + (a + 1));
                //swapcol
                for(int c = 0; c < numofrows; c++) {
                    matrix[c][i] = matrix[c][b];
                    matrix[c][b] = tempmatrix[c][i];
                }
                columnSwaps.add(new int[]{i,b});
                System.out.println("C" + (i + 1) + " <-> C" + (b + 1));
            }else{
                Matrix.checkSolutions(matrix);
            }
        }

        //column swaps must swap back in the end
        //if no such element, end first part of algo -> Matrix.checkSolutions();
        //proceed if returned ""

        return matrix;
    }
    private double[][] swaprow(double[][] matrix, int i){
        double[] row = matrix[i];
        if(matrix[i][i] == 0){
            int x = i;
            for(; x < numofrows; x++){
                if(matrix[x][i] != 0){
                    break;
                }
            }
            //swap
            if(x != numofrows) {
                matrix[i] = matrix[x];
                matrix[x] = row;
                System.out.println("R" + (i + 1) + " <-> R" + (x + 1));
                //System.out.println(Arrays.deepToString(matrix));
            }
        }

        return matrix;
    }
    private double[][] swapcol(double[][] matrix, int i){
        if(matrix[i][i] == 0){
            double[][] tempmatrix = matrix;
            int x = i;
            for(; x < numofcolumns - 1; x++){
                if(matrix[i][x] != 0){
                    break;
                }
            }
            //swap the entire column
            if(x != numofcolumns - 1) {
                for(int a = 0; a < numofrows; a++) {
                    matrix[a][i] = matrix[a][x];
                    matrix[a][x] = tempmatrix[a][i];
                }
                columnSwaps.add(new int[]{i,x});
                System.out.println("C" + (i + 1) + " <-> C" + (x + 1));
            }
        }
        return matrix;
    }

    public static double[][] swapback(double[][] matrix){
        double[][] tempmatrix = matrix;
        for(int i = 0; i < columnSwaps.size(); i++) {
            int x = columnSwaps.get(i)[0];
            int y = columnSwaps.get(i)[1];
            for(int a = 0; a < numofrows; a++) {
                matrix[a][y] = matrix[a][x];
                matrix[a][x] = tempmatrix[a][y];
                System.out.println("C" + (y + 1) + " <-> C" + (x + 1));
            }
        }
        return matrix;
    }
}
