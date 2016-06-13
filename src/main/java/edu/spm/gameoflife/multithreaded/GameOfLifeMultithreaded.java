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

    public static void start(Space space, int iterations, int NTHREADS) throws InterruptedException, BrokenBarrierException {
        Interval[] bounds = space.split(NTHREADS);
        final CyclicBarrier barrier = new CyclicBarrier(NTHREADS, space::swap);
        ExecutorService threadPool = Executors.newFixedThreadPool(NTHREADS);

        for (int j = 0; j < NTHREADS; j++){
            threadPool.execute(new ThreadConsumer(space, bounds[j].startRow, bounds[j].nRows, iterations, barrier));
        }

        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.MINUTES);
    }
}
