package com.nakuring.enhanced_boss_bars;

import com.nakuring.enhanced_boss_bars.bars_loader.BossBarsLoader;
import com.nakuring.enhanced_boss_bars.implementation.NeoForgePlatform;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BossBarsReloadListener
        implements ResourceManagerReloadListener {

    private static final Logger LOGGER =
            LoggerFactory.getLogger("Enhanced Boss Bars");

    @Override
    public void onResourceManagerReload(
            ResourceManager manager
    ) {

        LOGGER.info("BossBarsReloadListener reloading...");

        BossBarsManager.clear();

        BossBarsLoader.load(
                manager,
                new NeoForgePlatform()
        );
    }
}