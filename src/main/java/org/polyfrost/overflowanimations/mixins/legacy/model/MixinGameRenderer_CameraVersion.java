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
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.GameRenderer;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.util.enums.CameraVersionSetting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer_CameraVersion {
    @Unique
    private boolean overflowanimations$renderingAxisIndicators;

    @WrapOperation(method = "renderAxisIndicators", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;transformCamera(F)V"))
    private void overflowanimations$keepAxisIndicatorsOffset(final GameRenderer instance, final float tickDelta, final Operation<Void> original) {
        this.overflowanimations$renderingAxisIndicators = true;
        try {
            original.call(instance, tickDelta);
        } finally {
            this.overflowanimations$renderingAxisIndicators = false;
        }
    }

    @ModifyExpressionValue(method = "transformCamera", at = @At(value = "CONSTANT", args = "floatValue=-0.1F"))
    private float overflowanimations$cameraVersion(final float original) {
        if (this.overflowanimations$renderingAxisIndicators) {
            return original;
        }
        final CameraVersionSetting version = OverflowAnimationsConfig.instance().screen.cameraVersion;
        if (version == CameraVersionSetting.V1_9_TO_V1_13_2) {
            return 0.05F;
        } else if (version == CameraVersionSetting.V1_14_TO_V1_14_3) {
            return -0.05F;
        } else if (version == CameraVersionSetting.V1_14_4) {
            return 0.0F;
        } else {
            return original;
        }
    }
}
