/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {

  // the flywheel motor
  private final CANSparkMax m_shooterMaster;
  private final CANSparkMax m_shooterSlave;

  // controls the angle of hood
  private final CANSparkMax m_shooterAngleMotor;

  private final CANEncoder m_shooterAngleEncoder;

  double currentList[] = new double[5];
  int positionInList = 0;

  /**
   * Creates a new Shooter.
   */
  public Shooter() {
    m_shooterMaster = new CANSparkMax(Constants.kShooterNeoMaster, MotorType.kBrushless);
    m_shooterMaster.setInverted(Constants.kShooterInverted);

    m_shooterSlave = new CANSparkMax(Constants.kShooterNeoSlave, MotorType.kBrushless);
    m_shooterSlave.follow(m_shooterMaster, true);

    m_shooterAngleMotor = new CANSparkMax(Constants.kShooterNeoAngle, MotorType.kBrushless);
    m_shooterAngleMotor.setInverted(Constants.kShooterAngleInverted);
    
    m_shooterAngleEncoder = m_shooterAngleMotor.getEncoder();
    m_shooterAngleEncoder.setPositionConversionFactor(Constants.kShooterAngleScale);
    //42 counts per revolution
    
  }

  /**
   * Start the shooter motor spinning
   * 
   * @param percentOutput Motor Speed [ -1.0 to 1.0 ] - Positive Numbers Shoot
   */
  public void shoot(double percentOutput) {
    m_shooterMaster.set(percentOutput);
  }

  public void setAngleMotorDegrees() {
    /**
     * TODO determine how to set angle.
     */
  }

  public void setAngleMotorSpeed(double percentOutput){
    m_shooterAngleMotor.set(percentOutput);
  }
/**
 * Determines whether hood is at home position
 * TO DO:
 * Determine rest current
 * @param restCurrent D
 * @return
 */
  public boolean isHoodPosition(){
    //calculates the average
    double average = 0;
    for(int i = 0; i<5; i++) {
      average = average+currentList[i];
    }
    average = average/5;
    
    if (average>Constants.kCurrentThreshold) {
      return true;
    }
    else {
      return false;
    }
  }

  public double getAngle(){
    double position = m_shooterAngleEncoder.getPosition();
    return position;

  }

  public void initEncoder(){
      m_shooterAngleEncoder.setPosition(0);
  }

  /**
   * Stop the shooter motor
   */
  public void stop() {
    m_shooterMaster.stopMotor();
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    double newCurrent = m_shooterAngleMotor.getOutputCurrent();
    currentList[positionInList%5] = newCurrent;
    positionInList++;
  }
}
