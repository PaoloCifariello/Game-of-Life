package edu.spm.gameoflife.stream;

import edu.spm.gameoflife.GameOfLifeComputation;
import edu.spm.gameoflife.core.Interval;
import edu.spm.gameoflife.core.Universe;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class GameOfLifeStream implements GameOfLifeComputation {

    public long start(Universe universe, int nIterations, int nThreads) throws ExecutionException, InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(nThreads, universe::swap);
        ExecutorService pool = Executors.newFixedThreadPool(nThreads);

        final long startTime = System.currentTimeMillis();
        GameOfLifeWorker golEx = new GameOfLifeWorker(universe, nIterations, barrier);

        pool.submit(() -> universe.parallelStream(nThreads).forEach(golEx::execute)).get();
        // https://blog.krecan.net/2014/03/18/how-to-specify-thread-pool-for-java-8-parallel-streams/

        /* prevent newer tasks to be submitted */
        pool.shutdown();
        /* wait at most 2 minutes for termination of all tasks */
        pool.awaitTermination(2, TimeUnit.MINUTES);

        final long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
