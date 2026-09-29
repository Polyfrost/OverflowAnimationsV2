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

package org.polyfrost.overflowanimations.mixins.v1.entity.cape.movement;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.vertex.PoseStack;
//? if <1.21.9 {
/*import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
*///?} else {
import net.minecraft.client.renderer.SubmitNodeCollector;
//?}
import net.minecraft.client.renderer.entity.layers.CapeLayer;
//? if <1.21.9 {
/*import net.minecraft.client.renderer.entity.state.PlayerRenderState;
*///?} else {
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
//?}
//? if <1.21.9
//import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
//? if <1.21.9
//import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

@Mixin(CapeLayer.class)
public abstract class MixinCapeLayer_Position {
    //? if <1.21.9 {
    /*@Shadow
    @Final
    private HumanoidModel<PlayerRenderState> model;

    @ModifyExpressionValue(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/PlayerRenderState;FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/CapeLayer;hasLayer(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;)Z", ordinal = 1))
    *///?} else {
    @ModifyExpressionValue(method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/AvatarRenderState;FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/CapeLayer;hasLayer(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;)Z", ordinal = 1))
    //?}
    private boolean overflowanimations$capeChestplateTranslation(final boolean original) {
        if (OverflowAnimations.isEnabled() && !OverflowAnimationsConfig.instance().movement.capeChestplateTranslation) {
            return false;
        } else {
            return original;
        }
    }

    //? if <1.21.9 {
    /*@Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/PlayerRenderState;FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource;getBuffer(Lnet/minecraft/client/renderer/rendertype/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    *///?} elif <26.3 {
    /*@Inject(method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/AvatarRenderState;FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IIILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V", shift = At.Shift.BEFORE))
    *///?} else {
    @Inject(method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/AvatarRenderState;FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;III)V", shift = At.Shift.BEFORE))
    //?}
    //? if <1.21.9 {
    /*private void overflowanimations$capeSneakPosition(final PoseStack poseStack, final MultiBufferSource bufferSource, final int lightCoords, final PlayerRenderState state, final float yRot, final float xRot, final CallbackInfo ci) {
    *///?} else {
    private void overflowanimations$capeSneakPosition(final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords, final AvatarRenderState state, final float yRot, final float xRot, final CallbackInfo ci) {
    //?}
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().movement.capeSneakPosition && state.isCrouching) {
            poseStack.translate(0.0F, state.scale * 2.0F / 16.0F, 0.0F);
        }
    }

    //? if <1.21.9 {
    /*@Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/PlayerRenderState;FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V", shift = At.Shift.AFTER))
    private void overflowanimations$capeSwingRotation(final PoseStack poseStack, final MultiBufferSource bufferSource, final int lightCoords, final PlayerRenderState state, final float yRot, final float xRot, final CallbackInfo ci) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().movement.disableCapeSwingRotation) {
            this.model.body.yRot = 0;
        }
    }
    *///?}
}
