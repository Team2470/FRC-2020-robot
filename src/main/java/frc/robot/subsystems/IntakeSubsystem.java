/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase 
{
  private final WPI_VictorSPX m_IntakeSpin;
  private final DoubleSolenoid m_Deploy;

  public IntakeSubsystem() 
  {
      m_IntakeSpin = new WPI_VictorSPX(Constants.kIntakeTalonAdress);
      m_Deploy = new DoubleSolenoid(Constants.kSolenoidUp, Constants.kSolenoidDown);
  }

  /**
   * Stop intake motors and lift intake system
   */
  public void LiftIntake()
  {
      
  }

  /**
   * Pulls down intake system and starts intake motors.
   */
  public void DeployIntake()
  {

  }

  @Override
  public void periodic() 
  {
    // This method will be called once per scheduler run
  }
}
