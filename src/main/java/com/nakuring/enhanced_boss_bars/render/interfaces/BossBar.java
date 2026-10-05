package com.nakuring.enhanced_boss_bars.render.interfaces;
import com.nakuring.enhanced_boss_bars.util.BossBarKey;

import java.util.UUID;

public record BossBar(
        UUID uuid,
        BossBarKey key,
        String color,
        float percent,
        String name
) {}