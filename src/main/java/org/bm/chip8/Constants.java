package org.bm.chip8;

import java.awt.Color;

/**
 * @author Baptiste Morin
 */
public interface Constants {
    //
    int JUMP_MAX_NUMBER = 16;

    //
    int MEMORY_SIZE = 4096;
    int MEMORY_START_ADDRESS = 512;

    //
    int REGISTRY_NUMBER = 0x10;

    //
    int SCREEN_WIDTH_IN_PIXEL = 64;
    int SCREEN_HEIGHT_IN_PIXEL = 32;

    int SCREEN_PIXEL_SIZE = 8;

    //
    Color[] COLORS = new Color[]{Color.BLACK, Color.WHITE};
    int BLACK = 0;
    int WHITE = 1;

    //
    int FPS_SLEEP_TIME = 16; // 16 ms. for refresh
    int CPU_SPEED = 4; // number of operation by cycle.

    //
    int OPCODES_NUMBER = 35;
}

