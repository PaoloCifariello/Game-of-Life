package edu.spm.gameoflife.sequential;

import edu.spm.gameoflife.GameOfLifeComputation;
import edu.spm.gameoflife.core.LifeSimulator;
import edu.spm.gameoflife.core.Space;

public class GameOfLifeSequential implements GameOfLifeComputation {

    public long start(Space space, int nIterations, int nThreads) {
        final long startTime = System.currentTimeMillis();
        for (int i = 0; i < nIterations; i++) {
            LifeSimulator.makeCycle(space);
            space.swap();
        }
        final long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }
}
