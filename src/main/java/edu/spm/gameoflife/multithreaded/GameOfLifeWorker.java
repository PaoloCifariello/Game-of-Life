package edu.spm.gameoflife.multithreaded;

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
public class GameOfLifeWorker implements Runnable {
    Universe universe;
    int startRow, nRows, nIterations;
    private final CyclicBarrier barrier;

    public GameOfLifeWorker(Universe s, Interval bounds, int cycles, CyclicBarrier barrier) {
        universe = s;
        startRow = bounds.startRow;
        nRows = bounds.nRows;
        nIterations = cycles;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        for (int i = 0; i < nIterations; i++) {
            LifeSimulator.makeCycle(universe, startRow, nRows);
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException ex) { }
        }
    }
}
