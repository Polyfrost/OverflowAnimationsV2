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

import com.llamalad7.mixinextras.sugar.Local;
import dev.rdh.argentum.impl.render.blockentity.BlockEntityBaker;
import net.minecraft.client.render.model.block.BakedModel;
import org.joml.Matrix4f;
import org.polyfrost.overflowanimations.legacy.entity.LegacyItemRendering;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = BlockEntityBaker.class, remap = false)
public abstract class MixinBlockEntityBaker_Argentum {
    @Shadow
    @Final
    private Matrix4f partTransform;

    @Inject(method = "bakeFrame", at = @At(value = "INVOKE", target = "Lorg/joml/Matrix4f;mul(Lorg/joml/Matrix4fc;)Lorg/joml/Matrix4f;"))
    private void overflowanimations$flatItemOffset(final CallbackInfo ci, @Local(ordinal = 0) final BakedModel item) {
        if (LegacyItemRendering.isFlatModel(item)) {
            this.partTransform.translate(0.0F, 0.0F, -0.015625F);
        }
    }
}
