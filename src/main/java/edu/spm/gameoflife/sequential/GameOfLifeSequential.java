package edu.spm.gameoflife.sequential;

import edu.spm.gameoflife.core.LifeSimulator;
import edu.spm.gameoflife.core.Space;

public class GameOfLifeSequential {

    public static void start(Space space, int nIterations) {
        for (int i = 0; i < nIterations; i++) {
            LifeSimulator.makeCycle(space);
            space.swap();
        }
    }
}
