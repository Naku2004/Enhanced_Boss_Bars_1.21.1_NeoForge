package com.nakuring.enhanced_boss_bars.config;

import com.nakuring.enhanced_boss_bars.config.util.BossBarConfig;
import com.nakuring.enhanced_boss_bars.config.util.GeneralConfig;
import com.nakuring.enhanced_boss_bars.interfaces.Platform;
import com.nakuring.enhanced_boss_bars.util.BossBarKey;

import java.nio.file.Path;
import java.util.HashMap;

public final class ConfigManager {

    private static Config config = new Config();
    private static Path config_file;

    private ConfigManager() {}

    public static void initialize(Platform platform) {
        config_file = platform.configFile().resolve("enhanced_boss_bars.json");
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

    static Config normalize(Config config) {
        if (config.general == null) {
            config.general = new GeneralConfig();
        }

        if (config.boss_bars == null) {
            config.boss_bars = new HashMap<>();
        }

        GeneralConfig general = config.general;

        if (general.horizontal_alignment == null) {
            general.horizontal_alignment =
                    GeneralConfig.HorizontalAlignment.Center;
        }
        if (general.vertical_direction == null) {
            general.vertical_direction = GeneralConfig.VerticalDirection.Down;
        }

        general.position_x = Math.clamp(general.position_x, 0, 100);
        general.position_y = Math.clamp(general.position_y, 0, 100);
        general.max_boss_bars = Math.clamp(general.max_boss_bars, 1, 10);

        config.boss_bars.replaceAll(
                (key, value) -> value == null ? new BossBarConfig() : value
        );

        return config;
    }
}