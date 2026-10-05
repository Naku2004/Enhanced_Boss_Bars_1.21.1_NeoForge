package com.nakuring.enhanced_boss_bars.events;

import com.nakuring.enhanced_boss_bars.EnhancedBossBars;
import com.nakuring.enhanced_boss_bars.implementation.NeoForgeBossBarGraphics;
import com.nakuring.enhanced_boss_bars.implementation.NeoForgeBossBars;
import com.nakuring.enhanced_boss_bars.mixins.BossHealthOverlayAccessor;
import com.nakuring.enhanced_boss_bars.render.BossBarRenderPipeline;
import com.nakuring.enhanced_boss_bars.render.interfaces.BossBar;
import com.nakuring.enhanced_boss_bars.render.interfaces.BossBars;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent;
import org.spongepowered.asm.mixin.Unique;

public final class NeoForgeClientEvents {

    @Unique
    private static final BossBarRenderPipeline pipeline = new BossBarRenderPipeline();

    @Unique
    private static final NeoForgeBossBarGraphics graphics = new NeoForgeBossBarGraphics();

    @SubscribeEvent
    public static void onBossBarRender(
            CustomizeGuiOverlayEvent.BossEventProgress event
    ) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;

        BossBars bars = new NeoForgeBossBars(
                ((BossHealthOverlayAccessor) minecraft.gui.getBossOverlay())
                        .enhancedBossBars$getEvents()
        );

        // Este evento corresponde a una barra por vez.
        // Cancela el dibujo vanilla y ejecuta tu pipeline solo una vez.
        if (!bars.uuids().isEmpty()) {
            event.setCanceled(true);

        }

        EnhancedBossBars.LOGGER.info(
                "Cancelling event..."
        );

        for (BossBar bar : bars.getBossBars(5)) {
            EnhancedBossBars.LOGGER.info("Found: {}", bar.name());
        }

        graphics.begin(event.getGuiGraphics());

        pipeline.run(
                graphics,
                bars
        );
    }
}