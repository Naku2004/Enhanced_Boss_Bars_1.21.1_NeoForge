package com.nakuring.enhanced_boss_bars;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

public class NeoForgeEvents {

    @SubscribeEvent
    public static void registerClientReloadListeners(
            RegisterClientReloadListenersEvent event
    ) {

        EnhancedBossBars.LOGGER.info(
                "Registering BossBarsReloadListener..."
        );

        event.registerReloadListener(
                new BossBarsReloadListener()
        );
    }
}