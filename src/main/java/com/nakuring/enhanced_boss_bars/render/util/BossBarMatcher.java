package com.nakuring.enhanced_boss_bars.render.util;

import com.nakuring.enhanced_boss_bars.BossBarsManager;
import com.nakuring.enhanced_boss_bars.util.BossBarEntry;
import com.nakuring.enhanced_boss_bars.util.BossBarKey;

public class BossBarMatcher {

    public static BossBarEntry getBossBarEntry(BossBarKey name_key, String color) {
        BossBarEntry entry = BossBarsManager.getBossBar(name_key);

        if (entry != null && entry.data() != null && entry.isEnabled()) {
            return entry;
        }

        return BossBarsManager.getBossBar(
                new BossBarKey(
                        color,
                        BossBarKey.Type.color
                )
        );
    }
}