package com.nakuring.enhanced_boss_bars.util;

public record BossBarKey(String value, Type type) {

    public String id() {
        return value;
    }

    public enum Type {
        translation,
        contains,
        color
    }

}