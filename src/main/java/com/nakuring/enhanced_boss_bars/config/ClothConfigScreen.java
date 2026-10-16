package com.nakuring.enhanced_boss_bars.config;

import com.nakuring.enhanced_boss_bars.BossBarsManager;
import com.nakuring.enhanced_boss_bars.config.util.BossBarConfig;
import com.nakuring.enhanced_boss_bars.config.util.GeneralConfig;
import com.nakuring.enhanced_boss_bars.util.BossBarData;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.nakuring.enhanced_boss_bars.EnhancedBossBars.getModName;

public class ClothConfigScreen {

    private static me.shedaniel.clothconfig2.gui.ClothConfigScreen current_screen;
    public static IntegerSliderEntry scale_entry;
    public static IntegerSliderEntry horizontal_position_entry;
    public static IntegerSliderEntry vertical_position_entry;
    public static EnumListEntry horizontal_alignment_entry;
    public static EnumListEntry vertical_direction_entry;
    public static BooleanListEntry bar_preview_entry;
    public static IntegerSliderEntry max_boss_bars_entry;
    public static BooleanListEntry max_boss_bars_preview_entry;

    public static me.shedaniel.clothconfig2.gui.ClothConfigScreen getCurrentScreen() {
        return current_screen;
    }

    public static Screen create(Screen parent) {

        GeneralConfig general_config = ConfigManager.generalConfig();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("Enhanced Boss Bars"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(
                Component.literal("General")
        );

        scale_entry = entryBuilder
                .startIntSlider(
                        Component.literal("Boss bar scale"),
                        general_config.scale,
                        1,
                        4
                )
                .setDefaultValue(3)
                .setSaveConsumer(value -> general_config.scale = value)
                .build();

        horizontal_position_entry = entryBuilder
                .startIntSlider(
                        Component.literal("Horizontal position (%)"),
                        general_config.position_x,
                        0,
                        100
                )
                .setDefaultValue(50)
                .setSaveConsumer(value -> general_config.position_x = value)
                .build();


        vertical_position_entry = entryBuilder
                .startIntSlider(
                        Component.literal("Vertical position (%)"),
                        general_config.position_y,
                        0,
                        100
                )
                .setDefaultValue(1)
                .setSaveConsumer(value -> general_config.position_y = value)
                .build();

        horizontal_alignment_entry = entryBuilder
                .startEnumSelector(
                        Component.literal("Horizontal alignment"),
                        GeneralConfig.HorizontalAlignment.class,
                        general_config.horizontal_alignment
                )
                .setDefaultValue(GeneralConfig.HorizontalAlignment.Center)
                .setSaveConsumer(value -> general_config.horizontal_alignment = value)
                .build();

        vertical_direction_entry = entryBuilder
                .startEnumSelector(
                        Component.literal("Stack direction"),
                        GeneralConfig.VerticalDirection.class,
                        general_config.vertical_direction
                )
                .setDefaultValue(GeneralConfig.VerticalDirection.Down)
                .setSaveConsumer(value -> general_config.vertical_direction = value)
                .build();

        max_boss_bars_entry = entryBuilder
                .startIntSlider(
                        Component.literal("Maximum visible boss bars at the same time"),
                        general_config.max_boss_bars,
                        1,
                        10
                )
                .setDefaultValue(3)
                .setSaveConsumer(value -> general_config.max_boss_bars = value)
                .build();

        bar_preview_entry = entryBuilder
                .startBooleanToggle(
                        Component.literal("Bar preview"),
                        general_config.bar_preview
                )
                .setDefaultValue(false)
                .setSaveConsumer(value -> general_config.bar_preview = value)
                .build();

        max_boss_bars_preview_entry = entryBuilder
                .startBooleanToggle(
                        Component.literal("Maximum visible boss bars preview"),
                        general_config.max_boss_bars_preview
                )
                .setDefaultValue(false)
                .setSaveConsumer(value -> general_config.max_boss_bars_preview = value)
                .build();

        SubCategoryListEntry position = entryBuilder
                .startSubCategory(
                        Component.literal("Position"),
                        List.of(
                                horizontal_position_entry,
                                vertical_position_entry,
                                horizontal_alignment_entry,
                                vertical_direction_entry
                        )
                )
                .setExpanded(true)
                .build();


        SubCategoryListEntry boss_bars = entryBuilder
                .startSubCategory(
                        Component.literal("Boss bars"),
                        List.of(
                                scale_entry,
                                max_boss_bars_entry
                        )
                )
                .setExpanded(true)
                .build();


        SubCategoryListEntry preview = entryBuilder
                .startSubCategory(
                        Component.literal("Preview"),
                        List.of(
                                bar_preview_entry,
                                max_boss_bars_preview_entry
                        )
                )
                .setExpanded(true)
                .build();

        general.addEntry(position);
        general.addEntry(boss_bars);
        general.addEntry(preview);

        Map<String, ConfigCategory> categories = new LinkedHashMap<>();

        BossBarsManager.getAllBossBarDefinitions()
                .forEach((key, definitions) -> {
                    BossBarData boss_bar = definitions.getFirst();
                    BossBarConfig bar_config = ConfigManager.barConfig(key);
                    String mod_id = boss_bar.mod_id();
                    String mod_name = getModName(mod_id);
                    String boss_name_key = key.value();

                    ConfigCategory category = categories.computeIfAbsent(
                            mod_id,
                            modId -> builder.getOrCreateCategory(
                                    Component.literal(mod_name)
                            )
                    );

                    BooleanListEntry enabled_entry = entryBuilder
                            .startBooleanToggle(
                                    Component.literal("Enabled"),
                                    bar_config.enabled
                            )
                            .setDefaultValue(true)
                            .setSaveConsumer(value -> bar_config.enabled = value)
                            .build();

                    List<String> authors = definitions.stream()
                            .map(BossBarData::author)
                            .filter(Objects::nonNull)
                            .distinct()
                            .toList();

                    if (authors.size() > 1) {
                        String selected_author = authors.contains(bar_config.selected_author)
                                ? bar_config.selected_author
                                : authors.getFirst();

                        var author_entry = entryBuilder.startSelector(
                                        Component.literal("Author"),
                                        authors.toArray(new String[0]),
                                        selected_author
                                )
                                .setSaveConsumer(value -> bar_config.selected_author = (String) value)
                                .build();

                        category.addEntry(
                                entryBuilder.startSubCategory(
                                                Component.translatable(boss_name_key),
                                                List.of(enabled_entry, author_entry)
                                        )
                                        .build()
                        );
                    } else {
                        category.addEntry(
                                entryBuilder.startSubCategory(
                                                Component.translatable(boss_name_key),
                                                List.of(enabled_entry)
                                        )
                                        .build()
                        );
                    }
                });

        builder.setSavingRunnable(() -> {
            BossBarsManager.applyAuthorSelections();
            ConfigManager.save();
        });

        me.shedaniel.clothconfig2.gui.ClothConfigScreen screen = (me.shedaniel.clothconfig2.gui.ClothConfigScreen) builder.build();

        current_screen = screen;

        return screen;
    }
}
