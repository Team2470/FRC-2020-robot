/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.StorageSubsystem;

public class IndexBallCommand extends CommandBase {

  private final StorageSubsystem m_index;
  private final Shooter m_shooter;
  /**
   * Creates a new IndexBallCommand.
   */
  public IndexBallCommand(StorageSubsystem index, Shooter shooter) {
    // Use addRequirements() here to declare subsystem dependencies.

    m_index = index;
    m_shooter = shooter;
    addRequirements(m_index, m_shooter);
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_index.setCoveyorMotor(0.5);
    m_shooter.moveExitMotor(-0.25);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  
    m_index.stopMotors();

  }


  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    return !m_index.isBallAtInput();

  }
}
