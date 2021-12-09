package dev.tumyr.controller;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileOperation {
    public static String getTextFile(String url) throws IOException {
        String filepath = url.toString().replace("file:/", "").replace("\\", "/");
        FileReader fileToImport = new FileReader(filepath);
        StringBuilder stringbuilder = new StringBuilder();
        Scanner scanner = new Scanner(fileToImport);
        while(scanner.hasNextLine()) {
            stringbuilder.append(scanner.nextLine());
            stringbuilder.append("\n");
        }
        scanner.close();
        return stringbuilder.toString();
    }
    public static List<Path> listDirectories(Path path) throws IOException {
        List<Path> result;
        try (Stream<Path> walk = Files.walk(path, 1)) {
            result = walk.filter(Files::isDirectory)
                    .collect(Collectors.toList());
        }
        return result;
    }
}
