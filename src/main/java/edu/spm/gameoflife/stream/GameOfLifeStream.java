package edu.spm.gameoflife.stream;

import edu.spm.gameoflife.GameOfLifeComputation;
import edu.spm.gameoflife.core.Interval;
import edu.spm.gameoflife.core.LifeSimulator;
import edu.spm.gameoflife.core.Space;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameOfLifeStream implements GameOfLifeComputation {

    public long start(Space space, int nIterations, int nThreads) throws ExecutionException, InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(nThreads, space::swap);
        ExecutorService pool = Executors.newFixedThreadPool(nThreads);

        final long startTime = System.currentTimeMillis();
        Interval[] intervals = space.split(nThreads);

        // https://blog.krecan.net/2014/03/18/how-to-specify-thread-pool-for-java-8-parallel-streams/
        pool.submit(
                () -> Arrays.stream(intervals)
                        .parallel()
                        .forEach(new GameOfLifeExecutor(space, nIterations, barrier)::execute)).get();

        final long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }
}
