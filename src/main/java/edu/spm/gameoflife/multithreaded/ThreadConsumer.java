package edu.spm.gameoflife.multithreaded;

import edu.spm.gameoflife.core.LifeSimulator;
import edu.spm.gameoflife.core.Space;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * GameOfLife
 *
 * @author Paolo Cifariello
 */
public class ThreadConsumer implements Runnable {
    Space space;
    int startRow, nRows, iterations;
    private final CyclicBarrier barrier;

    public ThreadConsumer(Space s, int start, int n, int cycles, CyclicBarrier barrier) {
        space = s;
        startRow = start;
        nRows = n;
        iterations = cycles;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        for (int i = 0; i < iterations; i++) {
            LifeSimulator.makeCycle(space, startRow, nRows);
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException ex) { }
        }
    }
}
