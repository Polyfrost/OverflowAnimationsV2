/**
 * Animatium
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

package org.visuals.legacy.animatium.mixins.v1.general.camera.view_bobbing;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
//? if 1.21.11 {
/*import net.minecraft.client.entity.ClientAvatarState;
*///?}
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.GameRenderer;
//? if >=26.1 {
import net.minecraft.client.renderer.state.level.CameraEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
//?}
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
//? if 1.21.11 {
/*import net.minecraft.world.entity.LivingEntity;
*///?}
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer_ModifyViewBobbing {
    @Shadow
    @Final
    private Minecraft minecraft;

    //? if 1.21.11 {
    /*@WrapOperation(method = "bobHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getHurtDir()F"))
    private float animatium$damageTilt(final LivingEntity instance, final Operation<Float> original) {
    *///?} else {
    @WrapOperation(method = "bobHurt", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/state/level/CameraEntityRenderState;hurtDir:F", opcode = Opcodes.GETFIELD))
    private float animatium$damageTilt(final CameraEntityRenderState instance, final Operation<Float> original) {
    //?}
        if (Animatium.isEnabled() && AnimatiumConfig.instance().movement.legacyDamageTilt) {
            return 0.0F;
        } else {
            return original.call(instance);
        }
    }

    //? if 1.21.11 {
    /*@WrapWithCondition(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lcom/mojang/blaze3d/vertex/PoseStack;F)V"))
    private boolean animatium$minimalViewBobbing(final GameRenderer instance, final PoseStack poseStack, final float tickDelta) {
    *///?} else {
    @WrapWithCondition(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lnet/minecraft/client/renderer/state/level/CameraRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;)V"))
    private boolean animatium$minimalViewBobbing(final GameRenderer instance, final CameraRenderState cameraState, final PoseStack poseStack) {
    //?}
        return !Animatium.isEnabled() || !AnimatiumConfig.instance().extras.minimalViewBobbing;
    }

    //? if 1.21.11 {
    /*@WrapOperation(method = "bobHurt", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/LivingEntity;hurtTime:I", opcode = Opcodes.GETFIELD))
    private int animatium$offsetHurtTime(final LivingEntity instance, final Operation<Integer> original) {
        final int hurtTime = original.call(instance);
    *///?} else {
    @WrapOperation(method = "bobHurt", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/state/level/CameraEntityRenderState;hurtTime:F", opcode = Opcodes.GETFIELD))
    private float animatium$offsetHurtTime(final CameraEntityRenderState instance, final Operation<Float> original) {
        final float hurtTime = original.call(instance);
    //?}
        if (Animatium.isEnabled() && AnimatiumConfig.instance().movement.offsetHurtTiltTime) {
            return Math.max(hurtTime - 1, 0);
        } else {
            return hurtTime;
        }
    }

    @Inject(method = "bobView", at = @At("TAIL"))
    //? if 1.21.11 {
    /*private void animatium$fixVerticalBobbingTilt(final PoseStack poseStack, final float tickDelta, final CallbackInfo ci) {
    *///?} else {
    private void animatium$fixVerticalBobbingTilt(final CameraRenderState cameraState, final PoseStack poseStack, final CallbackInfo ci) {
    //?}
        if (Animatium.isEnabled() && AnimatiumConfig.instance().fixes.fixVerticalBobbingTilt && this.minecraft.getCameraEntity() instanceof AbstractClientPlayer player) {
            //? if 1.21.11 {
            /*final float fallDist = Mth.lerp(tickDelta, player.animatium$getPreviousBobbingTilt(), player.animatium$getBobbingTilt());
            *///?} else {
            final float fallDist = Mth.lerp(cameraState.animatium$getPartialTickTime(), player.animatium$getPreviousBobbingTilt(), player.animatium$getBobbingTilt());
            //?}
            //? if <26.3 {
            /*poseStack.mulPose(Axis.XP.rotationDegrees(fallDist));
            *///?} else {
            poseStack.rotate(Axis.XP.rotationDegrees(fallDist));
            //?}
        }
    }

    //? if 1.21.11 {
    /*@WrapOperation(method = "bobView", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/ClientAvatarState;getBackwardsInterpolatedWalkDistance(F)F"))
    private float animatium$viewBobbing$changeDistance(final ClientAvatarState instance, final float tickDelta, final Operation<Float> original) {
    *///?} else {
    @WrapOperation(method = "bobView", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/state/level/CameraEntityRenderState;backwardsInterpolatedWalkDistance:F", opcode = Opcodes.GETFIELD))
    private float animatium$viewBobbing$changeDistance(final CameraEntityRenderState instance, final Operation<Float> original) {
    //?}
        final Entity bobbingStorage = this.minecraft.getCameraEntity();
        if (Animatium.isEnabled() && AnimatiumConfig.instance().movement.handViewBobbingMovement && bobbingStorage != null) {
            final float walkDist = bobbingStorage.animatium$getHorizontalSpeed();
            final float walkDistO = bobbingStorage.animatium$getPreviousHorizontalSpeed();
            //? if 1.21.11 {
            /*return -(walkDist + (walkDist - walkDistO) * tickDelta);
            *///?} else {
            return -(walkDist + (walkDist - walkDistO) * Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(true));
            //?}
        } else {
            //? if 1.21.11 {
            /*return original.call(instance, tickDelta);
            *///?} else {
            return original.call(instance);
            //?}
        }
    }

    // TODO/MOVE
    //? if 1.21.11 {
    /*@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlobalSettingsUniform;update(IIDJLnet/minecraft/client/DeltaTracker;ILnet/minecraft/client/Camera;Z)V"), index = 2)
    *///?} elif >=26.1 <26.3 {
    /*@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlobalSettingsUniform;update(IIDJLnet/minecraft/client/DeltaTracker;ILnet/minecraft/world/phys/Vec3;Z)V"), index = 2)
    *///?} else {
    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlobalSettingsUniform;update(IIDJFILnet/minecraft/world/phys/Vec3;Z)V"), index = 2)
    //?}
    private double animatium$forceMaxGlintStrength(final double original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.maxGlintProperties) {
            // 100% glint strength
            return 1.0F;
        } else {
            return original;
        }
    }
}
