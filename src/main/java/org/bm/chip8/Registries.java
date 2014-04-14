package org.bm.chip8;

/**
 * 8 bits registry
 * @author Baptiste Morin
 */
public class Registries implements Constants {
    private int[] registries = new int[REGISTRY_NUMBER];

    public void reset() {
        for (int i = 0; i < REGISTRY_NUMBER; i++) {
            registries[i] = 0;
        }
    }

    int get(int registry) {
        checkBounds(registry);

        return registries[registry];
    }


    /**
     *
     * @param registry
     * @param value is automatically applied a X & 0xFF, to restrain it to 8 bits.
     */
    void set(int registry, int value) {
        checkBounds(registry);
        this.registries[registry] = value & 0xFF;
    }

    private void checkBounds(int registry) {
        if (registry < 0 || registry >= REGISTRY_NUMBER) {
            throw new IndexOutOfBoundsException("There is only " + REGISTRY_NUMBER + " registries.");
        }
    }

}
