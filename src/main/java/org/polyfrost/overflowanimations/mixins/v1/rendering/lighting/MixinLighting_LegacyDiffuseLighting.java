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

package org.polyfrost.overflowanimations.mixins.v1.rendering.lighting;

// Needs the 1.21.5 GPU API / 1.21.6 rendering changes; compiled out on older versions
//? if >=1.21.6 {
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.Lighting;
import org.joml.Matrix4f;
//? if <26.2 {
/*import org.joml.Vector3f;
*///?} else {
import org.joml.Vector3fc;
//?}
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.handler.rendering.lighting.LegacyDiffuseLighting;

@Mixin(Lighting.class)
public abstract class MixinLighting_LegacyDiffuseLighting {
    @Shadow
    @Final
    //? if <26.2 {
    /*private static Vector3f DIFFUSE_LIGHT_0;
    *///?} else {
    private static Vector3fc DIFFUSE_LIGHT_0;
    //?}

    @Shadow
    @Final
    //? if <26.2 {
    /*private static Vector3f DIFFUSE_LIGHT_1;
    *///?} else {
    private static Vector3fc DIFFUSE_LIGHT_1;
    //?}

    @Shadow
    //? if <26.2 {
    /*protected abstract void updateBuffer(final Lighting.Entry entry, final Vector3f light0, final Vector3f light1);
    *///?} else {
    protected abstract void updateBuffer(final Lighting.Entry entry, final Vector3fc light0, final Vector3fc light1);
    //?}

    // Use old "setupGui3DDiffuseLighting" calculation w/ normal diffuse lighting
    @Inject(method = "<init>", at = @At("TAIL"))
    //? if <26.1 {
    /*private void overflowanimations$legacyDiffuseLighting(final CallbackInfo ci, @Local(ordinal = 1) final Matrix4f item3DPose) {
    *///?} else {
    private void overflowanimations$legacyDiffuseLighting(final CallbackInfo ci, @Local(name = "item3DPose") final Matrix4f item3DPose) {
    //?}
        LegacyDiffuseLighting.setItem3dPose(item3DPose);
        LegacyDiffuseLighting.setUpdateLightingInvoker((entry, lights) -> this.updateBuffer(entry, lights.light0, lights.light1));
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.legacyDiffuseLighting) {
            LegacyDiffuseLighting.refresh();
        }
    }

    // Use normal light in nether
    //? if <26.2 {
    /*@ModifyExpressionValue(method = "updateLevel", at = @At(value = "FIELD", target = "Lcom/mojang/blaze3d/platform/Lighting;NETHER_DIFFUSE_LIGHT_0:Lorg/joml/Vector3f;", opcode = Opcodes.GETSTATIC))
    private Vector3f overflowanimations$legacyDiffuseLighting$useDiffuse0Nether(final Vector3f original) {
    *///?} else {
    @ModifyExpressionValue(method = "updateLevel", at = @At(value = "FIELD", target = "Lcom/mojang/blaze3d/platform/Lighting;NETHER_DIFFUSE_LIGHT_0:Lorg/joml/Vector3fc;", opcode = Opcodes.GETSTATIC))
    private Vector3fc overflowanimations$legacyDiffuseLighting$useDiffuse0Nether(final Vector3fc original) {
    //?}
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.legacyDiffuseLighting) {
            return DIFFUSE_LIGHT_0;
        } else {
            return original;
        }
    }

    //? if <26.2 {
    /*@ModifyExpressionValue(method = "updateLevel", at = @At(value = "FIELD", target = "Lcom/mojang/blaze3d/platform/Lighting;NETHER_DIFFUSE_LIGHT_1:Lorg/joml/Vector3f;", opcode = Opcodes.GETSTATIC))
    private Vector3f overflowanimations$legacyDiffuseLighting$useDiffuse1Nether(final Vector3f original) {
    *///?} else {
    @ModifyExpressionValue(method = "updateLevel", at = @At(value = "FIELD", target = "Lcom/mojang/blaze3d/platform/Lighting;NETHER_DIFFUSE_LIGHT_1:Lorg/joml/Vector3fc;", opcode = Opcodes.GETSTATIC))
    private Vector3fc overflowanimations$legacyDiffuseLighting$useDiffuse1Nether(final Vector3fc original) {
    //?}
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.legacyDiffuseLighting) {
            return DIFFUSE_LIGHT_1;
        } else {
            return original;
        }
    }
}
//?}
