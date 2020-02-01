/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {

  private final Encoder m_climbEncoder;
  private final Talon m_winchMotor;

  Solenoid climberSolenoid = new Solenoid(0);
  /**
   * Creates a new climberSubsystem
   */
  public Climber() {

    m_winchMotor    = new Talon(Constants.kWinchMotor);
    m_climbEncoder  = new Encoder(1,0);

    // Use addRequirements() here to declare subsystem dependencies.
  }
  //targetDist should not be max possible distance to account for sample time
  public void climb(double speed, double targetDist){
    boolean end = false;

    m_winchMotor.setSpeed(speed);

    while(end != true ){
      if (targetDist < m_climbEncoder.getDistance()) {
        end = true;
      }
    }
    

    m_winchMotor.stopMotor();

  }
 
  // Returns true when the command should end.
 // @Override
  //public boolean isFinished() {
   // return false;
 // }
}
