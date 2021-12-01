package dev.tumyr.adventofcode.y2021.day1;

import dev.tumyr.controller.FileOperation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day1 {
    private final String data;
    private final ArrayList<Integer> depths = new ArrayList<Integer>();
    private Integer partOne = 0;
    private Integer partTwo = 0;


    private void convertData() {
        Scanner scanner = new Scanner(this.data);
        while(scanner.hasNextLine()) {
            depths.add(Integer.parseInt(scanner.nextLine()));
        }
    }
    public Day1() throws IOException {
        this.data = FileOperation.getTextFile( getClass().getResource("") + "data.txt");
        convertData();
    }

    public String solve() {
        findVariations();
        return "Part One: " + this.partOne + "\n" + "Part Two: " + this.partTwo;
    }
    private void findVariations() {
        for(int i = 0; i < this.depths.size(); i++) {
            if(i > 0) {
                if(this.depths.get(i) > this.depths.get(i-1)) this.partOne++;
            }
            if(i > 2) {
                if((this.depths.get(i) + this.depths.get(i-1) + this.depths.get(i-2)) > (this.depths.get(i-1) + this.depths.get(i-2) + this.depths.get(i-3))) this.partTwo++;
            }
        }
    }
}
