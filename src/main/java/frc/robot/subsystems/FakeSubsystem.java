/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.Random;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FakeSubsystem extends SubsystemBase {
  private boolean m_runRandom = false;
  private final Random m_rand;

  /**
   * Creates a new FakeSubsystem.
   */
  public FakeSubsystem() {
    m_rand = new Random();
    setState("");
    setNumber(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if (m_runRandom) {
      SmartDashboard.putNumber("Number", m_rand.nextInt(100));
    }
  }

  /**
   * Set a string to the dashboard
   * @param text The text to set
   */
  public void setState(String text) {
    SmartDashboard.putString("CommandState", text);
  }

  /**
   * Sets our numeric value to a random number
   */
  public void setRandomNumber() {
    m_runRandom = true;
  }

  public void stopRandomNumber() {
    m_runRandom = false;
  }

  /**
   * Sets the number
   * @param number The number to the value
   */
  public void setNumber(int number) {
    SmartDashboard.putNumber("Number", number);
  }
}
