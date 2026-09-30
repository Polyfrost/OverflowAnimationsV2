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
    public boolean itemPositions = true;
    public boolean itemPositionsInThirdPerson = true;
    public boolean strictItemPositionsInThirdPerson = true;
    public boolean thinBlockPositions = false;
    public boolean skullPosition = false;
    public FishingRodVersionSetting fishingRodVersion = FishingRodVersionSetting.V1_7;
    // (Items) Other
    public boolean thinFishingRodLineThickness = false;
    public boolean itemUsageSwinging = true;
    public boolean usageSwingingParticles = true;
    public boolean disableSwingOnUse = false;
    public boolean disableSwingOnDrop = false;
    public boolean disableSwingOnEntityInteract = false;
    public boolean disableItemUsingTextureInGUI = false;
    public EquipAnimationVersionSetting equipAnimationVersion = EquipAnimationVersionSetting.VANILLA;
    public boolean durabilityBarColors = false;
    public boolean legacyItemRarities = false;
    public boolean heldItemVisibilityInBoat = false;
    public boolean itemPickupPosition = true;
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

    public boolean legacyGlint = false;
    public boolean legacyGuiGlint = false;
    public boolean legacyPotionGlint = false;
    public boolean disablePotionGlint = false;
    public boolean modernArmorGlint = false;
    public boolean itemDrops2DColors = false;
    public boolean legacyProjectiles = false;
    public boolean xpOrbPosition = false;
    public boolean fireballModel = false;
    public boolean disableItemPickupAnimation = false;
    public float itemPickupOffset = 0.0F;
    public boolean entityItemPositions = true;
    public boolean disableHandSway = false;
    public float reequipSpeed = 0.4F;
    public boolean smartSwingScaling = false;
    public boolean itemUseAnimationInGUI = false;
    public boolean dropItemSwing = false;
    public boolean disableDropSwingInContainers = false;
    public boolean itemUseCooldownAnimation = false;
    public boolean modernBlockBreaking = false;
    public boolean resetMiningOnUse = false;
    public boolean blockHitWhileMining = true;
    public boolean disableAdventureSwing = false;
    public boolean disableAdventureUsageSwinging = false;
    public boolean disableAdventureUsageParticles = false;
    public boolean lunarBlockHitPosition = false;
    public boolean lunarItemPositions = false;
    public boolean modernPotionColors = false;
    public boolean coloredPotionBottles = false;
    public boolean fishingRodLineFov = false;
    public float fishingRodLineThickness = 0.0F;
    public boolean customRodLine = false;
    public float rodLinePositionX = -0.36F;
    public float rodLinePositionY = 0.03F;
    public float rodLinePositionZ = 0.35F;
    public float swingPositionX = 0.0F;
    public float swingPositionY = 0.0F;
    public float swingPositionZ = 0.0F;
    public float consumePositionX = 0.0F;
    public float consumePositionY = 0.0F;
    public float consumePositionZ = 0.0F;
    public float consumeRotationX = 0.0F;
    public float consumeRotationY = 0.0F;
    public float consumeRotationZ = 0.0F;
    public float consumeScale = 0.0F;
    public float consumeIntensity = 0.0F;
    public float consumeSpeed = 0.0F;
    public boolean scaleConsumeWithItem = false;
    public float blockingPositionX = 0.0F;
    public float blockingPositionY = 0.0F;
    public float blockingPositionZ = 0.0F;
    public float blockingRotationX = 0.0F;
    public float blockingRotationY = 0.0F;
    public float blockingRotationZ = 0.0F;
    public float blockingScale = 0.0F;
    public float droppedPositionX = 0.0F;
    public float droppedPositionY = 0.0F;
    public float droppedPositionZ = 0.0F;
    public float droppedRotationX = 0.0F;
    public float droppedRotationY = 0.0F;
    public float droppedRotationZ = 0.0F;
    public float droppedScale = 0.0F;
    public float projectilePositionX = 0.0F;
    public float projectilePositionY = 0.0F;
    public float projectilePositionZ = 0.0F;
    public float projectileRotationX = 0.0F;
    public float projectileRotationY = 0.0F;
    public float projectileRotationZ = 0.0F;
    public float projectileScale = 0.0F;
    public float fireballPositionX = 0.0F;
    public float fireballPositionY = 0.0F;
    public float fireballPositionZ = 0.0F;
    public float fireballRotationX = 0.0F;
    public float fireballRotationY = 0.0F;
    public float fireballRotationZ = 0.0F;
    public float fireballScale = 0.0F;

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

        bundle.group("legacy_glint")
                .booleanEntry("legacyGlint")
                .booleanEntry("legacyGuiGlint")
                .booleanEntry("legacyPotionGlint")
                .booleanEntry("disablePotionGlint")
                .booleanEntry("modernArmorGlint");

        bundle.group("legacy_drops")
                .booleanEntry("itemDrops2DColors")
                .booleanEntry("legacyProjectiles")
                .booleanEntry("xpOrbPosition")
                .booleanEntry("fireballModel")
                .booleanEntry("disableItemPickupAnimation")
                .floatRange("itemPickupOffset", -1.0F, 1.0F, 0.05F);

        bundle.group("legacy_usage")
                .booleanEntry("entityItemPositions")
                .booleanEntry("disableHandSway")
                .floatRange("reequipSpeed", 0.1F, 1.0F, 0.05F)
                .booleanEntry("smartSwingScaling")
                .booleanEntry("itemUseAnimationInGUI")
                .booleanEntry("dropItemSwing")
                .booleanEntry("disableDropSwingInContainers")
                .booleanEntry("itemUseCooldownAnimation")
                .booleanEntry("modernBlockBreaking")
                .booleanEntry("resetMiningOnUse")
                .booleanEntry("blockHitWhileMining")
                .booleanEntry("disableAdventureSwing")
                .booleanEntry("disableAdventureUsageSwinging")
                .booleanEntry("disableAdventureUsageParticles")
                .booleanEntry("lunarBlockHitPosition")
                .booleanEntry("lunarItemPositions");

        bundle.group("legacy_potions")
                .booleanEntry("modernPotionColors")
                .booleanEntry("coloredPotionBottles");

        bundle.group("legacy_fishing")
                .booleanEntry("fishingRodLineFov")
                .floatRange("fishingRodLineThickness", 0.0F, 10.0F, 0.5F)
                .booleanEntry("customRodLine")
                .floatRange("rodLinePositionX", -2.0F, 2.0F, 0.01F)
                .floatRange("rodLinePositionY", -2.0F, 2.0F, 0.01F)
                .floatRange("rodLinePositionZ", -2.0F, 2.0F, 0.01F);

        bundle.group("swing_position")
                .floatRange("swingPositionX", -2.0F, 2.0F, 0.01F)
                .floatRange("swingPositionY", -2.0F, 2.0F, 0.01F)
                .floatRange("swingPositionZ", -2.0F, 2.0F, 0.01F);

        bundle.group("consume_position")
                .floatRange("consumePositionX", -2.0F, 2.0F, 0.01F)
                .floatRange("consumePositionY", -2.0F, 2.0F, 0.01F)
                .floatRange("consumePositionZ", -2.0F, 2.0F, 0.01F)
                .floatRange("consumeRotationX", -180.0F, 180.0F, 1.0F)
                .floatRange("consumeRotationY", -180.0F, 180.0F, 1.0F)
                .floatRange("consumeRotationZ", -180.0F, 180.0F, 1.0F)
                .floatRange("consumeScale", -1.0F, 1.0F, 0.01F)
                .floatRange("consumeIntensity", -1.0F, 1.0F, 0.05F)
                .floatRange("consumeSpeed", -1.0F, 1.0F, 0.05F)
                .booleanEntry("scaleConsumeWithItem");

        bundle.group("blocking_position")
                .floatRange("blockingPositionX", -2.0F, 2.0F, 0.01F)
                .floatRange("blockingPositionY", -2.0F, 2.0F, 0.01F)
                .floatRange("blockingPositionZ", -2.0F, 2.0F, 0.01F)
                .floatRange("blockingRotationX", -180.0F, 180.0F, 1.0F)
                .floatRange("blockingRotationY", -180.0F, 180.0F, 1.0F)
                .floatRange("blockingRotationZ", -180.0F, 180.0F, 1.0F)
                .floatRange("blockingScale", -1.0F, 1.0F, 0.01F);

        bundle.group("dropped_position")
                .floatRange("droppedPositionX", -2.0F, 2.0F, 0.01F)
                .floatRange("droppedPositionY", -2.0F, 2.0F, 0.01F)
                .floatRange("droppedPositionZ", -2.0F, 2.0F, 0.01F)
                .floatRange("droppedRotationX", -180.0F, 180.0F, 1.0F)
                .floatRange("droppedRotationY", -180.0F, 180.0F, 1.0F)
                .floatRange("droppedRotationZ", -180.0F, 180.0F, 1.0F)
                .floatRange("droppedScale", -1.0F, 1.0F, 0.01F);

        bundle.group("projectile_position")
                .floatRange("projectilePositionX", -2.0F, 2.0F, 0.01F)
                .floatRange("projectilePositionY", -2.0F, 2.0F, 0.01F)
                .floatRange("projectilePositionZ", -2.0F, 2.0F, 0.01F)
                .floatRange("projectileRotationX", -180.0F, 180.0F, 1.0F)
                .floatRange("projectileRotationY", -180.0F, 180.0F, 1.0F)
                .floatRange("projectileRotationZ", -180.0F, 180.0F, 1.0F)
                .floatRange("projectileScale", -1.0F, 1.0F, 0.01F);

        bundle.group("fireball_position")
                .floatRange("fireballPositionX", -2.0F, 2.0F, 0.01F)
                .floatRange("fireballPositionY", -2.0F, 2.0F, 0.01F)
                .floatRange("fireballPositionZ", -2.0F, 2.0F, 0.01F)
                .floatRange("fireballRotationX", -180.0F, 180.0F, 1.0F)
                .floatRange("fireballRotationY", -180.0F, 180.0F, 1.0F)
                .floatRange("fireballRotationZ", -180.0F, 180.0F, 1.0F)
                .floatRange("fireballScale", -1.0F, 1.0F, 0.01F);

        return bundle;
    }
}