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

package org.polyfrost.overflowanimations.mixins.v1.rendering.sky;

//? if <26.3 {
/*import com.mojang.blaze3d.buffers.GpuBufferSlice;
import net.minecraft.client.renderer.LevelRenderer;
*///?}
//? if <26.2 {
/*import net.minecraft.client.renderer.SkyRenderer;
*///?}
//? if 1.21.11 {
/*import net.minecraft.client.renderer.state.SkyRenderState;
*///?} elif >=26.1 <26.3 {
/*import net.minecraft.client.renderer.state.level.SkyRenderState;
*///?}
//? if <26.3 {
/*import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.handler.rendering.LegacySkyRenderer;
import org.polyfrost.overflowanimations.util.states.SkyUtilityState;
*///?}

//? if <26.3 {
/*@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer_SkyAdditions {
*///?}
    //? if 1.21.11 {
    /*@Inject(method = "method_62215", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SkyRenderer;renderDarkDisc()V", shift = At.Shift.AFTER))
    private static void overflowanimations$voidBox(final GpuBufferSlice gpuBufferSlice, final SkyRenderState state, final SkyRenderer skyRenderer, final CallbackInfo ci) {
    *///?} elif >=26.1 <26.3 {
    /*@Inject(method = "lambda$addSkyPass$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SkyRenderer;renderDarkDisc()V", shift = At.Shift.AFTER))
    *///?}
    //? if 26.1 {
    /*private static void overflowanimations$voidBox(final GpuBufferSlice skyFog, final SkyRenderState state, final SkyRenderer skyRenderer, final CallbackInfo ci) {
    *///?} elif 26.2 {
    /*private void overflowanimations$voidBox(final GpuBufferSlice skyFog, final SkyRenderState state, final CallbackInfo ci) {
    *///?}
    //? if <26.3 {
        /*if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.playerVoidBox) {
            LegacySkyRenderer.renderVoidBox(((SkyUtilityState) state).overflowanimations$getHorizonHeight());
        }
    }
    *///?}

    //? if 1.21.11 {
    /*@Inject(method = "method_62215", at = @At("TAIL"))
    *///?} elif >=26.1 <26.3 {
    /*@Inject(method = "lambda$addSkyPass$0", at = @At("TAIL"))
    *///?}
    //? if <26.2 {
    /*private static void overflowanimations$blueVoid(final GpuBufferSlice skyFog, final SkyRenderState state, final SkyRenderer skyRenderer, final CallbackInfo ci) {
    *///?}
    //? if 26.2 {
    /*private static void overflowanimations$blueVoid(final GpuBufferSlice skyFog, final SkyRenderState state, final CallbackInfo ci) {
    *///?}
        //? if <26.3 {
        /*if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.blueVoidSky && state.skybox == DimensionType.Skybox.OVERWORLD) {
        *///?}
    //? if <26.3 {
            /*LegacySkyRenderer.renderBlueVoid(state.skyColor, ((SkyUtilityState) state).overflowanimations$getHorizonHeight());
        }
    }
    *///?}

//? if <26.3 {
    /*@Inject(method = "close", at = @At("TAIL"))
    private void overflowanimations$closeSkyRenderUtility(final CallbackInfo ci) {
        LegacySkyRenderer.close();
    }
}
*///?}
