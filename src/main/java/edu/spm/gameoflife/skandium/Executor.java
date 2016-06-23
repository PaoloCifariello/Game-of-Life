package edu.spm.gameoflife.skandium;

import cl.niclabs.skandium.muscles.Execute;
import cl.niclabs.skandium.skeletons.Skeleton;
import cl.niclabs.skandium.skeletons.SkeletonVisitor;
import edu.spm.gameoflife.core.Interval;
import edu.spm.gameoflife.core.LifeSimulator;
import edu.spm.gameoflife.core.Space;

import java.util.concurrent.Future;

/**
 * GameOfLife
 *
 * @author Paolo Cifariello
 */
public class Executor implements Execute<Interval, Space> {
    private Space space;

    public Executor(Space space) {
        this.space = space;
    }

    @Override
    public Space execute(Interval interval) throws Exception {
        LifeSimulator.makeCycle(space, interval.startRow, interval.nRows);
        return space;
    }
}