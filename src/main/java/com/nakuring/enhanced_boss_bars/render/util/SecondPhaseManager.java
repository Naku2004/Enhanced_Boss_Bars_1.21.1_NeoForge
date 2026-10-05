package com.nakuring.enhanced_boss_bars.render.util;

import com.nakuring.enhanced_boss_bars.util.BossBarData;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class SecondPhaseManager {

    private static final Map<UUID, Boolean> second_phase_active = new HashMap<>();

    public static boolean isSecondPhase(
            UUID uuid,
            BossBarData boss_bar,
            float percent
    ) {

        if (!boss_bar.hasSecondPhase())
            return false;

        if (second_phase_active.getOrDefault(uuid, false))
            return true;

        if (percent < boss_bar.threshold_phase()) {
            second_phase_active.put(uuid, true);
            return true;
        }

        return false;
    }

    public static void cleanup(Set<UUID> bossBars) {
        second_phase_active.keySet().removeIf(uuid -> !bossBars.contains(uuid));
    }
}