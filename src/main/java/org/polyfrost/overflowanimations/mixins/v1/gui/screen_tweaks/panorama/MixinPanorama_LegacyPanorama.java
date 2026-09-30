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
//?}
//? if >=26.1 <26.3 {
/*import com.mojang.blaze3d.pipeline.RenderPipeline;
*///?} elif 26.3 {
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
//?} elif 26.4 {
/*import com.mojang.blaze3d.pipeline.RenderPipeline;
*///?}
//? if >=26.1 {
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.Panorama;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.handler.rendering.panorama.LegacyPanoramaRenderer;
//?}

//? if >=26.1 {
@Mixin(Panorama.class)
public abstract class MixinPanorama_LegacyPanorama {
//?}
    //? if >=26.1 <26.3 {
    /*@WrapOperation(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIII)V"))
    *///?} elif 26.3 {
    @WrapOperation(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIII)V"))
    //?} elif 26.4 {
    /*@WrapOperation(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIII)V"))
    *///?}
//? if >=26.1 {
    private void overflowanimations$legacyPanorama(final GuiGraphicsExtractor instance, final RenderPipeline renderPipeline, final Identifier texture, final int x, final int y, final float u, final float v, final int width, final int height, final int srcWidth, final int srcHeight, final int textureWidth, final int textureHeight, final Operation<Void> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.panoramaRendering) {
            LegacyPanoramaRenderer.INSTANCE.extractRenderState(instance, width, height, Minecraft.getInstance().getDeltaTracker().getRealtimeDeltaTicks());
            instance.fillGradient(0, 0, width, height, -2130706433, 16777215);
            instance.fillGradient(0, 0, width, height, 0, Integer.MIN_VALUE);
        } else {
            original.call(instance, renderPipeline, texture, x, y, u, v, width, height, srcWidth, srcHeight, textureWidth, textureHeight);
        }
    }
}
//?}
