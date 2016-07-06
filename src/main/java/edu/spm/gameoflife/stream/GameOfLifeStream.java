package edu.spm.gameoflife.stream;

import edu.spm.gameoflife.core.Interval;
import edu.spm.gameoflife.core.LifeSimulator;
import edu.spm.gameoflife.core.Space;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameOfLifeStream {

    public static long start(Space space, int nIterations, int nThreads) throws ExecutionException, InterruptedException {
        final long startTime = System.currentTimeMillis();
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        GameOfLifeExecutor golEx = new GameOfLifeExecutor(space);
        Interval[] intervals = space.split(nThreads);

        for (int i = 0; i < nIterations; i++) {
            commonPool.submit(
                    () -> Arrays.stream(intervals)
                            .parallel()
                            .forEach(golEx::execute)).get();
            space.swap();
        }

        final long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }
}
