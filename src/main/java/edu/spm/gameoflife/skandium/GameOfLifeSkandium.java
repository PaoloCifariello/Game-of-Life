package edu.spm.gameoflife.skandium;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.skeletons.For;
import cl.niclabs.skandium.skeletons.Map;
import edu.spm.gameoflife.GameOfLifeComputation;
import edu.spm.gameoflife.core.Universe;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * GameOfLife
 *
 * @author Paolo Cifariello
 */
public class GameOfLifeSkandium implements GameOfLifeComputation {

    public long start(Universe universe, int nIterations, int nThreads) throws ExecutionException, InterruptedException {

        final long startTime = System.currentTimeMillis();

        Skandium s = new Skandium(nThreads);
        For<Universe> forLoop =
                new For<Universe>(
                        new Map<>(
                                new Splitter(nThreads),
                                new Executor(universe),
                                new Merger(universe)),
                        nIterations);

        /* getting the output stream from the created skeleton */
        Stream<Universe, Universe> stream = s.newStream(forLoop);
        Future<Universe> outUniverse = stream.input(universe);

        outUniverse.get();
        final long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }
}
