package com.nakuring.enhanced_boss_bars;

import com.nakuring.enhanced_boss_bars.util.BossBarData;
import com.nakuring.enhanced_boss_bars.util.BossBarEntry;
import com.nakuring.enhanced_boss_bars.util.BossBarKey;
import net.minecraft.network.chat.Component;

import java.util.*;

import static com.nakuring.enhanced_boss_bars.EnhancedBossBars.getModName;

public class BossBarsManager {

    private static final Map<BossBarKey, BossBarData> boss_bars = new LinkedHashMap<>();
    private static final Map<BossBarKey, BossBarData> generic_bars = new HashMap<>();

    public static void registerBossBar(BossBarKey key, BossBarData data) {
        if (key.type() == BossBarKey.Type.color)
            generic_bars.put(key, data);
        else
            boss_bars.put(key, data);
    }

    public static BossBarEntry getBossBar(BossBarKey key) {

        switch (key.type()) {

            case translation -> {
                return new BossBarEntry(key, boss_bars.get(key), false);
            }

            case contains -> {

                for (Map.Entry<BossBarKey, BossBarData> entry : boss_bars.entrySet()) {

                    if (entry.getKey().type() != BossBarKey.Type.contains)
                        continue;

                    if (key.value().contains(entry.getKey().value())) {
                        return new BossBarEntry(entry.getKey(), entry.getValue(), false);
                    }
                }
            }

            case color -> {
                return new BossBarEntry(key, generic_bars.get(key), true);
            }
        }

        return null;
    }

    public static Map<BossBarKey, BossBarData> getBossBars(){
        return boss_bars;
    }

    public static void clear(){
        boss_bars.clear();
    }

    public static void sortBossBars() {

        List<Map.Entry<BossBarKey, BossBarData>> entries =
                new ArrayList<>(boss_bars.entrySet());

        entries.sort((a, b) -> {

            String modA = a.getValue().mod_id();
            String modB = b.getValue().mod_id();

            // Minecraft siempre primero
            if (modA.equals("minecraft")) return -1;
            if (modB.equals("minecraft")) return 1;

            // Luego ordenar mods alfabéticamente
            int modCompare = getModName(modA).compareToIgnoreCase(getModName(modB));

            if (modCompare != 0) {
                return modCompare;
            }

            // Si pertenecen al mismo mod, ordenar bosses
            String bossA = Component.translatable(a.getKey().value()).getString();
            String bossB = Component.translatable(b.getKey().value()).getString();

            return bossA.compareToIgnoreCase(bossB);
        });

        boss_bars.clear();

        for (Map.Entry<BossBarKey, BossBarData> entry : entries) {
            boss_bars.put(entry.getKey(), entry.getValue());
        }
    }
}