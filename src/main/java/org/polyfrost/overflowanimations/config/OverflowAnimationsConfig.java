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

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.polyfrost.compose.render.PolyColor;
import org.polyfrost.oneconfig.api.config.v1.Config;
import org.polyfrost.oneconfig.api.config.v1.Properties;
import org.polyfrost.oneconfig.api.config.v1.Property;
import org.polyfrost.oneconfig.api.config.v1.Tree;
import org.polyfrost.oneconfig.api.ui.v1.OneConfigUI;
import org.polyfrost.oneconfig.internal.ui.navigation.graph.ModConfigRoute;
import org.polyfrost.overflowanimations.OverflowAnimationsConstants;
import org.polyfrost.overflowanimations.config.category.*;
import org.polyfrost.overflowanimations.util.enums.CameraVersionSetting;
import org.polyfrost.overflowanimations.util.enums.DebugCrosshairSetting;
import org.polyfrost.overflowanimations.util.enums.FishingRodVersionSetting;
import org.polyfrost.overflowanimations.util.enums.SneakAnimationSetting;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class OverflowAnimationsConfig extends Config {
    private static final Logger LOGGER = LogManager.getLogger("OverflowAnimations/Config");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String ID = OverflowAnimationsConstants.MOD_ID + ".json";
    private static final Path CONFIG_DIR = FabricLoader.getInstance().getConfigDir();
    private static final int CONFIG_VERSION = 1;

    private static JsonObject legacyConfig = takeLegacyConfig();
    private static final OverflowAnimationsConfig INSTANCE = new OverflowAnimationsConfig();

    public final MovementConfigCategory movement = new MovementConfigCategory();
    public final ItemsConfigCategory items = new ItemsConfigCategory();
    public final ScreenConfigCategory screen = new ScreenConfigCategory();
    public final FixesConfigCategory fixes = new FixesConfigCategory();
    public final OtherConfigCategory other = new OtherConfigCategory();
    public int configVersion = 0;

    private OverflowAnimationsConfig() {
        super(ID, "OverflowAnimations", Category.VISUALS);
    }

    @Override
    protected Tree makeTree() {
        final Tree tree = Tree.tree(ID);
        this.movement.bundle().install(tree);
        this.screen.bundle().install(tree);
        this.items.bundle().install(tree);
        this.fixes.bundle().install(tree);
        this.other.bundle().install(tree);
        try {
            tree.put(Properties.field(null, null, OverflowAnimationsConfig.class.getField("configVersion"), this).addDisplayCondition(() -> Property.Display.HIDDEN));
        } catch (final NoSuchFieldException exception) {
            throw new IllegalStateException(exception);
        }
        return tree;
    }

    @Override
    protected void initialize(boolean byConfigManager) {
        super.initialize(byConfigManager);
        if (legacyConfig != null && this.getTree() != null) {
            this.importLegacyConfig(legacyConfig);
            legacyConfig = null;
            this.save();
        }

        if (this.configVersion < CONFIG_VERSION) {
            this.migrateDefaults();
            this.configVersion = CONFIG_VERSION;
            this.save();
        }
    }

    private void migrateDefaults() {
        final MovementConfigCategory movement = this.movement;
        final ItemsConfigCategory items = this.items;
        final ScreenConfigCategory screen = this.screen;
        if (items.rodLinePositionX == -0.36F && items.rodLinePositionY == 0.03F && items.rodLinePositionZ == 0.35F) {
            items.rodLinePositionX = items.rodLinePositionY = items.rodLinePositionZ = 0.0F;
        }

        if (movement.sneakAnimation == SneakAnimationSetting.VANILLA) {
            movement.sneakAnimation = SneakAnimationSetting.MODERN;
        }

        if (items.fishingRodVersion == FishingRodVersionSetting.VANILLA) {
            items.fishingRodVersion = FishingRodVersionSetting.MODERN;
        }

        if (screen.cameraVersion == CameraVersionSetting.VANILLA) {
            screen.cameraVersion = CameraVersionSetting.MODERN;
        }

        if (screen.debugCrosshairStyle == DebugCrosshairSetting.V1_12 || screen.debugCrosshairStyle == DebugCrosshairSetting.V1_8) {
            screen.debugCrosshairStyle = DebugCrosshairSetting.VANILLA;
        }

        movement.modernBackwardsWalking = true;
        movement.smoothHeadYaw = true;
        movement.modernViewBobbing = true;
        movement.directionalDamageTilt = true;
        items.itemPickupPosition = false;
        items.fakeMissPenaltySwing = true;
        items.legacyGlint = true;
        items.modernArmorGlint = true;
        items.itemUseAnimationInGUI = true;
        items.dropItemSwing = true;
        items.modernBlockBreaking = true;
        items.modernPotionColors = true;
        items.fishingRodLineFov = true;
        screen.hideCrosshairInThirdPerson = true;
    }

    public static void load() {
        INSTANCE.preload();
    }

    public static void openScreen() {
        OneConfigUI.open(new ModConfigRoute(ID, null));
    }

    public static OverflowAnimationsConfig instance() {
        return INSTANCE;
    }

    private static JsonObject takeLegacyConfig() {
        final Path filePath = CONFIG_DIR.resolve(ID);
        JsonObject root = readObject(filePath);
        if (root != null) {
            if (!(root.get("movement") instanceof JsonObject)) {
                return null;
            }

            try {
                Files.move(filePath, CONFIG_DIR.resolve(ID + ".yacl"), StandardCopyOption.REPLACE_EXISTING);
            } catch (final Exception exception) {
                LOGGER.error("Failed to move the YACL config file!", exception);
                return null;
            }
        } else if (Files.exists(filePath) || (root = readObject(CONFIG_DIR.resolve("animatium3.json"))) == null) {
            return null;
        }

        migrateExtras(root);
        migrateSneakBobbing(root);
        return root;
    }

    private static JsonObject readObject(final Path path) {
        try {
            return Files.exists(path) ? GSON.fromJson(new String(Files.readAllBytes(path)), JsonObject.class) : null;
        } catch (final Exception exception) {
            return null;
        }
    }

    private void importLegacyConfig(final JsonObject root) {
        final Object[][] categories = {
                {"movement", this.movement}, {"items", this.items}, {"screen", this.screen}, {"fixes", this.fixes}, {"other", this.other}
        };
        for (final Object[] category : categories) {
            if (!(root.get((String) category[0]) instanceof JsonObject values)) {
                continue;
            }

            for (final Field field : category[1].getClass().getFields()) {
                final JsonElement value = values.get(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES.translateName(field));
                if (value == null || Modifier.isStatic(field.getModifiers())) {
                    continue;
                }

                try {
                    field.set(category[1], field.getType() == PolyColor.class ? new PolyColor(value.getAsInt()) : GSON.fromJson(value, field.getGenericType()));
                } catch (final Exception exception) {
                    LOGGER.warn("Skipped importing config option {}", field.getName(), exception);
                }
            }
        }
        LOGGER.info("Imported YACL config file!");
    }

    // The Extras category was removed. Its surviving options moved to Other, Screen and Items, everything else was dropped.
    private static void migrateExtras(final JsonObject root) {
        if (!(root.remove("extras") instanceof JsonObject extras)) {
            return;
        }

        moveEntries(extras, root, "other", "damage_tint_items", "damage_tint_cape", "old_water_color_effects");
        moveEntries(extras, root, "screen", "legacy_loading_screen_progress_bar");
        moveEntries(extras, root, "items", "item_scale_x", "item_scale_y", "item_scale_z", "item_offset_x", "item_offset_y", "item_offset_z", "item_rotation_x", "item_rotation_y", "item_rotation_z", "apply_customization_to_block_items", "custom_swing_speed", "item_swing_speed", "haste_swing_speed", "mining_fatigue_swing_speed", "ignore_haste_speed", "ignore_mining_fatigue_speed", "offhand_usage_swinging", "always_usage_swing", "fake_miss_penalty_swing", "disable_swing_translate", "disable_swing_pivot", "legacy_swing_animation");
    }

    private static void migrateSneakBobbing(final JsonObject root) {
        if (root.get("movement") instanceof JsonObject movement && movement.remove("hand_view_bobbing_movement") instanceof JsonElement value && value.getAsBoolean()) {
            movement.addProperty("sneak_bobbing", "V1_21_1");
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
}
