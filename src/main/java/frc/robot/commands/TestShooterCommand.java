/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class TestShooterCommand extends CommandBase {

  // Subsystems
  private final Shooter m_shooter;

  /**
   * Creates a new TestShooterCommand.
   */
  public TestShooterCommand(Shooter shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_shooter = shooter;
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putNumber("Shooter Output", 0.0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = SmartDashboard.getNumber("Shooter Output", 0.0);
    m_shooter.shoot(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.stop();
    SmartDashboard.putNumber("Shooter Output", 0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
