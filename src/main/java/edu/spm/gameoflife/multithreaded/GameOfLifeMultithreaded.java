package edu.spm.gameoflife.multithreaded;

import edu.spm.gameoflife.GameOfLifeComputation;
import edu.spm.gameoflife.core.Interval;
import edu.spm.gameoflife.core.Universe;

import java.util.concurrent.*;

/**
 * GameOfLife
 *
 * @author Paolo Cifariello
 */
public class GameOfLifeMultithreaded implements GameOfLifeComputation {

    public long start(Universe universe, int nIterations, int nThreads) throws InterruptedException, BrokenBarrierException {
        CyclicBarrier barrier = new CyclicBarrier(nThreads, universe::swap);
        ExecutorService pool = Executors.newFixedThreadPool(nThreads);

        final long startTime = System.currentTimeMillis();
        /* split the universe into nThreads intervals */
        Interval[] invervals = universe.split(nThreads);
        /* insert a new task/thread for each interval inside the thread pool */
        for (int j = 0; j < nThreads; j++){
            pool.submit(
                    new GameOfLifeWorker(universe, invervals[j], nIterations, barrier)
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
