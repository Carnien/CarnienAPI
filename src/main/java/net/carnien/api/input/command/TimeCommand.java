package net.carnien.api.input.command;

import net.carnien.api.Carnien;
import net.carnien.api.input.CarnienCommand;
import net.carnien.api.input.command.time.TimeSetCommand;

public class TimeCommand extends CarnienCommand {

    public TimeCommand(Carnien carnien) {
        super(carnien, "time", "api.time", "time");
        addSubCommand(new TimeSetCommand(carnien));
    }

}
