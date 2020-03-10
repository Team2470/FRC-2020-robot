package com.kennedyrobotics.auto;

import com.kennedyrobotics.drivers.RevDigit;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.Command;

import java.util.*;

public class AutoSelector implements Runnable {

    private static class Auto {
        String id;
        String displayName;
        Command cmd;

        Auto(String id, String displayName, Command cmd) {
            this.id = id;
            this.displayName = displayName;
            this.cmd = cmd;
        }
    }

    private static final String kPreferenceKey = "selected_auto";
    private static final String kDefaultAutoId = "default";


//    private final Logger log = LoggerFactory.getLogger("AutoSelector");
    private final List<String> commandsIds_ = new ArrayList<>();
    private final Map<String, Auto> commands_ = new HashMap<>();

    private final RevDigit revDigit_;
    private final Thread workerThread_;

    private int selectedAutoPos_;

    public AutoSelector(RevDigit revDigit, Command defaultCommand) {
        revDigit_ = revDigit;
        workerThread_ = new Thread(this);
        registerCommand(kDefaultAutoId, "dflt", defaultCommand);

    }

    private synchronized Auto selectedAuto() {
        return commands_.get(commandsIds_.get(selectedAutoPos_));
    }

    public synchronized Command selected() {
        return selectedAuto().cmd;
    }

    public synchronized void registerCommand(String id, String displayName, Command cmd) {
        commandsIds_.add(id);
        commands_.put(id, new Auto(id, displayName, cmd));
    }

    public synchronized void initialize() {
        log("Starting initialization");

        // Get stored auto value from the file system
        String autoId = Preferences.getInstance().getString(kPreferenceKey, kDefaultAutoId);
        if (!commands_.containsKey(autoId)) {
            log("Auto does not exist in command map" + autoId);
            autoId = kDefaultAutoId;
        }

        int selectedAutoPos = commandsIds_.indexOf(autoId);
        if (selectedAutoPos_ == -1) {
            log("Auto id " + autoId + " does not exit in id list");
            selectedAutoPos = 0; // Default auto
        }

        selectedAutoPos_ = selectedAutoPos;
        workerThread_.start();

        log("Initialization done");
    }

    /**
     * Worker thread to update Selected auto from display
     */
    @Override
    public void run() {
        log("Starting Worker thread");
        while (true) {
            this.tick();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.err.println("AutoSelection Could not sleep");
            }
        }
    }

    /**
     * Worker thread tick
     */
    private synchronized void tick() {
        if(revDigit_.getA()) {
            // Goto next auto
            this.nextAuto();
        } else if(revDigit_.getB()) {
            // Goto previous auto
            this.perviousAuto();
        } else {
            // Do nothing
        }

        // Update Display
        Auto auto = selectedAuto();
        revDigit_.display(auto.displayName);
    }

    /**
     * Advance to the next auto
     */
    private synchronized void nextAuto() {
        selectedAutoPos_ = (selectedAutoPos_ + 1) % commandsIds_.size();
        updateSelectedAuto();
        log("Advance to next auto: " + selectedAuto().id);
    }

    /**
     * Advance to the previous auto
     */
    private synchronized void perviousAuto() {
        selectedAutoPos_ = selectedAutoPos_ - 1;
        if (selectedAutoPos_ < 0) {
            selectedAutoPos_ = commandsIds_.size() - 1;
        }
        updateSelectedAuto();
        log("Advance to pervious auto: " + selectedAuto().id);
    }

    /**
     * Update the currently selected auto
     * This will update the Prefernces and SmartDashboard key value
     */
    private synchronized void updateSelectedAuto() {
        Auto auto = selectedAuto();
        Preferences.getInstance().putString(kPreferenceKey, auto.id);
        log("Selected Auto "+ auto.id);
    }

    private void log(String msg) {
        System.out.println("[AutoSelector] " + msg);
    }
}
