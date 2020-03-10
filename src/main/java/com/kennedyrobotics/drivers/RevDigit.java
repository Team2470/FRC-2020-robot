package com.kennedyrobotics.drivers;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;

/*
 * Creates an instance of the Digit board.  You'll only need one.
 *
 * Thank you to https://github.com/red-green/REVDigitBoard/blob/master/REVDigitBoard.java
 */
public class RevDigit {
    public static final int KAddress = 0x70;
    public static final int kButtonA = 19;
    public static final int kButtonB = 20;
    public static final int kPotentiometer = 7;

    private static final byte kCmdBrightness = (byte)0xE0;
    private static final byte kCmdBlink = (byte)0x80;
    private static final byte kCmdBlinkDisplayOn = (byte)0x01;

    private static final int[] kAlphaFontTable = {
        // See Table 2-1 here http://www.revrobotics.com/content/docs/REV-11-1113-UM.pdf

		// Dp L M N K J H G2 G1 F E D C B A
		//  E D C B A 9 8  7  6 5 4 3 2 1 0

		0b0000000000000001,// 0x00
		0b0000000000000010,
		0b0000000000000100,
		0b0000000000001000,
		0b0000000000010000,
		0b0000000000100000,
		0b0000000001000000,
		0b0000000010000000,
		0b0000000100000000,
		0b0000001000000000,
		0b0000010000000000,
		0b0000100000000000,
		0b0001000000000000,
		0b0010000000000000,
		0b0100000000000000,
		0b1000000000000000,
		0b0000000000000000, // 10
		0b0000000000000000,
		0b0000000000000000,
		0b0000000000000000,
		0b0000000000000000,
		0b0000000000000000,
		0b0000000000000000,
		0b0000000000000000,
		0b0001001011001001,
		0b0001010111000000,
		0b0001001011111001,
		0b0000000011100011,
		0b0000010100110000,
		0b0001001011001000,
		0b0011101000000000,
		0b0001011100000000,
		0b0000000000000000, // 0x20
		0b0000000000000110, // !
		0b0000001000100000, // "
		0b0001001011001110, // #
		0b0001001011101101, // $
		0b0000110000100100, // %
		0b0010001101011101, // &
		0b0000010000000000, // '
		0b0010010000000000, // (
		0b0000100100000000, // )
		0b0011111111000000, // *
		0b0001001011000000, // +
		0b0000100000000000, // ,
		0b0000000011000000, // -
		0b0000000000000000, // .
		0b0000110000000000, // /
		0b0000000000111111, // 0x30, 0
		0b0000000000000110, // 1
		0b0000000011011011, // 2
		0b0000000011001111, // 3
		0b0000000011100110, // 4
		0b0000000011101101, // 5
		0b0000000011111101, // 6
		0b0000000000000111, // 7
		0b0000000011111111, // 8
		0b0000000011101111, // 9
		0b0001001000000000, // :
		0b0000101000000000, // ;
		0b0010010000000000, // <
		0b0000000011001000, // =
		0b0000100100000000, // >
		0b0001000010000011, // ?
		0b0000001010111011, // 0x40, @
		0b0000000011110111, // A
		0b0001001010001111, // B
		0b0000000000111001, // C
		0b0001001000001111, // D
		0b0000000011111001, // E
		0b0000000011110001, // F
		0b0000000010111101, // G
		0b0000000011110110, // H
		0b0001001000001001, // I
		0b0000000000011110, // J
		0b0000110001110000, // K
		0b0000000000111000, // L
		0b0000010100110110, // M
		0b0000100100110110, // N
		0b0000000000111111, // O
		0b0000000011110011, // 0x50, P
		0b0000100000111111, // Q
		0b0000100011110011, // R
		0b0000000110001101, // S
		0b0001001000000001, // T
		0b0000000000111110, // U
		0b0010010000110000, // V
		0b0010100000110110, // W
		0b0010110100000000, // X
		0b0001010100000000, // Y
		0b0010010000001001, // Z
		0b0000000000111001, // [
		0b0000100100000000, // (backslash)
		0b0000000000001111, // ]
		0b0000000000100011, // ^
		0b0000000000001000, // _
		0b0000000100000000, // 0x60, `

		// Dp L M N K J H G2 G1 F E D C B A
		//  E D C B A 9 8  7  6 5 4 3 2 1 0

		0b0000000011011111, // a
		0b0000000011111100, // b
		0b0000000011011000, // c
		0b0000000011011110, // d
		0b0000000011111011, // e
		0b0000000001110001, // f
		0b0000000110001111, // g
		0b0001000001110000, // h
		0b0001000000000000, // i
		0b0000000000001110, // j
		0b0001111000000000, // k
		0b0000000000110000, // l
		0b0001000011010100, // m
		0b0001000001010000, // n
		0b0000000011011100, // o
		0b0000000101110000, // ox70, p
		0b0000100011100011, // q
		0b0000000001010000, // r
		0b0000000110001101, // s
		0b0000000001111000, // t
		0b0000000000011100, // u
		0b0010000000010000, // v
		0b0010100000010100, // w
		0b0010110100000000, // x
		0b0000000110001110, // y
		0b0010000001001000, // z
		0b0010000101001001, // {
		0b0001001000000000, // |
		0b0000110010001001, // }
		0b0000010100100000, // ~
		0b0011111111111111
    }; 

