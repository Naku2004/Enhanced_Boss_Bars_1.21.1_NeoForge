package com.nakuring.enhanced_boss_bars.render.util;

import com.nakuring.enhanced_boss_bars.config.util.GeneralConfig;
import com.nakuring.enhanced_boss_bars.render.context.BossBarRenderContext;
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
            BossBarRenderContext context,
            BossBarData boss_bar,
            int name_width,
            boolean generic
    ) {
        GeneralConfig config = context.config();

        int y = context.y();

        if (!context.directionIsDown()){
            y -= (boss_bar.overlay_height() + (generic ? 5 : 0));
        }

        if (generic) y += 5;

        int x = switch (config.horizontal_alignment) {

            case Left ->
                    context.x() + boss_bar.overlay_width() / 2;

            case Center ->
                    context.x();

            case Right ->
                    context.x() - boss_bar.overlay_width() / 2;
        };

        int health_x = x - boss_bar.health_width() / 2;
        int health_y = y + boss_bar.health_y();

        int overlay_x = x - boss_bar.overlay_width() / 2 + boss_bar.overlay_offset_x();
        int overlay_y = y;

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