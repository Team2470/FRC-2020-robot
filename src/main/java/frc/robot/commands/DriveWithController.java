/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveWithController extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem m_drive;
  private final XboxController m_xboxController;
  
  /**
   * Creates a new DriveWithController.
   * @param drive Drive subsystem to control
   * @param xboxController xboxController controller to use for driving
   */
  public DriveWithController(DriveSubsystem drive, XboxController xboxController) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = drive;
    m_xboxController = xboxController;

    addRequirements(m_drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double xSpeed = -m_xboxController.getLeftY();
    double zRotation = m_xboxController.getRightX();
    boolean quickTurn = m_xboxController.getRightTriggerAxis() > 0.5;

    m_drive.curvatureDrive(xSpeed, zRotation, quickTurn);
    m_drive.setGear(m_xboxController.getAButton());

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.stop(); 
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
