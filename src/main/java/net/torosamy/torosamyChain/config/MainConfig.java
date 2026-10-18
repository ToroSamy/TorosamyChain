package net.torosamy.torosamyChain.config;

import net.torosamy.torosamyCore.config.IConfigManage;

import java.util.List;

public class MainConfig implements IConfigManage{
    public ChainOption farming = new ChainOption();
    public ChainOption mining = new ChainOption();
    public ChainOption logging = new ChainOption();
    public ChainOption plant = new ChainOption();
    
    public class ChainOption implements IConfigManage {
        public boolean enable;
        public String permission;
        public List<String> disableWorld;
        public boolean sneaking;
    }
    
    public List<String> specialTrees;
    public List<String> stone;
    public List<String> seed;
    public String blockBreakPermissionPrefix;
    public String useToolPermissionPrefix;
    public String plantSeedPermissionPrefix;
}
