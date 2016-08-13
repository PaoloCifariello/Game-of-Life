package edu.spm.gameoflife.stream;

import edu.spm.gameoflife.core.Interval;
import edu.spm.gameoflife.core.LifeSimulator;
import edu.spm.gameoflife.core.Universe;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * GameOfLife
 *
 * @author Paolo Cifariello
 */
public class GameOfLifeExecutor {
    protected Universe universe;
    protected Interval interval;
    protected int nIterations;
    protected final CyclicBarrier barrier;

    public GameOfLifeExecutor(Universe universe, Interval interval, int nIterations , CyclicBarrier barrier) {
        this(universe, nIterations, barrier);
        this.interval = interval;
    }

    public GameOfLifeExecutor(Universe universe, int nIterations , CyclicBarrier barrier) {
        this.universe = universe;
        this.nIterations = nIterations ;
        this.barrier = barrier;
    }

    public void execute(Interval interval) {
        for (int i = 0; i < nIterations; i++) {
            LifeSimulator.makeCycle(universe, interval.startRow, interval.nRows);
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException ex) { }
        }

    }
}
