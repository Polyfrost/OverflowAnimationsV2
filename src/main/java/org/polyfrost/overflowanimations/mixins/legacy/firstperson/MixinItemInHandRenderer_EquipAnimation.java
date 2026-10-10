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

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.ItemInHandRenderer;
import net.minecraft.entity.living.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.legacy.firstperson.LegacyFirstPerson;
import org.polyfrost.overflowanimations.util.enums.EquipAnimationVersionSetting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class MixinItemInHandRenderer_EquipAnimation {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private ItemStack itemInHand;

    @Shadow
    private int selectedSlot;

    @ModifyExpressionValue(method = "tick", at = @At(value = "CONSTANT", args = "floatValue=0.4F"))
    private float overflowanimations$reequipSpeed(final float original) {
        return OverflowAnimations.isEnabled() ? LegacyFirstPerson.items().reequipSpeed : original;
    }

    @ModifyVariable(method = "tick", at = @At("LOAD"))
    private boolean overflowanimations$equipLogic(final boolean reequip) {
        if (!OverflowAnimations.isEnabled()) {
            return reequip;
        }
        final EquipAnimationVersionSetting setting = LegacyFirstPerson.items().equipAnimationVersion;
        final PlayerInventory inventory = this.minecraft.player.inventory;
        final ItemStack stack = inventory.getSelectedItem();
        if (setting == EquipAnimationVersionSetting.DISABLED) {
            this.itemInHand = stack;
            this.selectedSlot = inventory.selectedSlot;
            return false;
        } else if (setting == EquipAnimationVersionSetting.V1_7) {
            boolean keep = this.selectedSlot == inventory.selectedSlot && stack == this.itemInHand;
            if (this.itemInHand == null && stack == null) {
                keep = true;
            }
            if (stack != null && this.itemInHand != null && stack != this.itemInHand
                    && stack.getItem() == this.itemInHand.getItem() && stack.getMetadata() == this.itemInHand.getMetadata()) {
                this.itemInHand = stack;
                keep = true;
            }
            return !keep;
        }
        return reequip;
    }

    @Inject(method = {"onBlockUsed", "onItemUsed"}, at = @At("HEAD"), cancellable = true)
    private void overflowanimations$disableEquipOnUse(final CallbackInfo ci) {
        if (OverflowAnimations.isEnabled() && LegacyFirstPerson.items().equipAnimationVersion == EquipAnimationVersionSetting.DISABLED) {
            ci.cancel();
        }
    }
}
