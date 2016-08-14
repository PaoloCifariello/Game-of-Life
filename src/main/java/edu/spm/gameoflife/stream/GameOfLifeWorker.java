package edu.spm.gameoflife.stream;

import edu.spm.gameoflife.core.Interval;
import edu.spm.gameoflife.core.LifeSimulator;
import edu.spm.gameoflife.core.Universe;

import java.util.concurrent.CyclicBarrier;

/**
 * GameOfLife
 *
 * @author Paolo Cifariello
 */
public class GameOfLifeWorker {
    protected Universe universe;
    protected int nIterations;
    protected CyclicBarrier barrier;

    public GameOfLifeWorker(Universe universe, int nIterations, CyclicBarrier barrier) {
        this.universe = universe;
        this.nIterations = nIterations;
        this.barrier = barrier;
    }

    protected void execute(Interval interval) {
        for (int i = 0; i < nIterations; i++) {
            LifeSimulator.makeCycle(universe, interval.startRow, interval.nRows);
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
