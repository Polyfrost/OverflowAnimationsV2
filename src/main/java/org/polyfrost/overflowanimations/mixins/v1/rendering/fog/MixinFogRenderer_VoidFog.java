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

package org.polyfrost.overflowanimations.mixins.v1.rendering.fog;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.util.LevelUtilKt;

@Mixin(FogRenderer.class)
public abstract class MixinFogRenderer_VoidFog {
    // TODO: Should only affect (DarknessFogFunction?) and Sky Fog
    // TODO: Fix minY
    @Definition(id = "renderDistance", local = @Local(type = int.class, argsOnly = true))
    @Expression("(float) (renderDistance * 16)")
    @ModifyExpressionValue(method = "setupFog", at = @At("MIXINEXTRAS:EXPRESSION"))
    private float overflowanimations$voidFog(final float original, final Camera camera, final int renderDistanceInChunks, final DeltaTracker deltaTracker, final float darkenWorldAmount, final ClientLevel level) {
        final Entity entity = camera.entity();
        final boolean isVoidFogAllowed = entity instanceof Player player && !(player.isCreative() || player.isSpectator());
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.voidFog.hasFog() && LevelUtilKt.hasVoidFog(level) && isVoidFogAllowed) {
            final double light = level.getLightEngine().getLayerListener(LightLayer.SKY).getLightValue(entity.blockPosition()) / 16.0;
            final double yOffset = (Mth.lerp(deltaTracker.getGameTimeDeltaPartialTick(true), entity.yo, entity.getY()) + 4.0) / 32.0;
            if (light + yOffset < 1.0) {
                return Math.min(original, Math.max(100.0F * (float) Math.pow(Math.max(light, 0.0), 2), 5.0F));
            }
        }

        return original;
    }
}
