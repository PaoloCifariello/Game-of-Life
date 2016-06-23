package edu.spm.gameoflife.core;

/**
 * GameOfLife
 *
 * @author Paolo Cifariello
 */
public class LifeSimulator {

    public static void makeCycle(Space space) {
        makeCycle(space, 0, space.rows());
    }

    public static void makeCycle(Space space, int startRow, int nRows) {
        for (int i = startRow; i < startRow + nRows; i++) {
            for (int j = 0; j < space.columns(); j++) {
                makeCycle(i, j, space);
            }
        }
    }

    private static void makeCycle(int i, int j, Space space) {
        int currentAliveNeighbors = getAliveNeighbors(i, j, space);

        if (space.getCellValue(i, j) == Space.ALIVE){
            switch(currentAliveNeighbors){
                case 2: space.setCellValue(i, j, Space.ALIVE);
                    break;
                case 3: space.setCellValue(i, j, Space.ALIVE);
                    break;
                default: space.setCellValue(i, j, Space.EMPTY);
                    break;
            }
        } else {
            switch (currentAliveNeighbors){
                case 3: space.setCellValue(i, j, Space.ALIVE);
                    break;
                default: space.setCellValue(i, j, Space.EMPTY);
                    break;
            }
        }
    }

    public static int getAliveNeighbors(int i, int j, Space space) {
        int n = space.rows(), m = space.columns();
        int prevRow = i - 1, succRow = i + 1, prevColumn = j - 1, succColumn = j + 1;

        if (i == 0)
            prevRow = n - 1;
        else if (i == n - 1)
            succRow = 0;

        if (j == 0)
            prevColumn = m - 1;
        else if (j == m - 1)
            succColumn = 0;


        return space.getCellValue(prevRow, prevColumn)
                + space.getCellValue(prevRow, j)
                + space.getCellValue(prevRow, succColumn)
                + space.getCellValue(i, prevColumn)
                + space.getCellValue(i, succColumn)
                + space.getCellValue(succRow, prevColumn)
                + space.getCellValue(succRow, j)
                + space.getCellValue(succRow, succColumn);
    }
}
