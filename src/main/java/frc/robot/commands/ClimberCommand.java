/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class ClimberCommand extends CommandBase {
  public static final double kTargetDistance = 5;

  Solenoid climberSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
  private final Climber m_climber;


  /**
   * Creates a new ClimberCommand.
   */
  public ClimberCommand(Climber climber) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climber = climber;
    addRequirements(m_climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //deploy solenoid to contain climber arm
    //climberSolenoid.set(true);
    m_climber.climb(50);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //retract solenoid to allow spring to deploy climber
    //climberSolenoid.set(false);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climber.climb(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(m_climber.getDistance()>= kTargetDistance){
      return true;
    }else{
    return false;
    }
  }
}
