package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class AimShooterHoodToAngleCommand extends CommandBase {

    private final double kToleranceDegrees = 0.5;

    private final Shooter m_shooter;
    private double m_goalAngleDegrees = 0;

    public AimShooterHoodToAngleCommand(double goalAngleDegrees, Shooter shooter) {
        m_shooter = shooter;
        m_goalAngleDegrees = goalAngleDegrees;
        addRequirements(m_shooter);
    }

    public AimShooterHoodToAngleCommand(String name, double goalAngleDegrees, Shooter shooter) {
        m_shooter = shooter;
        m_goalAngleDegrees = goalAngleDegrees;
        addRequirements(m_shooter);
        setName(name);
    }

    @Override
    public void initialize() {
        m_shooter.setHoodAngleDegrees(m_goalAngleDegrees);
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return Math.abs(m_shooter.getHoodAngleError()) <= kToleranceDegrees;
    }

    @Override
    public void end(boolean interrupted) {
        m_shooter.stopHoodMotor();
    }
}
