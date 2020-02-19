/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.kennedyrobotics.triggers;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * Add your docs here.
 */
public class DPadTrigger extends Trigger {

  public enum DPad {
    kUp, 
    KDown,
    KLeft,
    kRight
  }

  private final DPad dpad_;
  private final XboxController controller_;

  public DPadTrigger(XboxController controller, DPad dpad) {
    dpad_ = dpad;
    controller_ = controller;
  }

  @Override
  public boolean get() {

    int pov = controller_.getPOV();

    DPad current;

    switch (pov) {
    case 0:
      current = DPad.kUp;
      break;
    case 90:
      current = DPad.kRight;
      break;
    case 180:
      current = DPad.KDown;
      break;
    case 270:
      current = DPad.KLeft;
      break;
    default:
      return false;
    }

    return dpad_ == current;
  }
}