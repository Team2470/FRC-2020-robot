/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

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
    public static final int kShooterTalon        = 1;
    public static final boolean kShooterInverted = false;
    public static final int kWinchMotor          = 2;


    // CAN
    public static final int kIntakeTalonAdress = 22;
    public static final boolean kIntakeInverted = false;
    public static final int kStorageMotorTalonID = 20;
    public static final boolean kStorageMotorInverted = true;
    // public static final int kStorageOutputVictorID = 2;
    // public static final boolean kStorageOutputInverted = false;

    // PCM (Id 0)
    public static final int kIntakeSolenoidLeft = 0;
    public static final int kIntakeSolenoidRight = 1;
    public static final int kClimberSolenoid = 3;


    //Intake
    public static final double kIntakeCaptureSpeed = 0.5;

    //DigitalIO
    public static final int kStorageBallInputChannel = 0;
    public static final int kStorageBallOutputChannel = 1;
    public static final int kClimerEncoderChannelA = 2;
    public static final int kClimerEncoderChannelB = 3;
    
}

