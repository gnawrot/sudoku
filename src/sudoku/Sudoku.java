package sudoku;

import sudoku.utils.FileUtils;

import java.util.List;

public class Sudoku {

    // Wished I would read from CSV or JSON file instead.
    // It would be easy to use Jackson library for that purpose.
    // File with postfix 6 is world's most difficult Sudoku puzzle.
    // File with postfix 7 is an example of Sudoku in 4x4 size.
    public static void main(String[] args) {
        final int inputFileStartPostfix = 1;
        final int inputFileEndPostfix = 7;
        for (int filePostFix = inputFileStartPostfix; filePostFix <= inputFileEndPostfix; filePostFix++) {
            String puzzleFile = "";
            try {
                puzzleFile = "rsrc/puzzle" + filePostFix;
                int[][] sudokuPuzzle = FileUtils.readNumbersFromFile(puzzleFile + ".txt");
                SudokuSolver sudokuSolver = new SudokuSolver();
                sudokuSolver.solve(sudokuPuzzle);
                if (!sudokuSolver.postValidate(sudokuPuzzle)) {
                    System.out.printf("Solved Sudoku for file %s.txt is invalid.\n", puzzleFile);
                }
                List<String> textLines = FileUtils.toTextLines(sudokuPuzzle);
                FileUtils.writeToFile(textLines, puzzleFile + ".sln" + ".txt");
            }
            catch (Exception e) {
                System.out.printf("Failed to solve %s.txt puzzle due to an exception.\n", puzzleFile);
                e.printStackTrace();
            }
        }
    }
}
