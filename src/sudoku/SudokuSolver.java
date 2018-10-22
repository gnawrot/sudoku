package sudoku;

import sudoku.utils.MathUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class to solve sudoku puzzle.
 * Design is flexible and the program will allow to handle any size Sudoku grids: 4x4, 9x9, 16x16, 25x25 etc.
 */
public class SudokuSolver {

    private boolean preValidated = false;
    private static final int MIN_VALUE = 1;
    private static final int NO_VALUE = 0;
    private static final int START_INDEX = 0;
    private int gridSize;
    private int blockSize;

    public SudokuSolver() {

    }

    private void preValidate(int[][] board) throws IllegalArgumentException {

        this.gridSize = board.length;
        this.blockSize = (int) Math.round(Math.sqrt(gridSize));

        // Perform argument validation
        if (board.length == 0) throw new IllegalArgumentException("board array cannot be empty.");
        if (!MathUtils.isPerfectSquare(board.length)) {
            throw new IllegalArgumentException("gridSize is not a perfect square ");
        }
        double sqrt = Math.sqrt(board.length);
        if (Math.round(sqrt * sqrt) - gridSize != 0) {
            throw new IllegalArgumentException("gridSize is not equal to sqrt(board)");
        }
        this.preValidated = true;
    }

    private void init(int[][] board) throws IllegalArgumentException {

        this.gridSize = board.length;
        this.blockSize = (int) Math.round(Math.sqrt(gridSize));
    }

    public boolean solve(int[][] board) {

        if (!this.preValidated) {
            preValidate(board);
            init(board);
        }
        for (int rowIdx = START_INDEX; rowIdx < this.gridSize; rowIdx++) {
            for (int colIdx = START_INDEX; colIdx < this.gridSize; colIdx++) {
                if (board[rowIdx][colIdx] != NO_VALUE) {
                    continue;
                }
                for (int num = MIN_VALUE; num <= this.gridSize; num++) {
                    if (isAllowed(board, num, rowIdx, colIdx)) {
                        board[rowIdx][colIdx] = num;
                        if (solve(board)) {
                            return true;
                        }
                    }
                    board[rowIdx][colIdx] = NO_VALUE;
                }
                return false;
            }
        }
        return true;
    }
    private boolean isAllowed(int[][] board, int number, int rowIdx, int colIdx)
    {
        return !isNumberInRow(board, number, rowIdx)
                && !isNumberInColumn(board, number, colIdx)
                && !isNumberInBlock(board, number, rowIdx, colIdx);
    }
    private boolean isNumberInRow(int[][] board, int number, int rowIndex)
    {
        for (int colIdx = START_INDEX; colIdx < this.gridSize; colIdx++) {
            if (board[rowIndex][colIdx] == number)
                return true;
        }
        return false;
    }
    private boolean isNumberInColumn(int[][] board, int number, int colIndex)
    {
        for (int rowIdx = START_INDEX; rowIdx < this.gridSize; rowIdx++) {
            if (board[rowIdx][colIndex] == number)
                return true;
        }
        return false;
    }
    private boolean isNumberInBlock(int[][] board, int number, int rowIndex, int colIndex)
    {
        int blockStartRowIdx = rowIndex - rowIndex % this.blockSize;
        int blockEndRowIdx = blockStartRowIdx + this.blockSize - 1;
        int blockStartColIdx = colIndex - colIndex % this.blockSize;
        int blockEndColIdx = blockStartColIdx + this.blockSize - 1;

        for (int rowIdx = blockStartRowIdx; rowIdx <= blockEndRowIdx; rowIdx++) {
            for (int colIdx = blockStartColIdx; colIdx <= blockEndColIdx; colIdx++) {
                if (board[rowIdx][colIdx] == number)
                    return true;
            }
        }
        return false;
    }
    public boolean postValidate(int[][] board) {
        for (int rowIdx = START_INDEX; rowIdx < this.gridSize; rowIdx++) {
            if (!isValidRow(board, rowIdx)) {
                System.out.printf("Row %d contains duplicate numbers.\n", rowIdx);
                return false;
            }
        }
        for (int colIdx = START_INDEX; colIdx < this.gridSize; colIdx++) {
            if (!isValidColumn(board, colIdx)) {
                System.out.printf("Column %d contains duplicate numbers.\n", colIdx);
                return false;
            }
        }
        for (int rowIdx = START_INDEX; rowIdx < this.gridSize; rowIdx++) {
            for (int colIdx = START_INDEX; colIdx < this.gridSize; colIdx++) {
                if (!isValidBlock(board, rowIdx, colIdx)) {
                    System.out.printf("Number at row %d and column %d is not unique in its block.\n", rowIdx, colIdx);
                    return false;
                }
            }
        }
        return true;
    }
    private boolean isValidRow(int[][] board, int rowIdx) {
        Set<Integer> uniqueNumbers = new HashSet<>();
        for (int colIdx = START_INDEX; colIdx < this.gridSize; colIdx++) {
            if (!uniqueNumbers.add(board[rowIdx][colIdx])) {
                return false;
            }
        }
        return true;
    }
    private boolean isValidColumn(int[][] board, int colIdx) {
        Set<Integer> uniqueNumbers = new HashSet<>();
        for (int rowIdx = START_INDEX; rowIdx < this.gridSize; rowIdx++) {
            if (!uniqueNumbers.add(board[rowIdx][colIdx])) {
                return false;
            }
        }
        return true;
    }
    private boolean isValidBlock(int[][] board, int rowIdx, int colIdx) {
        int blockStartRowIdx = rowIdx- rowIdx % this.blockSize;
        int blockEndRowIdx = blockStartRowIdx + this.blockSize;
        int blockStartColIdx = colIdx - colIdx % this.blockSize;
        int blockEndColIdx = blockStartColIdx + this.blockSize;
        List<Integer> numbers = new ArrayList<>();
        for (int blockRowIdx = blockStartRowIdx; blockRowIdx < blockEndRowIdx; blockRowIdx++) {
            for (int blockColIdx = blockStartColIdx; blockColIdx < blockEndColIdx; blockColIdx++) {
                numbers.add(board[blockRowIdx][blockColIdx]);
            }
        }
        return (numbers.size() == new HashSet<>(numbers).size());
    }
}
