package edu.spm.gameoflife.core;

import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This class permits to initialize a Space in a random way or using a CSV file.
 * File must contains a list of x  y coordinates identifying ALIVE cells
 *
 * @author Paolo Cifariello
 */
public class Initiator {

    public static Space init(String initiator, int n, int m) {
        Space s;

        if (initiator.equals("random")) {
            s = Space.Random(n, m);
        } else {
            s = new Space(n, m);
            s.initialize();

            /* set all cell to EMPTY */
            initFromFile(s, initiator);
        }

        return s;
    }

    private static void initFromFile(Space space, String initiator) {
        try {
            InputStream is = Initiator.class.getClassLoader().getResourceAsStream(initiator + ".lif");
            CSVReader reader = new CSVReader(new InputStreamReader(is), ' ');
            int rows = space.rows(),
                columns = space.columns();

            String [] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                int x = normalize(Integer.parseInt(nextLine[0]), columns),
                        y = normalize(Integer.parseInt(nextLine[1]), rows);

                space.setCellValue(y, x, Space.ALIVE);
            }

            space.swap();
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
