/**
 * @author MingFang Li
 * Course: CSCI 340 - DATA STRUCTURES/ALGORITHM DSGN
 * Date: 04/25/2019
 * Assignment: 5
 * Project/Class Description:
 *
 * This program asks the user to input the name of the file that
 * contains the constrains for a KenKen puzzle, and try to solve the puzzle
 * by useing the constrains from the file.
 *
 * Known bugs: Constrains for the cells are not correctly checked.
 */

// imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// class KenKen
public class KenKen {
    // a hash map take points as the key and constrains as the value
    private static HashMap<Points, Constrains> map;
    // a 2D array represents the board
    private static int[][] twoDarray;
    // board size
    private static int size;

    private static Points end;

    /**
     * The main call the method readIn to read in and store the data
     * and call the method findSolution to see if there is a solution exist.
     * if there is , print out the solution.
     * if there is not , print out no solution found.
     * @param args
     * @throws FileNotFoundException throw an exception if the file input is
     * not found
     */
    public static void main(String[] args) throws FileNotFoundException {
        KenKen obj = new KenKen();
        obj.readIn();
        Points start = new Points(0,0);

        if (obj.findSolution(start,twoDarray))
        {
            System.out.println("Solution:");
           /* for (int[] ints : twoDarray) {
                for (int anInt : ints) {
                    System.out.print(" " + anInt);
                }
                System.out.println();
            }
            System.out.println();*/
            for (int[] row : twoDarray)
                System.out.println(Arrays.toString(row));
        }
        else System.out.println("No solution found.");
    }

    /**
     * this method ask the user to input the file name
     * then read in the file and store the data from the file
     * @throws FileNotFoundException
     */
    private void readIn() throws FileNotFoundException {
        // Input and scan the file
        System.out.println();
        System.out.println("Enter the name of the file that contains the specifications for a puzzle.");
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();
        Scanner scanFile = new Scanner(new File(name));
        ArrayList<Points> box = new ArrayList<>();

        // size of the board
        size = Integer.parseInt(scanFile.nextLine());
        map = new HashMap<>();
        twoDarray = new int [size][size];
        // the size of the board shouldn't be bigger than 9
        if (size < 10) {
            while (scanFile.hasNextLine()) {
                String[]line = scanFile.nextLine().split("\\s+");
                int totalNum = Integer.parseInt(line[0]);
                String op = line[1];

                // find the corresponding points from the file
                for (int i = 2; i < line.length; i = i + 2) {
                    int number = Integer.parseInt(line[i]);
                    int number2 = Integer.parseInt(line[i + 1]);
                    Points poi = new Points(number, number2);
                    box.add(poi);
                }

                Constrains constrain = new Constrains(totalNum, op, box);
                for (Points p : constrain.boxes) {
                //    if (!map.containsKey(p))
                  //  {
                        map.put(p,constrain);
                    //}
                }
            }
        } else
            System.out.println("Size of the puzzle too big.");
    }

