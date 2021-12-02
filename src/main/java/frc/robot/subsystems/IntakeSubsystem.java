/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase 
{
  private final WPI_VictorSPX m_intakeSpin;
  private final Solenoid m_deploy;

  public IntakeSubsystem() 
  {
    setName("Intake");

    m_deploy = new Solenoid(Constants.kIntakeSolenoid);
    addChild("Intake Deploy Solenoid", m_deploy);

    m_intakeSpin = new WPI_VictorSPX(Constants.kIntakeTalonId);
    addChild("Intake Motor", m_intakeSpin);
    m_intakeSpin.set(ControlMode.PercentOutput, 0.0);
    m_intakeSpin.setNeutralMode(NeutralMode.Coast);
    m_intakeSpin.setInverted(Constants.kIntakeInverted);
  }

  /**
   * Lift intake system.
   */
  public void liftIntake()
  {
      m_deploy.set(false);

  }

  /**
   * Pulls down intake system.
   */
  public void deployIntake()
  {
      m_deploy.set(true);
  }

  /**
   * Start spin intake motor with current assigned speed.
   *@param MotorSpeed Posistive values move balls into the robot
   */
  public void startIntakeMotor(double MotorSpeed)
  {
      m_intakeSpin.set(ControlMode.PercentOutput, MotorSpeed);
  }

  /**
   * Force stop intake motor.
   */
  public void stopIntakeMotor()
  {
      m_intakeSpin.neutralOutput();
  }

  @Override
  public void periodic() 
  {
    // This method will be called once per scheduler run
  }
}
