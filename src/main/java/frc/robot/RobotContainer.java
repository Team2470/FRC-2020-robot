/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.commands.DriveWithController;
import frc.robot.commands.TestShooterCommand;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.FakeSubsystem;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drive m_drive = new Drive();
  private final Shooter m_shooter = new Shooter();
  private final FakeSubsystem m_fake = new FakeSubsystem();

  private final XboxController m_controller = new XboxController(Constants.kControllerDriver); 

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Set default commands
    m_drive.setDefaultCommand(new DriveWithController(m_drive, m_controller));
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // Configure out shooter buttons
    TestShooterCommand m_testShooterCmd = new TestShooterCommand(m_shooter);

    new JoystickButton(m_controller, Button.kBumperLeft.value).whenPressed(m_testShooterCmd);
    new JoystickButton(m_controller, Button.kBumperRight.value).cancelWhenPressed(m_testShooterCmd);


    // Set a random number when pressed, set to 0 when released
    new JoystickButton(m_controller, Button.kA.value)
        .whenPressed(() -> m_fake.setRandomNumber())
        .whenReleased(() -> m_fake.stopRandomNumber());

    // Set a random number when pressed, set to 0 when released
    new JoystickButton(m_controller, Button.kB.value)
        .whenPressed(() -> m_fake.setState("B Pressed"))
        .whenReleased(() -> m_fake.setState(""));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
