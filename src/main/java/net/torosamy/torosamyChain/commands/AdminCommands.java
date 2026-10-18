package net.torosamy.torosamyChain.commands;

import net.torosamy.torosamyCore.utils.MessageUtil;
import net.torosamy.torosamyChain.api.TorosamyChainAPI;
import net.torosamy.torosamyChain.utils.ConfigUtil;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;

public class AdminCommands {
    @Command(value = "tc reload")
    @Permission("torosamychain.reload")
    @CommandDescription("重载TorosamyChain配置文件")
    public void reloadConfig(CommandSender sender) {
        ConfigUtil.reloadConfig();
        
        sender.sendMessage(MessageUtil.format(ConfigUtil.langConfig.reloadMessage));
    }
    
    @Command(value = "tc disable")
    @Permission("torosamychain.disable")
    @CommandDescription("将所有玩家重置为禁用")
    public void disable(CommandSender sender) {
        TorosamyChainAPI.disableToggles();
    }

}
