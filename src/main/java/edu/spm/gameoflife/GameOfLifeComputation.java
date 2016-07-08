package edu.spm.gameoflife;

import edu.spm.gameoflife.core.Space;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ExecutionException;

/**
 * GameOfLife
 *
 * @author Paolo Cifariello
 */
public interface GameOfLifeComputation {
    long start(Space space, int nIterations, int nThreads) throws InterruptedException, BrokenBarrierException, ExecutionException;
}
