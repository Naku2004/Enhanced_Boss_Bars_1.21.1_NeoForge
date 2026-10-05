package com.nakuring.enhanced_boss_bars.render.context;

import com.nakuring.enhanced_boss_bars.config.util.GeneralConfig;
import com.nakuring.enhanced_boss_bars.render.util.PreparedBossBar;
import com.nakuring.enhanced_boss_bars.util.BossBarData;
import com.nakuring.enhanced_boss_bars.util.BossBarEntry;

import java.util.List;

public class BossBarRenderContext {

    private final GeneralConfig config;

    private final int screen_width;
    private final int screen_height;

    private final int anchor_y;
    private int current_y;
    private int anchor_x;

    private int reference_width;

    public BossBarRenderContext(
            GeneralConfig config,
            int screen_width,
            int screen_height
    ) {
        this.config = config;
        this.screen_width = screen_width;
        this.screen_height = screen_height;

        this.anchor_x = (int) (screen_width * (config.position_x / 100.0));
        this.anchor_y = (int) (screen_height * (config.position_y / 100.0));
    }

    public void prepare(List<PreparedBossBar> prepared_boss_bars) {
        reference_width = 0;
        int total_height = 0;

        for (PreparedBossBar prepared_boss_bar : prepared_boss_bars) {

            BossBarEntry entry = prepared_boss_bar.entry();
            BossBarData data = entry.data();

            reference_width = Math.max(reference_width, data.overlay_width());
            total_height += height(entry) + spacing();
        }

        total_height -= 1;

        if (directionIsDown()){
            current_y = Math.max(Math.min(anchor_y, screen_height - total_height), 0);
        }
        else {
            current_y = Math.max(total_height, Math.min(anchor_y, screen_height));
        }

        switch (config.horizontal_alignment) {

            case Left ->
                    anchor_x = clamp(
                            anchor_x,
                            0,
                            screen_width - reference_width
                    );

            case Center ->
                    anchor_x = clamp(
                            anchor_x,
                            reference_width / 2,
                            screen_width - reference_width / 2
                    );

            case Right ->
                    anchor_x = clamp(
                            anchor_x,
                            reference_width,
                            screen_width
                    );
        }
    }

    public void advance(BossBarEntry entry) {
        if (directionIsDown()){
            current_y += height(entry) + spacing();
        }
        else{
            current_y -= (height(entry) + spacing());
        }
    }

    private int height(BossBarEntry entry) {
        return entry.data().overlay_height() + (entry.generic() ? 5 : 0);
    }

    private int spacing() {
        return 1;
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }

    public boolean directionIsDown() {
        return config.vertical_direction == GeneralConfig.VerticalDirection.Down;
    }

    public GeneralConfig config() {
        return config;
    }

    public int screenWidth() {
        return screen_width;
    }

    public int screenHeight() {
        return screen_height;
    }

    public int x() {
        return anchor_x;
    }

    public int y() {
        return current_y;
    }

    public int referenceWidth() {
        return reference_width;
    }
}