package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShooterCalibrateCommand extends CommandBase {

    private final double kCalibrateSpeed = 0;
    private final double kTimeOut = 10;

    private final Shooter m_shooter;
    private final Timer m_timer = new Timer();
    private boolean m_timedOut = false;

    public ShooterCalibrateCommand(Shooter shooter) {
        m_shooter = shooter;
        addRequirements(m_shooter);
    }

    @Override
    public void initialize() {
        m_timer.start();
        m_shooter.enableHoodSoftLimits(false);
        m_timedOut = false;
    }

    @Override
    public void execute() {
        m_shooter.setHoodAngleSpeed(kCalibrateSpeed);
        if (m_timer.hasPeriodPassed(kTimeOut)) {
            m_timedOut = true;
        }
    }

    @Override
    public boolean isFinished() {
        return m_shooter.isHoodAtHomePosition() || m_timedOut;
    }

    @Override
    public void end(boolean interrupted) {
        // Stop the shooter motor
        m_shooter.stopHoodMotor();
        // If Not interrupted
        //   - Zero the hood encoder
        if (!interrupted || !m_timedOut) {
            m_shooter.zeroHoodEncoder();
        }
        m_shooter.enableHoodSoftLimits(true);
    }
}
