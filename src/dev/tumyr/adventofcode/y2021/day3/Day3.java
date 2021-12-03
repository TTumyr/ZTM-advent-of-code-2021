package dev.tumyr.adventofcode.y2021.day3;

import dev.tumyr.controller.FileOperation;

import java.io.IOException;
import java.util.*;

public class Day3 {
    private final String data;
    private final ArrayList<String> binaryList = new ArrayList<String>();
    private Integer partOne = 0;
    private Integer partTwo = 0;

    public Day3() throws IOException {
        data = FileOperation.getTextFile( getClass().getResource("") + "data.txt");
        convertData();
    }

    private void convertData() {
        Scanner scanner = new Scanner(data);
        while(scanner.hasNextLine()) {
            binaryList.add(scanner.nextLine());
        }
    }

    public String solve() {
        calculatePowerConsumption();
        calculateLifeSupportRating();
        return "Part One: " + partOne + "\n" + "Part Two: " + partTwo;
    }
    private void calculatePowerConsumption() {
        StringBuilder gammaBin = new StringBuilder();
        StringBuilder epsilonBin = new StringBuilder();
        int maxBinLength = getMaxListLength();
        for(int i = 0; i < maxBinLength; i++) {
            Integer binOne = testBinary(binaryList, i);
            if(binOne > binaryList.size() / 2) {
                gammaBin.append("1");
                epsilonBin.append("0");
            } else {
                gammaBin.append("0");
                epsilonBin.append("1");
            }
        }
        Integer gammaInt = Integer.parseInt(gammaBin.toString(), 2);
        Integer epsilonInt = Integer.parseInt(epsilonBin.toString(), 2);
        this.partOne = gammaInt * epsilonInt;
    }
    private void calculateLifeSupportRating() {
        Integer oxygenRating = getRating("mostCommon");
        Integer co2Rating = getRating("leastCommon");
        partTwo = oxygenRating * co2Rating;
    }
    private Integer getMaxListLength() {
        String maxLength = binaryList.stream().max(Comparator.comparing(String::length)).orElse(null);
        assert maxLength != null;
        return maxLength.length();
    }
    private Integer testBinary(ArrayList<String> list, int i) {
        Long binCount = (Long) list.stream()
                .filter(binary -> binary.length() > i)
                .filter(binary -> Objects.equals(binary.charAt(i), '1'))
                .count();
        return binCount.intValue();
    }
    private Integer getRating(String type) {
        ArrayList<String> binaryData = new ArrayList<String>(binaryList);
        ArrayList<String> binaryReducer = new ArrayList<String>();
        int maxBinLength = getMaxListLength();
        for(int i = 0; i < maxBinLength; i++) {
            int finalI = i;
            if(binaryData.size() > 1) {
                int binOne = testBinary(binaryData, i);
                if((Objects.equals(type, "mostCommon") && (float) binOne >= binaryData.size() / (float) 2)
                || (Objects.equals(type, "leastCommon") && (float) binOne < binaryData.size() / (float) 2)) {
                    binaryData.forEach(binary -> {
                        if(binary.charAt(finalI) == '1') binaryReducer.add(binary);
                    });
                } else {
                    binaryData.forEach(binary -> {
                        if(binary.charAt(finalI) != '1') binaryReducer.add(binary);
                    });
                }
                binaryData.clear();
                binaryData.addAll(binaryReducer);
                binaryReducer.clear();
            }
        }
        return Integer.parseInt(binaryData.get(0).toString(), 2);
    }
}
