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
import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;

import com.revrobotics.CANEncoder;
import com.revrobotics.EncoderType;


public class WPI_CANEncoder extends CANEncoder implements Sendable {

    // Simulator variables
    private String _description;

    private SimDevice m_simDevice;
    private SimDouble m_simPosition;
    private SimDouble m_simVelocity;

		public WPI_CANEncoder(WPI_CANSparkMax device, EncoderType sensorType, int counts_per_rev) {
				super(device, sensorType, counts_per_rev);

        HAL.report(tResourceType.kResourceType_RevSparkMaxCAN, device.getDeviceId());
        _description = "SparkMax " + device.getDeviceId();

				SendableRegistry.addLW(this, "SparkMax Encoder", device.getDeviceId());

        m_simDevice = SimDevice.create("Spark Max Encoder", device.getDeviceId());
        if(m_simDevice != null) {
            m_simVelocity = m_simDevice.createDouble("Speed", false, 0.0);
            m_simPosition = m_simDevice.createDouble("Angle", false, 0.0);
        }
    }
	// ------ set/get routines for Encoder interfaces ------//
			
	public double getPosition() {
		if(m_simPosition != null){
			return m_simPosition.get();
		}
		return super.getPosition();
	}

	public double getVelocity() {
		if(m_simPosition != null){
			return m_simVelocity.get();
		}
		return super.getVelocity();
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
		builder.setSmartDashboardType("Encoder");
		builder.addDoubleProperty("Position", this::getPosition, this::setPosition);
	}

	/**
	 * @return description of controller
	 */
	public String getDescription() {
		return _description;
	}
}