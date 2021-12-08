package dev.tumyr.adventofcode.y2021.day4;

import dev.tumyr.controller.FileOperation;

import java.io.IOException;
import java.util.*;

public class Day4 {
    private final String data;
    private ArrayList<Integer> numbersToDraw = new ArrayList<Integer>();
    private ArrayList<ArrayList<ArrayList<Integer>>> tables = new ArrayList<>();
    private ArrayList<Integer> winningScores = new ArrayList<Integer>();
    private Integer partOne = 0;
    private Integer partTwo = 0;

    public Day4() throws IOException {
        data = FileOperation.getTextFile( getClass().getResource("") + "data.txt");
        convertData();
    }
    public String solve() {
        drawNumbers(tables, 0);
        partOne = winningScores.get(0);
        partTwo = winningScores.get(winningScores.size() - 1);
        return "Part One: " + partOne + "\n" + "Part Two: " + partTwo;
    }
    private void convertData() {
        ArrayList<String> arrTextData = new ArrayList<String>(List.of(data.split("\n\n", 0)));
        for(int i = 0; i < arrTextData.size(); i++) {
            String[] strElements = arrTextData.get(i).split(",", 0);
            if(i == 0) {
                //first entry in array to bingo draw numbers
                for (String s : strElements) {
                    numbersToDraw.add(Integer.parseInt(s));
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
                    tables.add(intRows);
                }
            }
        }
    }
    private void drawNumbers(ArrayList<ArrayList<ArrayList<Integer>>> tableArray, int drawNumberIndex) {
        for(int i = drawNumberIndex; i < numbersToDraw.size(); i++) {
            for(int j = 0; j < tableArray.size(); j++) {
                Integer returnedTableValue = checkTable(tableArray.get(j), i, j);
                if(returnedTableValue > 0) {
                    winningScores.add(returnedTableValue);
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
                if(Objects.equals(numbersToDraw.get(drawNumberIndex), table.get(i).get(j))) {
                    ArrayList<Integer> rowMatch = new ArrayList<Integer>();
                    ArrayList<Integer> columnMatch = new ArrayList<Integer>();
                    rowMatch.add(numbersToDraw.get(drawNumberIndex));
                    columnMatch.add(numbersToDraw.get(drawNumberIndex));
                    //Check rows
                    for(int k = 0; k < table.get(i).size(); k++) {
                        for(int l = 0; l < drawNumberIndex; l++) {
                            if(Objects.equals(numbersToDraw.get(l), table.get(i).get(k)))
                                rowMatch.add(numbersToDraw.get(l));
                            if(rowMatch.size() == table.get(i).size()) {
                                return calculateScore(table, drawNumberIndex, tableIndex);
                            }
                        }
                    }
                    //Check columns
                    for(int k = 0; k < table.size(); k++) {
                        for(int l = 0; l < drawNumberIndex; l++) {
                            if(Objects.equals(numbersToDraw.get(l), table.get(k).get(j)))
                                columnMatch.add(numbersToDraw.get(l));
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
                    if (Objects.equals(numbersToDraw.get(k), integer)) {
                        drawNumbersValue += numbersToDraw.get(k);
                    }
                }
            }
        }
        return (tableNumbersValue - drawNumbersValue) * numbersToDraw.get(drawNumberIndex);
    }
}
