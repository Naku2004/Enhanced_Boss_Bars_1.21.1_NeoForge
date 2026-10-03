package com.nakuring.enhanced_boss_bars;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(EnhancedBossBars.MODID)
public class EnhancedBossBars {

    public static final String MODID = "enhanced_boss_bars";

    public static final Logger LOGGER =
            LogUtils.getLogger();

    public EnhancedBossBars(
            IEventBus modEventBus,
            ModContainer modContainer
    ) {

        modEventBus.register(
                NeoForgeEvents.class
        );
    }

    public static String getModName(String modId) {

        return ModList.get()
                .getModContainerById(modId)
                .map(container ->
                        container.getModInfo().getDisplayName()
                )
                .orElse(modId);
    }
}