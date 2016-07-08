package edu.spm.gameoflife.core;

/**
 * This class contains information about an Interval of a GOL Space.
 * An instance identify a partition using a row index and the number of rows in that chunk.
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
