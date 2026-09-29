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

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
//? if <1.21.9 {
/*import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
*///?} else {
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
//?}
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

@Mixin(PlayerItemInHandLayer.class)
//? if <1.21.9 {
/*public abstract class MixinPlayerItemInHandLayer_DamageTintArmor<S extends PlayerRenderState, M extends EntityModel<S> & ArmedModel & HeadedModel> {
    // renderItemHeldToEye has no state parameter before 1.21.9, so capture it from the caller.
    @Unique
    private LivingEntityRenderState overflowanimations$state;

    @Inject(method = "renderArmWithItem(Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;Lnet/minecraft/client/renderer/item/ItemStackRenderState;Lnet/minecraft/world/entity/HumanoidArm;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"))
    private void overflowanimations$captureState(final S state, final ItemStackRenderState item, final HumanoidArm arm, final PoseStack poseStack, final MultiBufferSource bufferSource, final int packedLight, final CallbackInfo ci) {
        this.overflowanimations$state = state;
    }

    @ModifyExpressionValue(method = "renderItemHeldToEye", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/texture/OverlayTexture;NO_OVERLAY:I", opcode = Opcodes.GETSTATIC))
    private int overflowanimations$damageTintItems(final int original) {
        final LivingEntityRenderState state = this.overflowanimations$state;
*///?} else {
public abstract class MixinPlayerItemInHandLayer_DamageTintArmor<S extends AvatarRenderState, M extends EntityModel<S> & ArmedModel<S> & HeadedModel> {
    @ModifyExpressionValue(method = "renderItemHeldToEye", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/texture/OverlayTexture;NO_OVERLAY:I", opcode = Opcodes.GETSTATIC))
    private int overflowanimations$damageTintItems(final int original, @Local(argsOnly = true, ordinal = 0) final S state) {
//?}
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.damageTintItems) {
            return LivingEntityRenderer.getOverlayCoords(state, 0.0F);
        } else {
            return original;
        }
    }
}
