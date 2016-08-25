package edu.spm.gameoflife.stream;

import edu.spm.gameoflife.GameOfLifeComputation;
import edu.spm.gameoflife.core.Interval;
import edu.spm.gameoflife.core.Universe;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * GameOfLife stream computation
 *
 * @author Paolo Cifariello
 */
public class GameOfLifeStream implements GameOfLifeComputation {

    public long start(Universe universe, int nIterations, int nThreads) throws ExecutionException, InterruptedException {

        final long startTime = System.currentTimeMillis();

        CyclicBarrier barrier = new CyclicBarrier(nThreads, universe::swap);
        ExecutorService pool = Executors.newFixedThreadPool(nThreads);


        // https://blog.krecan.net/2014/03/18/how-to-specify-thread-pool-for-java-8-parallel-streams/
        GameOfLifeWorker golEx = new GameOfLifeWorker(universe, nIterations, barrier);
        pool.submit(() -> universe.parallelStream(nThreads).forEach(golEx::execute)).get();

        /* prevent newer tasks to be submitted */
        pool.shutdown();
        /* wait at most 15 minutes for termination of all tasks */
        pool.awaitTermination(15, TimeUnit.MINUTES);

        final long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
