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
    private int iterations;
    private CyclicBarrier barrier;

    public GameOfLifeExecutor(Space space, int iterations, CyclicBarrier barrier) {
        this.space = space;
        this.iterations = iterations;
        this.barrier = barrier;
    }

    public void execute(Interval interval) {
        for (int i = 0; i < iterations; i++) {
            LifeSimulator.makeCycle(space, interval.startRow, interval.nRows);
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException ex) { }
        }
    }
}
