package edu.spm.gameoflife.sequential;

import edu.spm.gameoflife.GameOfLifeComputation;
import edu.spm.gameoflife.core.LifeSimulator;
import edu.spm.gameoflife.core.Universe;

public class GameOfLifeSequential implements GameOfLifeComputation {

    public long start(Universe universe, int nIterations, int nThreads) {
        final long startTime = System.currentTimeMillis();
        for (int i = 0; i < nIterations; i++) {
            LifeSimulator.makeCycle(universe);
            universe.swap();
        }
        final long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }
}
