package xyz.zeppelin.ppconvert.command;

import dev.jorel.commandapi.CommandAPICommand;

public abstract class BaseCommand {

    public abstract CommandAPICommand getCommand();

    public BaseCommand() {

    }

    public void register() {
        getCommand().register();
    }
}
