package net.torosamy.torosamyChain.api;

import net.torosamy.torosamyChain.listener.BlockBreakListener;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayDeque;
import java.util.Deque;

public abstract class BaseChain {
    protected final Deque<Block> going = new ArrayDeque<>();

    protected final Player player;
    
    protected BaseChain(Player player) {
        this.player = player;
    }
    
    protected boolean cancel() {
        BlockBreakListener.unIgnore(going);
        return true;
    }
    
    public abstract boolean compute();

    protected abstract void chains(Block block);
}
