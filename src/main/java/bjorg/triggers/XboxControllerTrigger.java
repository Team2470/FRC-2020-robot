package bjorg.triggers;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class XboxControllerTrigger extends Trigger {

  private final XboxController m_controller;
  private final Boolean m_left;

  public XboxControllerTrigger(XboxController controller) {
    m_controller = controller;
    m_left = true;
  }

  public XboxControllerTrigger(XboxController controller, Boolean left) {
    m_controller = controller;
    m_left = left;
  }

  @Override
  public boolean get() {
    if(m_left) {
      return m_controller.getLeftTriggerAxis() > 0.1;
    }
    return m_controller.getRightTriggerAxis() > 0.1;
  }

}