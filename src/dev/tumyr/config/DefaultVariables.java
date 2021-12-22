package dev.tumyr.config;

public class DefaultVariables {
    private static final String CLASS_PATH_AOC_2021 = "dev.tumyr.adventofcode.y2021.";
    private static final String PATH_AOC_2021 = "adventofcode/y2021/";
    private static final String FOLDER_AOC_2021 = "y2021";
    private static final String DESCRIPTION = "description.txt";
    private static final String DATA_FILENAME = "data.txt";
    private static final String TEST_DATA_FILENAME = "tdata.txt";
    private static final String DAILY_RETURN_FUNCTION = "getSolution";

    public static String getClassPathAoc2021() {
        return CLASS_PATH_AOC_2021;
    }
    public static String getPathAoc2021() {
        return PATH_AOC_2021;
    }
    public static String getFolderAoc2021() {
        return FOLDER_AOC_2021;
    }
    public static String getDescription() {
        return DESCRIPTION;
    }
    public static String getDataFilename() {
        return DATA_FILENAME;
    }
    public static String getTestDataFilename() {
        return TEST_DATA_FILENAME;
    }
    public static String getDailyReturnFunction() {
        return DAILY_RETURN_FUNCTION;
    }
}
