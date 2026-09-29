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

package org.polyfrost.overflowanimations.mixins.v1.rendering.items.flat;

//? if <1.21.5 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

import java.util.List;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {
    @Unique
    private static ItemDisplayContext overflowanimations$displayContext;

    @Inject(method = "renderItem", at = @At("HEAD"))
    private static void overflowanimations$captureDisplayContext(final CallbackInfo ci, @Local(argsOnly = true) final ItemDisplayContext displayContext) {
        overflowanimations$displayContext = displayContext;
    }

    @WrapOperation(method = "renderModelLists", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/BakedModel;getQuads(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/util/RandomSource;)Ljava/util/List;"))
    private static List<BakedQuad> overflowanimations$itemDrops2D(final BakedModel model, final BlockState state, final Direction direction, final RandomSource random, final Operation<List<BakedQuad>> original) {
        final List<BakedQuad> quads = original.call(model, state, direction, random);
        if (OverflowAnimations.isEnabled() && overflowanimations$isTransformationModeValid() && !model.usesBlockLight()) {
            return quads.stream().filter(baked -> baked.getDirection() == Direction.SOUTH).toList();
        } else {
            return quads;
        }
    }

    @Unique
    private static boolean overflowanimations$isTransformationModeValid() {
        final boolean itemDrops2D = OverflowAnimationsConfig.instance().items.itemDrops2D;
        final boolean itemFramed2D = OverflowAnimationsConfig.instance().items.itemFramed2D;
        return (itemDrops2D && overflowanimations$displayContext == ItemDisplayContext.GROUND) || (itemFramed2D && overflowanimations$displayContext == ItemDisplayContext.FIXED);
    }
}
*///?}
