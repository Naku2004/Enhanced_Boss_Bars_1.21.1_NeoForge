package com.nakuring.enhanced_boss_bars.events;

import com.nakuring.enhanced_boss_bars.BossBarsReloadListener;
import com.nakuring.enhanced_boss_bars.EnhancedBossBars;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

public class NeoForgeModEvents {

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