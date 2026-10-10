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

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.rdh.argentum.impl.render.blockentity.BakedItemFrames;
import net.minecraft.client.render.model.block.BakedModel;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.legacy.entity.LegacyItemRendering;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(value = BakedItemFrames.class, remap = false)
public abstract class MixinBakedItemFrames_Argentum {
    @ModifyExpressionValue(method = "snapshot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemModelShaper;getModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/render/model/block/BakedModel;", remap = true))
    private static BakedModel overflowanimations$flatFramedItems(final BakedModel model) {
        return OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.itemFramed2D && !model.isGui3d() ? LegacyItemRendering.flatModel(model) : model;
    }
}
