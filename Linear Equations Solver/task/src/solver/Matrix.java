package solver;

import java.util.Arrays;
import java.util.List;

public class Matrix {
    private static double[][] matrix;
    public static int numofrows;
    public static int numofcolumns;
    Matrix(List<List<Double>> list, int numofrows){
        numofcolumns = list.get(0).size();
        this.numofrows = numofrows;
        matrix = new double[numofrows][numofcolumns];
        formMatrix(list);
        checkSolutions();
        calculations();
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
    public static double[] getMatrixSolution() {
        double[] solution = new double[numofrows];
        for(int i = 0; i < numofrows; i++){
            solution[i] = matrix[i][numofcolumns - 1];
        }
        System.out.println("The solution is: " + Arrays.toString(solution));
        return solution;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    private void checkSolutions(){
        //check if No solns or Infinite
    }
}
