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

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.LivingEntity;
//? if >=26.3 {
import net.minecraft.world.item.component.SwingAnimation;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.polyfrost.overflowanimations.util.SwingUtilKt;

// TODO/FIX: Should not affect swing code, only visual, currently matches Legacy Animatium tho
@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity_ItemSwing {
    //? if <26.3 {
    /*@WrapMethod(method = "getCurrentSwingDuration")
    private int overflowanimations$customSwingAnimationSpeed(final Operation<Integer> original) {
        return SwingUtilKt.getItemSwingSpeed((LivingEntity) (Object) this, original.call());
    *///?} else {
    @WrapMethod(method = "getModifiedSwingDuration")
    private int overflowanimations$customSwingAnimationSpeed(final SwingAnimation animation, final Operation<Integer> original) {
        return SwingUtilKt.getItemSwingSpeed((LivingEntity) (Object) this, animation, original.call(animation));
    //?}
    }
}
