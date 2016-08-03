package edu.spm.gameoflife.skandium;

import cl.niclabs.skandium.muscles.Merge;
import edu.spm.gameoflife.core.Universe;

/**
 * GameOfLife
 *
 * @author Paolo Cifariello
 */
public class Merger implements Merge<Universe, Universe> {
    private Universe universe;

    public Merger(Universe universe) {
        this.universe = universe;
    }

    @Override
    public Universe merge(Universe[] universes) throws Exception {
        universe.swap();
        return universe;
    }
}
