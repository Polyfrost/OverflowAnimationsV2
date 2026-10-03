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
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import org.polyfrost.overflowanimations.legacy.model.LegacyEyeHeight;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer_SneakCamera {
    @Shadow
    private Minecraft minecraft;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/ItemInHandRenderer;tick()V"))
    private void overflowanimations$tickEyeHeight(final CallbackInfo ci) {
        LegacyEyeHeight.tick(this.minecraft.getCamera());
    }

    @ModifyExpressionValue(method = "transformCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getEyeHeight()F"))
    private float overflowanimations$cameraEyeHeight(final float original, @Local final Entity entity, @Local(argsOnly = true) final float tickDelta) {
        return LegacyEyeHeight.get(entity, original, tickDelta);
    }

    @ModifyExpressionValue(method = "renderAxisIndicators", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getEyeHeight()F"))
    private float overflowanimations$axisIndicatorEyeHeight(final float original, @Local final Entity entity, @Local(argsOnly = true) final float tickDelta) {
        return LegacyEyeHeight.get(entity, original, tickDelta);
    }
}
