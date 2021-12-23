package dev.tumyr.adventofcode.y2021.day08;

import dev.tumyr.model.Day;

import java.io.IOException;
import java.util.*;

public class Day08 extends Day {
    private final ArrayList<ArrayList<String>> ENTRIES = new ArrayList<>();

    public Day08() throws IOException {
        convertData();
        dailySolution();
    }

    private void dailySolution() {
        super.setPartOne(decodePartOne());
        super.setPartTwo(decodePartTwo());
    }

    private void convertData() {
        Scanner scanner = new Scanner(super.getData());
        while(scanner.hasNextLine()) {
            ArrayList<String> line = new ArrayList<String>();
            Collections.addAll(line, scanner.nextLine().split("\\s*\\|\\s*"));
            ENTRIES.add(line);
        }
        scanner.close();
    }
    private int decodePartOne() {
        int counter = 0;
        for (ArrayList<String> entry : ENTRIES) {
            String[] getDigits = entry.get(1).split("\\s+");
            for (String getDigit : getDigits) {
                if (getDigit.length() == 2 || getDigit.length() == 3 ||
                        getDigit.length() == 4 || getDigit.length() == 7) counter++;
            }
        }
        return counter;
    }
    private int decodePartTwo() {
        int sumTotalSignal = 0;
        for (ArrayList<String> entry : ENTRIES) {
            HashMap<Integer, String> digits;
            String[] getDigits = entry.get(0).split("\\s+");
            String[] getSignal = entry.get(1).split("\\s+");
            digits = mapCharacters(getDigits);
            int signal = calculateSignal(digits, getSignal);
            sumTotalSignal += signal;
        }
        return sumTotalSignal;
    }
    private HashMap<Integer, String> mapCharacters(String[] getDigits) {
        HashMap<Integer, String> Digits = new HashMap<>();
        ArrayList<String> digitsToMap = new ArrayList<>(Arrays.asList(getDigits));
        int whileSafeBreak = 0;
        while (!digitsToMap.isEmpty() && whileSafeBreak < 100) {
            for (String digit : digitsToMap) {
                String notFoundInEight = "";
                boolean foundInFour = false;
                boolean foundInSeven = false;
                boolean foundInOne = false;
                int foundInSix = 0;
                if(Digits.containsKey(8)) {
                    String foundInEight = compareDigits(Digits.get(8), digit, "match");
                    notFoundInEight = compareDigits(Digits.get(8), foundInEight, "eliminate");
                }
                if(Digits.containsKey(4)) {
                    foundInFour = compareDigits(Digits.get(4), notFoundInEight, "match").length() > 0;
                }
                if(Digits.containsKey(7)) {
                    foundInSeven = compareDigits(Digits.get(7), notFoundInEight, "match").length() > 0;
                }
                if(Digits.containsKey(1)) {
                    foundInOne = compareDigits(Digits.get(1), notFoundInEight, "match").length() > 0;
                }
                if(Digits.containsKey(6)) {
                    foundInSix = compareDigits(Digits.get(6), notFoundInEight, "match").length();
                }
                switch (digit.length()) {
                    case 7 -> Digits.put(8, digit);
                    case 4 -> Digits.put(4, digit);
                    case 3 -> Digits.put(7, digit);
                    case 2 -> Digits.put(1, digit);
                    case 6 -> {
                        if (!foundInFour && !foundInSeven && !foundInOne) Digits.put(9, digit);
                        if (foundInFour && foundInSeven && foundInOne) Digits.put(6, digit);
                        if (foundInFour && !foundInSeven && !foundInOne) Digits.put(0, digit);
                    }
                    case 5 -> {
                        if (foundInSix == 1) Digits.put(5, digit);
                        if (foundInSix == 2 && foundInOne) Digits.put(2, digit);
                        if (foundInSix == 2 && !foundInOne) Digits.put(3, digit);
                    }
                }
            }
            whileSafeBreak++;
        }
        return Digits;
    }
    private int calculateSignal(HashMap<Integer, String> digits, String[] getSignal) {
        StringBuilder signal = new StringBuilder();
        for (String s : getSignal) {
            for (Integer digit : digits.keySet()) {
                StringBuilder matchingDigit = new StringBuilder();
                String value = digits.get(digit);
                if (s.length() == value.length()) {
                    for (int j = 0; j < value.length(); j++) {
                        for (int k = 0; k < s.length(); k++) {
                            if (s.charAt(k) == value.charAt(j)) matchingDigit.append(value.charAt(j));
                        }
                    }
                    if (value.equals(matchingDigit.toString())) signal.append(digit);
                }
            }
        }
        return Integer.parseInt(signal.toString());
    }
    private String compareDigits(String digit1, String digit2, String mode) {
        StringBuilder returnDigit = new StringBuilder();
        if (Objects.equals(mode, "eliminate")) returnDigit.append(digit1);
        for (int i = 0; i < digit1.length(); i++) {
            for (int j = 0; j < digit2.length(); j++) {
                if (digit2.charAt(j) == digit1.charAt(i))
                {
                    if(Objects.equals(mode, "eliminate")) {
                        returnDigit.deleteCharAt(returnDigit.indexOf(String.valueOf(digit2.charAt(j))));
                    }
                    else returnDigit.append(digit2.charAt(j));
                }
            }
        }
        return returnDigit.toString();
    }
}
