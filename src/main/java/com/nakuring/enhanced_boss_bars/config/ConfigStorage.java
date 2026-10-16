package com.nakuring.enhanced_boss_bars.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.nakuring.enhanced_boss_bars.EnhancedBossBars;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ConfigStorage {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private ConfigStorage() {}

    public static Config load(Path file) {
        if (!Files.exists(file)) {
            return new Config();
        }

        try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            Config config = GSON.fromJson(reader, Config.class);
            return ConfigManager.normalize(config != null ? config : new Config());
        } catch (IOException | JsonParseException e) {
            EnhancedBossBars.LOGGER.error(
                    "Can't load configuration from {}",
                    file,
                    e
            );
            return new Config();
        }
    }

    public static void save(Path file, Config config) {
        try {
            Path parent = file.getParent();

            if (parent != null) Files.createDirectories(parent);

            try (Writer writer = Files.newBufferedWriter(
                    file,
                    StandardCharsets.UTF_8
            )) {
                GSON.toJson(config, writer);
            }
        }
        catch (IOException e) {
            EnhancedBossBars.LOGGER.error(
                    "Can't save configuration on {}",
                    file,
                    e
            );
        }
    }
}