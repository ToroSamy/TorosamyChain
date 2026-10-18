package net.torosamy.torosamyChain.chain;

import net.torosamy.torosamyChain.api.BaseChain;
import net.torosamy.torosamyChain.api.TorosamyChainAPI;
//import net.torosamy.torosamyChain.listener.BlockBreakListener;
import net.torosamy.torosamyChain.scheduler.ChainBlockTask;
import net.torosamy.torosamyChain.utils.ConfigUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.data.Ageable;
import java.util.List;
import java.util.Map;

public class CropPlantChain extends BaseChain {
    private final Material seedType;
    
    public static boolean chains(Player player, Block block) {
        if (!TorosamyChainAPI.canChain(player, ConfigUtil.mainConfig.plant)) {
            return false;
        }

        if (!TorosamyChainAPI.isPlant(player.getName())) {
            return false;
        }

        if (block.getType() != Material.FARMLAND) {
            return false;
        }


        Material type = player.getInventory().getItemInMainHand().getType();

        if (!isSeed(type)) {
            return false;
        }


        ChainBlockTask.getInstance().poll(new CropPlantChain(player, block, type));
        return true;
    }

    private CropPlantChain(Player player, Block block, Material seedType) {
        super(player);
        this.seedType = seedType;
        chains(block);
    }



    @Override
    public boolean compute() {
        if (player == null || !player.isOnline() ||  going.isEmpty()) {
            return cancel();
        }

        Block block = going.poll();

        if (!block.getType().equals(Material.FARMLAND)) {
            return cancel();
        }

        if (!block.getWorld().equals(player.getWorld())) {
            return cancel();
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() != this.seedType) {
            return cancel();
        }
        
        if (!plant(block)) {
            return cancel();
        }
        
        item.setAmount(item.getAmount() - 1);
        
        chains(block);
        
        return going.isEmpty();
    }

    @Override
    protected void chains(Block block) {
        List<Block> list = TorosamyChainAPI.getNear(block).stream().filter(it-> {
            if (!it.getType().equals(Material.FARMLAND)) {
                return false;
            }
            if (this.going.contains(it)) {
                return false;
            }
            return true;
        }).toList();

        this.going.addAll(list);

//        BlockBreakListener.ignore(list);
    }
    
    private boolean plant(Block block) {
        Block aboveBlock = block.getRelative(0, 1, 0);

        if (aboveBlock.getType() != Material.AIR) {
            return false;
        }
        
        aboveBlock.setType(Material.WHEAT);
        
        if (!(aboveBlock.getBlockData() instanceof Ageable ageable)) {
            aboveBlock.setType(Material.AIR);
            return false;
        }
        
        ageable.setAge(0);
        aboveBlock.setBlockData(ageable);
        
        return true;
    }
    
    
    
    private static boolean isSeed(Material type) {
        if (type == null) {
            return false;
        }

        String typeName = type.toString();

        for (String name : ConfigUtil.mainConfig.seed) {
            if (typeName.equals(name.toUpperCase())) {
                return true;
            }
        }
        
        return false;
    }
}
