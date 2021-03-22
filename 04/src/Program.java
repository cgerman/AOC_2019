import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Project     : Advent of Code
 * Headline    : Practice programming.
 * <p>
 * (C) 2021 Carlos German Romero
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * For more information, see see <http://www.gnu.org/licenses/>
 */

public class Program {
    public static void main(String[] args) throws Exception {
        List<String> l = Files.readAllLines(Paths.get("D:\\src\\practicas\\AOC_2019\\04\\src\\problem.txt"));

        String[] parts = l.get(0).split("-");
        int[] range = new int[2];
        range[0] = Integer.parseInt(parts[0]);
        range[1] = Integer.parseInt(parts[1]);


        String result = partOne(range);
        System.out.println("Part one - solution: " + result);

        result = partTwo(range);
        System.out.println("Part two - solution: " + result);
    }

    private static String partOne(int[] range) throws Exception {
        int nMatches = 0;
        for (int i = range[0]; i <= range[1]; i++) {
            if (meetsCriteria1(i)) {
                nMatches++;
            }
        }
        return String.valueOf(nMatches);
    }

    private static String partTwo(int[] range) throws Exception {
        int nMatches = 0;
        for (int i = range[0]; i <= range[1]; i++) {
            if (meetsCriteria2(i)) {
                nMatches++;
            }
        }
        return String.valueOf(nMatches);
    }

    private static boolean meetsCriteria1(int number) {
        String sNumber = String.valueOf(number);
        boolean bConsecutiveEqualDigits = false;
        boolean bNotDecreasing = true;

        char prevChar = '-';
        for (int i = 0; i < sNumber.length(); i++) {
            char c = sNumber.charAt(i);
            if (!bConsecutiveEqualDigits && c == prevChar) {
                bConsecutiveEqualDigits = true;
            }
            if (bNotDecreasing && prevChar > c) {
                bNotDecreasing = false;
            }
            prevChar = c;
        }
        return bConsecutiveEqualDigits && bNotDecreasing;
    }

   private static boolean meetsCriteria2(int number) {
        String sNumber = String.valueOf(number);
        boolean bNotDecreasing = true;

        char prevChar = '-';
        for (int i = 0; i < sNumber.length(); i++) {
            char c = sNumber.charAt(i);
            if (bNotDecreasing && prevChar > c) {
                bNotDecreasing = false;
            }
            prevChar = c;
        }
        if (!bNotDecreasing) return false;

        int i = 0;
        while (i < sNumber.length()) {
            int nConsecutiveEqualDigits = countConsecutiveEqualDigits(sNumber, i);
            if (nConsecutiveEqualDigits == 2) {
                return true;
            }
            i += nConsecutiveEqualDigits;
        }
        return false;
    }

    private static int countConsecutiveEqualDigits(String sNumber, int from) {
        int nConsecutiveEqualDigits = 1;
        char c = sNumber.charAt(from);
        for (int i = from + 1; i < sNumber.length(); i++) {
            if (sNumber.charAt(i) == c) {
                nConsecutiveEqualDigits++;
            } else {
                break;
            }
        }
        return nConsecutiveEqualDigits;
    }

}
