package net.torosamy.torosamyChain.chain;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import net.torosamy.torosamyChain.api.TorosamyChainAPI;
import net.torosamy.torosamyChain.api.BaseChain;
import net.torosamy.torosamyChain.listener.BlockBreakListener;
import net.torosamy.torosamyChain.scheduler.ChainBlockTask;
import net.torosamy.torosamyChain.utils.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;


public class TreeBreakChain extends BaseChain {
    private final Deque<Block> leaves = new ArrayDeque<>();

    private int cl = 0;

    public static boolean chains(Player player, Block block) {
        if (!TorosamyChainAPI.canChain(player, ConfigUtil.mainConfig.logging)) {
            return false;
        }

        if (!TorosamyChainAPI.isLogging(player.getName())) {
            return false;
        }

        if (!isTrees(block.getType())) {
            return false;
        }

        ChainBlockTask.getInstance().poll(new TreeBreakChain(player, block));
        return true;
    }

    private TreeBreakChain(Player player, Block block) {
        super(player);
        chains(block);
    }
    @Override
    public boolean compute() {
        if (player == null || !player.isOnline()) {
            return true;
        }
        
        if (going.isEmpty()) {
            for (int i = 0; !leaves.isEmpty() && i < 6; i++) {
                Block leave = leaves.poll();

                BlockBreakEvent event = new BlockBreakEvent(leave, player);

                Bukkit.getPluginManager().callEvent(event);

                if (!event.isCancelled()) {
                    leave.breakNaturally();
                }

                chainsLeave(leave);
            }
            return (this.going.isEmpty() && this.leaves.isEmpty());
        }

        Block block = this.going.poll();


        if (!isTrees(block.getType())) {
            return cancel();
        }

        if (!block.getWorld().equals(player.getWorld())) {
            return cancel();
        }

        if (!isAxe(player)) {
            return cancel();
        }

        if (!player.breakBlock(block)) {
            return cancel();
        }

        chains(block);

        block.getWorld().playSound(block.getLocation(), block.getBlockData().getSoundGroup().getBreakSound(), 1.0F, 1.0F);

        return (this.going.isEmpty() && this.leaves.isEmpty());
    }






    private void chainsLeave(Block b) {
        cl++;

        if (cl > 64) {
            return;
        }
        List<Block> list = TorosamyChainAPI.getNear(b).stream().filter(it -> {
            if (!isLeaves(it)) return false;

            if (leaves.contains(it)) return false;

            return true;
        }).toList();

        leaves.addAll(list);
    }

    @Override
    protected void chains(Block block) {
        BlockBreakListener.unIgnore(block);
        
        for (Block it : TorosamyChainAPI.getNear(block)) {
            if (isLeaves(it) && !leaves.contains(it)) {
                leaves.add(it);
                continue;
            }

            if (!isTrees(it.getType())) {
                continue;
            }

            if (it.getY() < block.getY()) {
                continue;
            }

            if (going.contains(it)) {
                continue;
            }
            BlockBreakListener.ignore(it);
            going.add(it);
        }
    }

    private static boolean isTrees(Material material) {
        return (isNormalTress(material) || isSpecialTrees(material));
    }

    private static boolean isSpecialTrees(Material material) {
        return ConfigUtil.mainConfig.specialTrees.contains(material.name().toUpperCase());
    }

    private static boolean isNormalTress(Material material) {
        return material.toString().toUpperCase().contains("LOG");
    }


    private static boolean isLeaves(Block b) {
        return b.getType().toString().toUpperCase().contains("LEAVES");
    }

    private static boolean isAxe(Player player) {
        return player.getInventory().getItemInMainHand().getType().toString().toUpperCase().contains("_AXE");
    }
}