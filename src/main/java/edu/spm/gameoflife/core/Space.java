package edu.spm.gameoflife.core;

import java.util.Random;

/**
 * This class contains information about a GOL Space, it stores values of all cells
 * and contains methods to interact with those values.
 *
 * @author Paolo Cifariello
 */
public class Space {
    public static byte EMPTY = 0;
    public static byte ALIVE = 1;

    private int n;
    private int m;

    private byte[][] currentSpace;
    private byte[][] futureSpace;
    /**
     *
     * @param n number of rows of the space
     * @param m number of columns of the space
     */
    public Space(int n, int m)  {
        this.n = n;
        this.m = m;

        this.currentSpace = new byte[n][m];
        this.futureSpace = new byte[n][m];
    }

    /**
     *
     * @return number of rows
     */
    public int rows() {
        return n;
    }

    /**
     *
     * @return number of columns
     */
    public int columns() {
        return m;
    }

    /** Initialize all cells to EMPTY */
    public void initialize() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                currentSpace[i][j] = EMPTY;
                futureSpace[i][j] = EMPTY;
            }
        }
    }

    /**
     * Get value of a cell
     *
     * @param i row value
     * @param j column value
     * @return the value of the cell identified
     */
    public byte getCellValue(int i, int j) {
        return currentSpace[i][j];
    }

    /**
     * Set value of a cell
     *
     * @param i row value
     * @param j column value
     * @value the value to set
     */
    public void setCellValue(int i, int j, byte value) {
        futureSpace[i][j] = value;
    }

    /**
     * performs a swap of spaces (modifications and read are done on different spaces, whwn calling swap()
     * modifications are "written" in the current space and can be read)
     */
    public void swap() {
        byte[][] oldSpace = currentSpace;
        currentSpace = futureSpace;
        futureSpace = oldSpace;
    }

    /**
     * get a set of intervals that cover completely the space
     *
     * @param partitions number of partitions to do
     * @return list of Interval that identify partitions
     */
    public Interval[] split(int partitions) {
        int rows = rows();

        int rowsPerPartition = rows / partitions;
        int extraRows = rows % partitions;
        int start = 0;
        int nRows;
        Interval[] intervals = new Interval[partitions];

        for (int j = 0; j < partitions; j++) {
            nRows = rowsPerPartition + (extraRows > 0 ? 1:0);
            extraRows--;
            intervals[j] = new Interval(start, nRows);
            start = start + nRows;
        }

        return intervals;
    }

    /**
     * produce a String representation of a subset of the actual Space
     *
     * @param start starting row index
     * @param nrows number of rows to consider
     * @return String representation
     */
    public String toString(int start, int nrows) {
        StringBuilder sb = new StringBuilder();

        for (int i = start; i < start + nrows; i++) {
            for (int j = 0; j < m; j++) {
                sb.append(currentSpace[i][j] + "\t");
            }
            sb.append("\n");
        }
        sb.append("\n");

        return sb.toString();
    }

    /**
     * produce a String representation of the actual Space
     *
     * @return String representation
     */
    public String toString() {
        return toString(0, n);
    }

    public static Space Random(int n, int m) {
        Space s = new Space(n, m);
        Random r = new Random();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                byte value = (byte) (Math.abs(r.nextInt()) % 2);
                s.currentSpace[i][j] = value;
            }
        }

        return s;
    }
}
