/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.StorageExitSubsystem;

public class ForwardStorageExitCommand extends CommandBase {

  private final StorageExitSubsystem m_storageExit;

  /**
   * Creates a new ForwardStorageExitCommand.
   */
  public ForwardStorageExitCommand(StorageExitSubsystem exit) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_storageExit = exit;

    addRequirements(m_storageExit);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_storageExit.set(0.25);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_storageExit.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
