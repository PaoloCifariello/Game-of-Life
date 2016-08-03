package edu.spm.gameoflife.stream;

import edu.spm.gameoflife.core.Interval;
import edu.spm.gameoflife.core.LifeSimulator;
import edu.spm.gameoflife.core.Universe;

/**
 * GameOfLife
 *
 * @author Paolo Cifariello
 */
public class GameOfLifeExecutor {
    private Universe universe;

    public GameOfLifeExecutor(Universe universe) {
        this.universe = universe;
    }

    public void execute(Interval interval) {
        LifeSimulator.makeCycle(universe, interval.startRow, interval.nRows);
    }
}
