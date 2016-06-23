package edu.spm.gameoflife.skandium;

import cl.niclabs.skandium.muscles.Split;
import edu.spm.gameoflife.core.Interval;
import edu.spm.gameoflife.core.Space;

/**
 * GameOfLife
 *
 * @author Paolo Cifariello
 */
public class Splitter implements Split<Space, Interval> {
    private int nThreads;
    Interval[] splitted = null;

    public Splitter(int nThreads) {
        this.nThreads = nThreads;
    }

    @Override
    public Interval[] split(Space space) throws Exception {
        if (this.splitted != null)
            return this.splitted;

        this.splitted = space.split(nThreads);
        return this.splitted;
    }
}
