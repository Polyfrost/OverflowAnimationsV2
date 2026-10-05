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

//? if >=1.21.11 <26.3 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SwingAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.util.duck.SwingLockExt;

@Mixin(ArmedEntityRenderState.class)
public abstract class MixinArmedEntityRenderState_SwingLock {
    @WrapOperation(method = "extractArmedEntityRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getSwingAnimation()Lnet/minecraft/world/item/component/SwingAnimation;"))
    private static SwingAnimation overflowanimations$lockedSwingAnimation(final ItemStack instance, final Operation<SwingAnimation> original, @Local(argsOnly = true) final LivingEntity entity) {
        return ((SwingLockExt) entity).overflowanimations$swingAnimation(original.call(instance));
    }
}
*///?}
