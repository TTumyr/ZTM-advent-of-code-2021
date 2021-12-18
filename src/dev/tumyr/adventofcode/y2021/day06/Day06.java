package dev.tumyr.adventofcode.y2021.day06;

import dev.tumyr.controller.FileOperation;

import java.io.IOException;
import java.util.*;

public class Day06 {
    private final String data;
    ArrayList<Integer> lanternFish = new ArrayList<Integer>();

    public Day06() throws IOException {
        data = FileOperation.getTextFile( getClass().getResource("") + "data.txt");
        convertData();
    }
    public String solve() {
        long partOne = simulateFish(80);
        long partTwo = simulateFish(256);
        return "Part One: " + partOne + "\n" + "Part Two: " + partTwo;
    }

    private void convertData() {
        Scanner scanner = new Scanner(data);
        while(scanner.hasNextLine()) {
            String[] strLine = scanner.nextLine().split("\\s*,\\s*");
            for (String s : strLine) {
                ArrayList<String> line = new ArrayList<String>();
                lanternFish.add(Integer.parseInt(s));
            }
        }
        scanner.close();
    }

    private long simulateFish(int days) {
        long totalFish = 0;
        long[] fishProgression = new long[9];
        for (Integer fish : lanternFish) {
            if (fish > 0) fishProgression[fish]++;
        }
        for (int i = 0; i < days; i++) {
            long current = fishProgression[0];
            System.arraycopy(fishProgression, 1, fishProgression, 0, fishProgression.length - 1);
            fishProgression[8] = current;
            fishProgression[6] += current;
        }
        for (long l : fishProgression) {
            totalFish += l;
        }
        return totalFish;
    }
}
