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

package org.polyfrost.overflowanimations.mixins.v1.entity.items.drop_swing;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
//? if <1.21.5
//import net.minecraft.world.entity.player.Player;
//? if >=26.3 {
import net.minecraft.world.item.component.SwingAnimation;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.util.SwingUtilKt;

// Before 1.21.5 drop(ItemStack, boolean, boolean) lives in Player
//? if <1.21.5 {
/*@Mixin(Player.class)
*///?} else {
@Mixin(LivingEntity.class)
//?}
public abstract class MixinLivingEntity {
    //? if <1.21.5 {
    /*@WrapOperation(method = "drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;swing(Lnet/minecraft/world/InteractionHand;)V"))
    private void overflowanimations$swingOnDropInventory(final Player instance, final InteractionHand hand, final Operation<Void> original) {
    *///?} elif <26.3 {
    /*@WrapOperation(method = "drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;swing(Lnet/minecraft/world/InteractionHand;)V"))
    private void overflowanimations$swingOnDropInventory(final LivingEntity instance, final InteractionHand hand, final Operation<Void> original) {
    *///?} else {
    @WrapOperation(method = "drop", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;swing(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/component/SwingAnimation;Z)Z"))
    private boolean overflowanimations$swingOnDropInventory(final LivingEntity instance, final InteractionHand hand, final SwingAnimation animation, final boolean sendToSwingingEntity, final Operation<Boolean> original) {
    //?}
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.disableSwingOnDrop && instance instanceof LocalPlayer localPlayer) {
            //? if <26.3 {
            /*SwingUtilKt.sendSwingPacket(localPlayer, hand);
            *///?} else {
            return SwingUtilKt.sendSwingPacket(localPlayer, hand, animation);
            //?}
        } else {
            //? if <26.3 {
            /*original.call(instance, hand);
            *///?} else {
            return original.call(instance, hand, animation, sendToSwingingEntity);
            //?}
        }
    }
}
