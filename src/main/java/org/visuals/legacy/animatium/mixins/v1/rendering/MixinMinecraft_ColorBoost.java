/**
 * Animatium
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

package org.visuals.legacy.animatium.mixins.v1.rendering;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
//? if <26.2 {
/*import com.mojang.blaze3d.pipeline.RenderTarget;
*///?} elif 26.2 {
/*import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.GpuSurface;
*///?}
//? if <26.3 {
/*import com.mojang.blaze3d.textures.GpuTextureView;
*///?} else {
import com.mojang.renderpearl.api.commands.CommandEncoder;
import com.mojang.renderpearl.api.device.GpuSurface;
import com.mojang.renderpearl.api.textures.GpuTextureView;
//?}
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
//? if <26.2 {
/*import net.minecraft.client.gui.screens.Screen;
import org.jspecify.annotations.Nullable;
*///?} else {
import net.minecraft.client.renderer.GameRenderer;
//?}
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.handler.rendering.ColorBoostRenderer;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft_ColorBoost {
    @Shadow
    @Final
    //? if >=26.2 {
    public GameRenderer gameRenderer;
    //?}

    //? if >=26.2 {
    @Shadow
    @Final
    //?}
    public Gui gui;

    //? if <26.2 {
    /*@Shadow
    @Nullable
    public Screen screen;
    *///?}

    //? if <26.2 {
    /*@Shadow
    public abstract RenderTarget getMainRenderTarget();
    *///?}

    //? if 1.21.11 {
    /*@WrapOperation(method = "runTick", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderTarget;blitToScreen()V"))
    *///?} elif 26.1 {
    /*@WrapOperation(method = "renderFrame", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderTarget;blitToScreen()V"))
    *///?}
    //? if <26.2 {
    /*private void animatium$colorBoost(final RenderTarget instance, final Operation<Void> original) {
        final RenderTarget renderTarget = this.getMainRenderTarget();
        final GpuTextureView colorTextureView = renderTarget.getColorTextureView();
        if (AnimatiumConfig.instance().extras.colorBoost && colorTextureView != null && this.screen == null) {
            ColorBoostRenderer.render(colorTextureView, renderTarget.getDepthTextureView());
    *///?} elif 26.2 {
    /*@WrapOperation(method = "renderFrame", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/GpuSurface;blitFromTexture(Lcom/mojang/blaze3d/systems/CommandEncoder;Lcom/mojang/blaze3d/textures/GpuTextureView;)V"))
    *///?} else {
    @WrapOperation(method = "renderFrame", at = @At(value = "INVOKE", target = "Lcom/mojang/renderpearl/api/device/GpuSurface;blitFromTexture(Lcom/mojang/renderpearl/api/commands/CommandEncoder;Lcom/mojang/renderpearl/api/textures/GpuTextureView;)V"))
    //?}
    //? if >=26.2 {
    private void animatium$colorBoost(final GpuSurface instance, final CommandEncoder commandEncoder, final GpuTextureView colorAttachment, final Operation<Void> original) {
        if (AnimatiumConfig.instance().extras.colorBoost && this.gui.screen() == null) {
            ColorBoostRenderer.render(colorAttachment, this.gameRenderer.mainRenderTarget().getDepthTextureView());
    //?}
        }

        //? if <26.2 {
        /*original.call(instance);
        *///?} else {
        original.call(instance, commandEncoder, colorAttachment);
        //?}
    }
}
