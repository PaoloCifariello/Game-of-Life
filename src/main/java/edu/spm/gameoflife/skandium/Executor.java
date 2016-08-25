package edu.spm.gameoflife.skandium;

import cl.niclabs.skandium.muscles.Execute;
import edu.spm.gameoflife.core.Interval;
import edu.spm.gameoflife.core.LifeSimulator;
import edu.spm.gameoflife.core.Universe;

/**
 * GameOfLife Executor
 *
 * @author Paolo Cifariello
 */
public class Executor implements Execute<Interval, Interval> {
    private Universe universe;

    public Executor(Universe universe) {
        this.universe = universe;
    }

    @Override
    public Interval execute(Interval interval) {
        LifeSimulator.makeCycle(universe, interval.startRow, interval.nRows);
        return interval;
    }
}