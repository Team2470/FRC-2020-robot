/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.StorageSubsystem;

public class TestStorageBackwardCommand extends CommandBase {

  private final StorageSubsystem m_storage;

  /**
   * Creates a new TestStorageBackwardCommand.
   * @param storageSubsystem Storage Subsystem to control
   */
  public TestStorageBackwardCommand(StorageSubsystem storage){
    // Use addRequirements() here to declare subsystem dependencies.
    m_storage = storage;
    addRequirements(storage);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_storage.setCoveyorMotor(-1.0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_storage.setCoveyorMotor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
