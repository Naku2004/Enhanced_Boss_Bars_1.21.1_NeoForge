package com.nakuring.enhanced_boss_bars.config.util;

public class GeneralConfig {

    public HorizontalAlignment horizontal_alignment = HorizontalAlignment.Center;
    public VerticalDirection vertical_direction = VerticalDirection.Down;
    public int position_x = 50;
    public int position_y = 1;
    public double scale = 1.0;
    public boolean bar_preview = false;
    public int max_boss_bars = 3;
    public boolean max_boss_bars_preview = false;

    public enum HorizontalAlignment {
        Left,
        Center,
        Right
    }

    public enum VerticalDirection {
        Up,
        Down
    }

}