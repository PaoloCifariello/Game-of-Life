package edu.spm.gameoflife.multithreaded;

import edu.spm.gameoflife.core.Interval;
import edu.spm.gameoflife.core.LifeSimulator;
import edu.spm.gameoflife.core.Universe;
import edu.spm.gameoflife.stream.GameOfLifeExecutor;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * GameOfLife
 *
 * @author Paolo Cifariello
 */
public class GameOfLifeWorker extends GameOfLifeExecutor implements Runnable {

    public GameOfLifeWorker(Universe universe, Interval interval, int nIterations, CyclicBarrier barrier) {
        super(universe, interval, nIterations, barrier);
    }

    @Override
    public void run() {
        this.execute(this.interval);
    }
}
