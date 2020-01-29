/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drive extends SubsystemBase {
  //#region Hardware

  private final Spark m_leftMotor;
  private final Spark m_rightMotor;
  private final DifferentialDrive m_drive;

  //#endregion

  /**
   * Creates a new Drive.
   */
  public Drive() {
    m_leftMotor = new Spark(Constants.kDriveSparkLeft);
    m_rightMotor = new Spark(Constants.kDriveSparkRight);

    m_drive = new DifferentialDrive(m_leftMotor, m_rightMotor);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  /**
   * Drive using arcade drive joystick
   * @param move The speed and direction to move
   * @param rotate The speed and direction to rotate
   */
  public void arcadeDrive(double move, double rotate) {
    m_drive.arcadeDrive(move, rotate, true);
  }

  /**
   * Stop the drive from moving
   */
  public void stop() {
    m_drive.stopMotor();
  }
}
