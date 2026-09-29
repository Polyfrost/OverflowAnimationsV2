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

package org.polyfrost.overflowanimations.mixins.v1.entity.armor_hurt;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.moulberry.mixinconstraints.annotations.IfModAbsent;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.object.skull.SkullModelBase;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
//? if <1.21.9 {
/*import net.minecraft.client.renderer.MultiBufferSource;
*///?} else {
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
//?}
import net.minecraft.client.renderer.rendertype.RenderType;
//? if <26.1 {
/*import net.minecraft.core.Direction;
*///?}
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

@IfModAbsent("ichor")
@Mixin(CustomHeadLayer.class)
public abstract class MixinCustomHeadLayer_DamageTintArmor<S extends LivingEntityRenderState, M extends EntityModel<S> & HeadedModel> {
    //? if <1.21.9 {
    /*@WrapOperation(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/SkullBlockRenderer;renderSkull(Lnet/minecraft/core/Direction;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/model/object/skull/SkullModelBase;Lnet/minecraft/client/renderer/rendertype/RenderType;)V"))
    private void overflowanimations$damageTintArmor$skullOverride(final Direction direction, final float rotation, final float animationValue, final PoseStack poseStack, final MultiBufferSource bufferSource, final int lightCoords, final SkullModelBase model, final RenderType renderType, final Operation<Void> original, @Local(argsOnly = true, ordinal = 0) final S state) {
    *///?} elif <26.1 {
    /*@WrapOperation(method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/SkullBlockRenderer;submitSkull(Lnet/minecraft/core/Direction;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/model/object/skull/SkullModelBase;Lnet/minecraft/client/renderer/rendertype/RenderType;ILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V"))
    private void overflowanimations$damageTintArmor$skullOverride(final Direction direction, final float rotation, final float animationValue, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords, final SkullModelBase model, final RenderType renderType, final int outlineColor, final ModelFeatureRenderer.CrumblingOverlay breakProgress, final Operation<Void> original, @Local(argsOnly = true, ordinal = 0) final S state) {
    *///?} else {
    @WrapOperation(method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/SkullBlockRenderer;submitSkull(FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/model/object/skull/SkullModelBase;Lnet/minecraft/client/renderer/rendertype/RenderType;ILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V"))
    private void overflowanimations$damageTintArmor$skullOverride(final float animationValue, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords, final SkullModelBase model, final RenderType renderType, final int outlineColor, final ModelFeatureRenderer.CrumblingOverlay breakProgress, final Operation<Void> original, @Local(argsOnly = true, name = "state") final S state) {
    //?}
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.damageTintArmor) {
            //? if <26.1 {
            /*poseStack.pushPose();
            if (direction == null) {
                poseStack.translate(0.5F, 0.0F, 0.5F);
            } else {
                poseStack.translate(0.5F - (float) direction.getStepX() * 0.25F, 0.25F, 0.5F - (float) direction.getStepZ() * 0.25F);
            }
            *///?}

            //? if <26.1 {
            /*poseStack.scale(-1.0F, -1.0F, 1.0F);
            *///?}
            //? if <1.21.9 {
            /*model.setupAnim(animationValue, rotation, 0.0F);
            model.renderToBuffer(poseStack, bufferSource.getBuffer(renderType), lightCoords, LivingEntityRenderer.getOverlayCoords(state, 0.0F));
            *///?} else {
            final SkullModelBase.State modelState = new SkullModelBase.State();
            modelState.animationPos = animationValue;
            //?}
            //? if >=1.21.9 <26.1 {
            /*modelState.yRot = rotation;
            *///?}
            //? if >=1.21.9 <26.3 {
            /*submitNodeCollector.submitModel(model, modelState, poseStack, renderType, lightCoords, LivingEntityRenderer.getOverlayCoords(state, 0.0F), outlineColor, breakProgress);
            *///?}
            //? if <26.1 {
            /*poseStack.popPose();
            *///?} elif >=26.3 {
            submitNodeCollector.submitModel(model, modelState, poseStack, renderType, lightCoords, LivingEntityRenderer.getOverlayCoords(state, 0.0F), -1, null, outlineColor);
            //?}
        } else {
            //? if <1.21.9 {
            /*original.call(direction, rotation, animationValue, poseStack, bufferSource, lightCoords, model, renderType);
            *///?} elif <26.1 {
            /*original.call(direction, rotation, animationValue, poseStack, submitNodeCollector, lightCoords, model, renderType, outlineColor, breakProgress);
            *///?} else {
            original.call(animationValue, poseStack, submitNodeCollector, lightCoords, model, renderType, outlineColor, breakProgress);
            //?}
        }
    }

    //? if <1.21.9 {
    /*@ModifyExpressionValue(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;FF)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/texture/OverlayTexture;NO_OVERLAY:I", opcode = Opcodes.GETSTATIC))
    *///?} else {
    @ModifyExpressionValue(method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;FF)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/texture/OverlayTexture;NO_OVERLAY:I", opcode = Opcodes.GETSTATIC))
    //?}
    private int overflowanimations$damageTintArmor(final int original, @Local(argsOnly = true, ordinal = 0) final S state) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.damageTintArmor) {
            return LivingEntityRenderer.getOverlayCoords(state, 0.0F);
        } else {
            return original;
        }
    }
}
