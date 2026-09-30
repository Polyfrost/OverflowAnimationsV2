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

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer_FlameOffset {
    @ModifyArg(method = "renderOnFire", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/platform/GlStateManager;translatef(FFF)V", ordinal = 1), index = 1)
    private float overflowanimations$flameOffset(final float y, @Local(argsOnly = true) final Entity entity) {
        if (OverflowAnimationsConfig.instance().other.flameOffset && entity == Minecraft.getInstance().player) {
            final float scale = entity.width * 1.4F;
            return y + 1.62F * (1.0F - scale) / scale;
        }

        return y;
    }
}
