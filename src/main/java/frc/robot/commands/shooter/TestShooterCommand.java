/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.StorageExitSubsystem;

public class TestShooterCommand extends CommandBase {

  // Subsystems
  private final Shooter m_shooter;
  private final StorageExitSubsystem m_storageExit;

  /**
   * Creates a new TestShooterCommand.
   */
  public TestShooterCommand(Shooter shooter, StorageExitSubsystem storageExit) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_shooter = shooter;
    m_storageExit = storageExit;
    addRequirements(m_shooter, m_storageExit);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putNumber("Shooter Output", 0.0);
    SmartDashboard.putNumber("Shooter Exit Output", 0.0);
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = SmartDashboard.getNumber("Shooter Output", 0.0);
    m_shooter.shoot(speed);

    double exitSpeed = SmartDashboard.getNumber("Shooter Exit Output", 0.0);
    m_storageExit.set(exitSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.stop();
    m_storageExit.stop();
    SmartDashboard.putNumber("Shooter Output", 0.0);
    SmartDashboard.putNumber("Shooter Exit Output", 0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
