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

package org.polyfrost.overflowanimations.mixins.v1.rendering.fog;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.fog.FogRenderer;
//? if >=26.3 {
import org.joml.Vector3fc;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.handler.rendering.LegacyFogDarkness;

@Mixin(FogRenderer.class)
public abstract class MixinFogRenderer_Darkness {
    @WrapOperation(method = "computeFogColor", at = {
            //? if <26.3 {
            /*@At(value = "INVOKE", target = "Lnet/minecraft/util/ARGB;redFloat(I)F"),
            @At(value = "INVOKE", target = "Lnet/minecraft/util/ARGB;greenFloat(I)F"),
            @At(value = "INVOKE", target = "Lnet/minecraft/util/ARGB;blueFloat(I)F")
            *///?} else {
            @At(value = "INVOKE", target = "Lorg/joml/Vector3fc;x()F"),
            @At(value = "INVOKE", target = "Lorg/joml/Vector3fc;y()F"),
            @At(value = "INVOKE", target = "Lorg/joml/Vector3fc;z()F"),
            //?}
    })
    //? if 1.21.11 {
    /*private float overflowanimations$applyFogDarkness(final int color, final Operation<Float> original, @Local(argsOnly = true, ordinal = 0) final float tickDelta) {
    *///?} elif >=26.1 <26.3 {
    /*private float overflowanimations$applyFogDarkness(final int color, final Operation<Float> original, @Local(argsOnly = true, name = "partialTicks") final float tickDelta) {
    *///?} else {
    private float overflowanimations$applyFogDarkness(final Vector3fc color, final Operation<Float> original, @Local(argsOnly = true, name = "partialTicks") final float tickDelta) {
    //?}
        float component = original.call(color);
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.legacyFogDarkness) {
            component *= LegacyFogDarkness.getDarkness(tickDelta);
        }

        return component;
    }
}
