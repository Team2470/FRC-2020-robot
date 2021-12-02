/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.StorageSubsystem;

@Deprecated
public class WaitForBallCommand extends CommandBase {

  private final StorageSubsystem m_storage;
  /**
   * Creates a new WaitForBallCommand.
   */
  public WaitForBallCommand(StorageSubsystem storage) {
    // Use addRequirements() here to declare subsystem dependencies.
  
    m_storage = storage;

    addRequirements(m_storage);
  
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {


  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return m_storage.isBallAtInput();

  }
}
