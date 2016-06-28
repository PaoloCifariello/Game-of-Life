package edu.spm.gameoflife.skandium;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.skeletons.For;
import cl.niclabs.skandium.skeletons.Map;
import edu.spm.gameoflife.core.Space;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * GameOfLife
 *
 * @author Paolo Cifariello
 */
public class GameOfLifeSkandium {

    public static long start(Space space, int iterations, int nThreads) throws ExecutionException, InterruptedException {

        /* initialization for nThreads */
        Skandium s = new Skandium(nThreads);
        For<Space> forLoop =
                new For<Space>(
                        new Map<>(
                                new Splitter(nThreads),
                                new Executor(space),
                                new Merger(space)),
                        iterations);

        /* getting the output stream from the created skeleton */
        Stream<Space, Space> stream = s.newStream(forLoop);
        Future<Space> outSpace = stream.input(space);
        final long startTime = System.currentTimeMillis();
        outSpace.get();
        final long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }
}
