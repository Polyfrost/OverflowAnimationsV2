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

package org.polyfrost.overflowanimations.mixins.legacy.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Blocks;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.render.block.BlockRenderDispatcher;
import net.minecraft.client.render.model.block.BakedModel;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.legacy.entity.FastGrassModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockRenderDispatcher.class)
public abstract class MixinBlockRenderDispatcher_BlockModels {
    @ModifyExpressionValue(method = "getModel(Lnet/minecraft/block/state/BlockState;Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/client/render/model/block/BakedModel;", at = @At(value = "FIELD", target = "Lnet/minecraft/client/options/GameOptions;allowBlockAlternatives:Z"))
    private boolean overflowanimations$disableRandomBlockRotations(final boolean original) {
        return original && !OverflowAnimationsConfig.instance().other.disableRandomBlockRotations;
    }

    @ModifyReturnValue(method = "getModel(Lnet/minecraft/block/state/BlockState;Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/client/render/model/block/BakedModel;", at = @At("RETURN"))
    private BakedModel overflowanimations$fastGrass(final BakedModel model, @Local(argsOnly = true) final BlockState state) {
        return OverflowAnimationsConfig.instance().other.fastGrass && state.getBlock() == Blocks.GRASS ? FastGrassModel.of(model) : model;
    }
}
