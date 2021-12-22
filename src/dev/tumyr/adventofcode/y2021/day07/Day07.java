package dev.tumyr.adventofcode.y2021.day07;

import dev.tumyr.model.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Day07 extends Day {
    ArrayList<Integer> horPosition = new ArrayList<Integer>();

    public Day07() throws IOException {
        convertData();
        dailySolution();
    }

    private void dailySolution() {
        super.setPartOne(findCheapestPosition("partOne"));
        super.setPartTwo(findCheapestPosition("partTwo"));
    }

    private void convertData() {
        Scanner scanner = new Scanner(super.getData());
        while(scanner.hasNextLine()) {
            String[] strLine = scanner.nextLine().split("\\s*,\\s*");
            for (String s : strLine) {
                ArrayList<String> line = new ArrayList<String>();
                horPosition.add(Integer.parseInt(s));
            }
        }
        scanner.close();
    }

    private int findCheapestPosition(String mode) {
        int cheapestHorPos = 0;
        int maxHorPos = 0;
        for (Integer integer : horPosition) {
            if (maxHorPos < integer) maxHorPos = integer;
        }
        int[] horPosCost = new int[maxHorPos];
        for (int i = 0; i < horPosCost.length; i++) {
            for (Integer integer : horPosition) {
                if (integer != i) {
                    if (Objects.equals(mode, "partTwo")) {
                        if (integer > i) {
                            for (int k = 0; k < integer - i; k++) {
                                horPosCost[i] += (1 + k);
                            }
                        } else {
                            for (int k = 0; k < i - integer; k++) {
                                horPosCost[i] += (1 + k);
                            }
                        }
                    } else horPosCost[i] += (integer > i) ?
                            (integer - i) : (i - integer);
                }
            }
            if(horPosCost[i] < cheapestHorPos || cheapestHorPos == 0) cheapestHorPos = horPosCost[i];
        }
        return cheapestHorPos;
    }
}
