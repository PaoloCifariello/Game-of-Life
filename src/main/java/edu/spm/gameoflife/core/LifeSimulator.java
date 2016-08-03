package edu.spm.gameoflife.core;

/**
 * This class performs the GOL life simulator algorithm
 *
 * @author Paolo Cifariello
 */
public abstract class LifeSimulator {

    /**
     * Perform a simulation step for all the Universe
     *
     * @param universe universe on which the simulation step has to be performed
     */
    public static void makeCycle(Universe universe) {
        makeCycle(universe, 0, universe.rows());
    }

    /**
     * Perform a simulation step on a portion of a Universe
     *
     * @param universe universe on which the simulation step has to be performed
     * @param startRow index of starting row from which the simulation has to be performed
     * @param nRows number of rows to consider for the simulation
     */
    public static void makeCycle(Universe universe, int startRow, int nRows) {
        for (int i = startRow; i < startRow + nRows; i++) {
            for (int j = 0; j < universe.columns(); j++) {
                makeCycle(i, j, universe);
            }
        }
    }

    /**
     * Perform a simulation step on a single cell
     *
     * @param i row index of the cell
     * @param j columns index of the cell
     * @param universe universe on which the simulation step has to be performed
     */
    private static void makeCycle(int i, int j, Universe universe) {
        int currentAliveNeighbors = getAliveNeighbors(i, j, universe);
        byte cellCurrentValue = universe.getCellValue(i, j);
        byte cellNextValue;

        if (currentAliveNeighbors == 2 && cellCurrentValue == Universe.ALIVE)
            cellNextValue = Universe.ALIVE;
        else if (currentAliveNeighbors == 3)
            cellNextValue = Universe.ALIVE;
        else
            cellNextValue = Universe.EMPTY;

        universe.setCellValue(i, j, cellNextValue);
    }

    /**
     * Get number of alive neighbors of a specific cell
     *
     * @param i row index of the cell
     * @param j columns index of the cell
     * @param universe universe considered
     * @return number of neighobrs alive
     */
    public static int getAliveNeighbors(int i, int j, Universe universe) {
        int n = universe.rows(), m = universe.columns();
        int prevRow = i - 1, succRow = i + 1, prevColumn = j - 1, succColumn = j + 1;

        if (i == 0)
            prevRow = n - 1;
        else if (i == n - 1)
            succRow = 0;

        if (j == 0)
            prevColumn = m - 1;
        else if (j == m - 1)
            succColumn = 0;


        return universe.getCellValue(prevRow, prevColumn)
                + universe.getCellValue(prevRow, j)
                + universe.getCellValue(prevRow, succColumn)
                + universe.getCellValue(i, prevColumn)
                + universe.getCellValue(i, succColumn)
                + universe.getCellValue(succRow, prevColumn)
                + universe.getCellValue(succRow, j)
                + universe.getCellValue(succRow, succColumn);
    }

    /**
     * Get number of alive neighbors of a specific cell
     *
     * @param i row index of the cell
     * @param j columns index of the cell
     * @param universe universe considered
     * @return number of neighobrs alive
     */
    public static int getAliveNeighborsModulo(int i, int j, Universe universe) {
        int n = universe.rows(), m = universe.columns();
        int prevRow = (i - 1 + n) % n, succRow = (i + 1) % n, prevColumn = (j - 1 + m) % m, succColumn = (j + 1) % m;

        return universe.getCellValue(prevRow, prevColumn)
                + universe.getCellValue(prevRow, j)
                + universe.getCellValue(prevRow, succColumn)
                + universe.getCellValue(i, prevColumn)
                + universe.getCellValue(i, succColumn)
                + universe.getCellValue(succRow, prevColumn)
                + universe.getCellValue(succRow, j)
                + universe.getCellValue(succRow, succColumn);
    }
}
