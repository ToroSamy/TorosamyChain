package net.torosamy.torosamyChain.utils;

import net.torosamy.torosamyCore.config.Config;
import net.torosamy.torosamyCore.config.ConfigFile;
import net.torosamy.torosamyChain.TorosamyChain;
import net.torosamy.torosamyChain.config.LangConfig;
import net.torosamy.torosamyChain.config.MainConfig;

import java.util.ArrayList;
import java.util.List;

public class ConfigUtil {
    private static final List<Config> configs = new ArrayList<>();

    public static final MainConfig mainConfig = new MainConfig();
    public static final LangConfig langConfig = new LangConfig();

    public static void initConfig() {
        configs.clear();
        configs.add(new Config(mainConfig, new ConfigFile(TorosamyChain.instance, "config.yml")));
        configs.add(new Config(langConfig, new ConfigFile(TorosamyChain.instance, "lang.yml")));
    }

    public static void reloadConfig() {
        for (Config config : configs) {
            config.load();
        }
    }

    public static void saveConfig() {
        for (Config config : configs) {
            config.save();
        }
    }
}
