package org.bm.chip8;

/**
 * @author Baptiste Morin
 */
public class Jumps implements Constants {
    private int[] jumps = new int[JUMP_MAX_NUMBER];

    private int jumpNumber = 0;

    public Jumps() {
    }

    int getJump() {
        return jumps[jumpNumber];
    }

    public void reset() {
        for (int i = 0; i < JUMP_MAX_NUMBER; i++) {
            jumps[i] = 0;
        }
        jumpNumber = 0;
    }


    public void inc(int increment) {
        checkValidityPlus(increment);
        jumpNumber += increment;
    }

    public void inc() {
        inc(1);
    }

    public void dec(int decrement) {
        checkValidityMinus(decrement);
        jumpNumber -= decrement;
    }

    public void dec() {
        dec(1);
    }

    private void checkValidityMinus(int decrement) {
        if (jumpNumber - decrement < 0) {
            throw new IllegalArgumentException("Not so many jumps available");
        }
    }

    private void checkValidityPlus(int increment) {
        if (jumpNumber + increment > JUMP_MAX_NUMBER) {
            throw new IllegalArgumentException("Too many jumps.");
        }
    }

}
