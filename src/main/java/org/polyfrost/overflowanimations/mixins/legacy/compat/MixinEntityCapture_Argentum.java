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

package org.polyfrost.overflowanimations.mixins.legacy.compat;

import dev.rdh.argentum.impl.render.entity.instancing.EntityCapture;
import net.minecraft.client.render.entity.layer.AbstractArmorLayer;
import net.minecraft.client.render.model.block.BakedModel;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.item.ItemStack;
import org.polyfrost.overflowanimations.legacy.entity.LegacyItemRendering;
import org.polyfrost.overflowanimations.legacy.tint.LegacyGlint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(value = EntityCapture.class, remap = false)
public abstract class MixinEntityCapture_Argentum {
    @ModifyVariable(method = "recordItem", at = @At("HEAD"), argsOnly = true)
    private BakedModel overflowanimations$flatItems(final BakedModel model) {
        return LegacyItemRendering.flat ? LegacyItemRendering.flatModel(model) : model;
    }

    @Inject(method = "beginLayer", at = @At("HEAD"), cancellable = true)
    private void overflowanimations$modernArmorGlint(final Object layer, final LivingEntity entity, final CallbackInfoReturnable<Boolean> cir) {
        if (layer instanceof AbstractArmorLayer && LegacyGlint.replacesArmorGlint() && overflowanimations$enchantedArmor(entity)) {
            cir.setReturnValue(false);
        }
    }

    @Unique
    private static boolean overflowanimations$enchantedArmor(final LivingEntity entity) {
        for (int slot = 0; slot < 4; slot++) {
            final ItemStack armor = entity.getArmor(slot);
            if (armor != null && armor.hasEnchantmentGlint()) {
                return true;
            }
        }
        return false;
    }
}
