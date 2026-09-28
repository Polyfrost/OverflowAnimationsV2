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

package org.polyfrost.overflowanimations.config.category;

import dev.isxander.yacl3.api.ConfigCategory;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;
import org.polyfrost.overflowanimations.handler.config.bundle.EntryBundle;
import org.polyfrost.overflowanimations.handler.config.category.Category;
import org.polyfrost.overflowanimations.util.enums.EquipAnimationVersionSetting;
import org.polyfrost.overflowanimations.util.enums.FishingRodVersionSetting;

public final class ItemsConfigCategory extends Category {
    // (Items) Enchantment Glint
    public boolean legacyGlintSpeed = false;
    public boolean glintOnItemDrops2D = false;
    public boolean glintOnItemFramed2D = false;
    // (Items) 2D Drops
    public boolean itemDropsFaceCamera = false;
    public boolean itemDropsFaceCameraRotationFix = false;
    public boolean itemDrops2D = false;
    public boolean itemFramed2D = false;
    // (Items) Transformations
    public boolean itemPositions = false;
    public boolean itemPositionsInThirdPerson = false;
    public boolean strictItemPositionsInThirdPerson = false;
    public boolean thinBlockPositions = false;
    public boolean skullPosition = false;
    public FishingRodVersionSetting fishingRodVersion = FishingRodVersionSetting.VANILLA;
    // (Items) Other
    public boolean thinFishingRodLineThickness = false;
    public boolean itemUsageSwinging = false;
    public boolean disableSwingOnUse = false;
    public boolean disableSwingOnDrop = false;
    public boolean disableSwingOnEntityInteract = false;
    public boolean disableItemUsingTextureInGUI = false;
    public EquipAnimationVersionSetting equipAnimationVersion = EquipAnimationVersionSetting.VANILLA;
    public boolean durabilityBarColors = false;
    public boolean legacyItemRarities = false;
    public boolean heldItemVisibilityInBoat = false;
    public boolean itemPickupPosition = false;
    public boolean mobHeadIcons = false;
    public boolean eggSnowballParticles = false;

    public static ConfigCategory create(final ItemsConfigCategory defaults, final ItemsConfigCategory config) {
        final ConfigCategory.Builder category = ConfigCategory.createBuilder();
        category.name(Component.translatable("overflowanimations.category.items"));
        config.bundle().install(category, defaults, config);
        return category.build();
    }

    @Override
    public @NonNull EntryBundle bundle() {
        final EntryBundle bundle = new EntryBundle(this, "items");

        bundle.group("glint")
                .booleanEntry("legacyGlintSpeed")
                .booleanEntry("glintOnItemDrops2D")
                .booleanEntry("glintOnItemFramed2D");

        bundle.group("drops2d")
                .booleanEntry("itemDropsFaceCamera")
                .booleanEntry("itemDropsFaceCameraRotationFix")
                .booleanEntry("itemDrops2D")
                .booleanEntry("itemFramed2D");

        bundle.group("transformations")
                .booleanEntry("itemPositions")
                .booleanEntry("itemPositionsInThirdPerson")
                .booleanEntry("strictItemPositionsInThirdPerson")
                .booleanEntry("thinBlockPositions")
                .booleanEntry("skullPosition")
                .enumEntry("fishingRodVersion", FishingRodVersionSetting.class);

        bundle.group("other")
                .booleanEntry("thinFishingRodLineThickness")
                .booleanEntry("itemUsageSwinging")
                .booleanEntry("disableSwingOnUse")
                .booleanEntry("disableSwingOnDrop")
                .booleanEntry("disableSwingOnEntityInteract")
                .booleanEntry("disableItemUsingTextureInGUI")
                .enumEntry("equipAnimationVersion", EquipAnimationVersionSetting.class)
                .booleanEntry("durabilityBarColors")
                .booleanEntry("legacyItemRarities")
                .booleanEntry("heldItemVisibilityInBoat")
                .booleanEntry("itemPickupPosition")
                .booleanEntry("mobHeadIcons")
                .booleanEntry("eggSnowballParticles");

        return bundle;
    }
}