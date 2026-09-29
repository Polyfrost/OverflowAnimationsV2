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

// Extra block state models were added in 1.21.5
//? if >=1.21.5 {
//? if <26.1 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.OverflowAnimationsConstants;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
*///?}

//? if <26.1 {
/*@Mixin(BlockRenderDispatcher.class)
public abstract class MixinBlockRenderDispatcher_FastGrassSide {
    @Shadow
    @Final
    private BlockModelShaper blockModelShaper;
*///?}

//? if <26.1 {
    /*@WrapOperation(method = "getBlockModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/BlockModelShaper;getBlockModel(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/client/renderer/block/model/BlockStateModel;"))
    private BlockStateModel overflowanimations$fastGrass(final BlockModelShaper instance, final BlockState state, final Operation<BlockStateModel> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.fastGrass && (state.is(Blocks.GRASS_BLOCK) && !state.getValue(GrassBlock.SNOWY))) {
            return this.blockModelShaper.getModelManager().getModel(OverflowAnimationsConstants.FAST_GRASS_MODEL_KEY);
        } else {
            return original.call(instance, state);
        }
    }
}
*///?}
//?}
