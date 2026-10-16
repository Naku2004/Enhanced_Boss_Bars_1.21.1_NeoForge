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

    private final double effective_scale;

    private final int anchor_y;
    private int current_y;
    private final int position_x;
    private int anchor_x;

    private int reference_width;

    public BossBarRenderContext(
            GeneralConfig config,
            int screen_width,
            int screen_height,
            double effective_scale
    ) {
        this.config = config;
        this.screen_width = screen_width;
        this.screen_height = screen_height;

        this.effective_scale = effective_scale;

        //this.anchor_x = (int) (screen_width * (config.position_x / 100.0));
        this.position_x = config.position_x;
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

        if (!prepared_boss_bars.isEmpty()) total_height -= 1;

        int scaled_total_height = (int) Math.ceil(total_height * effective_scale);

        if (directionIsDown())
            current_y = Math.max(Math.min(anchor_y, screen_height - scaled_total_height), 0);
        else
            current_y = Math.max(scaled_total_height, Math.min(anchor_y, screen_height));


        int scaled_width = (int) Math.ceil(reference_width * effective_scale);

        int desired_x = getDesiredX(scaled_width);

        int min_anchor_x;
        int max_anchor_x;

        switch (config.horizontal_alignment) {
            case Left -> {
                min_anchor_x = 0;
                max_anchor_x = screen_width - scaled_width;
            }
            case Center -> {
                min_anchor_x = scaled_width / 2;
                max_anchor_x = screen_width - scaled_width / 2;
            }
            case Right -> {
                min_anchor_x = scaled_width;
                max_anchor_x = screen_width;
            }
            default -> throw new IllegalStateException(
                    "Unknown horizontal alignment: " + config.horizontal_alignment
            );
        }

        anchor_x = Math.clamp(desired_x, min_anchor_x, max_anchor_x);
    }

    private int getDesiredX(int scaled_width) {
        int min_x;
        int max_x;

        switch (config.horizontal_alignment) {
            case Left, Right -> {
                min_x = 0;
                max_x = screen_width;
            }
            case Center -> {
                min_x = scaled_width / 2;
                max_x = screen_width - scaled_width / 2;
            }
            default -> throw new IllegalStateException(
                    "Unknown horizontal alignment: " + config.horizontal_alignment
            );
        }

        int percent = Math.clamp(position_x, 0, 100);

        return min_x + (int) Math.round((max_x - min_x) * (percent / 100.0));
    }

    public void advance(BossBarEntry entry) {
        if (directionIsDown())
            current_y += height(entry) + spacing();
        else
            current_y -= (height(entry) + spacing());

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