/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Map;

import com.kennedyrobotics.triggers.DPadTrigger;

import bjorg.command.NamedInstantCommand;
import bjorg.triggers.XboxControllerTrigger;
import com.kennedyrobotics.triggers.DPadTrigger.DPad;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import frc.robot.commands.DriveWithController;
import frc.robot.commands.ForwardStorageExitCommand;
import frc.robot.commands.IndexBallCommand;
import frc.robot.commands.IntakeDeployCommand;
import frc.robot.commands.IntakeDumpCommand;
import frc.robot.commands.IntakeRetractCommand;
import frc.robot.commands.LoadConveyorCommand;
import frc.robot.commands.ReverseStorageExitCommand;
import frc.robot.commands.TestStorageBackwardCommand;
import frc.robot.commands.TestStorageForwardCommand;
import frc.robot.commands.WaitForBallCommand;
import frc.robot.commands.shooter.AimShooterHoodDownCommand;
import frc.robot.commands.shooter.AimShooterHoodUpCommand;
import frc.robot.commands.shooter.ManualShooterCommand;
import frc.robot.commands.shooter.TestShooterCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.StorageExitSubsystem;
import frc.robot.subsystems.StorageSubsystem;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem m_drive = new DriveSubsystem();
  private final Shooter m_shooter = new Shooter();
  private final IntakeSubsystem m_intake = new IntakeSubsystem();
  private final StorageSubsystem m_storage = new StorageSubsystem();
  private final StorageExitSubsystem m_storageExit = new StorageExitSubsystem();
  private final Climber m_climber = new Climber();
  private final Vision m_vision = new Vision();
  private final XboxController m_controller = new XboxController(Constants.kControllerDriver); 

  // private final InternalButton m_indexPowercellButton = new InternalButton();


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
    m_storage.setDefaultCommand(new SequentialCommandGroup(
      new WaitForBallCommand(m_storage),
      new IndexBallCommand(m_storage)
    ));

    m_storageExit.setDefaultCommand(new ReverseStorageExitCommand(m_storageExit));
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    JoystickButton intakeDeployButton = new JoystickButton(m_controller, XboxController.Button.kBumperLeft.value);
    intakeDeployButton.whileHeld(new IntakeDeployCommand(m_intake));

    JoystickButton dumpPowerCellsButton = new JoystickButton(m_controller, XboxController.Button.kY.value);
    dumpPowerCellsButton.whileHeld(new ParallelCommandGroup(
      new IntakeDumpCommand(m_intake),
      new TestStorageBackwardCommand(m_storage)
    ));

    JoystickButton conveyorForwardButton = new JoystickButton(m_controller, XboxController.Button.kBumperRight.value);
    conveyorForwardButton.whileHeld(new ParallelCommandGroup(
      new TestStorageForwardCommand(m_storage),
      new ForwardStorageExitCommand(m_storageExit)
    ));

    XboxControllerTrigger shooterTrigger = new XboxControllerTrigger(m_controller, Hand.kLeft);
    shooterTrigger.whileActiveOnce(new ManualShooterCommand(m_shooter, m_controller));

    DPadTrigger aimHoodUpTrigger = new DPadTrigger(m_controller, DPad.kUp);
    aimHoodUpTrigger.whileActiveOnce(new AimShooterHoodUpCommand(m_shooter));

    DPadTrigger aimHoodDownTrigger = new DPadTrigger(m_controller, DPad.KDown);
    aimHoodDownTrigger.whileActiveOnce(new AimShooterHoodDownCommand(m_shooter));


    // Configure out shooter buttons
    // TestShooterCommand m_testShooterCmd = new TestShooterCommand(m_shooter);
    //new JoystickButton(m_controller, Button.kBumperLeft.value).whenPressed(m_testShooterCmd);
    //new JoystickButton(m_controller, Button.kBumperRight.value).cancelWhenPressed(m_testShooterCmd);

    // m_indexPowercellButton.whenPressed(new IndexBallCommand(m_storage));
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
      storageCommands.add(new ParallelCommandGroup(
        new IntakeDumpCommand(m_intake),
        new TestStorageBackwardCommand(m_storage)
      ));
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
    shooterCommands.add(new TestShooterCommand(m_shooter, m_storageExit));
    shooterCommands.add(m_shooter);

    ShuffleboardLayout storageExit = Shuffleboard.getTab("Commands")
            .getLayout("StorageExit", BuiltInLayouts.kList)
            .withSize(2,2)
            .withPosition(6,0)
            .withProperties(Map.of("Label position", "HIDDEN"));
    storageExit.add(m_storageExit);

    // SmartDashboard.putData("Dump Powercells", );
    ShuffleboardLayout visionCommands = Shuffleboard.getTab("Commands")
    .getLayout("Vision", BuiltInLayouts.kList)
    .withSize(2,2)
    .withPosition(8,0)
    .withProperties(Map.of("Label position", "HIDDEN"));
    visionCommands.add(new NamedInstantCommand("Driver Mode", () -> m_vision.setDriverMode(true), m_vision));
    visionCommands.add(new NamedInstantCommand("Vision Mode", () -> m_vision.setDriverMode(false), m_vision));
    
  }

  public void periodic() {
    // m_indexPowercellButton.setPressed(m_storage.isBallAtInput());
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
