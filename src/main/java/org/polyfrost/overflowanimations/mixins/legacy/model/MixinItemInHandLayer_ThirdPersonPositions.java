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

package org.polyfrost.overflowanimations.mixins.legacy.model;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.layer.ItemInHandLayer;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.item.ItemStack;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.legacy.model.LegacyEyeHeight;
import org.polyfrost.overflowanimations.legacy.model.ThirdPersonItemTransforms;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandLayer.class)
public abstract class MixinItemInHandLayer_ThirdPersonPositions {
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/ItemInHandRenderer;render(Lnet/minecraft/entity/living/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resource/model/ModelTransformations$Type;)V"))
    private void overflowanimations$thirdPersonPositions(final LivingEntity entity, final float walkAnimationProgress, final float walkAnimationSpeed, final float tickDelta, final float bob, final float yaw, final float pitch, final float scale, final CallbackInfo ci, @Local final ItemStack stack) {
        if (OverflowAnimations.isEnabled()) {
            ThirdPersonItemTransforms.apply(entity, stack);
        }
    }

    @ModifyExpressionValue(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/LivingEntity;isSneaking()Z"))
    private boolean overflowanimations$removeSneakTranslation(final boolean original) {
        return original && !LegacyEyeHeight.isOldSneakModel();
    }
}
