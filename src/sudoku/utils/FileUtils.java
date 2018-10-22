package sudoku.utils;

import com.sun.deploy.util.StringUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileUtils {

    public static int[][] readNumbersFromFile(String fileNamePath) {

        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(fileNamePath), StandardCharsets.UTF_8);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return toNumbers(lines);
    }

    private static int[][] toNumbers(List<String> textLines) {

        Set<Integer> textLineLengths = new HashSet<>();
        for (String textLine: textLines) {
            textLineLengths.add(textLine.length());
        }
        if (textLineLengths.size() != 1) {
            throw new IllegalArgumentException("All text lines don't have the same length.");
        }
        int textLineLength = textLineLengths.iterator().next();
        int textLineCount = textLines.size();
        int[][] numbers = new int[textLineCount][textLineLength];
        for (int row = 0; row < textLineCount; row++) {
            for (int col = 0; col < textLineLength; col++) {
                int number = 0;
                try {
                    number = Integer.valueOf(Character.toString(textLines.get(row).charAt(col)));
                } catch (NumberFormatException nfe) {
                    number = 0;
                }
                numbers[row][col] = number;
            }
        }
        return numbers;
    }

    public static void writeToFile(List<String> textLines, String fileNamePath) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileNamePath));
            for (String textLine : textLines) {
                writer.write(textLine);
            }
            writer.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static List<String> toTextLines(int[][] numbers) {

        List<String> textLines = new ArrayList<>();
        for (int row = 0; row < numbers.length; row++) {
            StringBuilder sb = new StringBuilder();
            for (int col = 0; col < numbers[row].length; col++) {
                String digit;
                try {
                    digit = Integer.toString(numbers[row][col]);
                } catch (Exception e) {
                    digit = "X";
                }
                if (digit.equals("0")) {
                    digit = "X";
                }
                sb.append(digit);
            }
            textLines.add(sb.toString() + "\n");
        }
        return textLines;
    }
}
