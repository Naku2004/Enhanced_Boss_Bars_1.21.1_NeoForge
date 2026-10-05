package com.nakuring.enhanced_boss_bars.mixins;

import com.nakuring.enhanced_boss_bars.config.ClothConfigScreen;
import com.nakuring.enhanced_boss_bars.config.render.BossBarPreviewRenderer;
import com.nakuring.enhanced_boss_bars.implementation.NeoForgeBossBarGraphics;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(me.shedaniel.clothconfig2.gui.ClothConfigScreen.class)
public abstract class ClothConfigScreenMixin {

    @Unique
    private final NeoForgeBossBarGraphics graphics = new NeoForgeBossBarGraphics();

    @Inject(method = "render", at = @At("TAIL"))
    private void renderPreview(
            GuiGraphics guiGraphics,
            int mouseX,
            int mouseY,
            float delta,
            CallbackInfo ci
    ) {

        if ((Object) this != ClothConfigScreen.getCurrentScreen()) {
            return;
        }

        graphics.begin(guiGraphics);

        BossBarPreviewRenderer.render(graphics);
    }

}