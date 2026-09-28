/**
 * OverflowAnimations
 * The all-you-could-want legacy animations mod for modern minecraft versions.
 * Brings back animations from the 1.7/1.8 era and more.
 * <p>
 * Copyright (C) 2024-2027 lowercasebtw
 * Copyright (C) 2024-2027 mixces
 * Copyright (C) 2024-2027 Contributors to the project retain their copyright
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * "MINECRAFT" LINKING EXCEPTION TO THE GPL
 */

package org.polyfrost.overflowanimations.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.polyfrost.overflowanimations.config.category.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class OverflowAnimationsConfig {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final ConfigClassHandler<OverflowAnimationsConfig> HANDLER = ConfigClassHandler.createBuilder(OverflowAnimationsConfig.class)
            .serializer((config) -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("overflowanimations.json"))
                    .build()
            ).build();

    @SerialEntry
    public MovementConfigCategory movement = new MovementConfigCategory();

    @SerialEntry
    public ItemsConfigCategory items = new ItemsConfigCategory();

    @SerialEntry
    public ScreenConfigCategory screen = new ScreenConfigCategory();

    @SerialEntry
    public FixesConfigCategory fixes = new FixesConfigCategory();

    @SerialEntry
    public OtherConfigCategory other = new OtherConfigCategory();

    public static Screen getConfigScreen(@Nullable Screen parent) {
        return YetAnotherConfigLib.create(HANDLER, (defaults, config, builder) -> {
            builder.title(Component.translatable("overflowanimations.title"));
            builder.category(MovementConfigCategory.create(defaults.movement, config.movement));
            builder.category(ScreenConfigCategory.create(defaults.screen, config.screen));
            builder.category(ItemsConfigCategory.create(defaults.items, config.items));
            builder.category(FixesConfigCategory.create(defaults.fixes, config.fixes));
            builder.category(OtherConfigCategory.create(defaults.other, config.other));
            builder.save(HANDLER::save);
            return builder;
        }).generateScreen(parent);
    }

    public static void load() {
        final Path animatiumFilePath = YACLPlatform.getConfigDir().resolve("animatium3.json");
        final Path filePath = YACLPlatform.getConfigDir().resolve("overflowanimations.json");
        if (!Files.exists(filePath) && Files.exists(animatiumFilePath)) {
            try {
                Files.copy(animatiumFilePath, filePath);
                LOGGER.info("Imported Animatium config file!");
            } catch (final IOException exception) {
                LOGGER.error("Failed to import Animatium config file!", exception);
            }
        }

        migrateExtras(filePath);
        HANDLER.load();
    }

    // The Extras category was removed. Its surviving options moved to Other, Screen and Items, everything else was dropped.
    private static void migrateExtras(final Path filePath) {
        if (!Files.exists(filePath)) {
            return;
        }

        try {
            final JsonObject root = JsonParser.parseString(Files.readString(filePath)).getAsJsonObject();
            if (!(root.remove("extras") instanceof JsonObject extras)) {
                return;
            }

            moveEntries(extras, root, "other", "damage_tint_items", "damage_tint_cape", "old_water_color_effects");
            moveEntries(extras, root, "screen", "legacy_loading_screen_progress_bar");
            moveEntries(extras, root, "items", "item_scale_x", "item_scale_y", "item_scale_z", "item_offset_x", "item_offset_y", "item_offset_z", "item_rotation_x", "item_rotation_y", "item_rotation_z", "apply_customization_to_block_items", "custom_swing_speed", "item_swing_speed", "haste_swing_speed", "mining_fatigue_swing_speed", "ignore_haste_speed", "ignore_mining_fatigue_speed", "offhand_usage_swinging", "always_usage_swing", "fake_miss_penalty_swing", "disable_swing_translate", "disable_swing_pivot", "legacy_swing_animation");
            final Path tempPath = filePath.resolveSibling(filePath.getFileName() + ".tmp");
            Files.writeString(tempPath, GSON.toJson(root));
            Files.move(tempPath, filePath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            LOGGER.info("Migrated Extras config category!");
        } catch (final Exception exception) {
            LOGGER.error("Failed to migrate Extras config category!", exception);
        }
    }

    private static void moveEntries(final JsonObject from, final JsonObject root, final String category, final String... keys) {
        final JsonObject target = root.get(category) instanceof JsonObject object ? object : new JsonObject();
        for (final String key : keys) {
            if (from.has(key)) {
                target.add(key, from.get(key));
            }
        }

        root.add(category, target);
    }

    public static void save() {
        HANDLER.save();
    }

    public static OverflowAnimationsConfig instance() {
        return HANDLER.instance();
    }
}
