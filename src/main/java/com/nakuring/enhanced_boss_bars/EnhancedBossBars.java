package com.nakuring.enhanced_boss_bars;

import com.mojang.logging.LogUtils;
import com.nakuring.enhanced_boss_bars.config.ClothConfigScreen;
import com.nakuring.enhanced_boss_bars.config.ConfigManager;
import com.nakuring.enhanced_boss_bars.events.NeoForgeClientEvents;
import com.nakuring.enhanced_boss_bars.events.NeoForgeModEvents;
import com.nakuring.enhanced_boss_bars.implementation.NeoForgePlatform;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
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

        modEventBus.register(NeoForgeModEvents.class);
        NeoForge.EVENT_BUS.register(NeoForgeClientEvents.class);

        modContainer.registerExtensionPoint(
                IConfigScreenFactory.class,
                (minecraft, parent) -> ClothConfigScreen.create(parent)
        );

        ConfigManager.initialize(new NeoForgePlatform());
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