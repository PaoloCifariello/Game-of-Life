package edu.spm.gameoflife.multithreaded;

import edu.spm.gameoflife.core.Interval;
import edu.spm.gameoflife.core.Space;

import java.util.concurrent.*;

/**
 * GameOfLife
 *
 * @author Paolo Cifariello
 */
public class GameOfLifeMultithreaded {

    public static long start(Space space, int iterations, int nThreads) throws InterruptedException, BrokenBarrierException {
        final CyclicBarrier barrier = new CyclicBarrier(nThreads, space::swap);
        ExecutorService pool = Executors.newFixedThreadPool(nThreads);

        final long startTime = System.currentTimeMillis();
        /* split the space into nThreads intervals */
        Interval[] bounds = space.split(nThreads);
        /* insert a new task/thread for each interval inside the thread pool */
        for (int j = 0; j < nThreads; j++){
            pool.submit(new GameOfLifeWorker(space, bounds[j].startRow, bounds[j].nRows, iterations, barrier));
        }

        /* prevent newer tasks to be submitted */
        pool.shutdown();
        /* wait at most 2 minutes for termination of all tasks */
        pool.awaitTermination(2, TimeUnit.MINUTES);

        final long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
