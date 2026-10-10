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
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.ItemInHandRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.resource.model.ModelTransformations;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.item.ItemStack;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.legacy.tint.LegacyDamageTint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemInHandRenderer.class)
public abstract class MixinItemInHandRenderer_DamageTintItems {
    @WrapOperation(method = "renderInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/ItemInHandRenderer;render(Lnet/minecraft/entity/living/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resource/model/ModelTransformations$Type;)V"))
    private void overflowanimations$tintFirstPersonItem(final ItemInHandRenderer instance, final LivingEntity entity, final ItemStack item, final ModelTransformations.Type transform, final Operation<Void> original, @Local(argsOnly = true) final float tickDelta) {
        MixinLivingEntityRenderer_DamageTintInvoker renderer = null;
        if (OverflowAnimations.isEnabled() && LegacyDamageTint.config().damageTintItems) {
            final EntityRenderer<?> entityRenderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity);
            if (entityRenderer instanceof MixinLivingEntityRenderer_DamageTintInvoker invoker && invoker.overflowanimations$tint$setupOverlayColor(entity, tickDelta, true)) {
                renderer = invoker;
            }
        }

        original.call(instance, entity, item, transform);
        if (renderer != null) {
            renderer.overflowanimations$tint$tearDownOverlayColor();
        }
    }
}
