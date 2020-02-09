/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Map;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DriveWithController;
import frc.robot.commands.IntakeDeployCommand;
import frc.robot.commands.IntakeRetractCommand;
import frc.robot.commands.LoadConveyorCommand;
import frc.robot.commands.TestShooterCommand;
import frc.robot.commands.TestStorageBackwardCommand;
import frc.robot.commands.TestStorageForwardCommand;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.FakeSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.StorageSubsystem;
import frc.robot.subsystems.Climber;
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
  private final IntakeSubsystem m_intake = new IntakeSubsystem();
  private final StorageSubsystem m_storage = new StorageSubsystem();
  private final Climber m_climber = new Climber();

  private final XboxController m_controller = new XboxController(Constants.kControllerDriver); 

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Configure shuffleboard for test commands
    configureTestingCommands();

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
    // TestShooterCommand m_testShooterCmd = new TestShooterCommand(m_shooter);
    //new JoystickButton(m_controller, Button.kBumperLeft.value).whenPressed(m_testShooterCmd);
    //new JoystickButton(m_controller, Button.kBumperRight.value).cancelWhenPressed(m_testShooterCmd);


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
   * Add commands to shuffleboard here.  This is so you don't have to add 
   * them yourselves
   */
  private void configureTestingCommands() {
    ShuffleboardLayout storageCommands = Shuffleboard.getTab("Commands")
      .getLayout("Storage", BuiltInLayouts.kList)
      .withSize(2,2)
      .withPosition(0,0)
      .withProperties(Map.of("Label position", "HIDDEN"));


      storageCommands.add(new TestStorageBackwardCommand(m_storage));
      storageCommands.add(new TestStorageForwardCommand(m_storage));
      storageCommands.add(new LoadConveyorCommand(m_storage));
      storageCommands.add(m_storage);

    ShuffleboardLayout intakeCommands = Shuffleboard.getTab("Commands")
      .getLayout("Intake", BuiltInLayouts.kList)
      .withSize(2,2)
      .withPosition(2,0)
      .withProperties(Map.of("Label position", "HIDDEN"));
    intakeCommands.add(new IntakeDeployCommand(m_intake));
    intakeCommands.add(new IntakeRetractCommand(m_intake));
    intakeCommands.add(m_intake);

    ShuffleboardLayout shooterCommands = Shuffleboard.getTab("Commands")
      .getLayout("Shooter", BuiltInLayouts.kList)
      .withSize(2,2)
      .withPosition(4,0)
      .withProperties(Map.of("Label position", "HIDDEN"));
    shooterCommands.add(new TestShooterCommand(m_shooter));
    shooterCommands.add(m_shooter);
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
