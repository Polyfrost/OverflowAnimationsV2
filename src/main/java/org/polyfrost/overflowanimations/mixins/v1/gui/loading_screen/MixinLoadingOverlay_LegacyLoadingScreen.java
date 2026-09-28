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

package org.polyfrost.overflowanimations.mixins.v1.gui.loading_screen;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
//? if <26.3 {
/*import com.mojang.blaze3d.pipeline.RenderPipeline;
*///?}
//? if 1.21.11 {
/*import net.minecraft.client.gui.GuiGraphics;
*///?} elif 26.3 {
/*import com.mojang.renderpearl.api.pipeline.RenderPipeline;
*///?} elif 26.4 {
import com.mojang.blaze3d.pipeline.RenderPipeline;
//?}
//? if >=26.1 {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?}
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.handler.rendering.LegacyLogoTexture;

import java.util.function.IntSupplier;

@Mixin(LoadingOverlay.class)
public abstract class MixinLoadingOverlay_LegacyLoadingScreen {
    @Shadow
    @Final
    private ReloadInstance reload;

    @Unique
    private static final Identifier overflowanimations$MOJANG_LOGO = Identifier.withDefaultNamespace("textures/gui/title/mojang.png");

    @Inject(method = "registerTextures", at = @At("TAIL"))
    private static void overflowanimations$loadTextures(final TextureManager textureManager, final CallbackInfo ci) {
        textureManager.registerAndLoad(overflowanimations$MOJANG_LOGO, new LegacyLogoTexture(overflowanimations$MOJANG_LOGO));
    }

    //? if 1.21.11 {
    /*@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Ljava/util/function/IntSupplier;getAsInt()I"))
    *///?} else {
    @WrapOperation(method = "extractRenderState", at = @At(value = "INVOKE", target = "Ljava/util/function/IntSupplier;getAsInt()I"))
    //?}
    private int overflowanimations$replaceBackgroundColor(final IntSupplier instance, final Operation<Integer> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.legacyLoadingScreen) {
            return ARGB.white(1.0F);
        } else {
            return original.call(instance);
        }
    }

    // TODO/NOTE: It still doesn't feel instant, as the debug hud renders before the title screen does meaning theres still some time inbetween
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/LoadingOverlay;isReadyToFadeOut()Z"))
    private boolean overflowanimations$instantFadeOut(final LoadingOverlay instance, final Operation<Boolean> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.legacyLoadingScreen && this.reload.isDone()) {
            return true;
        } else {
            return original.call(instance);
        }
    }

    //? if 1.21.11 {
    /*@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/LoadingOverlay;replaceAlpha(II)I"))
    *///?} else {
    @WrapOperation(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/LoadingOverlay;replaceAlpha(II)I"))
    //?}
    private int overflowanimations$disableFade(final int color, final int alpha, final Operation<Integer> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.legacyLoadingScreen) {
            return color;
        } else {
            return original.call(color, alpha);
        }
    }

    //? if 1.21.11 {
    /*@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIIII)V", ordinal = 0))
    private void overflowanimations$changeLogo(final GuiGraphics instance, final RenderPipeline renderPipeline, final Identifier texture, final int x, final int y, final float u, final float v, final int width, final int height, final int srcWidth, final int srcHeight, final int textureWidth, final int textureHeight, final int color, final Operation<Void> original) {
    *///?} elif >=26.1 <26.3 {
    /*@WrapOperation(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIIII)V", ordinal = 0))
    *///?} elif 26.3 {
    /*@WrapOperation(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIIII)V", ordinal = 0))
    *///?} else {
    @WrapOperation(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIIII)V", ordinal = 0))
    //?}
    //? if >=26.1 {
    private void overflowanimations$changeLogo(final GuiGraphicsExtractor instance, final RenderPipeline renderPipeline, final Identifier texture, final int x, final int y, final float u, final float v, final int width, final int height, final int srcWidth, final int srcHeight, final int textureWidth, final int textureHeight, final int color, final Operation<Void> original) {
    //?}
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.legacyLoadingScreen) {
            final int size = 256;
            instance.blit(RenderPipelines.GUI_TEXTURED, overflowanimations$MOJANG_LOGO, (instance.guiWidth() - size) / 2, (instance.guiHeight() - size) / 2, 0, 0, size, size, size, size, size, size);
        } else {
            original.call(instance, renderPipeline, texture, x, y, u, v, width, height, srcWidth, srcHeight, textureWidth, textureHeight, color);
        }
    }

    //? if 1.21.11 {
    /*@WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIIII)V", ordinal = 1))
    private boolean overflowanimations$disableSecondLogoDraw(final GuiGraphics instance, final RenderPipeline renderPipeline, final Identifier texture, final int x, final int y, final float u, final float v, final int width, final int height, final int srcWidth, final int srcHeight, final int textureWidth, final int textureHeight, final int color) {
    *///?} elif >=26.1 <26.3 {
    /*@WrapWithCondition(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIIII)V", ordinal = 1))
    *///?} elif 26.3 {
    /*@WrapWithCondition(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIIII)V", ordinal = 1))
    *///?} else {
    @WrapWithCondition(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIIII)V", ordinal = 1))
    //?}
    //? if >=26.1 {
    private boolean overflowanimations$disableSecondLogoDraw(final GuiGraphicsExtractor instance, final RenderPipeline renderPipeline, final Identifier texture, final int x, final int y, final float u, final float v, final int width, final int height, final int srcWidth, final int srcHeight, final int textureWidth, final int textureHeight, final int color) {
    //?}
        return !OverflowAnimations.isEnabled() || !OverflowAnimationsConfig.instance().screen.legacyLoadingScreen;
    }

    //? if 1.21.11 {
    /*@WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/LoadingOverlay;drawProgressBar(Lnet/minecraft/client/gui/GuiGraphics;IIIIF)V"))
    private boolean overflowanimations$disableProgressBar(final LoadingOverlay instance, final GuiGraphics graphics, final int x0, final int y0, final int x1, final int y1, final float fade) {
    *///?} else {
    @WrapWithCondition(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/LoadingOverlay;extractProgressBar(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIIIF)V"))
    private boolean overflowanimations$disableProgressBar(final LoadingOverlay instance, final GuiGraphicsExtractor graphics, final int x0, final int y0, final int x1, final int y1, final float fade) {
    //?}
        return !OverflowAnimations.isEnabled() || !OverflowAnimationsConfig.instance().screen.legacyLoadingScreen || OverflowAnimationsConfig.instance().screen.legacyLoadingScreenProgressBar;
    }

    //? if 1.21.11 {
    /*@WrapOperation(method = "drawProgressBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ARGB;color(IIII)I"))
    *///?} else {
    @WrapOperation(method = "extractProgressBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ARGB;color(IIII)I"))
    //?}
    private int overflowanimations$blackProgressBar(final int alpha, final int red, final int green, final int blue, final Operation<Integer> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.legacyLoadingScreen && OverflowAnimationsConfig.instance().screen.legacyLoadingScreenProgressBar) {
            return ARGB.color(alpha, 0, 0, 0);
        } else {
            return original.call(alpha, red, green, blue);
        }
    }
}
