package com.nakuring.enhanced_boss_bars.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
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

        try (Reader reader = Files.newBufferedReader(file)) {

            Config config = GSON.fromJson(reader, Config.class);

            return config != null
                    ? config
                    : new Config();

        } catch (IOException e) {

            e.printStackTrace();
            return new Config();

        }

    }

    public static void save(
            Path file,
            Config config
    ) {
        try {

            Files.createDirectories(file.getParent());

            try (Writer writer = Files.newBufferedWriter(file)) {
                GSON.toJson(config, writer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}