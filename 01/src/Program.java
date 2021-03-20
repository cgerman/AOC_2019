import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Project     : Advent of Code
 * Headline    : Practice programming.
 *
 * (C) 2021 Carlos German Romero
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * For more information, see see <http://www.gnu.org/licenses/>
 */

public class Program {
   public static void main(String[] args) throws Exception {
      List<String> l = Files.readAllLines(Paths.get("D:\\src\\practicas\\AOC_2019\\01\\src\\problem.txt"));

      String result = partOne(l);
      System.out.println("Part one - solution: " + result);

      result = partTwo(l);
      System.out.println("Part two - solution: " + result);
   }

   private static String partOne(List<String> l) {
      long totalFuel = 0L;
      for (String line : l) {
         long moduleMass = Long.parseLong(line);
         long fuelMass = fuelForModule1(moduleMass);
         totalFuel += fuelMass;
      }
       return String.valueOf(totalFuel);
   }

   private static String partTwo(List<String> l) {
      long totalFuel = 0L;
      for (String line : l) {
         long moduleMass = Long.parseLong(line);
         long fuelMass = fuelForModule2(moduleMass);
         totalFuel += fuelMass;
      }

      return String.valueOf(totalFuel);
   }

   private static long fuelForModule1(long mass) {
      long fuel = (mass/3)-2;
      return fuel;
   }

   private static long fuelForModule2(long mass) {
      long fuel = (mass/3)-2;
      if (fuel <=0) return 0L;

      long extra = fuelForModule2(fuel);
      return fuel + extra;
   }
}
