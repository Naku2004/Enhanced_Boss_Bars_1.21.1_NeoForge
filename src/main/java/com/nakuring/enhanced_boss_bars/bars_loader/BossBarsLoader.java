package com.nakuring.enhanced_boss_bars.bars_loader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nakuring.enhanced_boss_bars.BossBarsManager;
import com.nakuring.enhanced_boss_bars.interfaces.Platform;
import com.nakuring.enhanced_boss_bars.util.BossBarData;
import com.nakuring.enhanced_boss_bars.util.BossBarKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.util.Map;
import java.util.Objects;

public class BossBarsLoader {

    private static final Logger LOGGER =
            LoggerFactory.getLogger("Enhanced Boss Bars");

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(ResourceLocation.class, new IdentifierAdapter())
            .create();

    public static void load(
            ResourceManager resourceManager,
            Platform platform
    ) {

        Map<ResourceLocation, Resource> resources =
                resourceManager.listResources(
                        "enhanced_boss_bars",
                        id -> id.getPath().endsWith(".json")
                );

        LOGGER.info(
                "Found {} boss bar files",
                resources.size()
        );

        for (ResourceLocation id : resources.keySet()) {
            LOGGER.info("Found: {}", id);
        }

        for (Map.Entry<ResourceLocation, Resource> entry : resources.entrySet()) {

            try (Reader reader = entry.getValue().openAsReader()) {

                BossBarDefinition json =
                        GSON.fromJson(
                                reader,
                                BossBarDefinition.class
                        );

                applyTextureSizeDefaults(json);

                if (!platform.isModLoaded(json.mod_id)
                        && !Objects.equals(json.mod_id, "")
                        && !Objects.equals(json.mod_id, "minecraft")) {

                    continue;
                }

                BossBarsManager.registerBossBar(

                        new BossBarKey(
                                json.key,
                                json.type
                        ),

                        new BossBarData(
                                json.author,
                                json.mod_id,

                                json.health_background_texture,
                                json.health_color_texture,
                                json.overlay_texture,

                                json.health_bar_width,
                                json.health_bar_height,

                                json.health_offset_x,
                                json.health_offset_y,

                                json.overlay_width,
                                json.overlay_height,
                                json.overlay_offset_x,
                                json.overlay_offset_y,

                                json.health_background_texture_width,
                                json.health_background_texture_height,
                                json.health_color_texture_width,
                                json.health_color_texture_height,
                                json.overlay_texture_width,
                                json.overlay_texture_height,

                                json.health_background_texture_coord_x,
                                json.health_background_texture_coord_y,
                                json.health_color_texture_coord_x,
                                json.health_color_texture_coord_y,
                                json.overlay_texture_coord_x,
                                json.overlay_texture_coord_y,

                                json.second_phase_health_background_texture,
                                json.second_phase_health_color_texture,
                                json.second_phase_overlay_texture,

                                json.second_phase_health_background_texture_width,
                                json.second_phase_health_background_texture_height,
                                json.second_phase_health_color_texture_width,
                                json.second_phase_health_color_texture_height,
                                json.second_phase_overlay_texture_width,
                                json.second_phase_overlay_texture_height,

                                json.second_phase_health_background_texture_coord_x,
                                json.second_phase_health_background_texture_coord_y,
                                json.second_phase_health_color_texture_coord_x,
                                json.second_phase_health_color_texture_coord_y,
                                json.second_phase_overlay_texture_coord_x,
                                json.second_phase_overlay_texture_coord_y,

                                json.threshold_phase
                        )
                );

            } catch (Exception e) {
                LOGGER.error(
                        "Couldn't load boss bar {}",
                        entry.getKey(),
                        e
                );
            }
        }

        BossBarsManager.applyAuthorSelections();
        BossBarsManager.sortBossBarDefinitions();
    }

    private static void applyTextureSizeDefaults(BossBarDefinition json) {
        if (json.health_background_texture_width == null) {
            json.health_background_texture_width = json.health_bar_width;
        }
        if (json.health_background_texture_height == null) {
            json.health_background_texture_height = json.health_bar_height;
        }
        if (json.health_color_texture_width == null) {
            json.health_color_texture_width = json.health_bar_width;
        }
        if (json.health_color_texture_height == null) {
            json.health_color_texture_height = json.health_bar_height;
        }
        if (json.overlay_texture_width == null) {
            json.overlay_texture_width = json.overlay_width;
        }
        if (json.overlay_texture_height == null) {
            json.overlay_texture_height = json.overlay_height;
        }

        if (json.second_phase_health_background_texture_width == null) {
            json.second_phase_health_background_texture_width = json.health_background_texture_width;
        }
        if (json.second_phase_health_background_texture_height == null) {
            json.second_phase_health_background_texture_height = json.health_background_texture_height;
        }
        if (json.second_phase_health_color_texture_width == null) {
            json.second_phase_health_color_texture_width = json.health_color_texture_width;
        }
        if (json.second_phase_health_color_texture_height == null) {
            json.second_phase_health_color_texture_height = json.health_color_texture_height;
        }
        if (json.second_phase_overlay_texture_width == null) {
            json.second_phase_overlay_texture_width = json.overlay_texture_width;
        }
        if (json.second_phase_overlay_texture_height == null) {
            json.second_phase_overlay_texture_height = json.overlay_texture_height;
        }
    }
}
