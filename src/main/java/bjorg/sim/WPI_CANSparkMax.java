/**
 * WPI Compliant motor controller class.
 * WPILIB's object model requires many interfaces to be implemented to use
 * the various features.
 * This includes...
 * - Software PID loops running in the robot controller
 * - LiveWindow/Test mode features
 * - Motor Safety (auto-turn off of motor if Set stops getting called)
 * - Single Parameter set that assumes a simple motor controller.
 */

package bjorg.sim;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.HALValue;
import edu.wpi.first.hal.SimBoolean;
import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.hal.SimValue;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;
import frc.robot.Robot;

import com.revrobotics.CANError;
import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;


public class WPI_CANSparkMax extends CANSparkMax implements Sendable {

    // Simulator variables
    private String _description;

    private SimDevice m_simDevice;
    private SimDouble m_simSpeed;
    private SimDouble m_simCurrent;
		private SimBoolean m_simInvert;
		private SimBoolean m_simFollow;
		private SimValue m_simFollowMaster;
		private SimValue m_simCurrentLimit;

    /**
     * Create a motor capable of simulation
     * @param deviceNumber The CAN ID of the device
     * @param motor The motor type
     */
    public WPI_CANSparkMax(int deviceNumber, MotorType motor) {
        super(deviceNumber, motor);
        HAL.report(tResourceType.kResourceType_RevSparkMaxCAN, deviceNumber +1);
        _description = "SparkMax " + deviceNumber;

        SendableRegistry.addLW(this, "Spark Max ", deviceNumber);

        m_simDevice = SimDevice.create("Spark Max", deviceNumber);
        if(m_simDevice != null) {
            m_simSpeed = m_simDevice.createDouble("Motor Output", false, 0.0);
						m_simInvert = m_simDevice.createBoolean("Inverted?", false, false);
						m_simCurrent = m_simDevice.createDouble("Current", false, 0.0);
						m_simFollow = m_simDevice.createBoolean("Follow?", true, false);
						m_simFollowMaster = m_simDevice.createValue("Master", true, HALValue.makeInt(0));
						m_simCurrentLimit = m_simDevice.createValue("CurrentLimit", true, HALValue.makeInt(80));
        }
    }

  // ------ set/get routines for WPILIB interfaces ------//
    
	/**
	 * Common interface for setting the speed of a simple speed controller.
	 *
	 * @param speed The speed to set.  Value should be between -1.0 and 1.0.
	 * 									Value is also saved for Get().
	 */
    @Override
    public void set(double speed) {
			if (Robot.isReal()) {
				super.set(speed);
			}
        simSet(speed);
    }

	/**
	 * Common interface for getting the current set speed of a speed controller.
	 *
	 * @return The current set speed. Value is between -1.0 and 1.0.
	 */
	@Override
	public double get() {
		if(m_simSpeed != null){
			return m_simSpeed.get();
		}
		return super.get();
    }

	/**
	 * Gets the simulated output current;
	 */
	@Override
	public double getOutputCurrent() {
		if(m_simCurrent != null) {
			return m_simCurrent.get();
		}
		return super.getOutputCurrent();
	}

	/**
	 * Helper class to set the simulated speed of the motor
	 * @param demand0 The requested speed
	 */
	private void simSet(double demand0){
		if(m_simSpeed != null && m_simInvert != null){
			m_simSpeed.set(demand0 * (m_simInvert.get() ? -1:1));
		}
	}


	// ----------------------- Invert routines -------------------//
	/**
	 * Common interface for inverting direction of a speed controller.
	 *
	 * @param isInverted The state of inversion, true is inverted.
	 */
	@Override
	public void setInverted(boolean isInverted) {
		if (Robot.isReal()) {
			super.setInverted(isInverted);
		}
		if(m_simInvert != null){
			m_simInvert.set(isInverted);
		}
    }
    
	/**
	 * Common interface for returning the inversion state of a speed controller.
	 *
	 * @return The state of inversion, true is inverted.
	 */
	@Override
	public boolean getInverted() {
		if(m_simInvert != null){
			return m_simInvert.get();
		}
		return super.getInverted();
    }

