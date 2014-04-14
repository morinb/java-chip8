package org.bm.chip8;

/**
 * @author Baptiste Morin
 */
public class Memory implements Constants {
    private int[] memory = new int[MEMORY_SIZE]; // 8-bits

    public Memory() {
    }

    public void reset() {
        for (int i = 0; i < memory.length; i++) {
            memory[i] = 0;
        }
    }

    public void setMem(byte[] mem, int length) {
        int startSrc = 0;
        int startDest = MEMORY_START_ADDRESS;
        for(int i = startSrc, j = startDest ; i < startSrc + length ; i++, j++) {
            set(j, mem[i]&0xFF);
        }
    }

    public int get(int pc) {
        return memory[pc];
    }

    /**
     *
     * @param pc
     * @param value is automatically applied a X & 0xFF to restrain it to 8 bits.
     */
    public void set(int pc, int value) {
        checkBounds(pc);
        memory[pc] = value & 0xFF; // 8 bits.
    }

    private void checkBounds(int pc) {
        if (pc < 0 || pc > MEMORY_SIZE) {
            throw new IndexOutOfBoundsException("PC (" + Integer.toHexString(pc) + ") is out of bounds.");
        }
    }

}
