package com.nakuring.enhanced_boss_bars.render.renderer;

import com.nakuring.enhanced_boss_bars.render.interfaces.BossBarGraphics;
import com.nakuring.enhanced_boss_bars.render.util.BossBarLayout;
import com.nakuring.enhanced_boss_bars.render.util.BossBarTextures;
import com.nakuring.enhanced_boss_bars.util.BossBarData;
import com.nakuring.enhanced_boss_bars.util.BossBarEntry;

import java.util.Map;

public class BossBarRender {

    private static final Map<String, Integer> colors = Map.of(
            "pink",   0xf90cbc,
            "blue",   0x299cbc,
            "red",    0xad2831,
            "green",  0x3ebe6d,
            "yellow", 0xffb300,
            "purple", 0x610cf9,
            "white",  0xebf8ff
    );

    public void draw(
            BossBarGraphics bridge,
            BossBarEntry entry,
            BossBarLayout layout,
            BossBarTextures textures,
            float percent,
            String name
    ) {

        BossBarData boss_bar = entry.data();

        drawHealth(bridge, boss_bar, layout, textures, percent);
        drawOverlay(bridge, boss_bar, layout, textures);

        if (entry.generic()) drawName(bridge, entry, layout, name);

    }

    private void drawHealth(BossBarGraphics bridge, BossBarData boss_bar, BossBarLayout layout, BossBarTextures textures, float percent) {
        bridge.drawTexture(
                textures.background(),
                layout.health_x(),
                layout.health_y(),
                boss_bar.health_texture_coord_x(),
                boss_bar.health_texture_coord_y(),
                boss_bar.health_width(),
                boss_bar.health_height(),
                boss_bar.health_width(),
                boss_bar.health_height()
        );

        bridge.drawTexture(
                textures.progress(),
                layout.health_x(),
                layout.health_y(),
                boss_bar.health_texture_coord_x(),
                boss_bar.health_texture_coord_y(),
                (int) (boss_bar.health_width() * percent),
                boss_bar.health_height(),
                boss_bar.health_width(),
                boss_bar.health_height()
        );
    }

    private void drawOverlay(BossBarGraphics bridge, BossBarData boss_bar, BossBarLayout layout, BossBarTextures textures) {
        bridge.drawTexture(
                textures.overlay(),
                layout.overlay_x(),
                layout.overlay_y(),
                boss_bar.overlay_texture_coord_x(),
                boss_bar.overlay_texture_coord_y(),
                boss_bar.overlay_width(),
                boss_bar.overlay_height(),
                boss_bar.overlay_width(),
                boss_bar.overlay_height()
        );
    }

    private void drawName(BossBarGraphics bridge, BossBarEntry entry, BossBarLayout layout, String name) {
        int color = colors.getOrDefault(entry.key().value(), 0xFFFFFF);

        int x = layout.name_x();
        int y = layout.name_y();

        int[][] outline = {
                {-1,0},{1,0},{0,-1},{0,1},
                {-1,-1},{-1,1},{1,-1},{1,1}
        };

        for (int[] o : outline)
            bridge.drawText(name, x + o[0], y + o[1], 0x161616, false
            );

        bridge.drawText(name, x, y, color, false);
    }
}