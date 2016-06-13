package edu.spm.gameoflife.core;

import java.util.Random;

/**
 * GameOfLife
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

    public int rows() {
        return n;
    }

    public int columns() {
        return m;
    }

    public void initialize() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                currentSpace[i][j] = EMPTY;
                futureSpace[i][j] = EMPTY;
            }
        }
    }

    /**
     *
     * @param i
     * @param j
     * @return
     */
    public byte getCellValue(int i, int j) {
        return currentSpace[i][j];
    }

    /**
     *
     * @param i
     * @param j
     * @param value
     */
    public void setCellValue(int i, int j, byte value) {
        futureSpace[i][j] = value;
    }

    public void swap() {
        byte[][] oldSpace = currentSpace;
        currentSpace = futureSpace;
        futureSpace = oldSpace;
    }

    public Interval[] split(int partitions) {
        int rows = rows();

        int rowsPerPartition = rows / partitions;
        int extraRows = rows % partitions;
        int start = 0;
        int nRows;
        Interval[] bounds = new Interval[partitions];

        for (int j = 0; j < partitions; j++) {
            nRows = rowsPerPartition + (extraRows-- > 0 ? 1:0);
            bounds[j] = new Interval(start, nRows);
            start = start + nRows;
        }

        return bounds;
    }

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
