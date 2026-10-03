package com.nakuring.enhanced_boss_bars.util;

//import com.nakuring.enhanced_boss_bars.config.ConfigManager;

public record BossBarEntry(
        BossBarKey key,
        BossBarData data,
        boolean generic
) {

    /*public boolean isEnabled() {
        return ConfigManager.barConfig(key).enabled;
    }
    */
}