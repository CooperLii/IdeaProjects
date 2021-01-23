package com.company;

/**
 * Name: MingFang Li (lim)
 * Course: CSCI 242 - Computer Science II
 * Assignment: 1
 *
 * Project/Class Description:
 *  This program asks the user for a file name in order to read in the equations
 *  then put the equations into a 2D array
 *  then print the original array out
 *  then this program look for the pivot row in the array
 *  once the pivot row is found, swap it with the row on the top
 *  then check if column under consideration has a zero as the coefficient in the pivot equation
 *  choose a new pivot equations if the column is under consideration
 *  then make a copy of the pivot row for easier calculation later
 *  then do the calculation to zero the lower left side of the matrix
 *  then print out the swapped and calculated equations
 *  then do the back substitution
 *  at the end print out the output
 */

import java.io.File;

// importing scanner library
import java.util.Scanner;

// Class GaussianElimination
public class GaussianElimination {
    public void calculate (){



    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        // The try is required in case the file doesn't exist

        try {
            //  ask user for the file name
            System.out.println("File Name: ");
            String fileNam = scan.nextLine();
            File file = new File(fileNam);
            Scanner fin = new Scanner(file);

            // create a 2D array to take the numbers in the file
            int numOfCoe = fin.nextInt();
            double[][] twoDAry = new double[numOfCoe][numOfCoe + 1];

            // print out the number of coefficients
            System.out.println("Number of coefficients :" + numOfCoe + "\n ");

            // fill the array with the numbers in the file
            while (fin.hasNextDouble()) {
                for (int i = 0; i < numOfCoe; i++) {
                    for (int in = 0; in < numOfCoe + 1; in++) {
                        twoDAry[i][in] = fin.nextDouble();
                    }
                }
            }

            // print the equations out
            System.out.println("Equations:");

            for (int i = 0; i < numOfCoe; i++) {
                for (int in = 0; in < numOfCoe + 1; in++) {
                    System.out.print(twoDAry[i][in] + " ");
                }
                System.out.println();
            }
            System.out.println();

    // numOfEquations represents how many equations in the array
            int numOfEquations = twoDAry.length;

            // find the pivot row
            for (int ind = 0; ind < numOfEquations; ind++) {
                int firstCoesOfEqu = ind;
                for (int i = ind + 1; i < numOfEquations; i++) {
                    if (Math.abs(twoDAry[i][ind])
                            < Math.abs(twoDAry[firstCoesOfEqu][ind]))
                        firstCoesOfEqu = i;
                }

                // sawp rows
                double[] swapRows = twoDAry[ind];
                twoDAry[ind] = twoDAry[firstCoesOfEqu];
                twoDAry[firstCoesOfEqu] = swapRows;

                // choose a new pivot equations when the column under
                // consideration has a zero as the coefficient in the pivot equation
                for (int i = 0; i < numOfCoe; i++) {
                    if (twoDAry[0][i] == 0) {
                        if (Math.abs(twoDAry[i][ind])
                                < Math.abs(twoDAry[firstCoesOfEqu][ind]))
                            firstCoesOfEqu = i;
                        swapRows = twoDAry[ind];
                        twoDAry[ind] = twoDAry[firstCoesOfEqu];
                        twoDAry[firstCoesOfEqu] = swapRows;
                    }
                }

            }

             //make a copy of the pivot row
            double[] copyOfPivot = new double[twoDAry[0].length];
            for (int i = 0; i < twoDAry[0].length; i++)
            {
               copyOfPivot[i] = twoDAry[0][i];
             }
             //calculate
            for (int ind = 0; ind < numOfEquations; ind++) {
                for (int i = ind+1; i < numOfEquations; i++) {
                    double coeInUse = twoDAry[i][ind] / twoDAry[ind][ind];
                    System.out.println(coeInUse);
                    for (int j = ind; j < copyOfPivot.length; j++) {
                        twoDAry[i][j] = twoDAry[i][j] -twoDAry[ind][j] * coeInUse;
                    }
                }
            }

            // print calculated equations
            System.out.println("Calculated Equations:");
            for (int i = 0; i < numOfCoe; i++) {
                for (int in = 0; in < numOfCoe + 1; in++) {
                    System.out.print(twoDAry[i][in] + " ");
                }
                System.out.println();
            }

            // back substitution
            double[] output = new double[numOfEquations];
            for (int i = numOfEquations - 1; i >= 0; i--) {
                double total = 0.0;
                for (int z = i + 1; z < numOfEquations; z++) {
                    total = twoDAry[i][z] * output[z] + total;
                }
                output[i] = (twoDAry[i][numOfEquations] - total) / twoDAry[i][i];
            }

            // print the output(the value of unknowns)
            System.out.println("\nOutput : ");
            for (int numOfX = 0; numOfX < numOfEquations; numOfX++) {
                System.out.printf("%s%d%s%.2f\n", "X", numOfX, " = ", output[numOfX]);
            }
        }


        catch(Exception e)
    {
        System.err.println("Error reading file: " + e.getMessage());
    }
//public void int BackSubstitution(int numOfEquations,twoDAry[][],solutions[])
    //    {
    //   double[] answers = new double[numOfEquations];
    //  for (int i = numOfEquations - 1; i >= 0; i--)
    // {
    //    double total = 0.0;
    // for (int z = i + 1; z < numOfEquations; z++)
    //  {
    //   total = twoDAry[i][z] * answers[z] + total;
    // }
    //  answers[i]=(solutions[i]-total)/twoDAry[i][i];
    //  }
}
}






