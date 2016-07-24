package edu.spm.gameoflife.core;

/**
 * This class performs the GOL life simulator algorithm
 *
 * @author Paolo Cifariello
 */
public class LifeSimulator {

    /**
     * Perform a simulation step for all the Space
     *
     * @param space space on which the simulation step has to be performed
     */
    public static void makeCycle(Space space) {
        makeCycle(space, 0, space.rows());
    }

    /**
     * Perform a simulation step on a portion of a Space
     *
     * @param space space on which the simulation step has to be performed
     * @param startRow index of starting row from which the simulation has to be performed
     * @param nRows number of rows to consider for the simulation
     */
    public static void makeCycle(Space space, int startRow, int nRows) {
        for (int i = startRow; i < startRow + nRows; i++) {
            for (int j = 0; j < space.columns(); j++) {
                makeCycle(i, j, space);
            }
        }
    }

    /**
     * Perform a simulation step on a single cell
     *
     * @param i row index of the cell
     * @param j columns index of the cell
     * @param space space on which the simulation step has to be performed
     */
    private static void makeCycle(int i, int j, Space space) {
        int currentAliveNeighbors = getAliveNeighbors(i, j, space);
        byte cellCurrentValue = space.getCellValue(i, j);
        byte cellNextValue;

        if (currentAliveNeighbors == 2 && cellCurrentValue == Space.ALIVE)
            cellNextValue = Space.ALIVE;
        else if (currentAliveNeighbors == 3)
            cellNextValue = Space.ALIVE;
        else
            cellNextValue = Space.EMPTY;

        space.setCellValue(i, j, cellNextValue);
    }

    /**
     * Get number of alive neighbors of a specific cell
     *
     * @param i row index of the cell
     * @param j columns index of the cell
     * @param space space considered
     * @return number of neighobrs alive
     */
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

    /**
     * Get number of alive neighbors of a specific cell
     *
     * @param i row index of the cell
     * @param j columns index of the cell
     * @param space space considered
     * @return number of neighobrs alive
     */
    public static int getAliveNeighborsModulo(int i, int j, Space space) {
        int n = space.rows(), m = space.columns();
        int prevRow = (i - 1 + n) % n, succRow = (i + 1) % n, prevColumn = (j - 1 + m) % m, succColumn = (j + 1) % m;

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
