package edu.spm.gameoflife.skandium;

import cl.niclabs.skandium.muscles.Merge;
import edu.spm.gameoflife.core.Interval;
import edu.spm.gameoflife.core.Universe;

/**
 * GameOfLife Merger
 *
 * @author Paolo Cifariello
 */
public class Merger implements Merge<Interval, Universe> {
    private Universe universe;

    public Merger(Universe universe) {
        this.universe = universe;
    }

    @Override
    public Universe merge(Interval[] intervals) {
        universe.swap();
        return universe;
    }
}
