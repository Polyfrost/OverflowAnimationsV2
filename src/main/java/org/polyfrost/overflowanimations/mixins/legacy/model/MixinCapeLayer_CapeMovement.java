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

import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.entity.living.player.ClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerRenderer;
import net.minecraft.client.render.entity.layer.CapeLayer;
import net.minecraft.client.render.platform.GlStateManager;
import net.minecraft.util.math.MathHelper;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.legacy.model.LegacyEyeHeight;
import org.polyfrost.overflowanimations.util.enums.CapeMovementSetting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CapeLayer.class)
public abstract class MixinCapeLayer_CapeMovement {
    @Shadow
    @Final
    private PlayerRenderer parent;

    @Inject(method = "render(Lnet/minecraft/client/entity/living/player/ClientPlayerEntity;FFFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/platform/GlStateManager;translatef(FFF)V", ordinal = 0))
    private void overflowanimations$followSneakingBody(final CallbackInfo ci, @Local(argsOnly = true) final ClientPlayerEntity entity) {
        if (overflowanimations$modern() && entity.isSneaking()) {
            if (!LegacyEyeHeight.isOldSneakModel()) {
                GlStateManager.translatef(0.0F, 0.2F, 0.0F);
            }

            GlStateManager.rotatef(this.parent.getModel().body.rotationX * 57.295776F, 1.0F, 0.0F, 0.0F);
        }
    }

    @ModifyExpressionValue(method = "render(Lnet/minecraft/client/entity/living/player/ClientPlayerEntity;FFFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/living/player/ClientPlayerEntity;isSneaking()Z"))
    private boolean overflowanimations$removeSneakFlap(final boolean original) {
        return original && !overflowanimations$modern();
    }

    @ModifyArg(method = "render(Lnet/minecraft/client/entity/living/player/ClientPlayerEntity;FFFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;sin(F)F", ordinal = 0))
    private float overflowanimations$modernBodyYawSin(final float original, @Local(argsOnly = true) final ClientPlayerEntity entity, @Local(argsOnly = true, ordinal = 2) final float tickDelta) {
        return overflowanimations$modern() ? overflowanimations$bodyYawRadians(entity, tickDelta) : original;
    }

    @ModifyArg(method = "render(Lnet/minecraft/client/entity/living/player/ClientPlayerEntity;FFFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;cos(F)F", ordinal = 0))
    private float overflowanimations$modernBodyYawCos(final float original, @Local(argsOnly = true) final ClientPlayerEntity entity, @Local(argsOnly = true, ordinal = 2) final float tickDelta) {
        return overflowanimations$modern() ? overflowanimations$bodyYawRadians(entity, tickDelta) : original;
    }

    @Expression("(float) (? * ? + ? * ?) * 100.0")
    @ModifyExpressionValue(method = "render(Lnet/minecraft/client/entity/living/player/ClientPlayerEntity;FFFFFFF)V", at = @At("MIXINEXTRAS:EXPRESSION"))
    private float overflowanimations$modernLean(final float original) {
        return overflowanimations$modern() ? Math.min(original, 150.0F) : original;
    }

    @Expression("(float) (? * ? - ? * ?) * 100.0")
    @ModifyExpressionValue(method = "render(Lnet/minecraft/client/entity/living/player/ClientPlayerEntity;FFFFFFF)V", at = @At("MIXINEXTRAS:EXPRESSION"))
    private float overflowanimations$modernSideLean(final float original) {
        return overflowanimations$modern() ? MathHelper.clamp(original, -20.0F, 20.0F) : original;
    }

    @Unique
    private static boolean overflowanimations$modern() {
        return OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().movement.capeMovement == CapeMovementSetting.V1_13;
    }

    @Unique
    private static float overflowanimations$bodyYawRadians(final ClientPlayerEntity entity, final float tickDelta) {
        return (entity.lastBodyYaw + MathHelper.wrapDegrees(entity.bodyYaw - entity.lastBodyYaw) * tickDelta) * (float) Math.PI / 180.0F;
    }
}
