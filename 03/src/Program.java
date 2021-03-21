import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
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
        List<String> l = Files.readAllLines(Paths.get("D:\\src\\practicas\\AOC_2019\\03\\src\\problem.txt"));

        List<Segment> path1 = parsePath(l.get(0).split(","));
        List<Segment> path2 = parsePath(l.get(1).split(","));


        String result = partOne(path1, path2);
        System.out.println("Part one - solution: " + result);

        result = partTwo(path1, path2);
        System.out.println("Part two - solution: " + result);
    }

    private static String partOne(List<Segment> path1, List<Segment> path2) throws Exception {
        int minDistance = Integer.MAX_VALUE;
        for (Segment seg1 : path1) {
            for (Segment seg2 : path2) {
                int[] crossPoint = seg1.crosses(seg2);
                if (crossPoint != null) {
                    int distance = manhattanDistance(crossPoint);
                    if (distance < minDistance) {
                        minDistance = distance;
                    }
                }
            }
        }
        return String.valueOf(minDistance);
    }

    private static String partTwo(List<Segment> path1, List<Segment> path2) throws Exception {
        int minSteps = Integer.MAX_VALUE;
        for (Segment seg1 : path1) {
            for (Segment seg2 : path2) {
                int[] crossPoint = seg1.crosses(seg2);
                if (crossPoint != null) {
                    int steps = countSteps(seg1, seg2, crossPoint, path1, path2);
                    if (steps < minSteps) {
                        minSteps = steps;
                    }
                }
            }
        }
        return String.valueOf(minSteps);
    }

    private static List<Segment> parsePath(String[] path) {
        Segment prevSegment = null;
        List<Segment> result = new ArrayList<>();
        for (String s : path) {
            Segment seg = new Segment(s, prevSegment);
            result.add(seg);
            prevSegment = seg;
        }
        return result;
    }

    private static int manhattanDistance(int[] point) {
        return Math.abs(point[0]) + Math.abs(point[1]);
    }

    private static int countSteps(Segment seg1, Segment seg2, int[] point, List<Segment> path1, List<Segment> path2) {
        int steps = 0;
        int i = 0;
        Segment s1 = path1.get(i);
        while (!s1.equals(seg1)) {
            steps += s1.length;
            s1 = path1.get(++i);
        }
        steps += seg1.steps(point);
        i = 0;
        Segment s2 = path2.get(i);
        while (!s2.equals(seg2)) {
            steps += s2.length;
            s2 = path2.get(++i);
        }
        steps += seg2.steps(point);
        return steps;
    }

    /////////////////////////

    private static class Segment {
        char direction;
        int length;
        int from_x;
        int from_y;
        int to_x;
        int to_y;

        public Segment(String s, Segment prev) {
            direction = s.charAt(0);
            length = Integer.parseInt(s.substring(1));

            if (prev == null) {
                from_x = 0;
                from_y = 0;
            } else {
                from_x = prev.to_x;
                from_y = prev.to_y;
            }

            if (direction == 'U') {
                to_x = from_x;
                to_y = from_y + length;
            } else if (direction == 'R') {
                to_x = from_x + length;
                to_y = from_y;
            } else if (direction == 'D') {
                to_x = from_x;
                to_y = from_y - length;
            } else {
                assert (direction == 'L');
                to_x = from_x - length;
                to_y = from_y;
            }
        }

        public int[] crosses(Segment other) {
            if ((this.direction == 'U' || this.direction == 'D') && (other.direction == 'U' || other.direction == 'D'))
                return null;
            if ((this.direction == 'R' || this.direction == 'L') && (other.direction == 'R' || other.direction == 'L'))
                return null;
            if (this.from_x == 0 && this.from_y == 0 && other.from_x == 0 && other.from_y == 0) return null;

            boolean bCross = false;
            // Si llegamos aquí, sabemos que son perpendiculares
            if (this.direction == 'U' || this.direction == 'D') {
                bCross = (other.from_x >= this.from_x && other.to_x <= this.from_x) || (other.from_x <= this.from_x && other.to_x >= this.from_x);
                bCross = bCross && ((this.from_y >= other.from_y && this.to_y <= other.from_y) || (this.from_y <= other.from_y && this.to_y >= other.from_y));
            } else if (this.direction == 'R' || this.direction == 'L') {
                bCross = (other.from_y >= this.from_y && other.to_y <= this.from_y) || (other.from_y <= this.from_y && other.to_y >= this.from_y);
                bCross = bCross && ((this.from_x >= other.from_x && this.to_x <= other.from_x) || (this.from_x <= other.from_x && this.to_x >= other.from_x));
            }
            if (bCross) {
                int[] crossPoint = new int[2];
                if (this.direction == 'U' || this.direction == 'D') {
                    crossPoint[0] = this.from_x;
                    crossPoint[1] = other.from_y;
                } else {
                    crossPoint[0] = other.from_x;
                    crossPoint[1] = this.from_y;
                }
                return crossPoint;
            }
            return null;
        }

        public int steps(int[] point) {
            int[] p = new int[2];
            p[0] = from_x;
            p[1] = from_y;
            int steps = 0;
            for (int i = 0; i < length && (p[0] != point[0] || p[1] != point[1]); i++) {
                if (direction == 'U') {
                    p[1] = p[1] + 1;
                } else if (direction == 'R') {
                    p[0] = p[0] + 1;
                } else if (direction == 'D') {
                    p[1] = p[1] - 1;
                } else {
                    assert (direction == 'L');
                    p[0] = p[0] - 1;
                }
                steps++;
            }
            return steps;
        }

        @Override
        public boolean equals(Object obj) {
            Segment other = (Segment) obj;
            return this.direction == other.direction && this.length == other.length
                    && this.from_x == other.from_x && this.from_y == other.from_y
                    && this.to_x == other.to_x && this.to_y == other.to_y;
        }
    }
}
