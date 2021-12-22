package dev.tumyr.adventofcode.y2021.day01;

import dev.tumyr.model.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day01 extends Day {
    private final ArrayList<Integer> depths = new ArrayList<Integer>();

    public Day01() throws IOException {
        convertData();
        dailySolution();
    }

    private void dailySolution() {
        Integer[] result = findVariations();
        super.setPartOne(result[0]);
        super.setPartTwo(result[1]);
    }

    private void convertData() {
        Scanner scanner = new Scanner(super.getData());
        while(scanner.hasNextLine()) {
            depths.add(Integer.parseInt(scanner.nextLine()));
        }
        scanner.close();
    }

    private Integer[] findVariations() {
        int partOne = 0;
        int partTwo = 0;
        for(int i = 0; i < depths.size(); i++) {
            if(i > 0) {
                if(depths.get(i) > depths.get(i-1)) partOne++;
            }
            if(i > 2) {
                if((depths.get(i) + depths.get(i-1) + depths.get(i-2)) > (depths.get(i-1) + depths.get(i-2)
                        + depths.get(i-3))) partTwo++;
            }
        }
        return new Integer[] {partOne, partTwo};
    }
}
