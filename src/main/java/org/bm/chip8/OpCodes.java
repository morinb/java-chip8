package org.bm.chip8;

/**
 * @author Baptiste Morin
 */
public class OpCodes implements Constants {
    private OpCode[] opCodes = new OpCode[OPCODES_NUMBER];

    public OpCodes() {
        int i = 0;
        opCodes[i++] = new OpCode(0x0000, 0x0FFF);  /* 0NNN */
        opCodes[i++] = new OpCode(0xFFFF, 0x00E0);  /* 00E0 */
        opCodes[i++] = new OpCode(0xFFFF, 0x00EE);  /* 00EE */
        opCodes[i++] = new OpCode(0xF000, 0x1000);  /* 1NNN */
        opCodes[i++] = new OpCode(0xF000, 0x2000);  /* 2NNN */
        opCodes[i++] = new OpCode(0xF000, 0x3000);  /* 3XNN */
        opCodes[i++] = new OpCode(0xF000, 0x4000);  /* 4XNN */
        opCodes[i++] = new OpCode(0xF00F, 0x5000);  /* 5XY0 */
        opCodes[i++] = new OpCode(0xF000, 0x6000);  /* 6XNN */
        opCodes[i++] = new OpCode(0xF000, 0x7000);  /* 7XNN */
        opCodes[i++] = new OpCode(0xF00F, 0x8000);  /* 8XY0 */
        opCodes[i++] = new OpCode(0xF00F, 0x8001);  /* 8XY1 */
        opCodes[i++] = new OpCode(0xF00F, 0x8002);  /* 8XY2 */
        opCodes[i++] = new OpCode(0xF00F, 0x8003);  /* BXY3 */
        opCodes[i++] = new OpCode(0xF00F, 0x8004);  /* 8XY4 */
        opCodes[i++] = new OpCode(0xF00F, 0x8005);  /* 8XY5 */
        opCodes[i++] = new OpCode(0xF00F, 0x8006);  /* 8XY6 */
        opCodes[i++] = new OpCode(0xF00F, 0x8007);  /* 8XY7 */
        opCodes[i++] = new OpCode(0xF00F, 0x800E);  /* 8XYE */
        opCodes[i++] = new OpCode(0xF00F, 0x9000);  /* 9XY0 */
        opCodes[i++] = new OpCode(0xF000, 0xA000);  /* ANNN */
        opCodes[i++] = new OpCode(0xF000, 0xB000);  /* BNNN */
        opCodes[i++] = new OpCode(0xF000, 0xC000);  /* CXNN */
        opCodes[i++] = new OpCode(0xF000, 0xD000);  /* DXYN */
        opCodes[i++] = new OpCode(0xF0FF, 0xE09E);  /* EX9E */
        opCodes[i++] = new OpCode(0xF0FF, 0xE0A1);  /* EXA1 */
        opCodes[i++] = new OpCode(0xF0FF, 0xF007);  /* FX07 */
        opCodes[i++] = new OpCode(0xF0FF, 0xF00A);  /* FX0A */
        opCodes[i++] = new OpCode(0xF0FF, 0xF015);  /* FX15 */
        opCodes[i++] = new OpCode(0xF0FF, 0xF018);  /* FX18 */
        opCodes[i++] = new OpCode(0xF0FF, 0xF01E);  /* FX1E */
        opCodes[i++] = new OpCode(0xF0FF, 0xF029);  /* FX29 */
        opCodes[i++] = new OpCode(0xF0FF, 0xF033);  /* FX33 */
        opCodes[i++] = new OpCode(0xF0FF, 0xF055);  /* FX55 */
        opCodes[i++] = new OpCode(0xF0FF, 0xF065);  /* FX65 */
    }

    public int getAction(int opCode) {
        int action;

        int result;

        for (action = 0; action < OPCODES_NUMBER; action++) {
            result = opCodes[action].getMask() & opCode; // gets only test concerned bits, opCode id.

            if (result == opCodes[action].getId()) { // we found the action to do.
                break; // no more iteration is required because loop is true only once.
            }
        }
        return action;
    }
}
