package org.bm.chip8;

/**
 * @author Baptiste Morin
 */
public class Counter {
    private int value;


    public void reset() {
        value = 0;
    }

    public void set(int value) {
        this.value = value;
    }

    public void dec() {
        value--;
    }

    public boolean isPositive() {
        return value > 0;
    }
}
