// Arup Guha
// 2/10/2014
// Solution to 2009 UCF Locals Problem: Ken Ken

// This solution is klunky, but I can't think of how to design it cleanly. I want a board, and I need to
// keep track of group information.

import java.util.*;

public class kenken {

    // Common stuff for a puzzle.
    public static int n;
    public static int numG;
    public static group[] myGroups;
    public static int[] sizes;
    public static String board;

    public static void main(String[] args) {

        Scanner stdin = new Scanner(System.in);
        n = stdin.nextInt();
        int loop = 1;

        // Go through each puzzle.
        while (n != 0) {

            numG = stdin.nextInt();
            myGroups = new group[4];
            sizes = new int[4];

            // Store board letters in one string.
            board = stdin.next();
            for (int i=0; i<n-1; i++)
                board = board + stdin.next();

            // Get group sizes.
            for (int i=0; i<board.length(); i++)
                sizes[map(board.charAt(i))]++;

            // Set up groups.
            for (int i=0; i<numG; i++) {
                int grNum = map(stdin.next().charAt(0));
                int value = stdin.nextInt();
                char op = stdin.next().charAt(0);
                myGroups[grNum] = new group(grNum,sizes[grNum],value,op);
            }

            // Add positions to each group.
            for (int i=0; i<board.length(); i++) {
                int grNum = map(board.charAt(i));
                myGroups[grNum].addToEnd(i);
            }

            // Set up the puzzle.
            int[] puzzle = new int[n*n];
            boolean canSolve = solveRec(puzzle, 0);

            // Solve it.
            System.out.println("KenKen Puzzle #"+loop+":");
            for (int i=0; i<n; i++) {
                for (int j=0; j<n; j++)
                    System.out.print(puzzle[n*i+j]);
                System.out.println();
            }
            System.out.println();

            // Get next puzzle.
            n = stdin.nextInt();
            loop++;
        }


    }

    public static boolean solveRec(int[] puzzle, int k) {

        // It's done!!!
        if (k == puzzle.length) return true;

        // Try each number in slot k.
        for (int i=1; i<=n; i++) {

            // See if it's possible.
            if (possible(puzzle, k, i)) {

                // Place the number in the square and group and solve recursively.
                int gNum = map(board.charAt(k));
                myGroups[gNum].add(i, n);
                puzzle[k] = i;
                boolean tmp = solveRec(puzzle, k+1);
                if (tmp) return true;
                puzzle[k] = 0;
                myGroups[gNum].del(i, n, puzzle);
            }
        }

        // If we get here, no solution worked.
        return false;
    }

    public static boolean possible(int[] puzzle, int slot, int value) {

        // First check row column rules.
        if (rowContains(puzzle, n, slot/n, value)) return false;
        if (colContains(puzzle, n, slot%n, value)) return false;

        // Group rules are final check.
        int gNum = map(board.charAt(slot));
        return myGroups[gNum].canAdd(value, n);
    }

    // Returns true iff row in puzzle contains value.
    public static boolean rowContains(int[] puzzle, int n, int row, int value) {
        for (int i=n*row; i<n*(row+1); i++)
            if (puzzle[i] == value)
                return true;
        return false;
    }

    // Returns true iff col in puzzle contains value.
    public static boolean colContains(int[] puzzle, int n, int col, int value) {
        for (int i=col; i<n*n; i+=n)
            if (puzzle[i] == value)
                return true;
        return false;
    }

    // Maps a character to a group number.
    public static int map(char c) {
        if (c >= 'a') return c - 'a';
        else          return c - 'A' + 26;
    }
}

class group {

    public int ID;
    public int size;
    public int value;
    public char op;
    public LinkedList<Integer> spots;
    public int curValue;
    public int empty;

    // Everything we keep track of in a group.
    public group(int myID, int mySize, int myValue, char myOp) {
        ID = myID;
        size = mySize;
        value = myValue;
        op = myOp;
        spots = new LinkedList<Integer>();
        empty = size;
        curValue = 1;
        if (op == '+' || op == '-') curValue = 0;
    }

    // Add a spot.
    public void addToEnd(int item) {
        spots.addLast(item);
    }

    public boolean canAdd(int newVal, int n) {

        // We can add it if it doesn't exceed the total, or equals it at the end.
        if (op == '+') {
            if (empty > 1) return newVal+curValue < value;
            else return newVal+curValue == value;
        }

        // We can subtract if the first value keeps everything in bounds or the second matches the answer.
        else if (op == '-') {
            if (empty == 2) return n-newVal >= value || newVal > value;
            else return Math.abs(curValue-newVal) == value;
        }

        // We can multiply this one if it maintains divisibility or matches our answer.
        else if (op == '*') {
            if (empty > 1) return value%(curValue*newVal) == 0;
            else return curValue*newVal == value;
        }

        // Similar to subtraction.
        else if (op == '/') {
            if (empty == 2) return newVal*value <= n || newVal%value == 0;
            else {
                return Math.max(curValue, newVal)%Math.min(curValue, newVal) == 0 &&
                        Math.max(curValue, newVal)/Math.min(curValue, newVal) == value;
            }
        }

        // Has to match perfectly.
        else {
            return newVal == value;
        }
    }

    // Add newVal to this group.
    public void add(int newVal, int n) {

        // One fewer slot.
        empty--;

        // Add it.
        if (op == '+') {
            curValue += newVal;
        }

        // Update subtration or division value, knowing it's value.
        else if (op == '-' || op == '/') {
            if (empty == 1) curValue = newVal;
            else curValue = value;
        }

        // Multiply it.
        else if (op == '*') {
            curValue *= newVal;
        }

        // For consistency.
        else {
            curValue = value;
        }
    }

    // Remove newVal from this group.
    public void del(int newVal, int n, int[] puzzle) {

        // One more empty slot.
        empty++;

        // Subtract it out.
        if (op == '+') {
            curValue -= newVal;
        }

        // Reset to initial or previous value.
        else if (op == '-') {
            if (empty == 2) curValue = 0;
            else if (empty == 1) curValue = puzzle[spots.get(0)];
        }

        // Divide it out.
        else if (op == '*') {
            curValue /= newVal;
        }

        // Reset to initial or previous value.
        else if (op == '/') {
            if (empty == 2) curValue = 1;
            else if (empty == 1) curValue = puzzle[spots.get(0)];
        }
    }
}
