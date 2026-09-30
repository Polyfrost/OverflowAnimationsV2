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
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.render.model.block.BakedModel;
import net.minecraft.client.render.model.block.BakedQuad;
import net.minecraft.client.render.vertex.BufferBuilder;
import net.minecraft.client.resource.model.ModelTransformations;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.legacy.entity.LegacyItemRendering;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer_FlatItems {
    @WrapMethod(method = "renderItemInHand(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/block/BakedModel;Lnet/minecraft/client/resource/model/ModelTransformations$Type;)V")
    private void overflowanimations$captureTransform(final ItemStack item, final BakedModel model, final ModelTransformations.Type transform, final Operation<Void> original) {
        final ModelTransformations.Type previous = LegacyItemRendering.transform;
        LegacyItemRendering.transform = transform;
        try {
            original.call(item, model, transform);
        } finally {
            LegacyItemRendering.transform = previous;
        }
    }

    @WrapMethod(method = "render(Lnet/minecraft/client/render/model/block/BakedModel;ILnet/minecraft/item/ItemStack;)V")
    private void overflowanimations$captureFlat(final BakedModel model, final int color, final ItemStack item, final Operation<Void> original) {
        final boolean previous = LegacyItemRendering.flat;
        LegacyItemRendering.flat = LegacyItemRendering.isFlat(model);
        try {
            original.call(model, color, item);
        } finally {
            LegacyItemRendering.flat = previous;
        }
    }

    @ModifyExpressionValue(method = "render(Lnet/minecraft/client/render/model/block/BakedModel;ILnet/minecraft/item/ItemStack;)V", at = {
            @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/block/BakedModel;getQuads(Lnet/minecraft/util/math/Direction;)Ljava/util/List;"),
            @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/block/BakedModel;getQuads()Ljava/util/List;")
    })
    private List<BakedQuad> overflowanimations$frontFaceOnly(final List<BakedQuad> quads) {
        if (!LegacyItemRendering.flat) {
            return quads;
        }

        final List<BakedQuad> front = new ArrayList<>(quads.size());
        for (final BakedQuad quad : quads) {
            if (quad.getFace() == Direction.SOUTH) {
                front.add(quad);
            }
        }

        return front;
    }

    @WrapOperation(method = "applyNormal", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/vertex/BufferBuilder;postNormal(FFF)V"))
    private void overflowanimations$legacySpriteNormal(final BufferBuilder instance, final float x, final float y, final float z, final Operation<Void> original) {
        if (LegacyItemRendering.flat && OverflowAnimationsConfig.instance().items.itemDrops2DColors) {
            original.call(instance, x, z, y);
        } else {
            original.call(instance, x, y, z);
        }
    }

    @ModifyExpressionValue(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/block/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;hasEnchantmentGlint()Z"))
    private boolean overflowanimations$flatItemGlint(final boolean original, @Local(argsOnly = true) final BakedModel model) {
        return original && !LegacyItemRendering.hideGlint(model);
    }
}
