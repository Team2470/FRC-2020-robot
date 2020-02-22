/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package bjorg.command;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * This is simply a wrapper that lets you name the isntant commands on creation
 * Just a utility to make debugging buttons cleaner
 */
public class NamedInstantCommand extends InstantCommand {

  public NamedInstantCommand(String name, Runnable toRun, Subsystem... requirements) {
    super(toRun, requirements);
    this.setName(name);
  }
}
