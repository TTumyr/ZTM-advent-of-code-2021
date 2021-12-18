package dev.tumyr.adventofcode.y2021.day02;

import dev.tumyr.controller.FileOperation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Day02 {
    private final String data;
    private final ArrayList<String> actions = new ArrayList<String>();
    private Integer horPos = 0;
    private Integer verPos = 0;
    private Integer depth = 0;
    private Integer aim = 0;
    private Integer partOne = 0;
    private Integer partTwo = 0;

    public Day02() throws IOException {
        data = FileOperation.getTextFile( getClass().getResource("") + "data.txt");
        convertData();
    }

    private void convertData() {
        Scanner scanner = new Scanner(data);
        while(scanner.hasNextLine()) {
            actions.add(scanner.nextLine());
        }
    }

    public String solve() {
        calculatePos();
        calculateSolutions();
        return "Part One: " + partOne + "\n" + "Part Two: " + partTwo;
    }
    private void calculateSolutions() {
        partOne = horPos * verPos;
        partTwo = horPos * depth;
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
