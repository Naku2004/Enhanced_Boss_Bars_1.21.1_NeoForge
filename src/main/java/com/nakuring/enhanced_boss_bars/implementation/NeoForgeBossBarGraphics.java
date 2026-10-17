package com.nakuring.enhanced_boss_bars.implementation;

import com.nakuring.enhanced_boss_bars.render.interfaces.BossBarGraphics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class NeoForgeBossBarGraphics implements BossBarGraphics {

    private final Minecraft minecraft = Minecraft.getInstance();
    private final Font font = minecraft.font;
    private GuiGraphics graphics;

    public void begin(GuiGraphics graphics) {
        this.graphics = graphics;
    }

    @Override
    public int screenWidth() {
        return minecraft.getWindow().getGuiScaledWidth();
    }

    @Override
    public int screenHeight() {
        return minecraft.getWindow().getGuiScaledHeight();
    }

    @Override
    public int textWidth(String text) {
        return font.width(text);
    }

    @Override
    public void drawTexture(
            ResourceLocation texture,
            int x, int y,
            int u, int v,
            int width, int height,
            int textureWidth, int textureHeight
    ) {
        if (texture == null) return;

        requireGraphics().blit(
                texture,
                x, y,
                width, height,
                u, v,
                width, height,
                textureWidth, textureHeight
        );
    }

    @Override
    public void drawText(
            String text,
            int x,
            int y,
            int color,
            boolean shadow
    ) {
        requireGraphics().drawString(font, text, x, y, color, shadow);
    }

    @Override
    public void pushScale(float scale, float centerX, float centerY) {
        var pose = requireGraphics().pose();
        pose.pushPose();
        pose.translate(centerX, centerY, 0);
        pose.scale(scale, scale, 1.0f);
        pose.translate(-centerX, -centerY, 0);
    }

    @Override
    public void popScale() {
        requireGraphics().pose().popPose();
    }

    public float effectiveScale(int scale) {
        return scale / (float) minecraft.getWindow().getGuiScale();
    }

    private GuiGraphics requireGraphics() {
        if (graphics == null) {
            throw new IllegalStateException(
                    "Call begin(GuiGraphics) before draw"
            );
        }
        return graphics;
    }
}