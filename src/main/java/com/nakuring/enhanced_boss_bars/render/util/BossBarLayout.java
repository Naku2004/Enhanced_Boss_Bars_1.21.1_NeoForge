package com.nakuring.enhanced_boss_bars.render.util;

import com.nakuring.enhanced_boss_bars.config.util.GeneralConfig;
import com.nakuring.enhanced_boss_bars.render.context.BossBarStackLayout;
import com.nakuring.enhanced_boss_bars.util.BossBarData;

public record BossBarLayout(
        int x,
        int y,

        int health_x,
        int health_y,

        int overlay_x,
        int overlay_y,

        int name_x,
        int name_y
) {

    public static BossBarLayout calculateCoords(
            BossBarStackLayout stack_layout,
            BossBarData boss_bar,
            BossBarTextures textures,
            int name_width,
            boolean generic
    ) {
        GeneralConfig config = stack_layout.getConfig();

        int y = stack_layout.getCurrentBarY();

        if (!stack_layout.isDirectionDown()){
            y -= (textures.overlay_height() + (generic ? 5 : 0));
        }

        if (generic) y += 5;

        int x = switch (config.horizontal_alignment) {

            case Left -> stack_layout.getAnchorX() + textures.overlay_width() / 2;

            case Center -> stack_layout.getAnchorX();

            case Right -> stack_layout.getAnchorX() - textures.overlay_width() / 2;
        };

        int health_x = x - boss_bar.health_bar_width() / 2 + boss_bar.health_offset_x();
        int health_y = y + boss_bar.health_offset_y();

        int overlay_x = x - textures.overlay_width() / 2 + textures.overlay_offset_x();
        int overlay_y = y + textures.overlay_offset_y();

        int name_x = x - name_width / 2;
        int name_y = y - 4;

        return new BossBarLayout(
                x,
                y,
                health_x,
                health_y,
                overlay_x,
                overlay_y,
                name_x,
                name_y
        );
    }
}