    /**
     * check the row and column to make sure no duplicate numbers
     * @param points is the point passing in
     * @param num is the number to check
     * @return either true or false
     */
    private boolean checkRowCol(Points points, int num) {
        for (int i = 0; i < size; i++) {
            if (twoDarray[points.row][i] == num) {
                return false;
            }
            if (twoDarray[i][points.col] == num) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method see if the board is fully filled
     * and recursively backtrack it if the constrains are not met
     * @param pointss is the point to pass in
     * @param twoDarray is the array to represent the board
     * @return true if there's a solution
     */
    private boolean findSolution(Points pointss, int[][] twoDarray) {
        if (twoDarray[size - 1][size - 1] != 0)
            return true;
        for (int i = 1; i < size + 1 ; i++) {
         //   System.out.println(i+ "    .");
            if (ifSolve(i,pointss)) {
                twoDarray[pointss.row][pointss.col] = i;
                // new index
                Points newIndex = increment(pointss);
                boolean recursive = findSolution(newIndex, twoDarray);
                if (recursive) {
                    return true;
                } else{
                    twoDarray[pointss.row][pointss.col] = 0;
                }
            }
        }
        return false;
    }

    // increment the row or column number
    private Points increment(Points pos) {
/*        Points newPoints = new Points(points.row, points.col);
        if (newPoints.col == size - 1) {
            newPoints.row++;
            newPoints.col = 0;
        } else newPoints.col++;
        return newPoints;*/

        int row = pos.row;
        int col = pos.col;

        if (col == size - 1) {
            row++;
            col = 0;
        } else {
            col++;
        }
        return new Points(row, col);
    }

    /**
     * this method see if the board is solvable
     * by checking the if the total number of the cells
     * equals to the total number it suppose to be
     * @param num is the number try to put into the cell
     * @param points is point to pass in
     * @return either true (solvable) or false (not solvable)
     */
    private boolean ifSolve(int num,Points points) {
       // Constrains constrains = null;


            Constrains constr = null;
            for (Points p: map.keySet()) {
            if (p.col == points.col && p.row == points.row)
            {
                constr = map.get(p);
            }
            }

        int zero = 0;
        int is1 = 1;
        assert constr != null;
        for (Points p : constr.boxes) {
            if (twoDarray[p.row][p.col] != 0) {
                is1++;
            } else if (twoDarray[p.row][p.col] == 1) {
                zero++;
            }
        }

       // boolean total = constr.checkTotal(num,points);
        int total = constr.checkTotal(num,points);

        if (checkRowCol(points, num)) {
          /*  return (total && is1 == constr.boxes.size()) ||
                    (constr.boxes.size() > is1) ||
                    (constr.boxes.size() < is1 &&
                            zero<=0&&constr.operators.equals("*"));*/
            // if cage has reached it's total and all listOfCagePoints are filled, return true
            if (total == constr.total && is1 == constr.boxes.size())
                return true;
                // else if cage total hasn't been reached yet but there are still empty listOfCagePoints, return true
            else if (total < constr.total && is1 < constr.boxes.size())
                return true;
                // else if cage total is more than the total it should be, and there are still empty listOfCagePoints, and
                // the cage op is - or / (ie. total will be reduced later), return true
            else if (total > constr.total && is1 < constr.boxes.size()
                    && constr.operators.equals("-")  || constr.operators.equals("/"))
                return true;
                // if all listOfCagePoints aren't filled, but it multiplies up to the total,
                // and a listOfCagePoints can still be 1, return true
            else return (total == constr.total && is1 < constr.boxes.size()
                        && zero <= 0 && constr.operators.equals("*"));
        }
        return false;
    }

    // inner class Points that has row number and column number
    private static class Points {
        private int row, col;
        private Points(int row, int col) {
            this.col = col;
            this.row = row;
        }
    }

    // inner class Constrains has the total number it supposed to be
    // the operator to use, and the corresponding cells
    private class Constrains {
        private int total;
        private String operators;
        private ArrayList<Points> boxes;

        private Constrains(int total, String operators, ArrayList<Points> boxes) {
            this.total = total;
            this.boxes = boxes;
            this.operators = operators;
        }

        /**
         * This method see which operator in which cells are required to use
         * and calculate the numbers in the cell by using that operator to see
         * if the total number in the cell equals to the number it suppose to be
         * @param num is the number to put into specific cell
        // * @param targetNum is the number it suppose to be
         * @return true if the totals are the same as the number it supposed to be
         * false if they are not the same.
         */
       // private boolean checkTotal(int num,Points points) {
            // a max heap PQ to find the largest number
          //  PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((a, b) -> b - a);

/*            int sum = 0;
            if (operators.equals("+")) {
                for (Points point : boxes) {
                    sum += twoDarray[point.row][point.col];
                }
                return (sum + num) == total;
            }

            if (operators.equals("-")) {
                int numberInplaces=0;
                for (Points p : boxes) {
                    numberInplaces = twoDarray[p.row][p.col];
                  //  priorityQueue.add(numberInplaces);
                }

           //     int largest = priorityQueue.peek();

                for (Points point : boxes) {
                        numberInplaces -= twoDarray[point.row][point.col];
                }
                return Math.abs(numberInplaces-num) == total;
            }

            if (operators.equals("*")) {
                sum = 1;
                for (Points point : boxes) {
                    if (twoDarray[point.row][point.col] != 0)
                        sum *= twoDarray[point.row][point.col];
                }
                return sum*num == total;
            }
            // a max heap PQ to find the largest number
            // PriorityQueue<Integer> priorityQueue2 = new PriorityQueue<>((a, b) -> b - a);
            ArrayList<Integer> list = new ArrayList<Integer>();

            if (operators.equals("/")) {
                int numInplace=0;
                for (Points p : boxes) {
                  //  if (twoDarray[p.row][p.col]<)
                    numInplace = twoDarray[p.row][p.col];
                    list.add(numInplace);
                }
                    Collections.sort(list);
                int largest  = list.get(1);
                        if (largest != 0&&list.get(0)!=0) {
                            int temp = largest / list.get(0);
                        return temp/num == total;
                    }
                }

            if (operators.equals("#")) {
              //  System.out.print(operators);
                // System.out.println(targetNum);
              //  System.out.println(num);
              //  Points p = boxes.get(0);
               // twoDarray[p.row][p.col] = targetNum;
              //  System.out.println(twoDarray[p.row][p.col]);

                return num == total;
            }
            return true;
        }*/
        private int checkTotal(int num,Points points) {
            int currentValue = 0;   // current total of cage = 0
            boolean maxChanged = false;     // maxChanged boolean used for subtraction and division operations

            // ADDITION:
            if (operators.equals("+")){
                // add up current value(s) of cage
                for (Points p : boxes)
                    currentValue += twoDarray[p.row][p.col];
                return currentValue + num;
            }
            // SUBTRACTION:
            else if (operators.equals("-")){
                // Get largest value and point in cage:
                int max = num;
                Points maxPoint = points;
                // search cage for larger max value
                for (Points mp : boxes){
                    if (twoDarray[mp.row][mp.col] > max){
                        max = twoDarray[mp.row][mp.col];
                        maxPoint = mp;
                        maxChanged = true;
                    }
                }
                for (Points p : boxes){
                    // subtract each point's value from max (except maxPoint's value)
                    if (p != maxPoint)
                        max -= twoDarray[p.row][p.col];
                }
                if (maxChanged)
                    return max - num;
                else
                    return max;
            }
            // MULTIPLICATION:
            else if (operators.equals("*")){
                // set current value = 1 so you don't multiple by 0
                currentValue = 1;
                // multiply up current value(s) of cage
                for (Points p : boxes){
                    // multiply by 0 would break everything, so only multiply current value if position != 0
                    if (twoDarray[p.row][p.col] != 0)
                        currentValue *= twoDarray[p.row][p.col];
                }
                return currentValue * num;
            }
            // DIVISION:
            else if (operators.equals("/")){
                // Get largest value and point in cage:
                int max = num;
                Points maxPoint = points;
                // search cage for larger max value
                for (Points mp : boxes){
                    if (twoDarray[mp.row][mp.col] > max){
                        max = twoDarray[mp.row][mp.col];
                        maxPoint = mp;
                        maxChanged = true;
                    }
                }
                for (Points p : boxes){
                    // divide each point's value from max (except maxPoint's value)
                    // also do not divide max by and position that is 0
                    if (p != maxPoint && twoDarray[p.row][p.col] != 0)
                        max /= twoDarray[p.row][p.col];
                }
                if (maxChanged)
                    return max / num;
                else
                    return max;
            }
            // FREE SPACE   (op == '#')
            else {
                // value must be equal to total in this space
                return num;
            }
    }
}
}


