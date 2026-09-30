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

package org.polyfrost.overflowanimations.handler.config.bundle

import org.polyfrost.oneconfig.api.config.v1.Properties
import org.polyfrost.oneconfig.api.config.v1.Property
import org.polyfrost.oneconfig.api.config.v1.Tree
import org.polyfrost.overflowanimations.OverflowAnimationsConstants
import org.polyfrost.overflowanimations.handler.config.category.Category
import java.util.function.Consumer
import java.util.function.Predicate

open class EntryBundle(protected val category: Category, private val name: String) : Bundle() {
    companion object {
        private val LEGACY_ONLY_OPTIONS = listOf(
            "modernBackwardsWalking", "smoothHeadYaw", "modernViewBobbing", "directionalDamageTilt",
            "modernSneakEyeHeight", "disableHurtCamera", "dinnerboneMode", "dinnerboneModeEntities", "wavyArms",
            "legacyGlint", "legacyGuiGlint", "legacyPotionGlint", "disablePotionGlint", "modernArmorGlint",
            "itemDrops2DColors", "legacyProjectiles", "xpOrbPosition", "fireballModel", "disableItemPickupAnimation",
            "itemPickupOffset", "entityItemPositions", "disableHandSway", "reequipSpeed", "smartSwingScaling",
            "itemUseAnimationInGUI", "dropItemSwing", "disableDropSwingInContainers", "itemUseCooldownAnimation",
            "modernBlockBreaking", "resetMiningOnUse", "disableAdventureSwing", "disableAdventureUsageSwinging",
            "disableAdventureUsageParticles", "lunarBlockHitPosition", "lunarItemPositions", "modernPotionColors",
            "coloredPotionBottles", "fishingRodLineFov", "fishingRodLineThickness", "customRodLine",
            "rodLinePositionX", "rodLinePositionY", "rodLinePositionZ", "swingPositionX", "swingPositionY",
            "swingPositionZ", "consumePositionX", "consumePositionY", "consumePositionZ", "consumeRotationX",
            "consumeRotationY", "consumeRotationZ", "consumeScale", "consumeIntensity", "consumeSpeed",
            "scaleConsumeWithItem", "blockingPositionX", "blockingPositionY", "blockingPositionZ", "blockingRotationX",
            "blockingRotationY", "blockingRotationZ", "blockingScale", "droppedPositionX", "droppedPositionY",
            "droppedPositionZ", "droppedRotationX", "droppedRotationY", "droppedRotationZ", "droppedScale",
            "projectilePositionX", "projectilePositionY", "projectilePositionZ", "projectileRotationX",
            "projectileRotationY", "projectileRotationZ", "projectileScale", "fireballPositionX", "fireballPositionY",
            "fireballPositionZ", "fireballRotationX", "fireballRotationY", "fireballRotationZ", "fireballScale",
            "debugCrosshairStyle", "tabListStyle", "legacyDebugScreen", "hideCrosshairInThirdPerson"
        )

        // Options whose feature does not exist on (or is compiled out of) this Minecraft version
        private val UNSUPPORTED_OPTIONS = buildSet<String> {
            //? if <=1.8.9 {
            /*addAll(listOf(
                "oldCapeMovement", "disableCapeSwingRotation", "capeChestplateTranslation", "fakeOldSneakEyeHeight",
                "rotateBackwardsWalking", "uncapBlockingHeadRotation", "handViewBobbingMovement", "legacyDamageTilt",
                "legacyGlintSpeed", "skullPosition", "disableSwingOnUse", "disableSwingOnDrop",
                "disableSwingOnEntityInteract", "disableItemUsingTextureInGUI", "durabilityBarColors",
                "legacyItemRarities", "heldItemVisibilityInBoat", "offhandUsageSwinging", "legacySwingAnimation",
                "crosshairInThirdPerson", "listWidgetSelectedBorderColor", "legacyWidgetHoverTextColor",
                "disableCameraTransparentPassthrough", "tooltipStyleRendering", "slotHoverStyleRendering",
                "listBackgroundGradient", "inventoryEffectsPosition", "fullWidthInventoryEffects", "panoramaRendering",
                "legacyLoadingScreen", "legacyLoadingScreenProgressBar", "oldChatPosition", "oldCrosshairPosition",
                "oldCraftingSlotsPosition", "fixSneakingFeetPosition", "fixVerticalBobbingTilt",
                "oldSkyRenderingCheck", "smoothParticles", "fixMirrorArmSwing", "fixOffHandUsingPose",
                "fixCastLineCheck", "fixCastLineSwing", "fixFireballClientsideVisual", "fixTextStrikethroughStyle",
                "fixHighAttackSpeedIndicator", "upMinPixelTransparencyLimit", "fixDoubleUsageVisual", "blueVoidSky",
                "cloudHeight", "playerVoidBox", "oldY0Height", "oldWaterOverlayOpacity", "oldWaterColorFog",
                "oldWaterColorEffects", "oldCloudRendering", "legacyLightmap", "legacyFogDarkness",
                "maxGlintProperties", "restoreParticleBlending", "disableInventoryEntityScissor",
                "legacyBlockMiningProgress", "projectileAgeCheck", "blockOutlineRendering",
                "disableModelWhilstSleeping", "flameDimensions", "itemGlintOnEntity", "heldItemArmLogic",
                "legacySplashPosition", "legacyDiffuseLighting",
                "fixEquipAnimationOnItemUse"
            ))
            *///?} else {
            addAll(LEGACY_ONLY_OPTIONS)
            //?}
            // Built on the 1.21.5 GPU API and the 1.21.6 rendering/fog/GUI rewrites
            //? if >1.8.9 <1.21.6 {
            /*addAll(listOf(
                "panoramaRendering", "oldCloudRendering", "legacyLightmap", "legacyDiffuseLighting", "planarSkyFog",
                "voidFog", "oldY0Height", "legacyFogDarkness", "oldWaterColorFog", "blueVoidSky", "playerVoidBox",
                "fastGrass", "disableRandomBlockRotations", "disableInventoryEntityScissor", "thinFishingRodLineThickness",
                "glintAffectsArmorTint"
            ))
            *///?}
            //? if <1.21.9 {
            /*add("fixTextStrikethroughStyle") // Fixes a bug in the 1.21.9 glyph pipeline
            *///?}
            //? if <1.21.11 {
            /*add("fullWidthInventoryEffects") // Effects already use the full width before 1.21.11
            *///?}
        }
    }

