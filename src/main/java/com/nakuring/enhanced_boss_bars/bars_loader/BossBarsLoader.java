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

                                json.health_background,
                                json.health_color,
                                json.overlay,

                                json.second_phase_background,
                                json.second_phase_color,
                                json.second_phase_overlay,

                                json.health_width,
                                json.health_height,
                                json.health_y,

                                json.overlay_width,
                                json.overlay_height,
                                json.overlay_offset_x,
                                json.overlay_offset_y,

                                json.health_texture_coord_x,
                                json.health_texture_coord_y,

                                json.overlay_texture_coord_x,
                                json.overlay_texture_coord_y,

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
}
