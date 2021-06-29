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

    public Complex[][] rowManipulation(Complex[][] matrix, int i){
        matrix = checkCoefficient(matrix, i);
        Complex[] row = matrix[i];
        //System.out.println(Arrays.toString(row));
        if(Matrix.str.equals("")) {
            row = simplify(row, i); //simplfies the row
            matrix = rowEchelon(matrix, row, i);
            //Matrix.checkSolutions(matrix);
        }
        return matrix;
    }

    private Complex[] rowDivision(Complex[] row, Complex coeff){
        for(int i = 0; i < row.length; i++){
            if(!Complex.Compare(row[i], 0)) {
                //System.out.println(row[i] + " " + coeff);
                row[i] = Complex.division(row[i], coeff);
            }
        }
        return row;
    }

    private Complex[] simplify(Complex[] row, int i){
        if(!Complex.Compare(row[i], 1) && !Complex.Compare(row[i], 0)){
            Complex coeff = row[i];
            row = rowDivision(row, coeff);
            System.out.println("R" + (i+1) + " / " + coeff + " -> R" + (i+1));
        }
        return row;
    }

    private Complex[][] rowEchelon(Complex[][] matrix, Complex[] row, int i){

        for(int a = i + 1; a < matrix.length; a++) {
            Complex[] nextrow = matrix[a];
            //System.out.println(Arrays.toString(nextrow));
            if(!Complex.Compare(nextrow[i], 0)) {
                Complex[] temprow = new Complex[row.length];
                Complex coeff = Complex.Multiplication(-1, nextrow[i]);
                //System.out.println(-1 + " " + nextrow[i]);
                //System.out.println(Arrays.toString(coeff.getComplex()));
                for (int n = 0; n < row.length; n++) {
                    temprow[n] = Complex.Multiplication(row[n], coeff);
                }
                for (int n = 0; n < row.length; n++) {
                    //System.out.println("For " + n + " place in row: " + nextrow[n] + " + " + temprow[n]);
                    nextrow[n] = Complex.Addition(nextrow[n], temprow[n]);
                    //System.out.println(Arrays.toString(nextrow));
                }
                matrix[a] = nextrow;
                //System.out.println(Arrays.toString(nextrow));
                System.out.println(coeff + " * R" + (i + 1) + " + R" + (a + 1) + " -> R" + (a + 1));
            }
        }
        return matrix;
    }
    private Complex[][] checkCoefficient(Complex[][] matrix, int i){

        //row i, elem i != 0 else swap
        matrix = swaprow(matrix, i);
        //if down row, all = 0, swap with next right column (entire column is swapped)
        matrix = swapcol(matrix, i);
        //if down row 0, right column = 0, find first non-zero elem in bottom, swap
        if(Complex.Compare(matrix[i][i], 0)){
            Complex[][] tempmatrix = matrix;
            int a = i;
            int b = i;
            for(; a < numofrows; a++){
                for(; b < numofcolumns; b++){
                    if(!Complex.Compare(matrix[a][b], 0)){
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
                LinearEquation.printarr(matrix);
            }else{
                Matrix.checkSolutions(matrix);
            }
        }

        //column swaps must swap back in the end
        //if no such element, end first part of algo -> Matrix.checkSolutions();
        //proceed if returned ""

        return matrix;
    }
    private Complex[][] swaprow(Complex[][] matrix, int i){
        Complex[] row = matrix[i];
        if(Complex.Compare(matrix[i][i], 0)){
            int x = i;
            for(; x < numofrows; x++){
                if(!Complex.Compare(matrix[x][i], 0)){ //first that is not 0
                    //System.out.println(matrix[x][i].real + " broke");
                    break;
                }
            }
            //System.out.println(x + " " + numofrows);
            //swap
            if(x != numofrows) {
                matrix[i] = matrix[x];
                matrix[x] = row;
                System.out.println("R" + (i + 1) + " <-> R" + (x + 1));
                //System.out.println(Arrays.deepToString(matrix));
            }
        }
        //LinearEquation.printarr(matrix);
        return matrix;
    }
    private Complex[][] swapcol(Complex[][] matrix, int i){
        if(Complex.Compare(matrix[i][i], 0)){
            Complex[][] tempmatrix = matrix;
            int x = i;
            for(; x < numofcolumns - 1; x++){
                if(!Complex.Compare(matrix[i][x], 0)){
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
        //LinearEquation.printarr(matrix);
        return matrix;
    }

    public static Complex[][] swapback(Complex[][] matrix){
        Complex[][] tempmatrix = matrix;
        for(int i = 0; i < columnSwaps.size(); i++) {
            int x = columnSwaps.get(i)[0];
            int y = columnSwaps.get(i)[1];
            for(int a = 0; a < numofrows; a++) {
                matrix[a][y] = matrix[a][x];
                matrix[a][x] = tempmatrix[a][y];
                System.out.println("C" + (y + 1) + " <-> C" + (x + 1));
            }
        }
        //LinearEquation.printarr(matrix);
        return matrix;
    }
}
