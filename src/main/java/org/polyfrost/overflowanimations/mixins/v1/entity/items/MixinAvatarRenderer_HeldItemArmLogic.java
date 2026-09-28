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

package org.polyfrost.overflowanimations.mixins.v1.entity.items;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
//? if <26.3 {
/*import net.minecraft.client.renderer.texture.TextureAtlasSprite;
*///?} else {
import net.minecraft.client.renderer.texture.UvMapping;
//?}
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.HumanoidArm;
import org.jspecify.annotations.NonNull;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

@Mixin(AvatarRenderer.class)
public abstract class MixinAvatarRenderer_HeldItemArmLogic<AvatarLikeEntity extends Avatar & ClientAvatarEntity> extends LivingEntityRenderer<@NonNull AvatarLikeEntity, AvatarRenderState, PlayerModel> {
    @Unique
    private final ThreadLocal<AvatarRenderState> overflowanimations$renderState = ThreadLocal.withInitial(() -> null);

    public MixinAvatarRenderer_HeldItemArmLogic(final EntityRendererProvider.Context context, final PlayerModel model, final float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("TAIL"))
    private void overflowanimations$storeAvatarState(final AvatarLikeEntity entity, final AvatarRenderState state, final float tickDelta, final CallbackInfo ci) {
        overflowanimations$renderState.set(state);
    }

    @Inject(method = "renderHand", at = @At(value = "FIELD", target = "Lnet/minecraft/client/model/geom/ModelPart;visible:Z", ordinal = 2, opcode = Opcodes.PUTFIELD))
    private void overflowanimations$heldItemArmLogic(final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords, final Identifier skinTexture, final ModelPart arm, final boolean hasSleeve, final CallbackInfo ci) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.heldItemArmLogic) {
            final HumanoidArm humanoidArm = arm == model.rightArm ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
            final AvatarRenderState avatarRenderState = overflowanimations$renderState.get();
            if (avatarRenderState != null && (avatarRenderState.mainArm == humanoidArm ? avatarRenderState.rightArmPose : avatarRenderState.leftArmPose) == HumanoidModel.ArmPose.ITEM) {
                // Adapted from the ITEM arm pose rotations in HumanoidModel#poseRightArm/poseLeftArm
                arm.xRot = arm.xRot * 0.5F - (float) (Math.PI / 10);
                arm.yRot = 0.0F;
            }
        }
    }

    //? if <26.3 {
    /*@WrapOperation(method = "renderHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModelPart(Lnet/minecraft/client/model/geom/ModelPart;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IILnet/minecraft/client/renderer/texture/TextureAtlasSprite;)V"))
    private void overflowanimations$damageTintArm(final SubmitNodeCollector instance, final ModelPart modelPart, final PoseStack poseStack, final RenderType renderType, final int packedLight, final int packedOverlay, final TextureAtlasSprite textureAtlasSprite, final Operation<Void> original) {
    *///?} else {
    @WrapOperation(method = "renderHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModelPart(Lnet/minecraft/client/model/geom/ModelPart;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IILnet/minecraft/client/renderer/texture/UvMapping;)V"))
    private void overflowanimations$damageTintArm(final SubmitNodeCollector instance, final ModelPart modelPart, final PoseStack poseStack, final RenderType renderType, final int packedLight, final int packedOverlay, final UvMapping uvMapping, final Operation<Void> original) {
    //?}
        final AvatarRenderState avatarRenderState = overflowanimations$renderState.get();

        int overlay = packedOverlay;
        if (OverflowAnimations.isEnabled() && avatarRenderState != null && OverflowAnimationsConfig.instance().other.damageTintItems) {
            overlay = LivingEntityRenderer.getOverlayCoords(avatarRenderState, 0.0F);
        }

        //? if <26.3 {
        /*original.call(instance, modelPart, poseStack, renderType, packedLight, overlay, textureAtlasSprite);
        *///?} else {
        original.call(instance, modelPart, poseStack, renderType, packedLight, overlay, uvMapping);
        //?}
    }
}
