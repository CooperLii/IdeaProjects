
/**
 * @author MingFang Li (lim)
 * Course: CSCI 242 - Computer Science II
 * Date: 10/25/2018
 * Assignment: 3
 *
 * Project/Class Description: This project has several methods that use recurtion to solve
 *                              problems that are given.
 *
 * Known bugs: none
 */


// class Recursion
public class Recursion {

    /**
     * This method counts pairs in a string.
     * If the string is empty or the length of the string is less than 3,
     * return 0 because that means there's no pairs to count in the string.
     * If the first character and the third character in a string match,
     * return 1 and the method itself with the same string,
     * but cut off the first character until the string length is less than 3.
     * Otherwise return the method itself with the same string,
     * but cut off the first character until the string length is less than 3.
     * @param str is the string to take in
     * @return the number of pairs in the string
     */
    public static int countPairs(String str) {
        if (str.equals("") || str.length() < 3) {
            return 0;
        }
        if (str.charAt(0) == str.charAt(2))
            return 1 + countPairs(str.substring(1));
        else
            return countPairs(str.substring(1));
    }

    /**
     * This method counts how many times 1 appears in the binary form of an integer.
     * If the number is 0 just return 0 because in binary form of 0 there's no 1.
     * Otherwise take the integer and turn it into binary form and make it 32 bits
     * check how many 1s are in the 32 bits binary form of that integer from
     * right side and return the method itself with the rest of the numbers
     * @param num is the number to take in as a integer
     * @return the number of 1s appeared in the binary form of the integer
     */
    public static int countOneBits(int num) {
        if (num == 0) {
            return 0;
        } else {
            String binary = Long.toBinaryString(num & 0xffffffffL | 0x100000000L);
            binary = binary.substring(1);
            return Integer.parseInt(String.valueOf(binary.charAt(binary.length() - 1)))
                    + countOneBits(Integer.parseInt(binary.substring(0, binary.length() - 1), 2));
        }
    }

    /**
     * @param str is the string to take in
     * @return call the clean method with the string first
     * then call the method checkChar that checks the characters in the string
     */
    public static boolean isBalanced(String str) {
        return checkChar(clean(str));
    }

    /**
     * This is one of the helper methods for method isBalanced.
     * This method finds the characters in the string that are not one of "(){}[]"
     * and take them out because we only want to check (){}[]
     * @param str is the string to take in to check
     * @return the string that is cleared
     */
    public static String clean(String str) {
        String clear = str.replaceAll("[^{}()\\[\\]]", "");
        return clear;
    }

    /**
     * This is the other helper method for method isBalanced.
     * This method checks if {}()[] are balanced in a string.
     * If the length of the string is 0 return true that means the string is balanced.
     * Otherwise if the length of this string is bigger or equals to 2 check
     * if the string contains the pairs of [](){} if it does, delete them
     * until the string is empty.
     * If at the end the string is not empty that means the string is not balanced
     * return false.
     * At the end call the method itself to recursively check.
     * @param strr is the string to take in to check if it is balanced
     * @return boolean says if the string is balanced
     */
    public static boolean checkChar(String strr) {
        if (strr.length() == 0) {
            return true;
        }
        if (strr.length() >= 2) {
            if (strr.contains("()"))
                strr = strr.replaceFirst("\\(\\)", "");
            else if (strr.contains("{}"))
                strr = strr.replaceFirst("\\{\\}", "");
            else if (strr.contains("[]"))
                strr = strr.replaceFirst("\\[\\]", "");
            else return false;
        } else return false;
        return checkChar(strr);
    }

    /**
     * this method calls its helper method to see if the array is splittable
     * @param array is the array to take in to see if it is splittable
     * @return the helper method with the array and the index, partial sum1,
     * and partial sum2 start at 0
     */
    public static boolean splitArray(int[] array) {
        return helper(array, 0, 0, 0);
    }

    /**
     * This method is made to see if it is possible
     * to divide the ints in a array into two groups,
     * so that the sums of the two groups are the same.
     * This is the helper method for the method splitArray.
     * If the array is empty then return true.
     * If 2 partial sums are the same and neither of them are 0 then
     * return true.
     * Otherwise if the index is less the array length
     * return the method itself to add numbers onto two partial sums and to
     * recursively check if two partial sums are same and not 0.
     * If they are the same and neither of them are 0 return true that means
     * the array is splittable.
     * Otherwise teh array is not splittable.
     * Every int must be in one group or the other.
     * @param arr is the array with integers to take in
     * @param ind is the index in the array
     * @param partialSum is the sum of integers in the array
     * @param partialSum2 is the other sum of integers in the array
     * @return boolean to see if the array is splittable
     */
    public static boolean helper(int[] arr, int ind, int partialSum, int partialSum2) {
        if (arr.length == 0)
            return true;
        else if (partialSum == partialSum2 && partialSum != 0 && partialSum2 != 0)
            return true;
        else if (ind < arr.length)
            return helper(arr, ind + 1, partialSum + arr[ind], partialSum2)
                    || helper(arr, ind + 1, partialSum, partialSum2 + arr[ind]);
        else return false;
    }

    /**
     * This method is to check if the array is solvable.
     * In the array move the marker the number of squares
     * indicated by the integer in the square it currently occupies.
     * The marker may move either left or right along the row
     * but may not move past either end.
     * If the length of the array is empty throw an exception says nothing
     * in the array.
     * If the number at the end of the array is not 0 return false, it is not solvable.
     * If there is only on number in the array and the number is 0 then the start
     * is the end of the array that means it is solvable.
     * If the starting number is the length of the array -1 that means
     * the first step is to the end so it is solvable return true.
     * If the index is within the array length call the method itself
     * to recursively keep moving until the the marker arrived exactly at the
     * end of the array then it is solvable return true.
     * Otherwise it is not solvable return false.
     * @param start is the starting number in the array
     * @param squares is an array with integers which is the puzzle
     * @return boolean to see if the array is solvable
     */
    public static boolean solvable(int start, int squares[]) {
        if (squares.length == 0) {
            throw new ArrayIndexOutOfBoundsException("Nothing in the array.");
        }
        if (squares[squares.length - 1] != 0) { return false; }
        if (squares.length == 1 && squares[start] == 0) { return true; }
        if (start == squares.length - 1) { return true; }
        int rightIndex = start + squares[start];
        int leftIndex = start - squares[start];
        if (rightIndex < squares.length && leftIndex > 0) {
            return solvable(rightIndex, squares)
                    || solvable(leftIndex, squares);
        } else if (rightIndex < squares.length) {
            return solvable(rightIndex, squares);
        } else if (leftIndex > 0) {
            return solvable(leftIndex, squares);
        } else { return false; }
    }

    /**
     * The main to call methods to test
     */
    public static void main(String[] args) {
        //System.out.println(countPairs("axaxa"));
        //System.out.println(countOneBits(3));
        //System.out.println(isBalanced("{(})"));
        //int[] test1 = {9, 4, 5};
        int[] array = { 2,2,1,2,0};
        System.out.println(Recursion.solvable(0, array));
        //System.out.println(splitArray(test1));
    }
}