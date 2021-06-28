package solver;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static java.lang.Math.round;

public class Matrix {
    public static double[][] matrix;
    public static int numofrows;
    public static int numofcolumns;
    public static String str = "";
    Matrix(List<List<Double>> list, int numofrows, int numofcolumns){
        Matrix.numofrows = numofrows;
        Matrix.numofcolumns = numofcolumns;
        matrix = new double[numofrows][numofcolumns];
        formMatrix(list);

        //checkSolutions(matrix);
        //if(str.equals("")) {
            calculations();
        //}
        //System.out.println(Arrays.deepToString(matrix));
    }
    private void formMatrix(List<List<Double>> list){
        for(int i = 0; i < numofrows; i++){
            for (int j = 0; j < numofcolumns; j++){
                matrix[i][j] = list.get(i).get(j);
            }
        }
    }
    private void calculations(){
        System.out.println("Start solving the equation");
        matrix = LinearEquation.solveLinearEquation(matrix, numofrows);
    }
    private static double[] MatrixSoln(){
        double[] solution = new double[numofcolumns - 1];
        for(int i = 0; i < numofrows; i++){
            for(int j = 0; j < numofcolumns -1; j++){
                if(round(matrix[i][j]) == 1){
                    solution[j] = matrix[i][numofcolumns - 1];
                }
            }
        }
        System.out.println("The solution is: " + Arrays.toString(solution));
        return solution;
    }
    public static String[] getMatrixSolution() {
        if(str.equals("")){
            return Arrays.stream(MatrixSoln()).mapToObj(String::valueOf).toArray(String[]::new);
        }
        return new String[]{str};
    }

    public void setMatrix(double[][] matrix) {
        Matrix.matrix = matrix;
    }

    public static void checkSolutions(double[][] matrix){
        //check if No solns or Infinite
        //amount of variables & amt of equations don't need to be equal
        //significant eqns = non zero rows in linear system
        //significant vars = number of all columns in the linear system
        //determine if the linear system has an infinite amount of solutions or a single.

        //variants:
        //number of significant equations = the number of significant variables.
        //number of significant equations < the number of significant variables.
        //(infinite number of solutions.)

        int numofsigeqns = 0;
        double[] zeroarr = new double[numofcolumns];
        Arrays.fill(zeroarr, 0);
        for(int i = 0; i < numofrows; i++){
            //System.out.println(Arrays.equals(matrix[i], zeroarr));
            if(!Arrays.equals(matrix[i], zeroarr)){
                numofsigeqns++;
            }
        }

        if(numofsigeqns < numofcolumns - 1){
            System.out.println(numofsigeqns);
            System.out.println("Infinitely many solutions");
            str = "Infinitely many solutions";
        }

        //if(numofrows < numofcolumns - 1){
        //    System.out.println("No solutions");
        //    str = "No solutions";
        //}
        for(double[] rows : matrix){
            int numofzeroes = 0;
            for(int i = 0; i < rows.length; i++){
                if(rows[i] == 0){
                    numofzeroes++;
                }
            }
            if(numofzeroes == rows.length - 1 && rows[rows.length - 1] != 0){
                System.out.println("No solutions");
                str = "No solutions";
                break;
            }
        }

    }
}
