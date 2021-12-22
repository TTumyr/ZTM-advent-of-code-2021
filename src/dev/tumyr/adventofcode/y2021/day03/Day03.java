package dev.tumyr.adventofcode.y2021.day03;

import dev.tumyr.model.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;

public class Day03 extends Day {
    private final ArrayList<String> binaryList = new ArrayList<String>();

    public Day03() throws IOException {
        convertData();
        dailySolution();
    }

    private void dailySolution() {
        super.setPartOne(calculatePowerConsumption());
        super.setPartTwo(calculateLifeSupportRating());
    }

    private void convertData() {
        Scanner scanner = new Scanner(super.getData());
        while(scanner.hasNextLine()) {
            binaryList.add(scanner.nextLine());
        }
        scanner.close();
    }

    private Integer calculatePowerConsumption() {
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
        return gammaInt * epsilonInt;
    }
    private Integer calculateLifeSupportRating() {
        Integer oxygenRating = getRating("mostCommon");
        Integer co2Rating = getRating("leastCommon");
        return oxygenRating * co2Rating;
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
