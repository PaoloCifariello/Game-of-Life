package edu.spm.gameoflife.stream;

import edu.spm.gameoflife.core.Interval;
import edu.spm.gameoflife.core.LifeSimulator;
import edu.spm.gameoflife.core.Space;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * GameOfLife
 *
 * @author Paolo Cifariello
 */
public class GameOfLifeExecutor {
    private Space space;

    public GameOfLifeExecutor(Space space) {
        this.space = space;
    }

    public void execute(Interval interval) {
        LifeSimulator.makeCycle(space, interval.startRow, interval.nRows);
    }
}
