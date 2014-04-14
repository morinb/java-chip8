package org.bm.chip8;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Queue;
import java.util.Random;

import com.google.common.collect.Queues;

/**
 * @author Baptiste Morin
 */
public class CPU implements Constants {
    private Memory memory = new Memory(); // 4K memory
    private Registries V = new Registries(); // 16 8-bits registries
    private int I; // stores a memory address or drawer.
    private Jumps jumps = new Jumps(); // to handle the number of jumps done, should never overcome 16.
    private Counter delayCounter = new Counter(); // synchronization counter
    private Counter soundCounter = new Counter(); // sound counter
    private int pc; // 16bit variable
    private Buttons buttons = new Buttons();
    private Queue<KeyEvent> eventQueue = Queues.newArrayDeque();

    public Queue<KeyEvent> getEventQueue() {
        return eventQueue;
    }


    private Random rand = new Random(System.currentTimeMillis());

    private Screen screen;

    private OpCodes opCodes = new OpCodes();

    public CPU(Screen screen) {
        if (screen == null) {
            throw new NullPointerException("screen must not be null.");
        }
        this.screen = screen;
    }

    public void initialize() {
        this.memory.reset();
        this.pc = MEMORY_START_ADDRESS;
        this.V.reset();
        this.jumps.reset();
        this.I = 0;
        loadFonts();
    }

    private void loadFonts() {
        int i = 0;
        // char 0-
        this.memory.set(i++, 0b11110000);
        this.memory.set(i++, 0b10010000);
        this.memory.set(i++, 0b10010000);
        this.memory.set(i++, 0b10010000);
        this.memory.set(i++, 0b11110000);

        // char 1
        this.memory.set(i++, 0b00100000);
        this.memory.set(i++, 0b01100000);
        this.memory.set(i++, 0b00100000);
        this.memory.set(i++, 0b00100000);
        this.memory.set(i++, 0b01110000);

        // char 2
        this.memory.set(i++, 0b11110000);
        this.memory.set(i++, 0b00010000);
        this.memory.set(i++, 0b11110000);
        this.memory.set(i++, 0b10000000);
        this.memory.set(i++, 0b11110000);

        // char 3
        this.memory.set(i++, 0b11110000);
        this.memory.set(i++, 0b00010000);
        this.memory.set(i++, 0b11110000);
        this.memory.set(i++, 0b00010000);
        this.memory.set(i++, 0b11110000);

        // char 4
        this.memory.set(i++, 0b10010000);
        this.memory.set(i++, 0b10010000);
        this.memory.set(i++, 0b11110000);
        this.memory.set(i++, 0b00010000);
        this.memory.set(i++, 0b00010000);

        // char 5
        this.memory.set(i++, 0b11110000);
        this.memory.set(i++, 0b10000000);
        this.memory.set(i++, 0b11110000);
        this.memory.set(i++, 0b00010000);
        this.memory.set(i++, 0b11110000);

        // char 6
        this.memory.set(i++, 0b11110000);
        this.memory.set(i++, 0b10000000);
        this.memory.set(i++, 0b11110000);
        this.memory.set(i++, 0b10010000);
        this.memory.set(i++, 0b11110000);

        // char 7
        this.memory.set(i++, 0b11110000);
        this.memory.set(i++, 0b00010000);
        this.memory.set(i++, 0b00100000);
        this.memory.set(i++, 0b01000000);
        this.memory.set(i++, 0b01000000);

        // char 8
        this.memory.set(i++, 0b11110000);
        this.memory.set(i++, 0b10010000);
        this.memory.set(i++, 0b11110000);
        this.memory.set(i++, 0b10010000);
        this.memory.set(i++, 0b11110000);

        // char 9
        this.memory.set(i++, 0b11110000);
        this.memory.set(i++, 0b10010000);
        this.memory.set(i++, 0b11110000);
        this.memory.set(i++, 0b00010000);
        this.memory.set(i++, 0b11110000);

        // char A
        this.memory.set(i++, 0b11110000);
        this.memory.set(i++, 0b10010000);
        this.memory.set(i++, 0b11110000);
        this.memory.set(i++, 0b10010000);
        this.memory.set(i++, 0b10010000);

        // char B
        this.memory.set(i++, 0b11100000);
        this.memory.set(i++, 0b10010000);
        this.memory.set(i++, 0b11100000);
        this.memory.set(i++, 0b10010000);
        this.memory.set(i++, 0b11100000);

        // char C
        this.memory.set(i++, 0b11110000);
        this.memory.set(i++, 0b10000000);
        this.memory.set(i++, 0b10000000);
        this.memory.set(i++, 0b10000000);
        this.memory.set(i++, 0b01110000);

        // char D
        this.memory.set(i++, 0b11100000);
        this.memory.set(i++, 0b10010000);
        this.memory.set(i++, 0b10010000);
        this.memory.set(i++, 0b10010000);
        this.memory.set(i++, 0b11100000);

        // char E
        this.memory.set(i++, 0b11110000);
        this.memory.set(i++, 0b10000000);
        this.memory.set(i++, 0b11110000);
        this.memory.set(i++, 0b10000000);
        this.memory.set(i++, 0b11110000);

        // char F
        this.memory.set(i++, 0b11110000);
        this.memory.set(i++, 0b10000000);
        this.memory.set(i++, 0b11110000);
        this.memory.set(i++, 0b10000000);
        this.memory.set(i++, 0b10000000);

    }

