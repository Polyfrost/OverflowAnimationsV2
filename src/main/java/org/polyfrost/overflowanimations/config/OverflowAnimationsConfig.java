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

public final class OverflowAnimationsConfig {
    private static final Logger LOGGER = LogUtils.getLogger();
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

    @SerialEntry
    public ExtrasConfigCategory extras = new ExtrasConfigCategory();

    public static Screen getConfigScreen(@Nullable Screen parent) {
        return YetAnotherConfigLib.create(HANDLER, (defaults, config, builder) -> {
            builder.title(Component.translatable("overflowanimations.title"));
            builder.category(MovementConfigCategory.create(defaults.movement, config.movement));
            builder.category(ScreenConfigCategory.create(defaults.screen, config.screen));
            builder.category(ItemsConfigCategory.create(defaults.items, config.items));
            builder.category(FixesConfigCategory.create(defaults.fixes, config.fixes));
            builder.category(OtherConfigCategory.create(defaults.other, config.other));
            builder.category(ExtrasConfigCategory.create(defaults.extras, config.extras));
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

        HANDLER.load();
    }

    public static void save() {
        HANDLER.save();
    }

    public static OverflowAnimationsConfig instance() {
        return HANDLER.instance();
    }
}
