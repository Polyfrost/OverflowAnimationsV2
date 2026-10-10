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

package org.polyfrost.overflowanimations.mixins.legacy.firstperson;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.game.inventory.InventoryMenuScreen;
import net.minecraft.client.gui.screen.game.inventory.PlayerInventoryScreen;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.category.ItemsConfigCategory;
import org.polyfrost.overflowanimations.legacy.firstperson.LegacyFirstPerson;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity_DropSwing {
    @Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/player/PlayerEntity;getEyeHeight()F"))
    private void overflowanimations$dropItemSwing(final ItemStack stack, final boolean velocityFromPlayerDirection, final boolean thrownByPlayer, final CallbackInfoReturnable<ItemEntity> cir) {
        final PlayerEntity self = (PlayerEntity) (Object) this;
        final ItemsConfigCategory items = LegacyFirstPerson.items();
        if (!OverflowAnimations.isEnabled() || items.dropSwing.isLegacy() || !self.world.isClient || self != Minecraft.getInstance().player) {
            return;
        }
        final Screen screen = Minecraft.getInstance().screen;
        if (items.disableDropSwingInContainers && screen instanceof InventoryMenuScreen && !(screen instanceof PlayerInventoryScreen)) {
            return;
        }
        LegacyFirstPerson.fakeSwing(self);
    }
}