    public void countdown() {
        if (delayCounter.isPositive()) {
            delayCounter.dec();
        }

        if (soundCounter.isPositive()) {
            soundCounter.dec();
        }
    }

    public boolean loadGame(InputStream is) throws IOException {
        byte[] game = new byte[MEMORY_SIZE - MEMORY_START_ADDRESS];
        int read = is.read(game);
        // stores it into memory.
        this.memory.setMem(game, read);

        return true;
    }

    public int getOpCode() {
        return ((memory.get(this.pc) << 8) + (memory.get(this.pc + 1))) & 0xFFFF;
    }

    public void pressButton(int button) {
        buttons.press(button);
    }

    public void releaseButton(int button) {
        buttons.release(button);
    }

    public void interpretOpCode(int opCode) {
        int b4 = opCodes.getAction(opCode);
        int b3 = ((opCode & (0x0F00)) >> 8) & 0xF; // take the 4 most significant bits, b3 means X
        int b2 = ((opCode & (0x00F0)) >> 4) & 0xF; // b2 means Y
        int b1 = ((opCode & (0x000F))) & 0xF; // b1 is the 4 least significant bits.
        // NNN is (b3 << 8) + (b2 << 4) + (b1)

        //System.out.println(String.format("%s : %d %d %d", Integer.toHexString(opCode), b3, b2, b1));
        switch (b4) {
            case 0: { /* 0NNN not implemented */

                break;
            }
            case 1: { /* 00E0 clear screen */
                clearScreen();
                break;
            }
            case 2: { /* 00EE come back from jump */
                this.jumps.dec();
                this.pc = this.jumps.getJump();
                break;
            }
            case 3: { /* 1NNN make jump to address NNN */
                this.pc = (b3 << 8) + (b2 << 4) + b1; // set pc to NNN value.
                this.pc -= 2; // because pc will be incremented by 2 at the end of the switch block.
                break;
            }
            case 4: { /* 2NNN call sub routine at NNN, and came back after*/
                this.jumps.set(this.pc); // stores the current pc.
                this.jumps.inc();

                this.pc = (b3 << 8) + (b2 << 4) + b1; // make pc equals NNN
                this.pc -= 2; // because pc will be incremented by 2 at the end of the switch block.

                break;
            }
            case 5: { /* 3XNN Skips the next instruction if VX equals NN. */
                if (this.V.get(b3) == ((b2 << 4) + b1)) {
                    this.pc += 2; // skip next instruction
                }

                break;
            }
            case 6: { /* 4XNN Skips the next instruction if VX doesn't equal NN. */
                if (this.V.get(b3) != ((b2 << 4) + b1)) {
                    this.pc += 2;
                }
                break;
            }
            case 7: { /* 5XY0 Skips the next instruction if VX equals VY. */
                if (this.V.get(b3) == this.V.get(b2)) {
                    this.pc += 2;
                }
                break;
            }
            case 8: { /* 6XNN Sets VX to NN. */
                this.V.set(b3, (b2 << 4) + b1);
                break;
            }
            case 9: { /* 7XNN Adds NN to VX. */
                this.V.set(b3, this.V.get(b3) + (b2 << 4) + b1);
                break;
            }
            case 10: {/* 8XY0 Sets VX to the value of VY. */
                this.V.set(b3, this.V.get(b2));
                break;
            }
            case 11: {/* 8XY1 Sets VX to VX or VY. */
                this.V.set(b3, this.V.get(b3) | this.V.get(b2));
                break;
            }
            case 12: {/* 8XY2 Sets VX to VX and VY. */
                this.V.set(b3, this.V.get(b3) & this.V.get(b2));
                break;
            }
            case 13: {/* 8XY3 Sets VX to VX xor VY.*/
                this.V.set(b3, this.V.get(b3) ^ this.V.get(b2));
                break;
            }
            case 14: {/* 8XY4 Adds VY to VX. VF is set to 1 when there's a carry, and to 0 when there isn't. */
                int sum = (this.V.get(b2) + this.V.get(b3));
                if (sum > 0xFF) {
                    this.V.set(0xF, 1);
                } else {
                    this.V.set(0xF, 0);
                }
                this.V.set(b3, sum);
                break;
            }
            case 15: {/* 8XY5 VY is subtracted from VX. VF is set to 0 when there's a borrow, and 1 when there isn't. */
                int minus = this.V.get(b3) - this.V.get(b2);
                if (minus < 0) {
                    this.V.set(0xF, 0); // borrow
                } else {
                    this.V.set(0xF, 1); // no borrow
                }
                this.V.set(b3, minus);
                break;
            }
            case 16: {/* 8XY6 Shifts VX right by one. VF is set to the value of the least significant bit of VX before the shift. */
                this.V.set(0xF, this.V.get(b3) & 0x01);
                this.V.set(b3, this.V.get(b3) >> 1);
                break;
            }
            case 17: {/* 8XY7 Sets VX to VY minus VX. VF is set to 0 when there's a borrow, and 1 when there isn't. */
                int minus = this.V.get(b2) - this.V.get(b3);
                if (minus < 0) {
                    this.V.set(0xF, 0); // borrow
                } else {
                    this.V.set(0xF, 1); // no borrow
                }
                this.V.set(b3, minus);
                break;
            }
            case 18: {/* 8XYE Shifts VX left by one. VF is set to the value of the most significant bit of VX before the shift. */
                this.V.set(0xF, this.V.get(b3) >> 7);
                this.V.set(b3, this.V.get(b3) << 1);
                break;
            }
            case 19: {/* 9XY0 Skips the next instruction if VX doesn't equal VY. */
                if (this.V.get(b3) != this.V.get(b2)) {
                    this.pc += 2;
                }
                break;
            }
            case 20: {/* ANNN Sets I to the address NNN. */
                this.I = (b3 << 8) + (b2 << 4) + b1;
                break;
            }
            case 21: {/* BNNN Jumps to the address NNN plus V0. */
                this.pc = (b3 << 8) + (b2 << 4) + b1 + this.V.get(0);

                break;
            }
            case 22: {/* CXNN Sets VX to a random number and NN. */

                this.V.set(b3, rand.nextInt((b2 << 4) + b1 + 1));
                break;
            }
            case 23: {/* DXYN Draws a sprite at coordinate (VX, VY) that has a width of 8 pixels and a height of N pixels. Each row of 8 pixels is read as bit-coded (with the most significant bit of each byte displayed on the left) starting from memory location I; I value doesn't change after the execution of this instruction. As described above, VF is set to 1 if any screen pixels are flipped from set to unset when the sprite is drawn, and to 0 if that doesn't happen.*/
                drawSprite(b1, b2, b3);
                break;
            }
            case 24: {/* EX9E Skips the next instruction if the key stored in VX is pressed. */
                if (this.buttons.get(this.V.get(b3)) == 1) {
                    this.pc += 2;
                }
                break;
            }
            case 25: {/* EXA1 Skips the next instruction if the key stored in VX isn't pressed. */
                if (this.buttons.get(this.V.get(b3)) == 0) {
                    this.pc += 2;
                }

                break;
            }
            case 26: {/* FX07 Sets VX to the value of the delay timer.*/
                this.V.set(b3, this.delayCounter.get());

                break;
            }
            case 27: {/* FX0A A key press is awaited, and then stored in VX.*/
                waitEvent();
                break;
            }
            case 28: {/* FX15 Sets the delay timer to VX.*/
                this.delayCounter.set(this.V.get(b3));
                break;
            }
            case 29: {/* FX18 Sets the sound timer to VX.*/
                this.soundCounter.set(this.V.get(b3));
                break;
            }
            case 30: {/* FX1E Adds VX to I. */
                this.I += this.V.get(b3);
                break;
            }
            case 31: {/* FX29 Sets I to the location of the sprite for the character in VX. Characters 0-F (in hexadecimal) are represented by a 4x5 font.*/
                this.I = 5 * this.V.get(b3);
                break;
            }
            case 32: {/* FX33 Stores the Binary-coded decimal representation of VX, with the most significant of three digits at the address in I, the middle digit at I plus 1, and the least significant digit at I plus 2.
                        (In other words, take the decimal representation of VX, place the hundreds digit in memory at location in I, the tens digit at location I+1, and the ones digit at location I+2.)*/

                this.memory.set(this.I, ((this.V.get(b3) - this.V.get(b3) % 100) / 100) & 0xFFFF); // hundreds
                this.memory.set(this.I + 1, ((this.V.get(b3) - this.V.get(b3) % 10) / 10) & 0xFFFF); // tens
                this.memory.set(this.I + 2, (this.V.get(b3) - this.memory.get(this.I) * 100 - this.memory.get(this.I + 1) * 10) & 0xFFFF); // units
                break;
            }
            case 33: {/* FX55 Stores V0 to VX in memory starting at address I. */
                for (int i = 0; i <= b3; i++) {
                    this.memory.set(this.I + i, this.V.get(i));
                }
                break;
            }
            case 34: {/* FX65 Fills V0 to VX with values from memory starting at address I.*/
                for (int i = 0; i <= b3; i++) {
                    this.V.set(i, this.memory.get(this.I + i));
                }
                break;
            }
            default: {
                // Should never be there !
                break;
            }
        }
        this.pc += 2; // go to next opcode.
    }



