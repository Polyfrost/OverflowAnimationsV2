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

//? if 1.21.11 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.handler.rendering.panorama.LegacyPanoramaRenderer;
*///?}

//? if 1.21.11 {
/*@Mixin(PanoramaRenderer.class)
public abstract class MixinPanoramaRenderer_LegacyPanorama {
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/CubeMap;render(Lnet/minecraft/client/Minecraft;FF)V"))
    private void overflowanimations$panoramaRendering(final CubeMap instance, final Minecraft minecraft, final float rotXInDegrees, final float rotYInDegrees, final Operation<Void> original, @Local(argsOnly = true, ordinal = 0) final GuiGraphics guiGraphics, @Local(argsOnly = true, ordinal = 0) final int width, @Local(argsOnly = true, ordinal = 1) final int height) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.panoramaRendering) {
            LegacyPanoramaRenderer.INSTANCE.extractRenderState(guiGraphics, width, height, Minecraft.getInstance().getDeltaTracker().getRealtimeDeltaTicks());
            LegacyPanoramaRenderer.INSTANCE.render();
        } else {
            original.call(instance, minecraft, rotXInDegrees, rotYInDegrees);
        }
    }
*///?}

//? if 1.21.11 {
    /*@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIII)V"))
    private void overflowanimations$legacyPanorama(final GuiGraphics instance, final RenderPipeline renderPipeline, final Identifier texture, final int x, final int y, final float u, final float v, final int width, final int height, final int srcWidth, final int srcHeight, final int textureWidth, final int textureHeight, final Operation<Void> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.panoramaRendering) {
            instance.fillGradient(0, 0, width, height, -2130706433, 16777215);
            instance.fillGradient(0, 0, width, height, 0, Integer.MIN_VALUE);
        } else {
            original.call(instance, renderPipeline, texture, x, y, u, v, width, height, srcWidth, srcHeight, textureWidth, textureHeight);
        }
    }
}
*///?}
