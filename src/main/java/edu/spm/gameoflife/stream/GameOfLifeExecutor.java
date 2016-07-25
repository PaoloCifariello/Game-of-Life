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
    private int nIterations;
    private CyclicBarrier barrier;

    public GameOfLifeExecutor(Space space, int nIterations, CyclicBarrier barrier) {
        this.space = space;
        this.nIterations = nIterations;
        this.barrier = barrier;
    }

    public void execute(Interval interval) {
        for (int i = 0; i < nIterations; i++) {
            LifeSimulator.makeCycle(space, interval.startRow, interval.nRows);
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException ex) { }
        }
    }
}
