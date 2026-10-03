package com.nakuring.enhanced_boss_bars.interfaces;

import java.nio.file.Path;

public interface Platform {

    Path configFile();

    boolean isModLoaded(String modId);
}