package edu.spm.gameoflife.multithreaded;

import edu.spm.gameoflife.core.Interval;
import edu.spm.gameoflife.core.LifeSimulator;
import edu.spm.gameoflife.core.Universe;
import edu.spm.gameoflife.stream.GameOfLifeWorker;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * GameOfLife
 *
 * @author Paolo Cifariello
 */
public class GameOfLifeWorkerRunnable extends GameOfLifeWorker implements Runnable {
    protected Interval interval;
    protected int nIterations;
    protected CyclicBarrier barrier;

    public GameOfLifeWorkerRunnable(Universe universe, Interval interval, int nIterations, CyclicBarrier barrier) {
        super(universe);
        this.interval = interval;
        this.nIterations = nIterations;
        this.barrier = barrier;
    }

    @Override
    public void run() {
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