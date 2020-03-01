package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShooterCalibrateCommand extends CommandBase {

    private final double kCalibrateSpeed = -0.1;
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
        System.out.println("Init");
        m_timer.reset();
        m_timer.start();
        m_shooter.enableHoodSoftLimits(false);
        m_timedOut = false;
    }

    @Override
    public void execute() {
        System.out.println("Execute");
        m_shooter.setHoodAngleSpeed(kCalibrateSpeed);
        if (m_timer.hasElapsed(kTimeOut)) {
            m_timedOut = true;
        }

        SmartDashboard.putNumber("Timer", m_timer.get());
        SmartDashboard.putBoolean("Has 0.25 passed", m_timer.hasElapsed(0.25));
        SmartDashboard.putBoolean("Has Timedout", m_timedOut);
    }

    @Override
    public boolean isFinished() {
        return (m_timer.hasElapsed(0.25) && m_shooter.isHoodAtHomePosition()) || m_timedOut;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("End");
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
