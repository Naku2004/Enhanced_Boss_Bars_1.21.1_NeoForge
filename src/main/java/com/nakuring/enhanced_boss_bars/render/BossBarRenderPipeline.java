package com.nakuring.enhanced_boss_bars.render;

import com.nakuring.enhanced_boss_bars.config.ConfigManager;
import com.nakuring.enhanced_boss_bars.config.util.GeneralConfig;
import com.nakuring.enhanced_boss_bars.render.context.BossBarRenderContext;
import com.nakuring.enhanced_boss_bars.render.interfaces.BossBar;
import com.nakuring.enhanced_boss_bars.render.interfaces.BossBarGraphics;
import com.nakuring.enhanced_boss_bars.render.interfaces.BossBars;
import com.nakuring.enhanced_boss_bars.render.renderer.BossBarRender;
import com.nakuring.enhanced_boss_bars.render.util.*;
import com.nakuring.enhanced_boss_bars.util.BossBarData;
import com.nakuring.enhanced_boss_bars.util.BossBarEntry;

import java.util.ArrayList;
import java.util.List;

public class BossBarRenderPipeline {

    private final BossBarRender render = new BossBarRender();

    public void run(
            BossBarGraphics graphics,
            BossBars client_boss_bars
    ) {

        GeneralConfig config = ConfigManager.generalConfig();

        BossBarRenderContext render_context =
                new BossBarRenderContext(
                        config,
                        graphics.screenWidth(),
                        graphics.screenHeight(),
                        graphics.effectiveScale(config.scale)
                );

        List<PreparedBossBar> prepared_boss_bars = new ArrayList<>();

        for (BossBar client_boss_bar : client_boss_bars.getBossBars(config.max_boss_bars)) {
            BossBarEntry entry = BossBarMatcher.getBossBarEntry(
                    client_boss_bar.key(),
                    client_boss_bar.color()
            );

            prepared_boss_bars.add(new PreparedBossBar(
                    client_boss_bar,
                    entry
            ));
        }

        render_context.prepare(prepared_boss_bars);


        graphics.pushScale(
                graphics.effectiveScale(config.scale),
                render_context.x(),
                render_context.y()
        );

        for (PreparedBossBar prepared_boss_bar : prepared_boss_bars) {

            BossBar client_boss_bar = prepared_boss_bar.client_boss_bar();
            BossBarEntry boss_bar_entry = prepared_boss_bar.entry();
            BossBarData boss_bar = boss_bar_entry.data();

            int name_width = graphics.textWidth(client_boss_bar.name());

            BossBarLayout layout = BossBarLayout.calculateCoords(render_context, boss_bar, name_width, boss_bar_entry.generic());

            Boolean second_phase = SecondPhaseManager.isSecondPhase(client_boss_bar.uuid(), boss_bar, client_boss_bar.percent());

            BossBarTextures textures = BossBarTextures.resolve(boss_bar, second_phase);

            render.draw(
                    graphics,
                    boss_bar_entry,
                    layout,
                    textures,
                    client_boss_bar.percent(),
                    client_boss_bar.name()
            );

            render_context.advance(boss_bar_entry);
        }

        graphics.popScale();

        SecondPhaseManager.cleanup(client_boss_bars.uuids());
    }
}
