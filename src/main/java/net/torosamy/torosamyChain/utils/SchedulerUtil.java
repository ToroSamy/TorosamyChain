package net.torosamy.torosamyChain.utils;

import net.torosamy.torosamyChain.TorosamyChain;
import net.torosamy.torosamyChain.scheduler.ChainBlockTask;

public class SchedulerUtil {
    public static void registerScheduler() {
        ChainBlockTask.getInstance().runTaskTimer(TorosamyChain.instance, 1L, 1L);
    }
}
