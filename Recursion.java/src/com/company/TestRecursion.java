package com.company;
/**
 * Test harness for the Recursion project
 *
 * @author Stuart Hansen
 * @version October 2018
 */
public class TestRecursion {
    public static void main(String[] args) {
        System.out.println("Testing Count Pairs");
        System.out.println(Recursion.countPairs("axa") + " should be 1");
        System.out.println(Recursion.countPairs("axax") + " should be 2");
        System.out.println(Recursion.countPairs("axbx") + " should be 1");
        System.out.println(Recursion.countPairs("AxAxA") + " should be 3");
        System.out.println(Recursion.countPairs("abcdefg") + " should be 0");
        System.out.println(Recursion.countPairs("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa") + " should be 32");
        System.out.println();

        System.out.println("Testing Count One Bits");
        System.out.println(Recursion.countOneBits (1) + " should be 1");
        System.out.println(Recursion.countOneBits (3) + " should be 2");
        System.out.println(Recursion.countOneBits (123414) + " should be 8");
        System.out.println(Recursion.countOneBits(65536) + " should be 1");
        System.out.println(Recursion.countOneBits(65535) + " should be 16");
        System.out.println(Recursion.countOneBits( 1431655765) + " should be 16");
        System.out.println(Recursion.countOneBits(Integer.MAX_VALUE) + " should be 31");
        System.out.println();

        System.out.println("Testing Count One Bits - Extra Credit");
        System.out.println(Recursion.countOneBits(Integer.MIN_VALUE) + " should be 1");
        System.out.println(Recursion.countOneBits (-1) + " should be 32");
        System.out.println();

        System.out.println("Testing Parentheses");
        System.out.println(Recursion.isBalanced("(){}[]") + " should be true");
        System.out.println(Recursion.isBalanced("") + " should be true");
        System.out.println(Recursion.isBalanced("()") + " should be true");
        System.out.println(Recursion.isBalanced("()()") + " should be true");
        System.out.println(Recursion.isBalanced("(())") + " should be true");
        System.out.println(Recursion.isBalanced("((){})[]") + " should be true");
        System.out.println(Recursion.isBalanced("()[{}][]") + " should be true");
        System.out.println(Recursion.isBalanced("(()())((()))((()))") + " should be true");
        System.out.println(Recursion.isBalanced("(") + " should be false");
        System.out.println(Recursion.isBalanced("{(})") + " should be false");
        System.out.println((Recursion.isBalanced(")(")) + " should be false");
        System.out.println("\n");


        System.out.println("Testing Split Array");
        int [] array = {2, 3, 5};
        int [] brray = {2, 5, 3};
        int [] crray = {2, 3, 7};
        int [] drray = {};
        int [] erray = {-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5};
        int [] frray = {1};
        System.out.println(Recursion.splitArray(array) + " should be true");
        System.out.println(Recursion.splitArray(brray) + " should be true");
        System.out.println(Recursion.splitArray(drray) + " should be true");
        System.out.println(Recursion.splitArray(erray) + " should be true");
        System.out.println(Recursion.splitArray(crray) + " should be false");
        System.out.println(Recursion.splitArray(frray) + " should be false");
        System.out.println();

        System.out.println("Testing Solvable");
        int [] grray = {3,6,4,1,3,4,2,5,3,0};
        int [] hrray = {3,1,2,3,0};
        int [] irray = {0};
        System.out.println(Recursion.solvable(0, grray) + " should be true");
        System.out.println(Recursion.solvable(0, irray) + " should be true");
        System.out.println(Recursion.solvable(0, hrray) + " should be false");

    }
}
