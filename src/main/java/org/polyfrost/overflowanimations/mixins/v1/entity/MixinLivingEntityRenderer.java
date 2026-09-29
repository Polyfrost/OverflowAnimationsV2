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

package org.polyfrost.overflowanimations.mixins.v1.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
//? if <1.21.9 {
/*import net.minecraft.client.renderer.MultiBufferSource;
*///?} else {
import net.minecraft.client.renderer.SubmitNodeCollector;
//?}
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
//? if >=1.21.9 <26.1 {
/*import net.minecraft.client.renderer.state.CameraRenderState;
*///?} elif >=26.1 {
import net.minecraft.client.renderer.state.level.CameraRenderState;
//?}
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.util.CameraUtilKt;
import org.polyfrost.overflowanimations.util.EntityUtilKt;
import org.polyfrost.overflowanimations.util.enums.SneakAnimationSetting;

@Mixin(LivingEntityRenderer.class)
public abstract class MixinLivingEntityRenderer<S extends LivingEntityRenderState> {
    // TODO/MOVE
    //? if <1.21.9 {
    /*@Inject(method = "render(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", ordinal = 1))
    private void overflowanimations$syncPlayerModelWithEyeHeight(final S livingEntityRenderState, final PoseStack poseStack, final MultiBufferSource bufferSource, final int packedLight, final CallbackInfo ci) {
    *///?} elif <26.1 {
    /*@Inject(method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", ordinal = 1))
    *///?} else {
    @Inject(method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", ordinal = 1))
    //?}
    //? if >=1.21.9 {
    private void overflowanimations$syncPlayerModelWithEyeHeight(final S livingEntityRenderState, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final CameraRenderState cameraRenderState, final CallbackInfo ci) {
    //?}
        if (OverflowAnimations.isEnabled()
                && OverflowAnimationsConfig.instance().movement.sneakAnimation == SneakAnimationSetting.V1_7
                && livingEntityRenderState instanceof ArmedEntityRenderState armedEntityRenderState
                && EntityUtilKt.isSelf(armedEntityRenderState)
                && !livingEntityRenderState.hasPose(Pose.SWIMMING) /* Disable Crawling/Swimming as it's wrong */
                //? if <26.2 {
                /*&& (Minecraft.getInstance().screen == null /^ Disable when in inventory/not in-game ^/)) {
                *///?} else {
                && (Minecraft.getInstance().gui.screen() == null /* Disable when in inventory/not in-game */)) {
                //?}
            final EntityDimensions standingDimensions = armedEntityRenderState.overflowanimations$getStandingDimensions();
            if (standingDimensions != null) {
                //? if <1.21.9 {
                /*final float cameraLerpValue = CameraUtilKt.getPositionLerped(Minecraft.getInstance().gameRenderer.getMainCamera());
                *///?} else {
                final float cameraLerpValue = CameraUtilKt.getPositionLerped(cameraRenderState);
                //?}
                poseStack.translate(0.0F, (standingDimensions.eyeHeight() * livingEntityRenderState.scale) - cameraLerpValue, 0.0F);
            }
        }
    }

    @ModifyExpressionValue(method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isAlive()Z"))
    private boolean overflowanimations$deathLimbs(final boolean original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().movement.deathLimbs) {
            return true;
        } else {
            return original;
        }
    }

    //? if <1.21.9 {
    /*@WrapMethod(method = "render(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    private void overflowanimations$disableModelWhilstSleeping(final S state, final PoseStack poseStack, final MultiBufferSource bufferSource, final int packedLight, final Operation<Void> original) {
    *///?} elif <26.1 {
    /*@WrapMethod(method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V")
    *///?} else {
    @WrapMethod(method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V")
    //?}
    //? if >=1.21.9 {
    private void overflowanimations$disableModelWhilstSleeping(final S state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final CameraRenderState camera, final Operation<Void> original) {
    //?}
        if (OverflowAnimations.isEnabled()
                && OverflowAnimationsConfig.instance().other.disableModelWhilstSleeping
                && state instanceof ArmedEntityRenderState armedEntityRenderState
                && EntityUtilKt.isSelf(armedEntityRenderState)
                && armedEntityRenderState.hasPose(Pose.SLEEPING)
                && armedEntityRenderState.overflowanimations$isSleeping()) {
            return;
        }

        //? if <1.21.9 {
        /*original.call(state, poseStack, bufferSource, packedLight);
        *///?} else {
        original.call(state, poseStack, submitNodeCollector, camera);
        //?}
    }
}
