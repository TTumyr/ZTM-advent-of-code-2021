package dev.tumyr.model;

import dev.tumyr.config.DefaultVariables;
import dev.tumyr.controller.FileOperation;

import java.io.IOException;

public class Day {
    private String data;
    private String testData;
    private Integer partOne = 0;
    private Integer partTwo = 0;

    public Day() throws IOException {
        try {
            this.data = FileOperation.getTextFile( getClass().getResource("") +
                    DefaultVariables.getDataFilename());
        } catch (Exception e) {
            this.data = "";
        }
        try {
            this.testData = FileOperation.getTextFile( getClass().getResource("") +
                    DefaultVariables.getTestDataFilename());
        } catch (Exception e) {
            this.testData = "";
        }
    }
    public String getSolution() {
        return "Part One: " + partOne + "\n" + "Part Two: " + partTwo;
    }

    public String getData() {
        return data;
    }

    public String getTestData() {
        return testData;
    }

    public void setPartOne(Integer partOne) {
        this.partOne = partOne;
    }

    public void setPartTwo(Integer partTwo) {
        this.partTwo = partTwo;
    }
}