    protected val entries = ArrayList<Entry>()
    private val groups = ArrayList<GroupBundle>()

    fun install(tree: Tree) {
        val categoryKey = "${OverflowAnimationsConstants.MOD_ID}.category.${this.name}"
        for (group in this.groups) {
            group.installEntries(tree, categoryKey, "$categoryKey.group.${group.name()}")
        }
        this.installEntries(tree, categoryKey, null)
    }

    internal fun installEntries(tree: Tree, categoryKey: String, subcategoryKey: String?) {
        for (entry in this.entries) {
            val key = "${OverflowAnimationsConstants.MOD_ID}.${entry.name}"
            val field = this.category.javaClass.getField(entry.name)
            val property: Property<Any?> = Properties.field(key, "$key.description", field, this.category)
            property.addMetadata("visualizer", entry.visualizer)
            property.addMetadata("category", categoryKey)
            subcategoryKey?.let { property.addMetadata("subcategory", it) }
            property.addMetadata(entry.metadata)
            @Suppress("UNCHECKED_CAST")
            (entry.listener as Consumer<Any?>?)?.let { listener ->
                property.addCallback(Predicate { value ->
                    field.set(this.category, value)
                    listener.accept(value)
                    false
                })
            }
            tree.put(property)
        }
    }

    override fun entry(entry: Entry): Bundle {
        if (entry.name !in UNSUPPORTED_OPTIONS) this.entries.add(entry)
        return this
    }

    open fun group(name: String): GroupBundle {
        val group = GroupBundle(this.category, name)
        this.groups.add(group)
        return group
    }

    fun name() = this.name
}