    private final I2C i2c_;
    private final DigitalInput aButton_;
    private final DigitalInput bButton_;
    private final AnalogInput pot_;

    public RevDigit() {
        this(I2C.Port.kMXP, kButtonA, kButtonB, kPotentiometer);
    }

    public RevDigit(I2C.Port port, int buttonA, int buttonB, int poteniometer) {
        i2c_ = new I2C(port, KAddress);
        aButton_ = new DigitalInput(buttonA);
        bButton_ = new DigitalInput(buttonB);
        pot_ = new AnalogInput(poteniometer);

        try {
            this.init();
        } catch (Exception e) {
            System.out.println("Could not initialize RevDigit");
        }
    }

    private void init() throws InterruptedException {
        byte[] data = new byte[1];

        // OSC 
        data[0] = 0x21;
        i2c_.writeBulk(data);
        Thread.sleep(10);

        // Blink
        int b  = 0;
        data[0] = (byte)(kCmdBlink | kCmdBlinkDisplayOn | (b << 1));
        i2c_.writeBulk(data);
        Thread.sleep(10);

        // Brightness
        int brightness = 15;
        data[0] = (byte) (kCmdBrightness | brightness);
        i2c_.writeBulk(data);
        Thread.sleep(10);        
    }

    public void display(String message) {
		char[] messageToDisplay = new char[] {' ', ' ',' ', ' '};
		// if (message.length() >= 1) {
		// 	messageToDisplay[0] = message.charAt(0);
		// }
		// if (message.length() >= 2) {
		// 	messageToDisplay[1] = message.charAt(1);
		// }
		// if(message.length() >= 3) {
		// 	messageToDisplay[2] = message.charAt(2);
		// }

		// if (message.length() >= 4) {
		// 	messageToDisplay[3] = message.charAt(3);
		// }

		for (int i = 0; i < 4; i++) {
			if (i < message.length()) {
				messageToDisplay[i] = message.charAt(i);
			}
		}

		byte[] msg = new byte[10];
		msg[0] = 0b00001111;
		msg[1] = 0b00001111;
		msg[2] = (byte)(kAlphaFontTable[messageToDisplay[3]] & 0xFF);
		msg[3] = (byte)(kAlphaFontTable[messageToDisplay[3]] >> 8);
		msg[4] = (byte)(kAlphaFontTable[messageToDisplay[2]] & 0xFF);
		msg[5] = (byte)(kAlphaFontTable[messageToDisplay[2]] >> 8);
		msg[6] = (byte)(kAlphaFontTable[messageToDisplay[1]] & 0xFF);
		msg[7] = (byte)(kAlphaFontTable[messageToDisplay[1]] >> 8);
		msg[8] = (byte)(kAlphaFontTable[messageToDisplay[0]] & 0xFF);
		msg[9] = (byte)(kAlphaFontTable[messageToDisplay[0]] >> 8);

		i2c_.writeBulk(msg);
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			System.out.println("Unable to sleep");
		}
	}

	/**
	 * Get the state of the A button
	 * @return true if pressed
	 */
	public boolean getA() {
		return !aButton_.get();
	}

	/**
	 * Get the state of the B button
	 * @return true if pressed
	 */
	public boolean getB() {
		return !bButton_.get();
	}

	/**
	 * Get the state of the potentiometer.
	 */
	public double potVoltage() {
		return pot_.getVoltage();
	}
}