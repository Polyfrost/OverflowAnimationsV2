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

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.item.ItemModelShaper;
import net.minecraft.client.render.model.block.BakedModel;
import net.minecraft.client.resource.model.ModelManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.legacy.entity.MobHeadIcons;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemModelShaper.class)
public abstract class MixinItemModelShaper_MobHeadIcons {
    @Shadow
    @Final
    private ModelManager modelManager;

    @ModifyReturnValue(method = "getModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/render/model/block/BakedModel;", at = @At("RETURN"))
    private BakedModel overflowanimations$mobHeadIcons(final BakedModel model, @Local(argsOnly = true) final ItemStack item) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.mobHeadIcons && item.getItem() == Items.SKULL) {
            return this.modelManager.getModel(MobHeadIcons.getModel(item.getMetadata()));
        }

        return model;
    }
}
