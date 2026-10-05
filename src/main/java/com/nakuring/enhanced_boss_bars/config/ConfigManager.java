package com.nakuring.enhanced_boss_bars.config;

import com.nakuring.enhanced_boss_bars.config.util.BossBarConfig;
import com.nakuring.enhanced_boss_bars.config.util.GeneralConfig;
import com.nakuring.enhanced_boss_bars.interfaces.Platform;
import com.nakuring.enhanced_boss_bars.util.BossBarKey;

import java.nio.file.Path;

public final class ConfigManager {

    private static Config config = new Config();
    private static Path config_file;

    private ConfigManager() {}

    public static void initialize(Platform platform) {
        config_file = platform.configFile();
        load();
    }

    public static void load() {
        config = ConfigStorage.load(config_file);
    }

    public static void save() {
        ConfigStorage.save(config_file, config);
    }

    public static Config config() {
        return config;
    }

    public static GeneralConfig generalConfig() {
        return config.general;
    }

    public static BossBarConfig barConfig(BossBarKey key) {
        return config.boss_bars.computeIfAbsent(
                key.id(),
                id -> new BossBarConfig()
        );
    }
}