	// ----------------------- turn-motor-off routines-------------------//
	/**
	 * Common interface for disabling a motor.
	 */
	@Override
	public void disable() {
		if (Robot.isReal()) {
			super.disable();
		}
		simSet(0.0);
	}

	/**
	 * Common interface to stop the motor until Set is called again.
	 */
	@Override
	public void stopMotor() {
				if (Robot.isReal()) {
					super.stopMotor();
				}
        simSet(0.0);
    }

	// ---- Low level overrides, including following mode ---- //
	@Override
	public CANError follow(final CANSparkMax leader, boolean invert) {
		if(m_simFollow != null && m_simFollowMaster != null){
			m_simFollow.set(true);
			m_simFollowMaster.setValue(HALValue.makeInt(leader.getDeviceId()));
		}
		if (m_simInvert != null) {
			m_simInvert.set(invert);
		}

		if (Robot.isReal()) {
			return super.follow(leader, invert);
		} else {
			return CANError.kOk;
		}
	}

	@Override
	public CANError restoreFactoryDefaults() {
		if (Robot.isReal()) {
			return super.restoreFactoryDefaults();
		} else {
			return CANError.kOk;
		}
	}

	@Override
	public CANError setSmartCurrentLimit(int limit) {
		if (m_simCurrentLimit != null) {
			m_simCurrentLimit.setValue(HALValue.makeInt(limit));
		}

		if (Robot.isReal()) {
			return super.setSmartCurrentLimit(limit);
		} else {
			return CANError.kOk;
		}
	}

	public WPI_CANEncoder getEncoder() {
		return new WPI_CANEncoder(this, EncoderType.kHallSensor, 0);
	}

	// ---- essentially a copy of SendableBase -------//
	private String m_name = "";
	private String m_subsystem = "Ungrouped";

	/**
	 * Free the resources used by this object.
	 */
	public void free() {
		SendableRegistry.remove(this);
	}

	/**
	 * @return name of object
	 */
	@Override
	public final synchronized String getName() {
		return m_name;
	}

	/**
	 * Sets the name of the object
	 * 
	 * @param name 
	 * 			  name of object
	 */
	@Override
	public final synchronized void setName(String name) {
		m_name = name;
	}

	/**
	 * Sets the name of the sensor with a channel number.
	 *
	 * @param moduleType
	 *            A string that defines the module name in the label for the
	 *            value
	 * @param channel
	 *            The channel number the device is plugged into
	 */
	public final void setName(String moduleType, int channel) {
		setName(moduleType + "[" + channel + "]");
	}

	/**
	 * Sets the name of the sensor with a module and channel number.
	 *
	 * @param moduleType
	 *            A string that defines the module name in the label for the
	 *            value
	 * @param moduleNumber
	 *            The number of the particular module type
	 * @param channel
	 *            The channel number the device is plugged into (usually PWM)
	 */
	public final void setName(String moduleType, int moduleNumber, int channel) {
		setName(moduleType + "[" + moduleNumber + "," + channel + "]");
	}

	/**
	 * @return subsystem name of this object
	 */
	@Override
	public final synchronized String getSubsystem() {
		return m_subsystem;
	}

	/**
	 * Sets the subsystem name of this object
	 * 
	 * @param subsystem
	 */
	@Override
	public final synchronized void setSubsystem(String subsystem) {
		m_subsystem = subsystem;
	}

	/**
	 * Add a child component.
	 *
	 * @param child
	 *            child component
	 */
	public final void addChild(Object child) {
		SendableRegistry.addChild(this, child);
	}

	/**
	 * Initialize sendable
	 * @param builder Base sendable to build on
	 */
	@Override
	public void initSendable(SendableBuilder builder) {
		builder.setSmartDashboardType("Speed Controller");
		builder.setSafeState(this::stopMotor);
		builder.addDoubleProperty("Value", this::get, this::set);
	}

	/**
	 * @return description of controller
	 */
	public String getDescription() {
		return _description;
	}
}