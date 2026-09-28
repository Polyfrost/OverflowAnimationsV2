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

package org.polyfrost.overflowanimations.mixins.v1.rendering.items.flat;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.state.ItemEntityRenderState;
//? if 1.21.11 {
/*import net.minecraft.client.renderer.state.CameraRenderState;
*///?} else {
import net.minecraft.client.renderer.state.level.CameraRenderState;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.util.UtilsKt;

@Mixin(ItemEntityRenderer.class)
public abstract class MixinItemEntityRenderer {
    //? if 1.21.11 {
    /*@WrapOperation(method = "submit(Lnet/minecraft/client/renderer/entity/state/ItemEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;getSpin(FF)F"))
    private float overflowanimations$itemDropsFaceCamera(final float ageInTicks, final float bobOffset, final Operation<Float> original, @Local(argsOnly = true, ordinal = 0) final ItemEntityRenderState state, @Local(argsOnly = true, ordinal = 0) final CameraRenderState camera) {
    *///?} else {
    @WrapOperation(method = "submit(Lnet/minecraft/client/renderer/entity/state/ItemEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;getSpin(FF)F"))
    private float overflowanimations$itemDropsFaceCamera(final float ageInTicks, final float bobOffset, final Operation<Float> original, @Local(argsOnly = true, name = "state") final ItemEntityRenderState state, @Local(argsOnly = true, name = "camera") final CameraRenderState camera) {
    //?}
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.itemDropsFaceCamera && !state.item.usesBlockLight()) {
            return UtilsKt.toRadians(180.0F - camera.overflowanimations$getYRot());
        } else {
            return original.call(ageInTicks, bobOffset);
        }
    }

    //? if 1.21.11 {
    /*@Inject(method = "submit(Lnet/minecraft/client/renderer/entity/state/ItemEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lorg/joml/Quaternionfc;)V", shift = At.Shift.AFTER))
    *///?} elif >=26.1 <26.3 {
    /*@Inject(method = "submit(Lnet/minecraft/client/renderer/entity/state/ItemEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lorg/joml/Quaternionfc;)V", shift = At.Shift.AFTER))
    *///?} else {
    @Inject(method = "submit(Lnet/minecraft/client/renderer/entity/state/ItemEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;rotate(Lcom/mojang/math/Axis;F)V", shift = At.Shift.AFTER))
    //?}
    private void overflowanimations$fixItemDrops2dRotation(final ItemEntityRenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final CameraRenderState camera, final CallbackInfo ci) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.itemDropsFaceCamera && OverflowAnimationsConfig.instance().items.itemDropsFaceCameraRotationFix && !state.item.usesBlockLight()) {
            //? if <26.3 {
            /*poseStack.mulPose(Axis.XP.rotationDegrees(-camera.overflowanimations$getXRot()));
            *///?} else {
            poseStack.rotate(Axis.XP.rotationDegrees(-camera.overflowanimations$getXRot()));
            //?}
        }
    }
}
