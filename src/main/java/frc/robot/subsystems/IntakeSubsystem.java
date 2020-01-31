/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase 
{
  private final VictorSPX m_IntakeSpin;
  private final DoubleSolenoid m_Deploy;

  public IntakeSubsystem() 
  {
      m_IntakeSpin = new VictorSPX(Constants.kIntakeTalonAdress);
      m_Deploy = new DoubleSolenoid(Constants.kSolenoidUp, Constants.kSolenoidDown);
      m_IntakeSpin.set(ControlMode.PercentOutput, 0.0);
  }

  /**
   * Lift intake system.
   */
  public void LiftIntakeSystem()
  {
      m_Deploy.set(DoubleSolenoid.Value.kReverse);

  }

  /**
   * Pulls down intake system.
   */
  public void DeployIntakeSystem()
  {
      m_Deploy.set(DoubleSolenoid.Value.kForward);
  }

  /**
   * Start spin intake motor with current assigned speed.
   */
  public void StartIntakeMotor(double MotorSpeed)
  {
      m_IntakeSpin.set(ControlMode.PercentOutput, MotorSpeed);
  }

  /**
   * Force stop intake motor.
   */
  public void StopIntakeMotor()
  {
      m_IntakeSpin.set(ControlMode.PercentOutput, 0.0);
  }

  @Override
  public void periodic() 
  {
    // This method will be called once per scheduler run
  }
}
