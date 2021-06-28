package solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        for(int i = 0; i < args.length; i+=2) {
            if(args[i].equals("-in")) {
                importfile(args[i+1]);
            }
            else if(args[i].equals("-out")){
                exportfile(args[i+1]);
                System.out.println("Saved to file " + args[i+1]);
            }
        }
        importfile("in.txt");
        exportfile("out.txt");
    }
    private static void importfile(String filename){
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            int numofrows = 0;
            List<List<Double>> list = new ArrayList<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] Data = data.split(" ");
                if(Data.length == 1){
                    numofrows = Integer.parseInt(Data[0]);
                }else {
                    List<Double> lst = new ArrayList<>();
                    for (String datum : Data) {
                        lst.add(Double.parseDouble(datum));
                    }
                    list.add(lst);
                }
            }
            new Matrix(list, numofrows);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    private static void exportfile(String filename){
        try {
            FileWriter myWriter = new FileWriter(filename);
            double[] solution = Matrix.getMatrixSolution();

            for(int i = 0; i < solution.length; i++) {
                myWriter.write(solution[i] + "\n");
            }
            myWriter.close();
            System.out.println("Saved to file " + filename);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
