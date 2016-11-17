package com.punisher.shooter.driver;

/**
 * Created by guillaumenostrenoff on 25/03/2016.
 */
public class FireCommand {

    private Command command;

    private int value;

    public FireCommand(Command command, int value) {
        this.command = command;
        this.value = value;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
