package com.nakuring.enhanced_boss_bars.events;

import com.mojang.blaze3d.systems.RenderSystem;
import com.nakuring.enhanced_boss_bars.implementation.NeoForgeBossBarGraphics;
import com.nakuring.enhanced_boss_bars.implementation.NeoForgeBossBars;
import com.nakuring.enhanced_boss_bars.mixins.BossHealthOverlayMixin;
import com.nakuring.enhanced_boss_bars.render.BossBarRenderPipeline;
import com.nakuring.enhanced_boss_bars.render.interfaces.BossBars;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import org.spongepowered.asm.mixin.Unique;

public final class NeoForgeClientEvents {

    @Unique
    private static final BossBarRenderPipeline pipeline = new BossBarRenderPipeline();

    @Unique
    private static final NeoForgeBossBarGraphics graphics = new NeoForgeBossBarGraphics();

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBossBarRender(CustomizeGuiOverlayEvent.BossEventProgress event) {
        if (Minecraft.getInstance().player == null) return;
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onBossOverlayLayerRender(RenderGuiLayerEvent.Pre event) {
        if (!event.getName().equals(VanillaGuiLayers.BOSS_OVERLAY)) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;

        BossBars bars = new NeoForgeBossBars(
                ((BossHealthOverlayMixin) minecraft.gui.getBossOverlay())
                        .enhancedBossBars$getEvents()
        );

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        graphics.begin(event.getGuiGraphics());
        pipeline.run(graphics, bars);

        RenderSystem.disableBlend();
    }
}
