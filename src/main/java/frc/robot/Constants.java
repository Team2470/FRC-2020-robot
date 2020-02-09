/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    // Controllers
    public static final int kControllerDriver = 0;

    // PWM
    // TODO: There should not be any PWM motors
    public static final int kDriveSparkRight     = 0;
    public static final int kDriveSparkLeft      = 9;
    public static final int kWinchMotor          = 2;


    // CAN
    public static final int kIntakeTalonAdress = 22;
    public static final boolean kIntakeInverted = false;
    public static final int kStorageMotorTalonID = 20;
    public static final boolean kStorageMotorInverted = true;

    public static final int kShooterNeoMaster   = 3;
    public static final int kShooterNeoSlave = 1;
    public static final boolean kShooterInverted = true;
    public static final int kShooterNeoAngle = 2;
    public static final boolean kShooterAngleInverted = false;
    public static int kShooterNeoExit = 4;
    public static boolean kShooterExitInverted = true;
    
    // PCM (Id 0)
    public static final int kIntakeSolenoidLeft = 0;
    public static final int kIntakeSolenoidRight = 1;
    public static final int kClimberSolenoid = 3;

    // Shooter
    /**
     * TO DO:
     * determine cureent threshold boiiiiiiii for calibration of hood position
     */
    public static final double kCurrentThreshold = 0.5;

    //conversion factor of the hood angler. The first number is the gear ratio
    //The second number is degrees over count per rev
    public final static double kShooterAngleScale = ((1/125)*(360/42));

    //Intake
    public static final double kIntakeCaptureSpeed = 0.5;

    //DigitalIO
    public static final int kStorageBallInputChannel = 0;
    public static final int kStorageBallOutputChannel = 1;
    public static final int kClimerEncoderChannelA = 2;
    public static final int kClimerEncoderChannelB = 3;
    
}

