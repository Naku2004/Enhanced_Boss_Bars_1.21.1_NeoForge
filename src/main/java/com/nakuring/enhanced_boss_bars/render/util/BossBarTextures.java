package com.nakuring.enhanced_boss_bars.render.util;
import com.nakuring.enhanced_boss_bars.util.BossBarData;
import net.minecraft.resources.ResourceLocation;

public record BossBarTextures(
        ResourceLocation background,
        ResourceLocation progress,
        ResourceLocation overlay,

        int overlay_width,
        int overlay_height,
        int overlay_offset_x,
        int overlay_offset_y,

        int health_background_texture_width,
        int health_background_texture_height,
        int health_color_texture_width,
        int health_color_texture_height,
        int overlay_texture_width,
        int overlay_texture_height,


        int health_background_texture_coord_x,
        int health_background_texture_coord_y,
        int health_color_texture_coord_x,
        int health_color_texture_coord_y,
        int overlay_texture_coord_x,
        int overlay_texture_coord_y
) {

    public static BossBarTextures resolve(
            BossBarData data,
            Boolean second_phase
    ) {
        if (second_phase) {
            return new BossBarTextures(
                    data.second_phase_health_background_texture(),
                    data.second_phase_health_color_texture(),
                    data.second_phase_overlay_texture(),

                    data.second_phase_overlay_width(),
                    data.second_phase_overlay_height(),
                    data.second_phase_overlay_offset_x(),
                    data.second_phase_overlay_offset_y(),

                    data.second_phase_health_background_texture_width(),
                    data.second_phase_health_background_texture_height(),
                    data.second_phase_health_color_texture_width(),
                    data.second_phase_health_color_texture_height(),
                    data.second_phase_overlay_texture_width(),
                    data.second_phase_overlay_texture_height(),

                    data.second_phase_health_background_texture_coord_x(),
                    data.second_phase_health_background_texture_coord_y(),
                    data.second_phase_health_color_texture_coord_x(),
                    data.second_phase_health_color_texture_coord_y(),
                    data.second_phase_overlay_texture_coord_x(),
                    data.second_phase_overlay_texture_coord_y()
            );
        }

        return new BossBarTextures(
                data.health_background_texture(),
                data.health_color_texture(),
                data.overlay_texture(),

                data.overlay_width(),
                data.overlay_height(),
                data.overlay_offset_x(),
                data.overlay_offset_y(),

                data.health_background_texture_width(),
                data.health_background_texture_height(),
                data.health_color_texture_width(),
                data.health_color_texture_height(),
                data.overlay_texture_width(),
                data.overlay_texture_height(),

                data.health_background_texture_coord_x(),
                data.health_background_texture_coord_y(),
                data.health_color_texture_coord_x(),
                data.health_color_texture_coord_y(),
                data.overlay_texture_coord_x(),
                data.overlay_texture_coord_y()
        );
    }
}
