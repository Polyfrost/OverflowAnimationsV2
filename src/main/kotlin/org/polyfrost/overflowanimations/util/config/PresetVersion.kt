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

package org.polyfrost.overflowanimations.util.config

//? if <1.21.11 {
/*import net.minecraft.client.GraphicsStatus
*///?} else {
import net.minecraft.client.GraphicsPreset
//?}
import net.minecraft.client.Minecraft
import org.polyfrost.overflowanimations.OverflowAnimations
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig
import org.polyfrost.overflowanimations.util.enums.*

enum class PresetVersion(private val applier: Runnable) {
    V1_7({
        // Values
        val movement = OverflowAnimationsConfig.instance().movement
        movement.sneakAnimation = SneakAnimationSetting.V1_7
        movement.longUnsneak = true
        movement.oldCapeMovement = true
        movement.disableCapeLean = false
        movement.disableCapeSwingRotation = true
        movement.capeChestplateTranslation = true
        movement.capeSneakPosition = true
        movement.rotateBackwardsWalking = true
        movement.uncapBlockingHeadRotation = true
        movement.disableHeadRotationInterpolation = true
        movement.handViewBobbingMovement = true
        movement.deathLimbs = true
        movement.bowArmMovement = true
        movement.legacyDamageTilt = true
        movement.offsetHurtTiltTime = true

        val screen = OverflowAnimationsConfig.instance().screen
        screen.crosshairInThirdPerson = true
        screen.disableHeartFlash = true
        screen.centerScrollableListWidgets = true
        screen.listWidgetSelectedBorderColor = true
        screen.legacyWidgetHoverTextColor = true
        screen.disableDebugHudBackground = true
        screen.debugHudTextShadow = true
        screen.disableCameraTransparentPassthrough = true
        screen.tooltipStyleRendering = true
        screen.slotHoverStyleRendering = true
        screen.listBackgroundGradient = true
        screen.inventoryEffectsPosition = true
        screen.fullWidthInventoryEffects = true
        screen.panoramaRendering = true
        screen.legacyLoadingScreen = true
        screen.oldChatPosition = true
        screen.oldCrosshairPosition = true
        screen.disconnectServerToTitleScreen = true
        screen.cameraVersion = CameraVersionSetting.V1_8

        val items = OverflowAnimationsConfig.instance().items
        items.thinFishingRodLineThickness = false
        items.legacyGlintSpeed = true
        items.glintOnItemDrops2D = true
        items.glintOnItemFramed2D = true
        items.itemDropsFaceCamera = true
        items.itemDropsFaceCameraRotationFix = false
        items.itemDrops2D = true
        items.itemFramed2D = true
        items.itemPositions = true
        items.itemPositionsInThirdPerson = true
        items.thinBlockPositions = true
        items.skullPosition = true
        items.fishingRodVersion = FishingRodVersionSetting.V1_7
        items.itemUsageSwinging = true
        items.equipAnimationVersion = EquipAnimationVersionSetting.V1_7
        items.disableSwingOnUse = true
        items.disableSwingOnDrop = true
        items.disableSwingOnEntityInteract = true
        items.disableItemUsingTextureInGUI = true
        items.durabilityBarColors = true
        items.legacyItemRarities = true
        items.heldItemVisibilityInBoat = true
        items.itemPickupPosition = true
        items.mobHeadIcons = true
        items.eggSnowballParticles = true

        val other = OverflowAnimationsConfig.instance().other
        other.blueVoidSky = true
        other.planarSkyFog = true
        other.cloudHeight = true
        other.playerVoidBox = true
        other.thirdPersonSwordBlockingPosition = true
        other.lockBlockingArmRotation = true
        other.projectileAgeCheck = true
        other.legacyBlockMiningProgress = true
        other.disableInventoryEntityScissor = true
        other.blockOutlineRendering = true
        other.disableModelWhilstSleeping = true
        other.damageTintArmor = true
        other.glintAffectsArmorTint = false
        other.damageTintStyle = DamageTintSetting.V1_7
        other.itemGlintOnEntity = true
        other.maxGlintProperties = true
        other.restoreParticleBlending = true
        other.heldItemArmLogic = false
        other.flameDimensions = true
        other.flameOffset = true
        other.persistentBlockOutline = true
        other.oldCloudRendering = true
        //? if <1.21.11 {
        /*other.fastGrass = Minecraft.getInstance().options.graphicsMode().get() == GraphicsStatus.FAST
        *///?} else {
        other.fastGrass = Minecraft.getInstance().options.graphicsPreset().get() == GraphicsPreset.FAST
        //?}
        other.voidFog = VoidFogSetting.PARTICLES
        other.oldWaterOverlayOpacity = true
        other.oldWaterColorFog = true
        other.disableRandomBlockRotations = true
        other.legacyDiffuseLighting = true
        other.legacyLightmap = true
        other.legacyFogDarkness = true
        other.legacySplashPosition = true
    }),
    V1_8({
        // Values
        val movement = OverflowAnimationsConfig.instance().movement
        movement.sneakAnimation = SneakAnimationSetting.V1_8
        movement.longUnsneak = false
        movement.oldCapeMovement = true
        movement.disableCapeLean = false
        movement.disableCapeSwingRotation = true
        movement.capeChestplateTranslation = false
        movement.capeSneakPosition = false
        movement.rotateBackwardsWalking = true
        movement.uncapBlockingHeadRotation = true
        movement.disableHeadRotationInterpolation = false
        movement.handViewBobbingMovement = true
        movement.deathLimbs = true
        movement.bowArmMovement = false
        movement.legacyDamageTilt = true
        movement.offsetHurtTiltTime = false

        val screen = OverflowAnimationsConfig.instance().screen
        screen.crosshairInThirdPerson = true
        screen.disableHeartFlash = false
        screen.centerScrollableListWidgets = false
        screen.listWidgetSelectedBorderColor = true
        screen.legacyWidgetHoverTextColor = true
        screen.disableDebugHudBackground = false
        screen.debugHudTextShadow = false
        screen.disableCameraTransparentPassthrough = true
        screen.tooltipStyleRendering = true
        screen.slotHoverStyleRendering = true
        screen.listBackgroundGradient = true
        screen.inventoryEffectsPosition = true
        screen.fullWidthInventoryEffects = true
        screen.panoramaRendering = true
        screen.legacyLoadingScreen = true
        screen.oldChatPosition = true
        screen.oldCrosshairPosition = true
        screen.disconnectServerToTitleScreen = false
        screen.cameraVersion = CameraVersionSetting.V1_8

        val items = OverflowAnimationsConfig.instance().items
        items.thinFishingRodLineThickness = false
        items.legacyGlintSpeed = true
        items.glintOnItemDrops2D = false
        items.glintOnItemFramed2D = false
        items.itemDropsFaceCamera = false
        items.itemDropsFaceCameraRotationFix = false
        items.itemDrops2D = false
        items.itemFramed2D = false
        items.itemPositions = false
        items.itemPositionsInThirdPerson = false
        items.thinBlockPositions = false
        items.skullPosition = true
        items.fishingRodVersion = FishingRodVersionSetting.V1_8
        items.itemUsageSwinging = false
        items.equipAnimationVersion = EquipAnimationVersionSetting.V1_8
        items.disableSwingOnUse = true
        items.disableSwingOnDrop = true
        items.disableSwingOnEntityInteract = true
        items.disableItemUsingTextureInGUI = true
        items.durabilityBarColors = true
        items.legacyItemRarities = true
        items.heldItemVisibilityInBoat = true
        items.itemPickupPosition = false
        items.mobHeadIcons = false
        items.eggSnowballParticles = false

        val other = OverflowAnimationsConfig.instance().other
        other.blueVoidSky = true
        other.planarSkyFog = true
        other.cloudHeight = true
        other.playerVoidBox = true
        other.thirdPersonSwordBlockingPosition = false
        other.lockBlockingArmRotation = true
        other.projectileAgeCheck = true
        other.legacyBlockMiningProgress = true
        other.disableInventoryEntityScissor = true
        other.blockOutlineRendering = true
        other.disableModelWhilstSleeping = true
        other.damageTintArmor = false
        other.glintAffectsArmorTint = true
        other.damageTintStyle = DamageTintSetting.VANILLA
        other.itemGlintOnEntity = true
        other.maxGlintProperties = true
        other.restoreParticleBlending = true
        other.heldItemArmLogic = true
        other.flameDimensions = true
        other.flameOffset = false
        other.persistentBlockOutline = false
        other.oldCloudRendering = true
        other.fastGrass = false
        other.voidFog = VoidFogSetting.OFF
        other.oldWaterOverlayOpacity = true
        other.oldWaterColorFog = true
        other.disableRandomBlockRotations = false
        other.legacyDiffuseLighting = true
        other.legacyLightmap = true
        other.legacyFogDarkness = true
        other.legacySplashPosition = true
    }),
    VANILLA({
        // Values
        val movement = OverflowAnimationsConfig.instance().movement
        movement.sneakAnimation = SneakAnimationSetting.VANILLA
        movement.longUnsneak = false
        movement.oldCapeMovement = false
        movement.disableCapeLean = false
        movement.disableCapeSwingRotation = false
        movement.capeChestplateTranslation = false
        movement.capeSneakPosition = false
        movement.rotateBackwardsWalking = false
        movement.uncapBlockingHeadRotation = false
        movement.disableHeadRotationInterpolation = false
        movement.handViewBobbingMovement = false
        movement.deathLimbs = false
        movement.bowArmMovement = false
        movement.legacyDamageTilt = false
        movement.offsetHurtTiltTime = false

        val screen = OverflowAnimationsConfig.instance().screen
        screen.crosshairInThirdPerson = false
        screen.disableHeartFlash = false
        screen.centerScrollableListWidgets = false
        screen.listWidgetSelectedBorderColor = false
        screen.legacyWidgetHoverTextColor = false
        screen.disableDebugHudBackground = false
        screen.debugHudTextShadow = false
        screen.disableCameraTransparentPassthrough = false
        screen.tooltipStyleRendering = false
        screen.slotHoverStyleRendering = false
        screen.listBackgroundGradient = false
        screen.inventoryEffectsPosition = false
        screen.fullWidthInventoryEffects = false
        screen.panoramaRendering = false
        screen.legacyLoadingScreen = false
        screen.oldChatPosition = false
        screen.oldCrosshairPosition = false
        screen.disconnectServerToTitleScreen = false
        screen.cameraVersion = CameraVersionSetting.VANILLA

        val items = OverflowAnimationsConfig.instance().items
        items.thinFishingRodLineThickness = false
        items.legacyGlintSpeed = false
        items.glintOnItemDrops2D = false
        items.glintOnItemFramed2D = false
        items.itemDropsFaceCamera = false
        items.itemDropsFaceCameraRotationFix = false
        items.itemDrops2D = false
        items.itemFramed2D = false
        items.itemPositions = false
        items.itemPositionsInThirdPerson = false
        items.thinBlockPositions = false
        items.skullPosition = false
        items.fishingRodVersion = FishingRodVersionSetting.VANILLA
        items.itemUsageSwinging = false
        items.equipAnimationVersion = EquipAnimationVersionSetting.VANILLA
        items.disableSwingOnUse = false
        items.disableSwingOnDrop = false
        items.disableSwingOnEntityInteract = false
        items.disableItemUsingTextureInGUI = false
        items.durabilityBarColors = false
        items.legacyItemRarities = false
        items.heldItemVisibilityInBoat = false
        items.itemPickupPosition = false
        items.mobHeadIcons = false
        items.eggSnowballParticles = false

        val other = OverflowAnimationsConfig.instance().other
        other.blueVoidSky = false
        other.planarSkyFog = false
        other.cloudHeight = false
        other.playerVoidBox = false
        other.thirdPersonSwordBlockingPosition = false
        other.lockBlockingArmRotation = false
        other.projectileAgeCheck = false
        other.legacyBlockMiningProgress = false
        other.disableInventoryEntityScissor = false
        other.blockOutlineRendering = false
        other.disableModelWhilstSleeping = false
        other.damageTintArmor = false
        other.glintAffectsArmorTint = false
        other.damageTintStyle = DamageTintSetting.VANILLA
        other.itemGlintOnEntity = false
        other.maxGlintProperties = false
        other.restoreParticleBlending = false
        other.heldItemArmLogic = false
        other.flameDimensions = false
        other.flameOffset = false
        other.persistentBlockOutline = false
        other.oldCloudRendering = false
        other.fastGrass = false
        other.voidFog = VoidFogSetting.OFF
        other.oldWaterOverlayOpacity = false
        other.oldWaterColorFog = false
        other.disableRandomBlockRotations = false
        other.legacyDiffuseLighting = false
        other.legacyLightmap = false
        other.legacyFogDarkness = false
        other.legacySplashPosition = false
    });

    fun apply(reload: Boolean = true) {
        this.applier.run()
        OverflowAnimationsConfig.save()
        if (reload) {
            OverflowAnimations.reload()
        }
    }
}