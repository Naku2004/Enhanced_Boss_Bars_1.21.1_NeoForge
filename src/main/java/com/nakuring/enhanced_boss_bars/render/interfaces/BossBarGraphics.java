package com.nakuring.enhanced_boss_bars.render.interfaces;

import net.minecraft.resources.ResourceLocation;

public interface BossBarGraphics {

    int screenWidth();

    int screenHeight();

    int textWidth(String text);

    void drawTexture(
            ResourceLocation texture,
            int x, int y,
            int u, int v,
            int width, int height,
            int textureWidth, int textureHeight
    );

    void drawText(
            String text,
            int x,
            int y,
            int color,
            boolean shadow
    );

}