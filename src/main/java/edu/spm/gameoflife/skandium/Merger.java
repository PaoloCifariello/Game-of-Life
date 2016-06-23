package edu.spm.gameoflife.skandium;

import cl.niclabs.skandium.muscles.Merge;
import edu.spm.gameoflife.core.Space;

/**
 * GameOfLife
 *
 * @author Paolo Cifariello
 */
public class Merger implements Merge<Space, Space> {
    private Space space;

    public Merger(Space space) {
        this.space = space;
    }

    @Override
    public Space merge(Space[] spaces) throws Exception {
        space.swap();
        return space;
    }
}
