package org.bm.chip8;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * @author Baptiste Morin
 */
public class Screen extends JPanel implements Constants {
    private Pixel[][] pixels = new Pixel[SCREEN_WIDTH_IN_PIXEL][SCREEN_HEIGHT_IN_PIXEL];
    private Rectangle clip = null;
    BufferedImage offscreen = Screen.createCompatibleImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

    public Screen() {
        clear();
    }

    public static BufferedImage createCompatibleImage(int width, int height, int type) {
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        return gc.createCompatibleImage(width, height, type);
    }

    @Override
    public int getWidth() {
        return SCREEN_WIDTH_IN_PIXEL * SCREEN_PIXEL_SIZE;
    }

    @Override
    public int getHeight() {
        return SCREEN_HEIGHT_IN_PIXEL * SCREEN_PIXEL_SIZE;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getWidth(), getHeight());
    }

    public void clear() {
        for (int x = 0; x < SCREEN_WIDTH_IN_PIXEL; x++) {
            for (int y = 0; y < SCREEN_HEIGHT_IN_PIXEL; y++) {
                Pixel p = new Pixel(new Point(x * SCREEN_PIXEL_SIZE, y * SCREEN_PIXEL_SIZE), BLACK);
                pixels[x][y] = p;
            }
        }
    }

    public Pixel[][] getPixels() {
        return pixels;
    }

    public Pixel getPixelAt(int x, int y) {
        return pixels[x][y];
    }

    @Override
    public void paint(Graphics g) {


        Graphics2D g2 = offscreen.createGraphics();

        if (clip != null) {
            g2.setClip(clip);
        }

        Pixel[][] pixels = getPixels();
        for (int x = 0; x < SCREEN_WIDTH_IN_PIXEL; x++) {
            for (int y = 0; y < SCREEN_HEIGHT_IN_PIXEL; y++) {
                Pixel p = pixels[x][y];
                g2.setPaint(COLORS[p.getColor()]);
                g2.fillRect(p.getX(), p.getY(), p.getWidth(), p.getHeight());
            }
        }

        clip = null;

        g2.dispose();
        g.drawImage(offscreen, 0, 0, null);
        g.dispose();
    }

    public void debugPixels() {
        for (int y = 0; y < SCREEN_HEIGHT_IN_PIXEL; y++) {
            for (int x = 0; x < SCREEN_WIDTH_IN_PIXEL; x++) {
                if (pixels[x][y].getColor() == BLACK) {
                    System.out.print(".");
                } else {
                    System.out.print("X");
                }
            }
            System.out.println();
        }
    }

}
