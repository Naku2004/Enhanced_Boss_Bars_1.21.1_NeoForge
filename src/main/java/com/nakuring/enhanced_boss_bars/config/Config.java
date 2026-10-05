package com.nakuring.enhanced_boss_bars.config;

import com.nakuring.enhanced_boss_bars.config.util.BossBarConfig;
import com.nakuring.enhanced_boss_bars.config.util.GeneralConfig;

import java.util.HashMap;
import java.util.Map;

public class Config {

    public GeneralConfig general = new GeneralConfig();

    public Map<String, BossBarConfig> boss_bars = new HashMap<>();

}