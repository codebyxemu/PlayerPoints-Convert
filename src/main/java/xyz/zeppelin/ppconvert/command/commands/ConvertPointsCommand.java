package xyz.zeppelin.ppconvert.command.commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.DoubleArgument;
import xyz.zeppelin.ppconvert.ConvertPlugin;
import xyz.zeppelin.ppconvert.command.BaseCommand;
import xyz.zeppelin.ppconvert.menu.menus.ConvertPointsMenu;

public class ConvertPointsCommand extends BaseCommand {

    private ConvertPlugin plugin;

    public ConvertPointsCommand(ConvertPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public CommandAPICommand getCommand() {
        return new CommandAPICommand("convertpoints")
                .executesPlayer((player, args) -> {
                    new ConvertPointsMenu(plugin).openGui(player);
                });
    }
}
