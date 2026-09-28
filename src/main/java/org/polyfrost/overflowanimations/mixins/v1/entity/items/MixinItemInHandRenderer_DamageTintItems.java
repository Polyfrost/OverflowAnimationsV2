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
/*import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
*///?}

//? if <26.3 {
/*@Mixin(ItemInHandRenderer.class)
public abstract class MixinItemInHandRenderer_DamageTintItems {
    @ModifyExpressionValue(method = "renderItem", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/texture/OverlayTexture;NO_OVERLAY:I", opcode = Opcodes.GETSTATIC))
*///?}
    //? if 1.21.11 {
    /*private int overflowanimations$damageTintItems(final int original, @Local(argsOnly = true, ordinal = 0) final LivingEntity mob) {
    *///?} elif >=26.1 <26.3 {
    /*private int overflowanimations$damageTintItems(final int original, @Local(argsOnly = true, name = "mob") final LivingEntity mob) {
    *///?}
//? if <26.3 {
        /*if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.damageTintItems) {
            return OverlayTexture.pack(0, OverlayTexture.v(mob.hurtTime > 0 || mob.deathTime > 0));
        } else {
            return original;
        }
    }
}
*///?}
