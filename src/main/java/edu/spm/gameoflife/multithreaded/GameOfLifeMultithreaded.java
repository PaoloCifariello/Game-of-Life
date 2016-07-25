package edu.spm.gameoflife.multithreaded;

import edu.spm.gameoflife.GameOfLifeComputation;
import edu.spm.gameoflife.core.Interval;
import edu.spm.gameoflife.core.Space;

import java.util.concurrent.*;

/**
 * GameOfLife
 *
 * @author Paolo Cifariello
 */
public class GameOfLifeMultithreaded implements GameOfLifeComputation {

    public long start(Space space, int nIterations, int nThreads) throws InterruptedException, BrokenBarrierException {
        CyclicBarrier barrier = new CyclicBarrier(nThreads, space::swap);
        ExecutorService pool = Executors.newFixedThreadPool(nThreads);

        final long startTime = System.currentTimeMillis();
        /* split the space into nThreads intervals */
        Interval[] invervals = space.split(nThreads);
        /* insert a new task/thread for each interval inside the thread pool */
        for (int j = 0; j < nThreads; j++){
            pool.submit(
                    new GameOfLifeWorker(space, invervals[j], nIterations, barrier)
            );
        }
        /* prevent newer tasks to be submitted */
        pool.shutdown();
        /* wait at most 2 minutes for termination of all tasks */
        pool.awaitTermination(2, TimeUnit.MINUTES);

        final long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
