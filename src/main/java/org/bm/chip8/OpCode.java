package org.bm.chip8;

/**
 * @author Baptiste Morin
 */
public class OpCode {
    private final int mask;
    private final int id;

    public OpCode(int mask, int id) {
        this.mask = mask;
        this.id = id;
    }

    public int getMask() {
        return mask;
    }

    public int getId() {
        return id;
    }
}
