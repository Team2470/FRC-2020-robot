/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.Set;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Vision;

public class AutoAlignCommand extends CommandBase {
  private final Vision m_vision;
  private final DriveSubsystem m_drive;
  private final double m_kp = 0.1;
  private final double m_minimum = 0.05;
  /**
   * Creates a new AutoAlignCommand.
   */
  public AutoAlignCommand(Vision vision, DriveSubsystem drive) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_vision = vision;
    m_drive = drive;
    
    addRequirements(vision);
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    m_drive.setGear(false);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if(m_vision.getTargetFound())  {
      double angle = m_vision.getHorizontalAngleD();
      double turnAngle = 0;

      if(angle > 0) {
        turnAngle = m_kp * angle + m_minimum;
      }
      else if (angle < 0) {
        turnAngle = m_kp * angle - m_minimum;

      }
      m_drive.arcadeDrive(0, turnAngle);


    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    boolean targetAligned = Math.abs(m_vision.getHorizontalAngleD()) < 0.5;
    return targetAligned;

  }
}
