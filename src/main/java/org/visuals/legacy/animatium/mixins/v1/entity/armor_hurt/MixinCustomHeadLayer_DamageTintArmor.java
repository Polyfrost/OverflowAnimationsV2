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

package org.visuals.legacy.animatium.mixins.v1.entity.armor_hurt;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.moulberry.mixinconstraints.annotations.IfModAbsent;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.object.skull.SkullModelBase;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
//? if 1.21.11 {
/*import net.minecraft.core.Direction;
*///?}
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@IfModAbsent("ichor")
@Mixin(CustomHeadLayer.class)
public abstract class MixinCustomHeadLayer_DamageTintArmor<S extends LivingEntityRenderState, M extends EntityModel<S> & HeadedModel> {
    //? if 1.21.11 {
    /*@WrapOperation(method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/SkullBlockRenderer;submitSkull(Lnet/minecraft/core/Direction;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/model/object/skull/SkullModelBase;Lnet/minecraft/client/renderer/rendertype/RenderType;ILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V"))
    private void animatium$damageTintArmor$skullOverride(final Direction direction, final float rotation, final float animationValue, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords, final SkullModelBase model, final RenderType renderType, final int outlineColor, final ModelFeatureRenderer.CrumblingOverlay breakProgress, final Operation<Void> original, @Local(argsOnly = true, ordinal = 0) final S state) {
    *///?} else {
    @WrapOperation(method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/SkullBlockRenderer;submitSkull(FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/model/object/skull/SkullModelBase;Lnet/minecraft/client/renderer/rendertype/RenderType;ILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V"))
    private void animatium$damageTintArmor$skullOverride(final float animationValue, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords, final SkullModelBase model, final RenderType renderType, final int outlineColor, final ModelFeatureRenderer.CrumblingOverlay breakProgress, final Operation<Void> original, @Local(argsOnly = true, name = "state") final S state) {
    //?}
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.damageTintArmor) {
            //? if 1.21.11 {
            /*poseStack.pushPose();
            if (direction == null) {
                poseStack.translate(0.5F, 0.0F, 0.5F);
            } else {
                poseStack.translate(0.5F - (float) direction.getStepX() * 0.25F, 0.25F, 0.5F - (float) direction.getStepZ() * 0.25F);
            }
            *///?}

            //? if 1.21.11 {
            /*poseStack.scale(-1.0F, -1.0F, 1.0F);
            *///?}
            final SkullModelBase.State modelState = new SkullModelBase.State();
            modelState.animationPos = animationValue;
            //? if 1.21.11 {
            /*modelState.yRot = rotation;
            *///?}
            //? if <26.3 {
            /*submitNodeCollector.submitModel(model, modelState, poseStack, renderType, lightCoords, LivingEntityRenderer.getOverlayCoords(state, 0.0F), outlineColor, breakProgress);
            *///?}
            //? if 1.21.11 {
            /*poseStack.popPose();
            *///?} elif >=26.3 {
            submitNodeCollector.submitModel(model, modelState, poseStack, renderType, lightCoords, LivingEntityRenderer.getOverlayCoords(state, 0.0F), -1, null, outlineColor);
            //?}
        } else {
            //? if 1.21.11 {
            /*original.call(direction, rotation, animationValue, poseStack, submitNodeCollector, lightCoords, model, renderType, outlineColor, breakProgress);
            *///?} else {
            original.call(animationValue, poseStack, submitNodeCollector, lightCoords, model, renderType, outlineColor, breakProgress);
            //?}
        }
    }

    @ModifyExpressionValue(method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;FF)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/texture/OverlayTexture;NO_OVERLAY:I", opcode = Opcodes.GETSTATIC))
    private int animatium$damageTintArmor(final int original, @Local(argsOnly = true, ordinal = 0) final S state) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.damageTintArmor) {
            return LivingEntityRenderer.getOverlayCoords(state, 0.0F);
        } else {
            return original;
        }
    }
}
