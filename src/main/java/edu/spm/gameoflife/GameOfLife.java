package edu.spm.gameoflife;

import edu.spm.gameoflife.core.GameOfLifeGUI;
import edu.spm.gameoflife.core.Initiator;
import edu.spm.gameoflife.core.Space;
import edu.spm.gameoflife.multithreaded.GameOfLifeMultithreaded;
import edu.spm.gameoflife.sequential.GameOfLifeSequential;
import edu.spm.gameoflife.skandium.GameOfLifeSkandium;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ExecutionException;

/**
 * GameOfLife
 *
 * @author Paolo Cifariello
 */
public class GameOfLife {

    private final static String SEQUENTIAL = "sequential";
    private final static String MULTITHREADED = "multithreaded";
    private final static String SKANDIUM = "skandium";

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException, IOException, ExecutionException {
        int defaultIterations = 1000,
                defaultNthreads = Runtime.getRuntime().availableProcessors(),
                defaultRows = 100,
                defaultColumns = 100,
                defaultScale = 4;

        String defaultInitiator = "random",
                defaultComputation = "sequential";
        boolean defaultGraphic = false;

        int iterations,
                nThreads,
                rows,
                columns,
                scale;
        String initiator,
                computation;
        boolean graphic;

        Properties config = null;

        try {
            config = getProperties(args[0]);
        } catch (IOException | IndexOutOfBoundsException e) {
            System.err.println("Please provide a valid configuration file");
            System.exit(1);
        }

        iterations = Integer.parseInt(config.getProperty("ITERATIONS", String.valueOf(defaultIterations)));
        nThreads = Integer.parseInt(config.getProperty("NTHREADS", String.valueOf(defaultNthreads)));
        rows = Integer.parseInt(config.getProperty("ROWS", String.valueOf(defaultRows)));
        columns = Integer.parseInt(config.getProperty("COLUMNS", String.valueOf(defaultColumns)));
        scale = Integer.parseInt(config.getProperty("SCALE", String.valueOf(defaultScale)));
        initiator = config.getProperty("INITIATOR", defaultInitiator);
        graphic = Boolean.parseBoolean(config.getProperty("GRAPHIC", String.valueOf(defaultGraphic)));
        computation = config.getProperty("COMPUTATION", defaultComputation);

        Space space = Initiator.init(initiator, rows, columns);

        if (graphic)
            initGraphics(space, scale);

        long executionTime = 0;

        switch (computation) {
            case SEQUENTIAL:
                executionTime = GameOfLifeSequential.start(space, iterations);
                break;
            case MULTITHREADED:
                executionTime = GameOfLifeMultithreaded.start(space, iterations, nThreads);
                break;
            case SKANDIUM:
                executionTime = GameOfLifeSkandium.start(space, iterations, nThreads);
                break;
        }

        System.out.println("Execution time using " + computation + ": " + executionTime);
        System.exit(0);
    }

    private static Properties getProperties(String configFilename) throws IOException {
        File configFile = new File(configFilename);
        FileReader reader = new FileReader(configFile);
        Properties props = new Properties();

        // load the properties file:
        props.load(reader);
        return props;
    }

    private static void initGraphics(Space space, int scale) {
        JFrame frame = new JFrame("Game of Life");
        Graphics g = frame.getGraphics();
        frame.pack();
        Insets insets = frame.getInsets();
        frame.getContentPane().add(new GameOfLifeGUI(space, scale), BorderLayout.CENTER);
        frame.paint(g);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(insets.left + insets.right + (space.columns() * scale), insets.top + insets.bottom + (space.rows() * scale));
        frame.setVisible(true);
    }
}
