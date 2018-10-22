package sudoku.utils;

public class MathUtils {
    public static boolean isPerfectSquare(int number) {
        double sqrt = Math.sqrt(number);
        return Math.round(sqrt*sqrt) - number == 0;
    }
}
