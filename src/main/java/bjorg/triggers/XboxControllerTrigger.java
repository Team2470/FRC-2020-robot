package bjorg.triggers;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class XboxControllerTrigger extends Trigger {

  private final XboxController m_controller;
  private final Hand m_hand;

  public XboxControllerTrigger(XboxController controller, Hand hand) {
    m_controller = controller;
    m_hand = hand;
  }

  @Override
  public boolean get() {
    return m_controller.getTriggerAxis(m_hand) > 0.1;
  }

}