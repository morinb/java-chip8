package org.bm.chip8;

import java.awt.Point;

/**
 * @author Baptiste Morin
 */
public class Pixel implements Constants{
    private Point coordinates = new Point();
    private int color;


    public Pixel(Point coordinates, int color) {
        this.coordinates = coordinates;
        this.color = color;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getX() {
        return coordinates.x;
    }

    public int getY() {
        return coordinates.y;
    }

    public int getWidth() {
        return SCREEN_PIXEL_SIZE;
    }

    public int getHeight() {
        return SCREEN_PIXEL_SIZE;
    }
}
