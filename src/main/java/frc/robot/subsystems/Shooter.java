/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {

  // Motors
  private final Talon m_shooterMotor;

  /**
   * Creates a new Shooter.
   */
  public Shooter() {
      m_shooterMotor = new Talon(Constants.kShooterTalon);
      m_shooterMotor.setInverted(Constants.kShooterInverted);
  }

  /**
   * Start the shooter motor spinning
   * @param percentOutput Motor Speed [ -1.0 to 1.0 ] - Positive Numbers Shoot 
   */
  public void shoot(double percentOutput) {
    m_shooterMotor.setSpeed(percentOutput);
  }

  /**
   * Stop the shooter motor
   */
  public void stop() {
    m_shooterMotor.stopMotor();
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