    private void clearScreen() {
        this.screen.clear();
    }

    private void drawSprite(int b1, int b2, int b3) {

        this.V.set(0xF, 0);

        for (int k = 0; k < b1; k++) {
            int codage = memory.get(this.I + k); // gets the code of the line to draw
            int y = (this.V.get(b2) + k) % SCREEN_HEIGHT_IN_PIXEL; // compute the ordinate of the drawed line, should not overcome the screen height.

            for (int j = 0, decalage = 7; j < 8; j++, decalage--) {
                int x = (this.V.get(b3) + j) % SCREEN_WIDTH_IN_PIXEL; // gets the abscisse, should not overcome scren width.

                // gets corresponding bit
                if ((codage & (0x1 << decalage)) != 0) {
                    // if it's white
                    if (screen.getPixelAt(x, y).getColor() == WHITE) {
                        screen.getPixelAt(x, y).off(); // switch off the pixel
                        this.V.set(0xF, 1);// there is collusion
                    } else {
                        screen.getPixelAt(x, y).on();
                    }

                }
            }
        }
    }

    public KeyEvent waitEvent() {
        KeyEvent keyEvent;
        do {
            keyEvent = getEventQueue().poll();
        } while (keyEvent == null);

        return keyEvent;

    }

    public KeyEvent poll() {
        KeyEvent ke = getEventQueue().poll();
        return ke;
    }
}
