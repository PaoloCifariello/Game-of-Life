package edu.spm.gameoflife.skandium;

import cl.niclabs.skandium.muscles.Split;
import edu.spm.gameoflife.core.Interval;
import edu.spm.gameoflife.core.Universe;

/**
 * GameOfLife
 *
 * @author Paolo Cifariello
 */
public class Splitter implements Split<Universe, Interval> {
    private int nThreads;
    Interval[] splitted = null;

    public Splitter(int nThreads) {
        this.nThreads = nThreads;
    }

    @Override
    public Interval[] split(Universe universe) {
        if (this.splitted != null)
            return this.splitted;

        this.splitted = universe.split(nThreads);
        return this.splitted;
    }
}
