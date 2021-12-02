/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Vision extends SubsystemBase {

  private NetworkTable m_limeTable = NetworkTableInstance.getDefault().getTable("limelight");
  private NetworkTableEntry m_tx = m_limeTable.getEntry("tx");
  private NetworkTableEntry m_ty = m_limeTable.getEntry("ty");
  private NetworkTableEntry m_ta = m_limeTable.getEntry("ta");
  private NetworkTableEntry m_tv = m_limeTable.getEntry("tv");

  private NetworkTableEntry m_usbCam = m_limeTable.getEntry("stream");
  /**
   * Creates a new Vision.
   */
  public Vision() {
    // Set camera to PIP mode
    m_usbCam.setNumber(1);

  }

  @Override
  public void periodic() {
    //read values periodically
    double x = m_tx.getDouble(0.0);
    double y = m_ty.getDouble(0.0);
    double area = m_ta.getDouble(0.0);
    double valid = m_tv.getDouble(0.0);

    //post to smart dashboard periodically
    SmartDashboard.putNumber("Vision X", x);
    SmartDashboard.putNumber("Vision Y", y);
    SmartDashboard.putNumber("Vision Area", area);
    SmartDashboard.putNumber("Target Found", valid);
    SmartDashboard.putNumber("Distance", geTargetDistanceM());
    SmartDashboard.putNumber("Horizontal Angle", getHorizontalAngleD());
    
  }
  /**
   * Switches between driving/aiming vision
   * 
   * @param setVision true = driving mode
   */
  public void setDriverMode(boolean setVision) {

    if(setVision) {
      m_limeTable.getEntry("camMode").setNumber(1);

    } else {
      m_limeTable.getEntry("camMode").setNumber(0);
    }

  }
  /**
   * Swtches big picture mode for Driving/Conveyor View
   * 
   * @param setVision true = Driving/Aiming mode
   */
  public void viewConveyor(boolean setVision) {

    if(setVision) {
      m_limeTable.getEntry("stream").setNumber(2);

    } else {
      m_limeTable.getEntry("stream").setNumber(1);
    }

  }
  /**
   * Finds the distance from the base of the robot to the base of the target
   * @return Distance in degrees from the base of the camera to the base of the target
   */
  public double geTargetDistanceM() {
    if(m_tv.getDouble(0.0) == 1.0)  {
      double yDegree = m_ty.getDouble(0.0);
      return (Constants.kTargetHeightM - Constants.kCameraHeightM) / Math.tan( (Constants.kCameraAngleD + yDegree) * (Math.PI / 180) );    
    } else {
      return 0;
    }

  }
  /**
   * Returns if target is found
   * @return true if found 
   */
  public boolean getTargetFound() {

    if(m_tv.getDouble(0.0) == 1.0) {

      return true;
    } else {
      return false;
    }
  }
  public double getHorizontalAngleD() {
    if(m_tv.getDouble(0.0) == 1.0)  {
      return m_tx.getDouble(0.0);  
    } else {
      return 0;
    }

  }

  }







