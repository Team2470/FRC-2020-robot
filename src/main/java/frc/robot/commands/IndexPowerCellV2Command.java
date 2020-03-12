package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.StorageSubsystem;

public class IndexPowerCellV2Command extends CommandBase {

    private enum State {
        kWaitForPowerCellAtIntake,
        kWaitForPowerCellAtInput,
        kWaitForPowerCellToClear,
        kFinish
    }

    private State m_state;

    private final StorageSubsystem m_storage;
    /**
     * Creates a new IndexBallCommand.
     */
    public IndexPowerCellV2Command(StorageSubsystem index) {
        // Use addRequirements() here to declare subsystem dependencies.

        m_storage = index;
        addRequirements(m_storage);

    }

    @Override
    public void initialize() {
        m_state = State.kWaitForPowerCellAtIntake;
    }

    @Override
    public void execute() {
        SmartDashboard.putString("Index PowerCell State", m_state.toString());
        switch(m_state){
            case kWaitForPowerCellAtIntake:
                if (m_storage.isBallAtIntake()) {
                    m_state = State.kWaitForPowerCellAtInput;
                    break;
                }
                break;
            case kWaitForPowerCellAtInput:
                if (m_storage.isBallAtInput()) {
                    m_state = State.kWaitForPowerCellToClear;
                    break;
                }
                m_storage.setCoveyorMotor(0.5);
                break;
            case kWaitForPowerCellToClear:
                if (!m_storage.isBallAtInput()) {
                    m_state = State.kFinish;
                    break;
                }
                m_storage.setCoveyorMotor(0.5);
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return m_state == State.kFinish;
    }

    @Override
    public void end(boolean interrupted) {
        m_storage.stopMotors();
    }
}
