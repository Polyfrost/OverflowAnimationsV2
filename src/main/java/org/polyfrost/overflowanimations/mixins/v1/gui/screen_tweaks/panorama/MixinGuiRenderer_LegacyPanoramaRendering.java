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

package org.polyfrost.overflowanimations.mixins.v1.gui.screen_tweaks.panorama;

//? if >=26.1 {
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.renderer.CubeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.handler.rendering.panorama.LegacyPanoramaRenderer;
//?}

//? if >=26.1 {
@Mixin(GuiRenderer.class)
public abstract class MixinGuiRenderer_LegacyPanoramaRendering {
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/CubeMap;render(FF)V", ordinal = 0))
    private void overflowanimations$panoramaRendering(final CubeMap instance, final float rotXInDegrees, final float rotYInDegrees, final Operation<Void> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.panoramaRendering) {
            LegacyPanoramaRenderer.INSTANCE.render();
        } else {
            original.call(instance, rotXInDegrees, rotYInDegrees);
        }
    }
//?}

//? if >=26.1 {
    @Inject(method = "close", at = @At("TAIL"))
    private void overflowanimations$closePanorama(final CallbackInfo ci) {
        LegacyPanoramaRenderer.INSTANCE.close();
    }
}
//?}
