package org.bm.chip8;

/**
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
        if (registry < 0 || registry >= REGISTRY_NUMBER) {
            throw new IllegalArgumentException("There is only " + REGISTRY_NUMBER + " registries.");
        }

        return registries[registry];
    }
}
