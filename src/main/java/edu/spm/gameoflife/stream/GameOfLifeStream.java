package edu.spm.gameoflife.stream;

import edu.spm.gameoflife.GameOfLifeComputation;
import edu.spm.gameoflife.core.Interval;
import edu.spm.gameoflife.core.Universe;

import java.util.Arrays;
import java.util.concurrent.*;

public class GameOfLifeStream implements GameOfLifeComputation {

    public long start(Universe universe, int nIterations, int nThreads) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(nThreads);

        final long startTime = System.currentTimeMillis();
        Interval[] intervals = universe.split(nThreads);
        GameOfLifeExecutor golEx = new GameOfLifeExecutor(universe);

        for (int i = 0; i < nIterations; i++) {
            pool.submit(
                    () -> Arrays.stream(intervals)
                            .parallel()
                            .forEach(golEx::execute)).get();

            universe.swap();
        }
        // https://blog.krecan.net/2014/03/18/how-to-specify-thread-pool-for-java-8-parallel-streams/


        final long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }
}
