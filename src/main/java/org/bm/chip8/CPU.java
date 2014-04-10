package org.bm.chip8;

/**
 * @author Baptiste Morin
 */
public class CPU implements Constants {
    private Memory memory = new Memory(); // 4K memory
    private Registries v = new Registries(); // 16 registries
    private int I; // stores a memory address or drawer.
    private Jumps jumps = new Jumps(); // to handle the number of jumps done, should never overcome 16.
    private Counter delayCounter = new Counter(); // synchronization counter
    private Counter soundCounter = new Counter(); // sound counter
    private int pc;

    private OpCodes opCodes = new OpCodes();

    public void initialize() {
        this.memory.reset();
        this.pc = MEMORY_START_ADDRESS;
        this.v.reset();
        this.jumps.reset();
        this.I = 0;
    }

    public void countdown() {
        if (delayCounter.isPositive()) {
            delayCounter.dec();
        }

        if (soundCounter.isPositive()) {
            soundCounter.dec();
        }
    }

    public int getOpCode() {
        return memory.get(this.pc) << 8 + memory.get(this.pc + 1);
    }

    public void interpretOpCode(int opCode) {
        int b4 = opCodes.getAction(opCode);

        switch (b4) {
            case 0: { /* 0NNN not implemented */

                break;
            }
            case 1: { /* 00E0 clear screen */
                break;
            }
            case 2: { /* 00EE come back from jump */
                break;
            }
            case 3: { /* 1NNN make jump to address NNN */
                break;
            }
            case 4: { /* 2NNN call sub routine at NNN, and came after*/
                break;
            }
            case 5: { /* 3XNN Skips the next instruction if VX equals NN. */
                break;
            }
            case 6: { /* 4XNN Skips the next instruction if VX doesn't equal NN. */
                break;
            }
            case 7: { /* 5XY0 Skips the next instruction if VX equals VY. */
                break;
            }
            case 8: { /* 6XNN Sets VX to NN. */
                break;
            }
            case 9: { /* 7XNN Adds NN to VX. */
                break;
            }
            case 10: {/* 8XY0 Sets VX to the value of VY. */
                break;
            }
            case 11: {/* 8XY1 Sets VX to VX or VY. */
                break;
            }
            case 12: {/* 8XY2 Sets VX to VX and VY. */
                break;
            }
            case 13: {/* 8XY3 Sets VX to VX xor VY.*/
                break;
            }
            case 14: {/* 8XY4 Adds VY to VX. VF is set to 1 when there's a carry, and to 0 when there isn't. */
                break;
            }
            case 15: {/* 8XY5 VY is subtracted from VX. VF is set to 0 when there's a borrow, and 1 when there isn't. */
                break;
            }
            case 16: {/* 8XY6 Shifts VX right by one. VF is set to the value of the least significant bit of VX before the shift. */
                break;
            }
            case 17: {/* 8XY7 Sets VX to VY minus VX. VF is set to 0 when there's a borrow, and 1 when there isn't. */
                break;
            }
            case 18: {/* 8XYE Shifts VX left by one. VF is set to the value of the most significant bit of VX before the shift. */
                break;
            }
            case 19: {/* 9XY0 Skips the next instruction if VX doesn't equal VY. */
                break;
            }
            case 20: {/* ANNN Sets I to the address NNN. */
                break;
            }
            case 21: {/* BNNN Jumps to the address NNN plus V0. */
                break;
            }
            case 22: {/* CXNN Sets VX to a random number and NN. */
                break;
            }
            case 23: {/* DXYN Draws a sprite at coordinate (VX, VY) that has a width of 8 pixels and a height of N pixels. Each row of 8 pixels is read as bit-coded (with the most significant bit of each byte displayed on the left) starting from memory location I; I value doesn't change after the execution of this instruction. As described above, VF is set to 1 if any screen pixels are flipped from set to unset when the sprite is drawn, and to 0 if that doesn't happen.*/
                break;
            }
            case 24: {/* EX9E Skips the next instruction if the key stored in VX is pressed. */
                break;
            }
            case 25: {/* EXA1 Skips the next instruction if the key stored in VX isn't pressed. */
                break;
            }
            case 26: {/* FX07 Sets VX to the value of the delay timer.*/
                break;
            }
            case 27: {/* FX0A A key press is awaited, and then stored in VX.*/
                break;
            }
            case 28: {/* FX15 Sets the delay timer to VX.*/
                break;
            }
            case 29: {/* FX18 Sets the sound timer to VX.*/
                break;
            }
            case 30: {/* FX1E Adds VX to I. */
                break;
            }
            case 31: {/* FX29 Sets I to the location of the sprite for the character in VX. Characters 0-F (in hexadecimal) are represented by a 4x5 font.*/
                break;
            }
            case 32: {/* FX33 Stores the Binary-coded decimal representation of VX, with the most significant of three digits at the address in I, the middle digit at I plus 1, and the least significant digit at I plus 2. (In other words, take the decimal representation of VX, place the hundreds digit in memory at location in I, the tens digit at location I+1, and the ones digit at location I+2.)*/
                break;
            }
            case 33: {/* FX55 Stores V0 to VX in memory starting at address I. */
                break;
            }
            case 34: {/* FX65 Fills V0 to VX with values from memory starting at address I.*/
                break;
            }
        }

    }

}
