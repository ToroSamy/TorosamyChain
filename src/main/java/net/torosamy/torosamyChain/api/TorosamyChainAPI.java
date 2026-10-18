package net.torosamy.torosamyChain.api;

import net.torosamy.torosamyChain.config.MainConfig;
import net.torosamy.torosamyChain.toggle.PlayerToggle;
import net.torosamy.torosamyChain.utils.ConfigUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TorosamyChainAPI {
    private static final Map<String, PlayerToggle> toggles = new HashMap<>();

    public static PlayerToggle getToggle(String playerName) {
        return toggles.get(playerName);
    }

    public static void disableToggles() {
        toggles.values().forEach(PlayerToggle::disable);
    }
    
    public static boolean isLogging(String playerName) {
        PlayerToggle toggle = toggles.get(playerName);

        if (toggle == null) {
            return false;
        }

        return toggle.isLogging();
    }

    public static boolean isMining(String playerName) {
        PlayerToggle toggle = toggles.get(playerName);

        if (toggle == null) {
            return false;
        }

        return toggle.isMining();
    }

    public static boolean isPlant(String playerName) {
        PlayerToggle toggle = toggles.get(playerName);

        if (toggle == null) {
            return false;
        }

        return toggle.isPlant();
    }


    public static boolean isFarming(String playerName) {
        PlayerToggle toggle = toggles.get(playerName);

        if (toggle == null) {
            return false;
        }

        return toggle.isFarming();
    }
    
    public static void addToggle(String playerName) {
        toggles.put(playerName, new PlayerToggle());
    }

    public static void removeToggle(String playerName) {
        toggles.remove(playerName);
    }
    
    public static Collection<Block> getNear(Block b) {
        HashSet<Block> bs = new HashSet<>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (x != 0 || y != 0 || z != 0) {
                        bs.add((new Location(b.getWorld(), (x + b.getX()), (y + b.getY()), (z + b.getZ()))).getBlock());
                    }
                }
            }
        }
        return bs;
    }

    public static Collection<Block> getNear(Block b, int extra) {
        HashSet<Block> bs = new HashSet<>();
        for (int x = -1 - extra; x <= 1 + extra; x++) {
            for (int y = -1 - extra; y <= 1 + extra; y++) {
                for (int z = -1 - extra; z <= 1 + extra; z++) {
                    if (x != 0 || y != 0 || z != 0) {
                        bs.add((new Location(b.getWorld(), (x + b.getX()), (y + b.getY()), (z + b.getZ()))).getBlock());
                    }
                }
            }
        }
        return bs;
    }
    
    
    public static boolean canChain(Player player, MainConfig.ChainOption option) {
        if (!option.enable) {
            return false;
        }

        if (!player.hasPermission(option.permission)) {
            return false;
        }

        if (option.disableWorld.contains(player.getWorld().getName())) {
            return false;
        }

        if (option.sneaking && !player.isSneaking()) {
            return false;
        }
        return true;
    }
    
    public boolean isStone(Block block) {
        return ConfigUtil.mainConfig.stone.contains(block.getType().name());
    }
}