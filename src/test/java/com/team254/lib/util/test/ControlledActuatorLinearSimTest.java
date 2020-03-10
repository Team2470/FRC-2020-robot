package com.team254.lib.util.test;

import com.team254.util.test.ControlledActuatorLinearSim;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ControlledActuatorLinearSimTest {
    private static final double EPSILON = 1e-8;

    @Test
    void testItMoves() {
        ControlledActuatorLinearSim sim = new ControlledActuatorLinearSim(0, 1, 1);
        sim.setCommandedPosition(1);
        double now = sim.update(1);
        Assertions.assertEquals(1, now, EPSILON, "it moves one unit");
    }

    @Test
    void testItMovesCorrectly() {
        ControlledActuatorLinearSim sim = new ControlledActuatorLinearSim(0, 1, 1);
        sim.setCommandedPosition(1);
        for (double ts = 0; ts < 1.0; ts += 0.05) {
            double now = sim.update(0.05);
            Assertions.assertEquals(ts + 0.05, now, EPSILON, "it moves one ts unit");
        }

    }

    @Test
    void testItHitsMinLimit() {
        ControlledActuatorLinearSim sim = new ControlledActuatorLinearSim(0, 1, 1);
        sim.setCommandedPosition(-1);
        for (double ts = 0; ts < 1.0; ts += 0.05) {
            double now = sim.update(0.05);
            Assertions.assertEquals(0, now, EPSILON, "it doesnt move past lower limit");
        }
    }

    @Test
    void testItHitsMaxLimit() {
        ControlledActuatorLinearSim sim = new ControlledActuatorLinearSim(0, 1, 1);
        sim.setCommandedPosition(2);
        for (double ts = 0; ts < 1.0; ts += 0.05) {
            double now = sim.update(0.05);
            double raw = ts + 0.05;
            double expected = (raw > 1 ? 1 : raw);
            Assertions.assertEquals(expected, now, EPSILON, "it doesnt move past lower limit");
        }
    }
}
