/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.DriveSubsystem;

public class DriveWithController extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem m_drive;
  private final XboxController m_xboxController;
  private final JoystickButton m_gearSwitch;
  
  /**
   * Creates a new DriveWithController.
   * @param drive Drive subsystem to control
   * @param xboxController xboxController controller to use for driving
   * @param gearSwitchButton JoystickButton to use for gear shift
   */
  public DriveWithController(DriveSubsystem drive, XboxController xboxController, JoystickButton gearSwitchButton) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = drive;
    m_xboxController = xboxController;
    m_gearSwitch = gearSwitchButton;

    addRequirements(m_drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Get data from the controller
    double move = m_xboxController.getY(Hand.kLeft);
    double rotate = m_xboxController.getX(Hand.kRight);

    // Process the data
    move = -move;

    // Tell the drive subsystem
    m_drive.arcadeDrive(move, rotate);
    m_drive.setGear(m_gearSwitch.get());

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
