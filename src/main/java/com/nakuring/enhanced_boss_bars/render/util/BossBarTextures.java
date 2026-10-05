package com.nakuring.enhanced_boss_bars.render.util;
import com.nakuring.enhanced_boss_bars.util.BossBarData;
import net.minecraft.resources.ResourceLocation;

public record BossBarTextures(
        ResourceLocation background,
        ResourceLocation progress,
        ResourceLocation overlay
) {

    public static BossBarTextures resolve(
            BossBarData data,
            Boolean second_phase
    ) {
        if (second_phase) {
            return new BossBarTextures(
                    data.second_phase_background(),
                    data.second_phase_color(),
                    data.second_phase_overlay()
            );
        }

        return new BossBarTextures(
                data.health_background(),
                data.health_color(),
                data.overlay()
        );
    }
}
