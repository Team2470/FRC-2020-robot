/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import bjorg.sim.WPI_CANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class StorageExitSubsystem extends SubsystemBase {

  //moves powercell into shooter
  private final WPI_CANSparkMax m_exitMotor;

  /**
   * Creates a new ConyeyorExitSubsystem.
   */
  public StorageExitSubsystem() {

    m_exitMotor = new WPI_CANSparkMax(Constants.kShooterNeoExit, MotorType.kBrushless);
    initSparkMax(m_exitMotor);
    m_exitMotor.setInverted(Constants.kShooterExitInverted);

  }

  private void initSparkMax(CANSparkMax spark){
    spark.restoreFactoryDefaults();
    spark.setSmartCurrentLimit(40); // 40 amps
  }

  public void set(double percentOutput){
    m_exitMotor.set(percentOutput);
  }

  /**
   * Stop the shooter motor
   */
  public void stop() {
    m_exitMotor.stopMotor();
  }

  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
