package edu.spm.gameoflife.core;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.nio.Buffer;
import java.util.Arrays;

/**
 * This class permits to draw the status of the GOL Space on the screen
 *
 * @author Paolo Cifariello
 */
public class GameOfLifeGUI extends JPanel {
    private final Space space;
    private int scale;
    private int offset;
    private final int aliveColor = 65000;
    private final int emptyColor = 0;


    public GameOfLifeGUI(Space s) {
        space = s;
    }

    public GameOfLifeGUI(Space s, int scale, int offset) {
        this(s);
        this.scale = scale;
        this.offset = offset;
    }
    private void updateScreen(Graphics g) throws InterruptedException {
        BufferedImage bufferedImage = new BufferedImage(space.columns() * scale, space.rows() * scale, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < space.rows(); i++) {
            for (int j = 0; j < space.columns(); j++) {
                fillRGB(space, i, j, bufferedImage);
            }
        }
        if (imageUpdate(bufferedImage, ImageObserver.FRAMEBITS, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight()))
            g.drawImage(bufferedImage, 0, 0, Color.BLACK, null);
    }

    private int getColor(byte value) {
        if (value == Space.ALIVE) {
            return aliveColor;
        } else {
            return emptyColor;
        }
    }

    private void fillRGB(Space space, int i, int j, BufferedImage bufferedImage) {
        int rgbValue = getColor(space.getCellValue(i, j)),
                startX = ((j + offset) % space.rows()) * scale,
                startY = ((i + offset) % space.columns()) * scale;

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