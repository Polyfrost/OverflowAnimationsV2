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

import net.minecraft.client.render.model.block.ModelBakery;
import net.minecraft.client.resource.model.BlockModel;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.resource.Identifier;
import org.polyfrost.overflowanimations.legacy.entity.MobHeadIcons;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(ModelBakery.class)
public abstract class MixinModelBakery_MobHeadIcons {
    @Shadow
    private Map<Item, List<String>> itemVariants;

    @Inject(method = "registerItemVariants", at = @At("TAIL"))
    private void overflowanimations$registerMobHeadIcons(final CallbackInfo ci) {
        final List<String> variants = new ArrayList<>(this.itemVariants.get(Items.SKULL));
        variants.addAll(MobHeadIcons.VARIANTS);
        this.itemVariants.put(Items.SKULL, variants);
    }

    @Inject(method = "loadBlockModel", at = @At("HEAD"), cancellable = true)
    private void overflowanimations$loadMobHeadIconModel(final Identifier location, final CallbackInfoReturnable<BlockModel> cir) {
        final BlockModel model = MobHeadIcons.loadItemModel(location);
        if (model != null) {
            cir.setReturnValue(model);
        }
    }
}
