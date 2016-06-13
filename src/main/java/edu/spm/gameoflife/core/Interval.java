package edu.spm.gameoflife.core;

/**
 * GameOfLife
 *
 * @author Paolo Cifariello
 */
public class Interval {
    public final int startRow;
    public final int nRows;

    public Interval(int start, int n) {
        startRow = start;
        nRows = n;
    }
}
