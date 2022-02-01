/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {
  private final WPI_TalonFX m_leftMaster;
  private final WPI_TalonFX m_leftSlave;
  private final WPI_TalonFX m_rightMaster;
  private final WPI_TalonFX m_rightSlave;
  // private final Spark m_leftMotorController;
  // private final Spark m_rightMotorController;
  private final DifferentialDrive m_diffDriveController;

  private final Solenoid m_gearShiftSolenoid;


  /**
   * Creates a new DriveSubsystem.
   */
  public DriveSubsystem() {
    setName("Drive");
    // m_leftMotorController = new Spark(Constants.kDriveSparkLeft);
    // m_rightMotorController = new Spark(Constants.kDriveSparkRight);
    // Motor Controllers
    m_leftMaster = new WPI_TalonFX(Constants.kDriveTalonLeftAId);
    m_leftSlave = new WPI_TalonFX(Constants.kDriveTalonLeftBId);
    m_leftMaster.setInverted(true);
    m_leftSlave.setInverted(InvertType.FollowMaster);
    m_leftSlave.follow(m_leftMaster);
    addChild("Left Talon", m_leftMaster);
    
    m_rightMaster = new WPI_TalonFX(Constants.kDriveTalonRightAId);
    m_rightSlave = new WPI_TalonFX(Constants.kDriveTalonRightBId);
    m_rightMaster.setInverted(false);
    m_rightSlave.setInverted(InvertType.FollowMaster);
    m_rightSlave.follow(m_rightMaster);
    addChild("Right Talon", m_rightMaster);
    m_diffDriveController = new DifferentialDrive(m_leftMaster, m_rightMaster);

    // Solenoid
    m_gearShiftSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, Constants.kGearShiftSolenoidId);
  }

  /**
   * Set the gear
   * @param gear The gear setting.  True for high gear, False for low gear
   */
  public void setGear(boolean gear) {
    m_gearShiftSolenoid.set(gear);
  }

  /**
   * Drive using arcade drive joystick
   * @param move The speed and direction to move
   * @param rotate The speed and direction to rotate
   */
  public void arcadeDrive(double move, double rotate) {
    SmartDashboard.putNumber("Arcade Turn", rotate);
    m_diffDriveController.arcadeDrive(move, rotate, true);
  }
  public void curvatureDrive(double xSpeed, double zRotation, boolean allowTurnInPlace){
    xSpeed = Math.copySign(xSpeed*xSpeed, xSpeed)*0.7;
    zRotation = Math.copySign(zRotation*zRotation, zRotation);
    m_diffDriveController.curvatureDrive(xSpeed, zRotation, allowTurnInPlace);
  }

  /**
   * Stop the drive from moving
   */
  public void stop() {
    m_diffDriveController.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
