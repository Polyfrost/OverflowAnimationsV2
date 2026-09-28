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

//? if <26.2 {
/*import com.mojang.blaze3d.vertex.VertexConsumer;
*///?}
import net.minecraft.client.model.player.PlayerCapeModel;
//? if <26.2 {
/*import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.SubmitNodeStorage;
*///?}
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
//? if <26.2 {
/*import net.minecraft.client.renderer.rendertype.RenderType;
*///?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

@Mixin(ModelFeatureRenderer.class)
public abstract class MixinModelFeatureRenderer_CapeSwinging {
    //? if <26.2 {
    /*@Inject(method = "renderModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/Model;setupAnim(Ljava/lang/Object;)V", shift = At.Shift.AFTER))
    private <S> void overflowanimations$capeSwingRotation(final SubmitNodeStorage.ModelSubmit<S> submit, final RenderType renderType, final VertexConsumer buffer, final OutlineBufferSource outlineBufferSource, final MultiBufferSource.BufferSource crumblingBufferSource, final CallbackInfo ci) {
    *///?} else {
    @Inject(method = "prepareModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/Model;setupAnim(Ljava/lang/Object;)V", shift = At.Shift.AFTER))
    private <S> void overflowanimations$capeSwingRotation(final ModelFeatureRenderer.Submit<S> submit, final CallbackInfo ci) {
    //?}
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().movement.disableCapeSwingRotation && submit.model() instanceof PlayerCapeModel playerCapeModel) {
            playerCapeModel.body.yRot = 0;
        }
    }
}
