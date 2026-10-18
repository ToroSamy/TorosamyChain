package net.torosamy.torosamyChain.toggle;


public class PlayerToggle {
    private boolean farming = false;
    private boolean logging = false;
    private boolean mining = false;
    private boolean plant = false;
    
    public boolean toggleFarming() {
        farming = !farming;
        return farming;
    }

    public boolean togglePlant() {
        plant = !plant;
        return plant;
    }
    
    public boolean toggleMining() {
        mining = !mining;
        return mining;
    }

    public boolean toggleLogging() {
        logging = !logging;
        return logging;
    }

    public boolean isFarming() {
        return farming;
    }

    public boolean isLogging() {
        return logging;
    }

    public boolean isMining() {
        return mining;
    }

    public boolean isPlant() {
        return plant;
    }
    
    public void disable() {
        mining = false;
        farming = false;
        logging = false;
        plant = false;
    }
}
