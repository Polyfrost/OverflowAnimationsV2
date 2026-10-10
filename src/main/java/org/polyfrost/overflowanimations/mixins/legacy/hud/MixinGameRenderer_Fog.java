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

package org.polyfrost.overflowanimations.mixins.legacy.hud;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.player.PlayerEntity;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.legacy.hud.VoidFog;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer_Fog {
    @Unique
    private static final int GL_EYE_PLANE_ABSOLUTE_NV = 34140;

    @Shadow
    private Minecraft minecraft;

    @ModifyExpressionValue(method = "setupFog", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/GameRenderer;renderDistance:F", ordinal = 1))
    private float overflowanimations$voidFog(final float renderDistance, @Local(argsOnly = true) final float tickDelta) {
        if (!OverflowAnimations.isEnabled() || !OverflowAnimationsConfig.instance().other.voidFog.hasFog() || !VoidFog.hasVoidFog(this.minecraft.world)) {
            return renderDistance;
        }
        final Entity camera = this.minecraft.getCamera();
        if (camera instanceof PlayerEntity && (((PlayerEntity) camera).abilities.creativeMode || ((PlayerEntity) camera).isSpectator())) {
            return renderDistance;
        }
        final double eyeOffset = camera == this.minecraft.player ? camera.getEyeHeight() : 0.0;
        double depth = ((camera.getLightLevel(tickDelta) & 0xF00000) >> 20) / 16.0 + (camera.prevY + (camera.y - camera.prevY) * tickDelta + eyeOffset + 4.0) / 32.0;
        if (depth >= 1.0) {
            return renderDistance;
        }
        depth = Math.max(depth, 0.0);
        return Math.min(renderDistance, Math.max(100.0F * (float) (depth * depth), 5.0F));
    }

    @ModifyArg(method = "setupFog", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glFogi(II)V", remap = false), index = 1)
    private int overflowanimations$planarSkyFog(final int distanceMode, @Local(argsOnly = true, ordinal = 0) final int mode) {
        return mode == -1 && OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.planarSkyFog ? GL_EYE_PLANE_ABSOLUTE_NV : distanceMode;
    }
}
