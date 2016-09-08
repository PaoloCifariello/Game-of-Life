package edu.spm.gameoflife.multithreaded;

import edu.spm.gameoflife.core.Interval;
import edu.spm.gameoflife.core.LifeSimulator;
import edu.spm.gameoflife.core.Universe;
import edu.spm.gameoflife.stream.GameOfLifeWorker;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * GameOfLife thread code, simply calls execute from stream code
 *
 * @author Paolo Cifariello
 */
public class GameOfLifeWorkerRunnable extends GameOfLifeWorker implements Runnable {
    protected Interval interval;

    public GameOfLifeWorkerRunnable(Universe universe, Interval interval, int nIterations, CyclicBarrier barrier) {
        super(universe, nIterations, barrier);
        this.interval = interval;
    }

    @Override
    public void run() {
        execute(interval);
    }
}