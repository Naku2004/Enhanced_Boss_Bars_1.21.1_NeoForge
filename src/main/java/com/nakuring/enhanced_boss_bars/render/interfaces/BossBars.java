package com.nakuring.enhanced_boss_bars.render.interfaces;

import java.util.Set;
import java.util.UUID;

public interface BossBars {
    Iterable<BossBar> getBossBars(int limit);

    Set<UUID> uuids();
}