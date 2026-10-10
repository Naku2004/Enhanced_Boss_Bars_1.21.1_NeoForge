package com.nakuring.enhanced_boss_bars.implementation;

import com.nakuring.enhanced_boss_bars.render.interfaces.BossBar;
import com.nakuring.enhanced_boss_bars.render.interfaces.BossBars;
import com.nakuring.enhanced_boss_bars.util.BossBarKey;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class NeoForgeBossBars implements BossBars {

    private final Map<UUID, LerpingBossEvent> clientBossBars;

    public NeoForgeBossBars(Map<UUID, LerpingBossEvent> clientBossBars) {
        this.clientBossBars = clientBossBars;
    }

    @Override
    public Iterable<BossBar> getBossBars(int limit) {
        List<BossBar> boss_bars = new ArrayList<>();
        int count = 0;

        for (LerpingBossEvent bar : clientBossBars.values()) {
            if (count >= limit) {
                break;
            }

            boss_bars.add(new BossBar(
                    bar.getId(),
                    getKey(bar),
                    bar.getColor().getName(),
                    bar.getProgress(),
                    bar.getName().getString()
            ));
            count++;
        }

        return boss_bars;
    }

    @Override
    public Set<UUID> uuids() {
        return clientBossBars.keySet();
    }

    private BossBarKey getKey(LerpingBossEvent bar) {
        Component name = bar.getName();

        if (name.getContents() instanceof TranslatableContents translation) {
            return new BossBarKey(
                    translation.getKey(),
                    BossBarKey.Type.translation
            );
        }

        return new BossBarKey(
                name.getString(),
                BossBarKey.Type.contains
        );
    }
}