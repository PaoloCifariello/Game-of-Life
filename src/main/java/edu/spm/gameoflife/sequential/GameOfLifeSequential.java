package edu.spm.gameoflife.sequential;

import edu.spm.gameoflife.core.LifeSimulator;
import edu.spm.gameoflife.core.Space;

public class GameOfLifeSequential {

    public static void start(Space space, int nIterations) {
        final long startTime = System.currentTimeMillis();
        for (int i = 0; i < nIterations; i++) {
            LifeSimulator.makeCycle(space);
            space.swap();
        }
        final long endTime = System.currentTimeMillis();

        System.out.println(endTime - startTime);
    }
}
