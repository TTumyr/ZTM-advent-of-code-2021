package dev.tumyr.adventofcode.y2021.day04;

import dev.tumyr.model.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day04 extends Day {
    private final ArrayList<Integer> BINGO_NUMBERS = new ArrayList<Integer>();
    private final ArrayList<ArrayList<ArrayList<Integer>>> TABLES = new ArrayList<>();
    private final ArrayList<Integer> WINNING_SCORES = new ArrayList<Integer>();

    public Day04() throws IOException {
        convertData();
        dailySolution();
    }

    private void dailySolution() {
        drawNumbers(TABLES, 0);
        super.setPartOne(WINNING_SCORES.get(0));
        super.setPartTwo(WINNING_SCORES.get(WINNING_SCORES.size() - 1));
    }

    private void convertData() {
        ArrayList<String> arrTextData = new ArrayList<String>(List.of(super.getData().split("\n\n", 0)));
        for(int i = 0; i < arrTextData.size(); i++) {
            String[] strElements = arrTextData.get(i).split(",", 0);
            if(i == 0) {
                //first entry in array to bingo draw numbers
                for (String s : strElements) {
                    BINGO_NUMBERS.add(Integer.parseInt(s));
                }
            } else {
                for (int j = 0; j < strElements.length; j++) {
                    //extract string tables from data
                    String[] strTables = strElements[j].split("\n\n", 0);
                    String[] strRows =  strTables[j].split("\n", 0);
                    ArrayList<ArrayList<Integer>> intRows = new ArrayList<>();
                    for (String strRow : strRows) {
                        //extract table rows from string tables
                        String[] strDigits = strRow.trim().split("\\s+", 0);
                        ArrayList<Integer> intDigits = new ArrayList<Integer>();
                        for (int l = 0; l < strRows.length; l++) {
                            //extract digits from string rows
                            intDigits.add(Integer.parseInt(strDigits[l]));
                        }
                        intRows.add(intDigits);
                    }
                    TABLES.add(intRows);
                }
            }
        }
    }

    private void drawNumbers(ArrayList<ArrayList<ArrayList<Integer>>> tableArray, int drawNumberIndex) {
        for(int i = drawNumberIndex; i < BINGO_NUMBERS.size(); i++) {
            for(int j = 0; j < tableArray.size(); j++) {
                Integer returnedTableValue = checkTable(tableArray.get(j), i, j);
                if(returnedTableValue > 0) {
                    WINNING_SCORES.add(returnedTableValue);
                    tableArray.remove(j);
                    drawNumbers(tableArray, i);
                    break;
                }
            }
        }
    }

    private Integer checkTable(ArrayList<ArrayList<Integer>> table, int drawNumberIndex, int tableIndex) {
        for(int i = 0; i < table.size(); i++) {
            for(int j = 0; j < table.get(i).size(); j++) {
                if(Objects.equals(BINGO_NUMBERS.get(drawNumberIndex), table.get(i).get(j))) {
                    ArrayList<Integer> rowMatch = new ArrayList<Integer>();
                    ArrayList<Integer> columnMatch = new ArrayList<Integer>();
                    rowMatch.add(BINGO_NUMBERS.get(drawNumberIndex));
                    columnMatch.add(BINGO_NUMBERS.get(drawNumberIndex));
                    //Check rows
                    for(int k = 0; k < table.get(i).size(); k++) {
                        for(int l = 0; l < drawNumberIndex; l++) {
                            if(Objects.equals(BINGO_NUMBERS.get(l), table.get(i).get(k)))
                                rowMatch.add(BINGO_NUMBERS.get(l));
                            if(rowMatch.size() == table.get(i).size()) {
                                return calculateScore(table, drawNumberIndex, tableIndex);
                            }
                        }
                    }
                    //Check columns
                    for(int k = 0; k < table.size(); k++) {
                        for(int l = 0; l < drawNumberIndex; l++) {
                            if(Objects.equals(BINGO_NUMBERS.get(l), table.get(k).get(j)))
                                columnMatch.add(BINGO_NUMBERS.get(l));
                            if(columnMatch.size() == table.size()) {
                                return calculateScore(table, drawNumberIndex, tableIndex);
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }
    private Integer calculateScore(ArrayList<ArrayList<Integer>> table, int drawNumberIndex, int tableIndex) {
        Integer tableNumbersValue = 0;
        Integer drawNumbersValue = 0;
        for (ArrayList<Integer> integers : table) {
            for (Integer integer : integers) {
                tableNumbersValue += integer;
                for (int k = 0; k <= drawNumberIndex; k++) {
                    if (Objects.equals(BINGO_NUMBERS.get(k), integer)) {
                        drawNumbersValue += BINGO_NUMBERS.get(k);
                    }
                }
            }
        }
        return (tableNumbersValue - drawNumbersValue) * BINGO_NUMBERS.get(drawNumberIndex);
    }
}
