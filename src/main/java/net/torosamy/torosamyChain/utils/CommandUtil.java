package net.torosamy.torosamyChain.utils;


import net.torosamy.torosamyCore.commands.CommandManager;
import net.torosamy.torosamyChain.TorosamyChain;
import net.torosamy.torosamyChain.commands.AdminCommands;
import net.torosamy.torosamyChain.commands.PlayerCommands;

public class CommandUtil {
    private static final CommandManager commandManager = new CommandManager(TorosamyChain.instance);
    
    public static final PlayerCommands PLAYER_COMMANDS = new PlayerCommands();
    public static final AdminCommands ADMIN_COMMANDS = new AdminCommands();

    public static void registerCommand() {
        commandManager.annotationParser.parse(ADMIN_COMMANDS);
        commandManager.annotationParser.parse(PLAYER_COMMANDS);
    }
}
