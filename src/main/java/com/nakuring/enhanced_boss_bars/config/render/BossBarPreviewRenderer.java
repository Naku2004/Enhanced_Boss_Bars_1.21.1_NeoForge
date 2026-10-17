package com.nakuring.enhanced_boss_bars.config.render;

import com.nakuring.enhanced_boss_bars.BossBarsManager;
import com.nakuring.enhanced_boss_bars.config.ClothConfigScreen;
import com.nakuring.enhanced_boss_bars.config.util.GeneralConfig;
import com.nakuring.enhanced_boss_bars.render.context.BossBarStackLayout;
import com.nakuring.enhanced_boss_bars.render.interfaces.BossBarGraphics;
import com.nakuring.enhanced_boss_bars.render.util.BossBarLayout;
import com.nakuring.enhanced_boss_bars.render.util.BossBarTextures;
import com.nakuring.enhanced_boss_bars.render.util.PreparedBossBar;
import com.nakuring.enhanced_boss_bars.util.BossBarData;
import com.nakuring.enhanced_boss_bars.util.BossBarEntry;
import com.nakuring.enhanced_boss_bars.util.BossBarKey;

import java.util.ArrayList;
import java.util.List;

public class BossBarPreviewRenderer {

    public static void render(BossBarGraphics graphics) {

        boolean bar_preview = ClothConfigScreen.bar_preview_entry.getValue();

        if (!bar_preview) {
            return;
        }

        BossBarEntry preview_bar_entry = BossBarsManager.getBossBar(
                new BossBarKey(
                        "entity.minecraft.wither",
                        BossBarKey.Type.translation
                )
        );

        if (preview_bar_entry == null) {
            return;
        }

        int amount = ClothConfigScreen.max_boss_bars_preview_entry.getValue()
                ? ClothConfigScreen.max_boss_bars_entry.getValue()
                : 1;


        GeneralConfig config = new GeneralConfig();

        config.scale = ClothConfigScreen.scale_entry.getValue();

        config.position_x = ClothConfigScreen.horizontal_position_entry.getValue();

        config.position_y = ClothConfigScreen.vertical_position_entry.getValue();

        config.horizontal_alignment = (GeneralConfig.HorizontalAlignment) ClothConfigScreen.horizontal_alignment_entry.getValue();

        config.vertical_direction = (GeneralConfig.VerticalDirection) ClothConfigScreen.vertical_direction_entry.getValue();


        List<PreparedBossBar> bars = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            bars.add(new PreparedBossBar(null, preview_bar_entry, BossBarTextures.resolve(preview_bar_entry.data(), false)));
        }

        BossBarStackLayout stack_layout =
                new BossBarStackLayout(
                        config,
                        graphics.screenWidth(),
                        graphics.screenHeight(),
                        graphics.effectiveScale(config.scale)
                );

        stack_layout.prepare(bars);

        graphics.pushScale(
                graphics.effectiveScale(config.scale),
                stack_layout.getAnchorX(),
                stack_layout.getCurrentBarY()
        );

        for (PreparedBossBar prepared : bars) {

            BossBarData data = prepared.entry().data();

            BossBarLayout layout =
                    BossBarLayout.calculateCoords(
                            stack_layout,
                            data,
                            prepared.textures(),
                            0,
                            prepared.entry().generic()
                    );

            graphics.drawTexture(
                    data.health_color_texture(),
                    layout.health_x(),
                    layout.health_y(),
                    0,
                    0,
                    data.health_bar_width(),
                    data.health_bar_height(),
                    data.health_bar_width(),
                    data.health_bar_height()
            );


            graphics.drawTexture(
                    data.overlay_texture(),
                    layout.overlay_x(),
                    layout.overlay_y(),
                    0,
                    0,
                    data.overlay_width(),
                    data.overlay_height(),
                    data.overlay_width(),
                    data.overlay_height()
            );

            stack_layout.advanceToNextBar(prepared);
        }

        graphics.popScale();
    }
}