package dev.tumyr.adventofcode.y2021.day02;

import dev.tumyr.model.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Day02 extends Day {
    private final ArrayList<String> actions = new ArrayList<String>();
    private Integer horPos = 0;
    private Integer verPos = 0;
    private Integer depth = 0;
    private Integer aim = 0;

    public Day02() throws IOException {
        convertData();
        dailySolution();
    }

    private void dailySolution() {
        calculatePos();
        super.setPartOne(horPos * verPos);
        super.setPartTwo(horPos * depth);
    }

    private void convertData() {
        Scanner scanner = new Scanner(super.getData());
        while(scanner.hasNextLine()) {
            actions.add(scanner.nextLine());
        }
        scanner.close();
    }

    private void calculatePos() {
        actions.forEach(action -> {
           String[] current = action.split(" ");
            if(Objects.equals(current[0], "forward")) {
                horPos += Integer.parseInt(current[1]);
                if(aim > 0) depth += aim * Integer.parseInt(current[1]);
            }
            if(Objects.equals(current[0], "up")) {
                verPos -= Integer.parseInt(current[1]);
                aim -= Integer.parseInt(current[1]);
            }
            if(Objects.equals(current[0], "down")) {
                verPos += Integer.parseInt(current[1]);
                aim += Integer.parseInt(current[1]);
            }
        });
    }
}
