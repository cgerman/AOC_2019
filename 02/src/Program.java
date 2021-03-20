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
        List<String> l = Files.readAllLines(Paths.get("D:\\src\\practicas\\AOC_2019\\02\\src\\problem.txt"));
        int[] memory = parseProblemInput(l.get(0));

        String result = partOne(memory);
        System.out.println("Part one - solution: " + result);

        result = partTwo(memory);
        System.out.println("Part two - solution: " + result);
    }

    private static String partOne(int[] memory) throws Exception {
        int[] mem = new int[memory.length];
        System.arraycopy(memory, 0, mem, 0, memory.length);
        mem[1] = 12;
        mem[2] = 2;
        run(mem);
        return String.valueOf(mem[0]);
    }

    private static String partTwo(int[] memory) throws Exception {
        for (int noun = 0; noun < 100; noun++) {
            for (int verb = 0; verb < 100; verb++) {
                int[] mem = new int[memory.length];
                System.arraycopy(memory, 0, mem, 0, memory.length);
                mem[1] = noun;
                mem[2] = verb;
                run(mem);
                int res = mem[0];
                if (res == 19690720) {
                    return String.valueOf(100 * noun + verb);
                }
            }
        }
        return "";
    }

    private static void run(int[] memory) throws Exception {
        int iPtr = 0;
        while (memory[iPtr] != 99) {
            long op = memory[iPtr];
            int op1Address = memory[iPtr + 1];
            int op2Address = memory[iPtr + 2];
            int resultAddress = memory[iPtr + 3];
            int op1 = memory[op1Address];
            int op2 = memory[op2Address];
            if (op == 1) {
                // Suma
                memory[resultAddress] = op1 + op2;
            } else if (op == 2) {
                // multiplicacion
                memory[resultAddress] = op1 * op2;
            } else {
                // something went wrong
                throw new Exception("Unknown opCode: " + op);
            }
            iPtr += 4;
        }
    }

    private static int[] parseProblemInput(String line) {
        String[] parts = line.split(",");
        int[] mem = new int[parts.length];
        for (int i = 0; i < parts.length; i++) mem[i] = Integer.parseInt(parts[i]);
        return mem;
    }
}
