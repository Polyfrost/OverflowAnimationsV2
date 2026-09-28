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

package org.polyfrost.overflowanimations.mixins.v1.gui.screen_tweaks;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.components.SplashRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

@Mixin(SplashRenderer.class)
public abstract class MixinSplashRenderer_LegacySplashPosition {
    //? if 1.21.11 {
    /*@ModifyExpressionValue(method = "render", at = @At(value = "CONSTANT", args = "floatValue=123.0"))
    *///?} else {
    @ModifyExpressionValue(method = "extractRenderState", at = @At(value = "CONSTANT", args = "floatValue=123.0"))
    //?}
    private float overflowanimations$legacySplashPosition(final float original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.legacySplashPosition) {
            return 90.0F;
        } else {
            return original;
        }
    }
}
