package edu.spm.gameoflife.stream;

import edu.spm.gameoflife.core.LifeSimulator;
import edu.spm.gameoflife.core.Universe;

/**
 * GameOfLife
 *
 * @author Paolo Cifariello
 */
public class GameOfLifeWorker {
    protected Universe universe;

    public GameOfLifeWorker(Universe universe) {
        this.universe = universe;
    }

    public void execute(int rowIndex) {
        LifeSimulator.makeCycle(universe, rowIndex, 1);
    }
}
