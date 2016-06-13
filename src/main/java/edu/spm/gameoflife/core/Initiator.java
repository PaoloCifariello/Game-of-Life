package edu.spm.gameoflife.core;

import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * GameOfLife
 *
 * @author Paolo Cifariello
 */
public class Initiator {

    public static Space init(String initiator, int n, int m) {
        Space s;
        int traslation = 20;

        if (initiator.equals("random")) {
            s = Space.Random(n, m);
        } else {
            s = new Space(n, m);
            s.initialize();

            initFromFile(s, initiator);
        }

        return s;
    }

    private static void initFromFile(Space space, String initiator) {
        try {
            InputStream is = Initiator.class.getClassLoader().getResourceAsStream(initiator + ".lif");
            CSVReader reader = new CSVReader(new InputStreamReader(is), ' ');
            String [] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                int x = Integer.parseInt(nextLine[0]) + 30,
                        y = Integer.parseInt(nextLine[1]) + 30;

                space.setCellValue(x, y, Space.ALIVE);
            }

            space.swap();
        } catch (IOException e) {
            System.err.println("Cannot load initiator");
        }
    }
}