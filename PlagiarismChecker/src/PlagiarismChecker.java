/**
 * @author MingFang Li
 * Course: CSCI 340 - DATA STRUCTURES/ALGORITHM DSGN
 * Date: 04/30/2019
 * Assignment: 6
 * Project/Class Description:
 * <p>
 * This program takes an array of files which
 * are java source codes and a threshold,
 * read in the files and then find the longest common sequence length
 * between each 2 files and then calculate the plagiarism score
 * between them by using the longest common sequence length,
 * and print the pairs of files that have the plagiarism score higher
 * than the threshold.
 * <p>
 * Known bugs: none
 */

// imports
import java.io.File;
import java.io.RandomAccessFile;

// class PlagiarismChecker
public class PlagiarismChecker {
    /**
     * The main calls functions to pass in the test files
     * and find the plagiarism score between files.
     * @param args
     */
    public static void main(String[] args) {
        PlagiarismChecker obj = new PlagiarismChecker();
        String player1 = "Player1.java";
        String player2 = "Player2.java";
        //String mine = "mine.txt";
       // String others = "others.txt";
       // double score = obj.plagiarismScore(mine, others);

        double score = obj.plagiarismScore(player1, player2);
        System.out.println();
        System.out.printf("%-15s%-15s%s\n", "File1", "File2", "Score");
        System.out.printf("%-15s%-15s%.2f\n", player1, player2, score);
        //System.out.printf("%-15s%-15s%.2f\n", mine, others, score);
        String[] files = new String[]{"1.txt", "2.txt", "3.txt", "4.txt", "5.txt", "6.txt", "7.txt", "8.txt", "9.txt",
                "10.txt", "11.txt", "12.txt", "13.txt", "14.txt", "15.txt", "16.txt", "17.txt", "18.txt", "19.txt",
                "20.txt", "21.txt", "22.txt", "23.txt", "24.txt", "25.txt", "26.txt", "27.txt", "28.txt", "29.txt",};
        long start = System.nanoTime();
        obj.plagiarismChecker(files, 55.0);
        long finish = System.nanoTime();
        long seconds = finish - start;
        System.out.println("It took " + (double) seconds / 1000000000.0 + " seconds to compare and calculate " + files.length + " files' plagiarism scores!");
    }

    /**
     * Read in a file building it into one long string
     *
     * @param fileName the name of the file
     * @return the contents of a file as a string
     */
    private String readFile(String fileName) {
        String contents = "";
        try {
            RandomAccessFile fin = new RandomAccessFile(new File(fileName), "r");

            int b = fin.read();
            while (b != -1) {
                contents = contents + (char) b;
                b = fin.read();
            }
        } catch (Exception e) {
            System.err.println("Trouble reading from: " + fileName);
        }
        return contents;
    }

    /**
     * lcsLength method calculate the longest common sequence
     * length of the two programs' source code.
     * @param prog1 is program1
     * @param prog2 is program2
     * @return the longest common sequence length of two programs' source code
     */
    private int lcsLength(String prog1, String prog2) {
        // turn two programs' source file into characters arrays
        char[] program1 = prog1.toCharArray();
        char[] program2 = prog2.toCharArray();
        // program 1's source code character length
        int prog1Length = program1.length;
        // program 2's source code character length
        int prog2Length = program2.length;

        // create a 2D array with only two rows
        int[][] twoDarray = new int[2][prog2Length + 1];
        // current index
        int currentBinaryIndex = 0;
        for (int i = 0; i <= prog1Length; i++) {
            // find the current binary index
            currentBinaryIndex = i & 1;
            // System.out.println(i + " " + currentBinaryIndex);
            for (int j = 0; j <= prog2Length; j++) {
                if (i == 0 || j == 0)
                    twoDarray[currentBinaryIndex][j] = 0;
                else if (i > 0 && j > 0 && program1[i - 1] == program2[j - 1]) {
                    // When filling what would be an even numbered row,
                    // assume row 1 is the previous row
                    // When filling what would be an odd numbered row,
                    // assume row 0 is the previous row
                    if (currentBinaryIndex % 2 == 0)
                        twoDarray[currentBinaryIndex][j] = twoDarray[1][j - 1] + 1;
                    else
                        twoDarray[currentBinaryIndex][j] = twoDarray[0][j - 1] + 1;
                } else
                    twoDarray[currentBinaryIndex][j] = Math.max(twoDarray[currentBinaryIndex][j - 1], twoDarray[1 - currentBinaryIndex][j]);
            }
        }
        // return the longest common sequence length of the two programs
        // last filled entry contains the longest common sequence length
        return twoDarray[currentBinaryIndex][prog2Length];
    }

    /**
     * This method call the method readFile to read in the file and then
     * call the method lcsLength to find the longest common sequence length of
     * two files, then calculate the plagiarism score between them.
     * @param filename1 is the first file name
     * @param filename2 is the second file name
     * @return the plagiarism score between the first file and the second file
     */
    private double plagiarismScore(String filename1, String filename2) {
        // read in the files
        String f1 = readFile(filename1);
        String f2 = readFile(filename2);
        // get the longest common sequence between two files
        int length = lcsLength(f1, f2);
        // calculate the plagiarism score between them
        int m = f1.toCharArray().length;
        int n = f2.toCharArray().length;
        return Math.round((200.0 * length / (m + n)) * 100) / 100.0;
    }

    /**
     * This method take an array of file names and a threshold,
     * this method call the plagiarismScore method to calculate the plagiarism score
     * between every file from the array passed in, and find the pairs of files
     * that have the plagiarism score higher than the threshold.
     * @param filenames is an array of file names
     * @param threshold is the limit
     */
    private void plagiarismChecker(String[] filenames, double threshold) {
        // print the header
        System.out.println();
        System.out.println("Threshold set at " + threshold);
        System.out.println("Here are the files that have the plagiarism scores over the threshold. ↓");
        System.out.printf("%-15s%-15s%s\n", "File1", "File2", "Score");
        // calculate the plagiarism score between every file from
        // the array passed in, and print out the pairs of files
        // that have the plagiarism score higher than the threshold
        // and their plagiarism score.
        for (int i = 0; i < filenames.length; i++) {
            for (int j = i + 1; j < filenames.length; j++) {
                if (!filenames[i].equals(filenames[j])) {
                    double score = plagiarismScore(filenames[i], filenames[j]);
                    if (score != 0 && score >= threshold) {
                        System.out.printf("%-15s%-15s%.2f\n", filenames[i], filenames[j], score);
                    }
                }
            }
        }
    }
}