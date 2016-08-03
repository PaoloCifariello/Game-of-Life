package edu.spm.gameoflife.core;

import java.util.Random;

/**
 * This class contains information about a GOL Universe, it stores values of all cells
 * and contains methods to interact with those values.
 *
 * @author Paolo Cifariello
 */
public class Universe {
    public static byte EMPTY = 0;
    public static byte ALIVE = 1;

    private int n;
    private int m;

    private byte[][] currentUniverse;
    private byte[][] futureUniverse;
    /**
     *
     * @param n number of rows of the universe
     * @param m number of columns of the universe
     */
    public Universe(int n, int m)  {
        this.n = n;
        this.m = m;

        this.currentUniverse = new byte[n][m];
        this.futureUniverse = new byte[n][m];
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
                currentUniverse[i][j] = EMPTY;
                futureUniverse[i][j] = EMPTY;
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
        return currentUniverse[i][j];
    }

    /**
     * Set value of a cell
     *
     * @param i row value
     * @param j column value
     * @value the value to set
     */
    public void setCellValue(int i, int j, byte value) {
        futureUniverse[i][j] = value;
    }

    /**
     * performs a swap of universes (modifications and read are done on different universes, after calling swap()
     * modifications are "written" in the current universe and can be read)
     */
    public void swap() {
        byte[][] oldUniverse = currentUniverse;
        currentUniverse = futureUniverse;
        futureUniverse = oldUniverse;
    }

    /**
     * get a set of intervals that completely covers the universe
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
     * produce a String representation of a subset of the actual Universe
     *
     * @param start starting row index
     * @param nrows number of rows to consider
     * @return String representation
     */
    public String toString(int start, int nrows) {
        StringBuilder sb = new StringBuilder();

        for (int i = start; i < start + nrows; i++) {
            for (int j = 0; j < m; j++) {
                sb.append(currentUniverse[i][j] + "\t");
            }
            sb.append("\n");
        }
        sb.append("\n");

        return sb.toString();
    }

    /**
     * produce a String representation of the actual Universe
     *
     * @return String representation
     */
    public String toString() {
        return toString(0, n);
    }

    public static Universe Random(int n, int m) {
        Universe s = new Universe(n, m);
        Random r = new Random();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                byte value = (byte) (Math.abs(r.nextInt()) % 2);
                s.currentUniverse[i][j] = value;
            }
        }

        return s;
    }
}
