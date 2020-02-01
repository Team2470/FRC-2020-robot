/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class StorageSubsysten extends SubsystemBase {

  private final TalonSRX m_conveyorMotor;
  private final VictorSPX m_outputMotor;
  private final DigitalInput m_ballAtInput;  
  private final DigitalInput m_ballAtOutput;


  /**
   * Creates a new StorageSubsystem.
   */
  public StorageSubsysten() {
    m_ballAtInput = new DigitalInput(Constants.kStorageBallInputChannel);
    m_ballAtOutput = new DigitalInput(Constants.kStorageBallOutputChannel);
    
    m_conveyorMotor = new TalonSRX(Constants.kStorageMotorTalonID);
    m_conveyorMotor.setInverted(Constants.kStorageMotorInverted);
    m_outputMotor = new VictorSPX(Constants.kStorageOutputVictorID);
    m_outputMotor.setInverted(Constants.kStorageOutputInverted);
  }

  public void setCoveyorMotor(double MotorSpeed) 
  {
    m_conveyorMotor.set(ControlMode.PercentOutput, MotorSpeed);
  }

  public void setOutputMotor(double MotorSpeed)
  {
    m_outputMotor.set(ControlMode.PercentOutput, MotorSpeed);
  }

  public boolean isBallAtInput() {
    return m_ballAtInput.get();
  }

  public boolean isBallAtOutput() {
    return m_ballAtOutput.get();
  }

  public void stopMotors() {
    m_conveyorMotor.setNeutralMode(NeutralMode.Coast);
    m_outputMotor.setNeutralMode(NeutralMode.Coast);
  }
 
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("Conveyor Ball at input", isBallAtInput());
    SmartDashboard.putBoolean("Conveyor Bal at output", isBallAtOutput());
  }
}
