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

package org.polyfrost.overflowanimations.mixins.v1.rendering.blocks;

// Targets the block model system added in 1.21.5
//? if >=1.21.5 {
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
//? if <26.1 {
/*import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.resources.model.WeightedVariants;
*///?} else {
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.WeightedVariants;
//?}
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

import java.util.List;

@Mixin(WeightedVariants.Unbaked.class)
public abstract class MixinWeightedVariantsUnbaked_DisableRandomBlockRotations {
    //? if <26.1 {
    /*@WrapOperation(method = "bake", at = @At(value = "NEW", target = "(Lnet/minecraft/util/random/WeightedList;)Lnet/minecraft/client/resources/model/WeightedVariants;"))
    *///?} else {
    @WrapOperation(method = "bake", at = @At(value = "NEW", target = "(Lnet/minecraft/util/random/WeightedList;)Lnet/minecraft/client/renderer/block/dispatch/WeightedVariants;"))
    //?}
    private WeightedVariants overflowanimations$disableRandomBlockRotations(final WeightedList<?> list, final Operation<WeightedVariants> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.disableRandomBlockRotations) {
            final List<? extends Weighted<?>> items = list.unwrap();
            return new WeightedVariants(WeightedList.of(List.of((Weighted<BlockStateModel>) items.getFirst())));
        } else {
            return original.call(list);
        }
    }
}
//?}
