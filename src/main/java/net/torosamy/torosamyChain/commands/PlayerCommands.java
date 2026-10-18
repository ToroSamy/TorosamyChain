package net.torosamy.torosamyChain.commands;

import net.torosamy.torosamyCore.utils.MessageUtil;
import net.torosamy.torosamyChain.api.TorosamyChainAPI;
import net.torosamy.torosamyChain.toggle.PlayerToggle;
import net.torosamy.torosamyChain.utils.ConfigUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;

public class PlayerCommands {
    @Command(value = "tc logging")
    @Permission("torosamychain.logging")
    @CommandDescription("切换连锁伐木状态")
    public void logging(Player player) {
        PlayerToggle toggle = TorosamyChainAPI.getToggle(player.getName());
        
        if (toggle == null) {
            TorosamyChainAPI.addToggle(player.getName());
        }
        
        toggle = TorosamyChainAPI.getToggle(player.getName());

        if (toggle.toggleLogging()) {
            player.sendMessage(MessageUtil.format(ConfigUtil.langConfig.enableLogging));
            return;
        }
        player.sendMessage(MessageUtil.format(ConfigUtil.langConfig.disableLogging));
    }

    @Command(value = "tc plant")
    @Permission("torosamychain.plant")
    @CommandDescription("切换连锁种植状态")
    public void plant(Player player) {
        PlayerToggle toggle = TorosamyChainAPI.getToggle(player.getName());

        if (toggle == null) {
            TorosamyChainAPI.addToggle(player.getName());
        }

        toggle = TorosamyChainAPI.getToggle(player.getName());

        if (toggle.togglePlant()) {
            player.sendMessage(MessageUtil.format(ConfigUtil.langConfig.enablePlant));
            return;
        }
        player.sendMessage(MessageUtil.format(ConfigUtil.langConfig.disablePlant));
    }

    @Command(value = "tc farming")
    @Permission("torosamychain.farming")
    @CommandDescription("切换连锁收获状态")
    public void farming(Player player) {
        PlayerToggle toggle = TorosamyChainAPI.getToggle(player.getName());

        if (toggle == null) {
            TorosamyChainAPI.addToggle(player.getName());
        }

        toggle = TorosamyChainAPI.getToggle(player.getName());

        if (toggle.toggleFarming()) {
            player.sendMessage(MessageUtil.format(ConfigUtil.langConfig.enableFarming));
            return;
        }
        player.sendMessage(MessageUtil.format(ConfigUtil.langConfig.disableFarming));
    }


    @Command(value = "tc mining")
    @Permission("torosamychain.mining")
    @CommandDescription("切换连锁挖矿状态")
    public void mining(Player player) {
        PlayerToggle toggle = TorosamyChainAPI.getToggle(player.getName());

        if (toggle == null) {
            TorosamyChainAPI.addToggle(player.getName());
        }

        toggle = TorosamyChainAPI.getToggle(player.getName());

        if (toggle.toggleMining()) {
            player.sendMessage(MessageUtil.format(ConfigUtil.langConfig.enableMining));
            return;
        }
        player.sendMessage(MessageUtil.format(ConfigUtil.langConfig.disableMining));
    }
}
