package net.torosamy.torosamyChain.scheduler;


import net.torosamy.torosamyChain.api.BaseChain;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class ChainBlockTask extends BukkitRunnable {
    private static ChainBlockTask instance = null;
    
    private final static long limit = 3L;

    private final Deque<BaseChain> chains = new ArrayDeque<>();


    public synchronized void poll(BaseChain work) {
        this.chains.addLast(work);
    }
    
    public static ChainBlockTask getInstance() {
        if (instance == null) {
            instance = new ChainBlockTask();
        }
        return instance;
    }
    
    
    private ChainBlockTask() {}
    
    @Override
    public void run() {
        long reach = System.currentTimeMillis() + limit;

        ArrayList<BaseChain> chains = new ArrayList<>();

        while (true) {
            if (this.chains.isEmpty()) {
                break;
            }

            if (System.currentTimeMillis() >= reach) {
                break;
            }

            final BaseChain chain = this.chains.poll();
            if (chain == null) {
                continue;
            }

            if (!chain.compute()) {
                chains.add(chain);
            }
            
        }
        this.chains.addAll(chains);
    }
}
