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
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.model.Model;
import net.minecraft.entity.living.LivingEntity;
import org.polyfrost.overflowanimations.legacy.tint.LegacyDamageTint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.FloatBuffer;

@Mixin(LivingEntityRenderer.class)
public abstract class MixinLivingEntityRenderer_DamageTint {
    @Shadow
    protected Model model;

    @Shadow
    protected FloatBuffer tintBuffer;

    @Inject(method = "setupOverlayColor(Lnet/minecraft/entity/living/LivingEntity;FZ)Z", at = @At(value = "INVOKE", target = "Ljava/nio/FloatBuffer;flip()Ljava/nio/Buffer;"))
    private void overflowanimations$applyTintStyle(final LivingEntity entity, final float tickDelta, final boolean alwaysRender, final CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) final float brightness) {
        if (LegacyDamageTint.isHurt(entity)) {
            LegacyDamageTint.putCombinerTint(this.tintBuffer, brightness);
        }
    }

    @WrapOperation(method = "render(Lnet/minecraft/entity/living/LivingEntity;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;setupOverlayColor(Lnet/minecraft/entity/living/LivingEntity;F)Z"))
    private boolean overflowanimations$skipCombinerTint(final LivingEntityRenderer<?> instance, final LivingEntity entity, final float tickDelta, final Operation<Boolean> original) {
        return !LegacyDamageTint.usesFlatPass(entity) && original.call(instance, entity, tickDelta);
    }

    @WrapOperation(method = "render(Lnet/minecraft/entity/living/LivingEntity;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;renderModel(Lnet/minecraft/entity/living/LivingEntity;FFFFFF)V", ordinal = 1))
    private void overflowanimations$renderFlatTint(final LivingEntityRenderer<?> instance, final LivingEntity entity, final float walkAnimationProgress, final float walkAnimationSpeed, final float bob, final float yaw, final float pitch, final float scale, final Operation<Void> original, @Local(argsOnly = true, ordinal = 1) final float tickDelta) {
        original.call(instance, entity, walkAnimationProgress, walkAnimationSpeed, bob, yaw, pitch, scale);
        if (!entity.isInvisible() && LegacyDamageTint.beginFlatPass(entity, tickDelta)) {
            this.model.render(entity, walkAnimationProgress, walkAnimationSpeed, bob, yaw, pitch, scale);
            LegacyDamageTint.endFlatPass();
        }
    }
}
