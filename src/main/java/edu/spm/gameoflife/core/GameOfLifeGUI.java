package edu.spm.gameoflife.core;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * This class permits to draw the status of the GOL Universe on the screen
 *
 * @author Paolo Cifariello
 */
public class GameOfLifeGUI extends JPanel {
    private final Universe universe;
    private int scale;
    private int offset;
    private final int aliveColor = 65000;
    private final int emptyColor = 0;


    public GameOfLifeGUI(Universe s) {
        universe = s;
    }

    public GameOfLifeGUI(Universe s, int scale, int offset) {
        this(s);
        this.scale = scale;
        this.offset = offset;
    }
    private void updateScreen(Graphics g) throws InterruptedException {
        BufferedImage bufferedImage = new BufferedImage(universe.columns() * scale, universe.rows() * scale, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < universe.rows(); i++) {
            for (int j = 0; j < universe.columns(); j++) {
                fillRGB(universe, i, j, bufferedImage);
            }
        }
        if (imageUpdate(bufferedImage, ImageObserver.FRAMEBITS, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight()))
            g.drawImage(bufferedImage, 0, 0, Color.BLACK, null);
    }

    private int getColor(byte value) {
        if (value == Universe.ALIVE) {
            return aliveColor;
        } else {
            return emptyColor;
        }
    }

    private void fillRGB(Universe universe, int i, int j, BufferedImage bufferedImage) {
        int rgbValue = getColor(universe.getCellValue(i, j)),
                startX = ((j + offset) % universe.rows()) * scale,
                startY = ((i + offset) % universe.columns()) * scale;

        for (int y = startY; y < startY + scale; y++) {
            for (int x = startX; x < startX + scale; x++) {
                bufferedImage.setRGB(x, y, rgbValue);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            updateScreen(g);
        } catch (InterruptedException ex) {
        }
    }
}