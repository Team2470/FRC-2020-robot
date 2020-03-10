package com.team254.lib.auto.actions;

public class PrintAction implements Action {

    String _message;

    public PrintAction(String message) {
        _message = message;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void start() {
        System.out.println(_message);
    }

    @Override
    public void update() {

    }

    @Override
    public void done() {

    }


}