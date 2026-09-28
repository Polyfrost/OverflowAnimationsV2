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

//? if <26.3 {
/*import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
*///?}

//? if <26.3 {
/*@Mixin(ItemInHandRenderer.class)
public abstract class MixinItemInHandRenderer_HeldItemArmLogic {
    @Inject(method = "renderPlayerArm", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/ClientAsset$Texture;texturePath()Lnet/minecraft/resources/Identifier;", shift = At.Shift.AFTER))
*///?}
    //? if 1.21.11 {
    /*private void overflowanimations$extractArmState(final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords, final float inverseArmHeight, final float attackValue, final HumanoidArm arm, final CallbackInfo ci, @Local(ordinal = 0) final AvatarRenderer<AbstractClientPlayer> avatarRenderer) {
    *///?} elif >=26.1 <26.3 {
    /*private void overflowanimations$extractArmState(final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final int lightCoords, final float inverseArmHeight, final float attackValue, final HumanoidArm arm, final CallbackInfo ci, @Local(name = "avatarRenderer") final AvatarRenderer<AbstractClientPlayer> avatarRenderer) {
    *///?}
//? if <26.3 {
        /*final Minecraft minecraft = Minecraft.getInstance();
        if (OverflowAnimations.isEnabled() && (OverflowAnimationsConfig.instance().other.heldItemArmLogic || OverflowAnimationsConfig.instance().other.damageTintItems) && minecraft.player != null) {
            avatarRenderer.extractRenderState(minecraft.player, avatarRenderer.createRenderState(), minecraft.getDeltaTracker().getGameTimeDeltaTicks());
        }
    }
}
*///?}
