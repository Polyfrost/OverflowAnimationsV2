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

package org.polyfrost.overflowanimations.mixins.legacy.tint;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.render.item.ItemModelShaper;
import net.minecraft.client.render.model.block.BakedModel;
import net.minecraft.client.resource.model.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer_ItemUseAnimationInGUI {
    @WrapOperation(method = "renderGuiItemModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemModelShaper;getModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/render/model/block/BakedModel;"))
    private BakedModel overflowanimations$useModel(final ItemModelShaper shaper, final ItemStack item, final Operation<BakedModel> original) {
        final LocalClientPlayerEntity player = Minecraft.getInstance().player;
        if (OverflowAnimationsConfig.instance().items.usingTextureInGUI.isLegacy() || player == null) {
            return original.call(shaper, item);
        }

        String model = null;
        if (item.getItem() == Items.FISHING_ROD && player.fishingBobber != null && item == player.getItemInHand()) {
            model = "fishing_rod_cast";
        } else if (item.getItem() == Items.BOW && item == player.getItemInUse()) {
            final int useTicks = item.getUseDuration() - player.getItemUseTimer();
            if (useTicks >= 18) {
                model = "bow_pulling_2";
            } else if (useTicks > 13) {
                model = "bow_pulling_1";
            } else if (useTicks > 0) {
                model = "bow_pulling_0";
            }
        }

        return model == null ? original.call(shaper, item) : shaper.getManager().getModel(new ModelIdentifier(model, "inventory"));
    }
}
