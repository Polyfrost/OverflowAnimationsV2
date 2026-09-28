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

package org.polyfrost.overflowanimations.mixins.v1.gui.screen_tweaks;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.mixins.accessor.SlotAccessor;

// TODO: Move recipe book button
@Mixin(InventoryMenu.class)
public abstract class MixinInventoryMenu_OldCraftingSlots {
    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/InventoryMenu;addCraftingGridSlots(II)V"))
    private void overflowanimations$modifyCraftingSlotsPosition(final InventoryMenu instance, final int x, final int y, final Operation<Void> original) {
        int newX = x, newY = y;
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.oldCraftingSlotsPosition) {
            newX -= 10;
            newY += 8;
        }

        original.call(instance, newX, newY);
    }

    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/InventoryMenu;addResultSlot(Lnet/minecraft/world/entity/player/Player;II)Lnet/minecraft/world/inventory/Slot;"))
    private Slot overflowanimations$modifyCraftingResultSlotPosition(final InventoryMenu instance, final Player player, final int x, final int y, final Operation<Slot> original) {
        int newX = x, newY = y;
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.oldCraftingSlotsPosition) {
            newX -= 10;
            newY += 8;
        }

        return original.call(instance, player, newX, newY);
    }

    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/InventoryMenu;addSlot(Lnet/minecraft/world/inventory/Slot;)Lnet/minecraft/world/inventory/Slot;", ordinal = 1))
    private Slot overflowanimations$moveOffhandSlot(final InventoryMenu instance, final Slot slot, final Operation<Slot> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.oldCraftingSlotsPosition) {
            ((SlotAccessor) slot).overflowanimations$setX(slot.x + 75);
        }

        return original.call(instance, slot);
    }
}