package dev.tumyr.adventofcode.y2021.day05;

import dev.tumyr.model.Day;

import java.io.IOException;
import java.util.*;

public class Day05 extends Day {
    private final ArrayList<ArrayList<String>> VENT_LIST = new ArrayList<ArrayList<String>>();
    private Integer[][] grid;

    public Day05() throws IOException {
        convertData();
        dailySolution();
    }

    private void dailySolution() {
        setGridSize();
        super.setPartOne(locateDanger("partOne"));
        clearGrid();
        super.setPartTwo(locateDanger("partTwo"));
    }

    private void convertData() {
        Scanner scanner = new Scanner(super.getData());
        while(scanner.hasNextLine()) {
            String[] strLine = scanner.nextLine().split("\\s*->\\s*");
            for (String s : strLine) {
                ArrayList<String> line = new ArrayList<String>();
                Collections.addAll(line, s.split(","));
                VENT_LIST.add(line);
            }
        }
        scanner.close();
    }

    private void setGridSize() {
        int x = 0, y = 0;
        for (ArrayList<String> strings : VENT_LIST) {
            for (int j = 0; j < strings.size(); j++) {
                if (j % 2 == 0) {
                    if (Integer.parseInt(strings.get(j)) > x)
                        x = Integer.parseInt(strings.get(j));
                } else {
                    if (Integer.parseInt(strings.get(j)) > y)
                        y = Integer.parseInt(strings.get(j));
                }
            }
        }
        grid = new Integer[y+1][x+1];
    }
    private void insertGridValues(String mode) {
        for (int i = 1; i < VENT_LIST.size(); i += 2) {
            int x1 = Integer.parseInt(VENT_LIST.get(i - 1).get(0));
            int x2 = Integer.parseInt(VENT_LIST.get(i).get(0));
            int y1 = Integer.parseInt(VENT_LIST.get(i - 1).get(1));
            int y2 = Integer.parseInt(VENT_LIST.get(i).get(1));
            if(x1 == x2) {
                for (int j = Math.min(y1, y2); j <= Math.max(y1, y2); j++) {
                    if(grid[j][x1] == null) grid[j][x1] = 1;
                    else grid[j][x1]++;
                }
            }
            if(y1 == y2) {
                for (int j = Math.min(x1, x2); j <= Math.max(x1, x2); j++) {
                    if(grid[y1][j] == null) grid[y1][j] = 1;
                    else grid[y1][j]++;
                }
            }
            if(Objects.equals(mode, "partTwo")) {
                if(Math.max(x1, x2) - Math.min(x1, x2) == (Math.max(y1, y2) - Math.min(y1, y2))) {
                    int dx1, dx2, dy1, dy2;
                    if (y1 <= y2) {
                        dx1 = x1;
                        dx2 = x2;
                        dy1 = y1;
                        dy2 = y2;
                    } else {
                        dx1 = x2;
                        dx2 = x1;
                        dy1 = y2;
                        dy2 = y1;
                    }
                    for (int j = dy1, k = dx1; j <= dy2; j++) {
                        if (grid[j][k] == null) grid[j][k] = 1;
                        else grid[j][k]++;
                        if (k < dx2) k++;
                        else k--;
                    }
                }
            }
        }
    }
    private int locateDanger(String mode) {
        insertGridValues(mode);
        int dangerousVents = 0;
        for (Integer[] integers : grid) {
            for (Integer integer : integers) {
                if (integer != null) if (integer > 1 ) dangerousVents++;
            }
        }
        return dangerousVents;
    }
    private void clearGrid() {
        for (Integer[] integers : grid) {
            Arrays.fill(integers, null);
        }
    }
}
