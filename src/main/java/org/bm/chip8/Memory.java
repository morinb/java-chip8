package org.bm.chip8;

/**
 * @author Baptiste Morin
 */
public class Memory implements Constants {
    private int[] memory = new int[MEMORY_SIZE];

    public Memory() {
    }

    public void reset() {
        for (int i = 0; i < memory.length; i++) {
            memory[i] = 0;
        }
    }

    public int get(int pc) {
        return memory[pc];
    }

}
