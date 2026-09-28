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

//? if >=26.3 {
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import com.mojang.renderpearl.api.commands.RenderPass;
//?}
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SkyRenderer;
//? if 1.21.11 {
/*import net.minecraft.client.renderer.state.SkyRenderState;
*///?} else {
import net.minecraft.client.renderer.state.level.SkyRenderState;
//?}
//? if >=26.3 {
import net.minecraft.util.ARGB;
import net.minecraft.world.level.dimension.DimensionType;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//? if >=26.3 {
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
//?}
import org.polyfrost.overflowanimations.handler.rendering.LegacySkyRenderer;
import org.polyfrost.overflowanimations.util.states.SkyUtilityState;

@Mixin(SkyRenderer.class)
public abstract class MixinSkyRenderer_SkyAdditions {
    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void overflowanimations$extractHorizonHeight(final ClientLevel level, final float tickDelta, final Camera camera, final SkyRenderState state, final CallbackInfo ci) {
        ((SkyUtilityState) state).overflowanimations$setHorizonHeight(LegacySkyRenderer.getHorizonEyeHeight(level, tickDelta));
    }

    //? if >=26.3 {
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SkyRenderer;renderDarkDisc(Lcom/mojang/renderpearl/api/commands/RenderPass;)V", shift = At.Shift.AFTER))
    private void overflowanimations$voidBox(final GpuBufferSlice skyFog, final SkyRenderState state, final CallbackInfo ci, @Local(name = "renderPass") final RenderPass pass) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.playerVoidBox) {
            LegacySkyRenderer.renderVoidBox(pass, ((SkyUtilityState) state).overflowanimations$getHorizonHeight());
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/renderpearl/api/commands/RenderPass;close()V"))
    private static void overflowanimations$blueVoid(final GpuBufferSlice skyFog, final SkyRenderState state, final CallbackInfo ci, @Local(name = "renderPass") final RenderPass pass) {
        // TODO/NOTE: Ignore the intellij warning for 'state.skyColor' here as it is wrong, it can be null
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.blueVoidSky && state.skybox == DimensionType.Skybox.OVERWORLD && state.skyColor != null) {
            LegacySkyRenderer.renderBlueVoid(pass, ARGB.colorFromVector3f(state.skyColor), ((SkyUtilityState) state).overflowanimations$getHorizonHeight());
        }
    }

    @Inject(method = "close", at = @At("TAIL"))
    private void overflowanimations$closeSkyRenderUtility(final CallbackInfo ci) {
        LegacySkyRenderer.close();
    }
    //?}
}
