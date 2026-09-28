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

package org.polyfrost.overflowanimations.mixins.v1.entity.flame;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.feature.FlameFeatureRenderer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.util.CameraUtilKt;
import org.polyfrost.overflowanimations.util.enums.SneakAnimationSetting;

@Mixin(FlameFeatureRenderer.class)
public abstract class MixinFlameFeatureRenderer_OldFlameDimensions {
    //? if <26.2 {
    /*@ModifyExpressionValue(method = "renderFlame", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/EntityRenderState;boundingBoxWidth:F", opcode = Opcodes.GETFIELD))
    *///?}
    //? if 1.21.11 {
    /*private float overflowanimations$flameWidth(final float original, @Local(ordinal = 0, argsOnly = true) final EntityRenderState state) {
    *///?} elif >=26.2 {
    @ModifyExpressionValue(method = "prepare", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/EntityRenderState;boundingBoxWidth:F", opcode = Opcodes.GETFIELD))
    //?}
    //? if >=26.1 {
    private float overflowanimations$flameWidth(final float original, @Local(name = "state") final EntityRenderState state) {
    //?}
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.flameDimensions && state instanceof AvatarRenderState) {
            return 0.6F;
        } else {
            return original;
        }
    }

    //? if <26.2 {
    /*@ModifyExpressionValue(method = "renderFlame", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/EntityRenderState;boundingBoxHeight:F", opcode = Opcodes.GETFIELD))
    *///?}
    //? if 1.21.11 {
    /*private float overflowanimations$flameHeight(final float original, @Local(ordinal = 0, argsOnly = true) final EntityRenderState state) {
    *///?} elif >=26.2 {
    @ModifyExpressionValue(method = "prepare", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/EntityRenderState;boundingBoxHeight:F", opcode = Opcodes.GETFIELD))
    //?}
    //? if >=26.1 {
    private float overflowanimations$flameHeight(final float original, @Local(name = "state") final EntityRenderState state) {
    //?}
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.flameDimensions && state instanceof AvatarRenderState) {
            return 1.8F;
        } else {
            return original;
        }
    }

    //? if <26.2 {
    /*@ModifyArg(method = "renderFlame", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack$Pose;translate(FFF)Lorg/joml/Matrix4f;", ordinal = 0), index = 1)
    *///?}
    //? if 1.21.11 {
    /*private float overflowanimations$flameOffset(final float original, @Local(ordinal = 0, argsOnly = true) final EntityRenderState state) {
    *///?} elif >=26.2 {
    @ModifyArg(method = "prepare", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack$Pose;translate(FFF)Lorg/joml/Matrix4f;", ordinal = 0), index = 1)
    //?}
    //? if >=26.1 {
    private float overflowanimations$flameOffset(final float original, @Local(name = "state") final EntityRenderState state) {
    //?}
        final Camera camera = Minecraft.getInstance().getEntityRenderDispatcher().camera;
        if (OverflowAnimations.isEnabled() && state instanceof AvatarRenderState avatarRenderState && camera != null && camera.entity().getId() == avatarRenderState.id) {
            final boolean shouldSyncPlayerModelWithEyeHeight = OverflowAnimationsConfig.instance().movement.sneakAnimation == SneakAnimationSetting.V1_7;

            float value = original;
            if (shouldSyncPlayerModelWithEyeHeight) {
                final float cameraLerpValue = CameraUtilKt.getPositionLerped(camera);
                value = (avatarRenderState.eyeHeight * avatarRenderState.scale) - cameraLerpValue;
            }

            if (OverflowAnimationsConfig.instance().other.flameOffset) {
                value += ((shouldSyncPlayerModelWithEyeHeight && avatarRenderState.isCrouching ? 0.140625F : 0.296875F) * avatarRenderState.scale);
            }

            return value;
        } else {
            return original;
        }
    }
}
