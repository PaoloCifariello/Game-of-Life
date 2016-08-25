package edu.spm.gameoflife.core;

import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This class permits to initialize a Universe in a random way or using a CSV file.
 * File must contains a list of x  y coordinates identifying ALIVE cells
 *
 * @author Paolo Cifariello
 */
public class Initiator {

    public static Universe init(String initiator, int n, int m) {
        Universe universe;

        if (initiator.equals("random")) {
            universe = Universe.Random(n, m);
        } else {
            universe = new Universe(n, m);

            /* set all cell states to EMPTY */
            universe.initialize();
            initFromFile(universe, initiator);
        }

        return universe;
    }

    private static void initFromFile(Universe universe, String initiator) {
        try {
            InputStream is = Initiator.class.getClassLoader().getResourceAsStream(initiator + ".lif");
            CSVReader reader = new CSVReader(new InputStreamReader(is), ' ');
            int rows = universe.rows(),
                columns = universe.columns();

            String [] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                int x = normalize(Integer.parseInt(nextLine[0]), columns),
                        y = normalize(Integer.parseInt(nextLine[1]), rows);

                universe.setCellValue(y, x, Universe.ALIVE);
            }

            universe.swap();
        } catch (IOException e) {
            System.err.println("Cannot load initiator");
        }
    }

    private static int normalize(int coordinate, int max) {
        while (coordinate < 0)
            coordinate += max;

        return coordinate % max;
    }
}
