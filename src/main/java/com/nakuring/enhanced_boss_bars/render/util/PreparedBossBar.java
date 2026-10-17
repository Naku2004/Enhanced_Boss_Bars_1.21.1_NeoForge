package com.nakuring.enhanced_boss_bars.render.util;

import com.nakuring.enhanced_boss_bars.render.interfaces.BossBar;
import com.nakuring.enhanced_boss_bars.util.BossBarEntry;

public record PreparedBossBar(
        BossBar client_boss_bar,
        BossBarEntry entry,
        BossBarTextures textures
) {}
