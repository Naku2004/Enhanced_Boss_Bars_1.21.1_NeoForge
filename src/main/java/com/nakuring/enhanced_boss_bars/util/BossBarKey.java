package com.nakuring.enhanced_boss_bars.util;

import net.minecraft.network.chat.Component;

public record BossBarKey(String value, Type type) {

    public String id() {
        return Component.translatable(this.value).getString();
    }

    public enum Type {
        translation,
        contains,
        color
    }

}