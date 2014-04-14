package org.bm.chip8;

/**
 * @author Baptiste Morin
 */
public class Buttons implements Constants {
    private int[] buttons = new int[16];

    void press(int button) {
        buttons[button] = 1;
    }

    void release(int button) {
        buttons[button] = 0;
    }

    int get(int button) {
        return buttons[button];
    }
}
