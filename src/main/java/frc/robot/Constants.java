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
    public static final int kControllerDriver     = 0; // XBox Controller
    public static final int kLeftButtonController = 3; // Left button hub on Driver Station
    
    // Buttons
    public static final int kSwitchArcadeRight = 5; // Right arcade locking switch

    // CAN IDs
    public static final int kDriveTalonRightAId = 0;
    public static final int kDriveTalonRightBId = 1;
    public static final int kDriveTalonLeftAId  = 2;
    public static final int kDriveTalonLeftBId  = 3;

    // PCM / Pneumatics Control Module
    public static final int kGearShiftSolenoidId = 0; // Channel 0

    // PWM
    public static final int kShooterTalon        = 1;
    public static final boolean kShooterInverted = false;



}

