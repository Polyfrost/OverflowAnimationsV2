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

import net.minecraft.client.Minecraft;
import org.jspecify.annotations.NonNull;
import org.polyfrost.compose.render.PolyColor;
import org.polyfrost.overflowanimations.handler.compatibility.ModsKt;
import org.polyfrost.overflowanimations.handler.config.bundle.EntryBundle;
import org.polyfrost.overflowanimations.handler.config.bundle.GroupBundle;
import org.polyfrost.overflowanimations.handler.config.category.Category;
//? if >1.8.9
import org.polyfrost.overflowanimations.handler.rendering.RenderUtilsKt;
//? if >=1.21.6
import org.polyfrost.overflowanimations.handler.rendering.lighting.LegacyDiffuseLighting;
import org.polyfrost.overflowanimations.util.enums.DamageTintSetting;
import org.polyfrost.overflowanimations.util.enums.VoidFogSetting;


public final class OtherConfigCategory extends Category {
    // Sky
    public boolean blueVoidSky = false;
    public boolean planarSkyFog = false;
    public boolean cloudHeight = false;
    public boolean playerVoidBox = false;
    public boolean oldCloudRendering = false;
    public VoidFogSetting voidFog = VoidFogSetting.OFF;
    // Damage Tint
    public boolean damageTintArmor = true;
    public boolean glintAffectsArmorTint = false;
    public boolean damageTintItems = false;
    public boolean damageTintCape = false;
    public DamageTintSetting damageTintStyle = DamageTintSetting.VANILLA;
    public PolyColor customTintColor = new PolyColor(0x4DFF0000); // Vanilla color as of 26.2
    // Other
    public boolean restoreParticleBlending = false;
    public boolean lockBlockingArmRotation = false;
    public boolean legacyBlockMiningProgress = false;
    public boolean projectileAgeCheck = false;
    public boolean blockOutlineRendering = false;
    public boolean disableModelWhilstSleeping = false;
    public boolean flameDimensions = false;
    public boolean heldItemArmLogic = false;
    public boolean thirdPersonSwordBlockingPosition = true;
    public boolean disableInventoryEntityScissor = false;
    public boolean itemGlintOnEntity = false;
    public boolean maxGlintProperties = false;
    public boolean flameOffset = false;
    public boolean persistentBlockOutline = false;
    public boolean fastGrass = false;
    public boolean oldY0Height = false;
    public boolean oldWaterOverlayOpacity = false;
    public boolean oldWaterColorFog = false;
    public boolean oldWaterColorEffects = false;
    public boolean disableRandomBlockRotations = false;
    public boolean legacyDiffuseLighting = false;
    public boolean legacyLightmap = false;
    public boolean legacyFogDarkness = false;
    public boolean legacySplashPosition = false;

    @Override
    public @NonNull EntryBundle bundle() {
        final EntryBundle bundle = new EntryBundle(this, "other");

        bundle.group("sky")
                .booleanEntry("oldCloudRendering")
                .booleanEntry("cloudHeight")
                .booleanEntry("blueVoidSky")
                .booleanEntry("playerVoidBox")
                .enumEntry("voidFog", VoidFogSetting.class)
                .booleanEntry("planarSkyFog");

        // Lunar Client handles armor tint itself, but item and cape tint still apply there.
        final GroupBundle damageTint = bundle.group("damage_tint");
        if (!ModsKt.HAS_LUNAR_CLIENT) {
            damageTint.booleanEntry("damageTintArmor")
                    .booleanEntry("glintAffectsArmorTint");
        }

        damageTint.booleanEntry("damageTintItems")
                .booleanEntry("damageTintCape");
        if (!ModsKt.HAS_LUNAR_CLIENT) {
            //? if >1.8.9 {
            damageTint.enumEntry("damageTintStyle", DamageTintSetting.class, value -> RenderUtilsKt.updateOverlayTint(value))
                    .colorEntry("customTintColor", value -> RenderUtilsKt.updateOverlayTint(this.damageTintStyle));
            //?} else {
            /*damageTint.enumEntry("damageTintStyle", DamageTintSetting.class)
                    .colorEntry("customTintColor");
            *///?}
        }

        bundle.group("other")
                .booleanEntry("restoreParticleBlending")
                .booleanEntry("lockBlockingArmRotation")
                .booleanEntry("legacyBlockMiningProgress")
                .booleanEntry("projectileAgeCheck")
                .booleanEntry("blockOutlineRendering")
                .booleanEntry("disableModelWhilstSleeping")
                .booleanEntry("flameDimensions")
                .booleanEntry("heldItemArmLogic")
                .booleanEntry("thirdPersonSwordBlockingPosition")
                .booleanEntry("disableInventoryEntityScissor")
                .booleanEntry("itemGlintOnEntity")
                .booleanEntry("maxGlintProperties")
                .booleanEntry("flameOffset")
                .booleanEntry("persistentBlockOutline")
                //? if <=1.8.9 {
                /*.booleanEntry("fastGrass", value -> Minecraft.getInstance().worldRenderer.reload())
                *///?} elif <26.2 {
                /*.booleanEntry("fastGrass", value -> Minecraft.getInstance().levelRenderer.allChanged())
                *///?} else {
                .booleanEntry("fastGrass", value -> Minecraft.getInstance().levelExtractor.allChanged())
                //?}
                .booleanEntry("oldY0Height")
                .booleanEntry("oldWaterOverlayOpacity")
                .booleanEntry("oldWaterColorFog")
                //? if <=1.8.9 {
                /*.booleanEntry("oldWaterColorEffects")
                *///?} elif <26.2 {
                /*.booleanEntry("oldWaterColorEffects", value -> Minecraft.getInstance().levelRenderer.allChanged())
                *///?} else {
                .booleanEntry("oldWaterColorEffects", value -> Minecraft.getInstance().levelExtractor.allChanged())
                //?}
                //? if <=1.8.9 {
                /*.booleanEntry("disableRandomBlockRotations", value -> Minecraft.getInstance().worldRenderer.reload())
                *///?} else
                .booleanEntry("disableRandomBlockRotations")
                //? if <1.21.6 {
                /*.booleanEntry("legacyDiffuseLighting")
                *///?} else {
                .booleanEntry("legacyDiffuseLighting", value -> LegacyDiffuseLighting.refresh())
                //?}
                .booleanEntry("legacyLightmap")
                .booleanEntry("legacyFogDarkness")
                .booleanEntry("legacySplashPosition");

        return bundle;
    }
}
