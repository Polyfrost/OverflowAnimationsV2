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
    public boolean usageSwingingParticles = true;
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
    // Item Swing
    public boolean customSwingSpeed = false;
    public float itemSwingSpeed = 0.0F;
    public float hasteSwingSpeed = 0.0F;
    public float miningFatigueSwingSpeed = 0.0F;
    public boolean ignoreHasteSpeed = false;
    public boolean ignoreMiningFatigueSpeed = false;
    public boolean offhandUsageSwinging = false;
    public boolean alwaysUsageSwing = false;
    public boolean fakeMissPenaltySwing = false;
    public boolean disableSwingTranslate = false;
    public boolean disableSwingPivot = false;
    public boolean legacySwingAnimation = false;
    // Item Modifications
    public float itemScaleX = 1.0F;
    public float itemScaleY = 1.0F;
    public float itemScaleZ = 1.0F;
    public float itemOffsetX = 0.0F;
    public float itemOffsetY = 0.0F;
    public float itemOffsetZ = 0.0F;
    public float itemRotationX = 0.0F;
    public float itemRotationY = 0.0F;
    public float itemRotationZ = 0.0F;
    public boolean applyCustomizationToBlockItems = true;

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

        bundle.group("item_swing")
                .booleanEntry("customSwingSpeed")
                .floatRange("itemSwingSpeed", -2.0F, 1.0F, 0.1F)
                .floatRange("hasteSwingSpeed", -2.0F, 1.0F, 0.1F)
                .floatRange("miningFatigueSwingSpeed", -2.0F, 1.0F, 0.1F)
                .booleanEntry("ignoreHasteSpeed")
                .booleanEntry("ignoreMiningFatigueSpeed")
                .booleanEntry("offhandUsageSwinging")
                .booleanEntry("alwaysUsageSwing")
                .booleanEntry("fakeMissPenaltySwing")
                .booleanEntry("disableSwingTranslate")
                .booleanEntry("disableSwingPivot")
                .booleanEntry("legacySwingAnimation");

        bundle.group("item_modifications")
                .floatRange("itemScaleX", 0.2F, 2.0F, 0.1F)
                .floatRange("itemScaleY", 0.2F, 2.0F, 0.1F)
                .floatRange("itemScaleZ", 0.2F, 2.0F, 0.1F)
                .floatEntry("itemOffsetX")
                .floatEntry("itemOffsetY")
                .floatEntry("itemOffsetZ")
                .floatEntry("itemRotationX")
                .floatEntry("itemRotationY")
                .floatEntry("itemRotationZ")
                .booleanEntry("applyCustomizationToBlockItems");

        bundle.group("other")
                .booleanEntry("thinFishingRodLineThickness")
                .booleanEntry("itemUsageSwinging")
                .booleanEntry("usageSwingingParticles")
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