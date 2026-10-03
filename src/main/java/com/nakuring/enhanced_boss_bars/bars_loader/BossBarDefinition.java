package com.nakuring.enhanced_boss_bars.bars_loader;

import com.nakuring.enhanced_boss_bars.util.BossBarKey;
import net.minecraft.resources.ResourceLocation;

public class BossBarDefinition {

    public String key;
    public BossBarKey.Type type;

    public String mod_id;

    public ResourceLocation health_background;
    public ResourceLocation health_color;
    public ResourceLocation overlay;

    public ResourceLocation second_phase_background;
    public ResourceLocation second_phase_color;
    public ResourceLocation second_phase_overlay;

    public int health_width;
    public int health_height;
    public int health_y;

    public int overlay_width;
    public int overlay_height;
    public int overlay_offset_x;
    public int overlay_offset_y;

    public int health_texture_coord_x;
    public int health_texture_coord_y;

    public int overlay_texture_coord_x;
    public int overlay_texture_coord_y;

    public double threshold_phase;
}