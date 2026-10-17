package com.nakuring.enhanced_boss_bars.render.context;

import com.nakuring.enhanced_boss_bars.config.util.GeneralConfig;
import com.nakuring.enhanced_boss_bars.render.util.PreparedBossBar;

import java.util.List;

public class BossBarStackLayout {

    private static final int BAR_SPACING = 1;
    private static final int GENERIC_BAR_EXTRA_HEIGHT_FOR_NAME = 5;

    private final GeneralConfig config;

    private final int screen_width;
    private final int screen_height;

    private final double effective_scale;

    private final int horizontal_position;

    private int anchorX;
    private int current_bar_y;

    public BossBarStackLayout(
            GeneralConfig config,
            int screen_width,
            int screen_height,
            double effective_scale
    ) {
        this.config = config;
        this.screen_width = screen_width;
        this.screen_height = screen_height;

        this.effective_scale = effective_scale;

        this.horizontal_position = config.position_x;
    }

    public void prepare(List<PreparedBossBar> prepared_boss_bars) {

        int reference_width = findReferenceWidth(prepared_boss_bars);
        int stack_height = calculateStackHeight(prepared_boss_bars);

        current_bar_y = calculateStartingY(stack_height);
        anchorX = calculateAnchorX(reference_width);
    }

    private int findReferenceWidth(List<PreparedBossBar> bars) {
        int widest_overlay = 0;

        for (PreparedBossBar bar : bars) {
            widest_overlay = Math.max(
                    widest_overlay,
                    bar.textures().overlay_width()
            );
        }

        return widest_overlay;
    }

    private int calculateStackHeight(List<PreparedBossBar> bars) {
        int total_height = 0;

        for (PreparedBossBar bar : bars) {
            total_height += barHeight(bar) + BAR_SPACING;
        }

        if (!bars.isEmpty()) {
            total_height -= BAR_SPACING;
        }

        return total_height;
    }

    private int calculateStartingY(int stack_height) {
        int anchorY = (int) (screen_height * (config.position_y / 100.0));
        int scaled_stack_height = (int) Math.ceil(stack_height * effective_scale);

        if (isDirectionDown()) {
            return Math.max(Math.min(anchorY, screen_height - scaled_stack_height), 0);
        }

        return Math.max(scaled_stack_height, Math.min(anchorY, screen_height));
    }

    private int calculateAnchorX(int stack_width) {
        int scaled_stack_width = (int) Math.ceil(stack_width * effective_scale);
        int desiredX = calculateDesiredX(scaled_stack_width);

        int minX;
        int maxX;

        switch (config.horizontal_alignment) {
            case Left -> {
                minX = 0;
                maxX = screen_width - scaled_stack_width;
            }
            case Center -> {
                minX = scaled_stack_width / 2;
                maxX = screen_width - scaled_stack_width / 2;
            }
            case Right -> {
                minX = scaled_stack_width;
                maxX = screen_width;
            }
            default -> throw new IllegalStateException(
                    "Unknown horizontal alignment: " + config.horizontal_alignment
            );
        }

        return Math.clamp(desiredX, minX, maxX);
    }

    private int calculateDesiredX(int scaled_stack_width) {
        int min_x;
        int max_x;

        switch (config.horizontal_alignment) {
            case Left, Right -> {
                min_x = 0;
                max_x = screen_width;
            }
            case Center -> {
                min_x = scaled_stack_width / 2;
                max_x = screen_width - scaled_stack_width / 2;
            }
            default -> throw new IllegalStateException(
                    "Unknown horizontal alignment: " + config.horizontal_alignment
            );
        }

        int percent = Math.clamp(horizontal_position, 0, 100);

        return min_x + (int) Math.round((max_x - min_x) * (percent / 100.0));
    }

    public void advanceToNextBar(PreparedBossBar bar) {
        int verticalStep = barHeight(bar) + BAR_SPACING;

        if (isDirectionDown()) {
            current_bar_y += verticalStep;
        } else {
            current_bar_y -= verticalStep;
        }
    }

    private int barHeight(PreparedBossBar bar) {
        int extra_height = bar.entry().generic()
                ? GENERIC_BAR_EXTRA_HEIGHT_FOR_NAME
                : 0;

        return bar.textures().overlay_height() + extra_height;
    }

    public boolean isDirectionDown() {
        return config.vertical_direction == GeneralConfig.VerticalDirection.Down;
    }

    public GeneralConfig getConfig() {
        return config;
    }

    public int getAnchorX() {
        return anchorX;
    }

    public int getCurrentBarY() {
        return current_bar_y;
    }

}