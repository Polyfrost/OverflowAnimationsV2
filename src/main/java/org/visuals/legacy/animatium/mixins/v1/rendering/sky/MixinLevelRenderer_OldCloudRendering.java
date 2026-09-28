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

package org.visuals.legacy.animatium.mixins.v1.rendering.sky;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
//? if >=26.3 {
import com.mojang.renderpearl.api.commands.RenderPass;
import com.mojang.renderpearl.api.textures.GpuTextureView;
//?}
import net.minecraft.client.CloudStatus;
import net.minecraft.client.renderer.CloudRenderer;
import net.minecraft.client.renderer.LevelRenderer;
//? if >=26.3 {
import net.minecraft.client.renderer.oit.OitRenderPassProvider;
import net.minecraft.client.renderer.oit.OitStage;
import net.minecraft.client.renderer.state.OptionsRenderState;
import net.minecraft.client.renderer.state.level.LevelRenderState;
//?}
import net.minecraft.world.phys.Vec3;
//? if >=26.3 {
import org.spongepowered.asm.mixin.Final;
//?}
import org.spongepowered.asm.mixin.Mixin;
//? if >=26.3 {
import org.spongepowered.asm.mixin.Shadow;
//?}
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.handler.rendering.clouds.LegacyCloudRenderer;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer_OldCloudRendering {
    //? if >=26.3 {
    @Shadow
    @Final
    private OptionsRenderState optionsRenderState;
    //?}

    //? if >=26.3 {
    @Shadow
    @Final
    private LevelRenderState levelRenderState;
    //?}

    @Inject(method = "close", at = @At("TAIL"))
    private void animatium$closeLegacyClouds(final CallbackInfo ci) {
        LegacyCloudRenderer.INSTANCE.close();
    }

    //? if <26.2 {
    /*@WrapOperation(method = {"allChanged", "needsUpdate"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/CloudRenderer;markForRebuild()V"))
    *///?} else {
    @WrapOperation(method = "invalidateCompiledGeometry", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/CloudRenderer;markForRebuild()V"))
    //?}
    private void animatium$markLegacyCloudsForRebuild$1(final CloudRenderer instance, final Operation<Void> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.oldCloudRendering) {
            LegacyCloudRenderer.INSTANCE.markForRebuild();
        } else {
            original.call(instance);
        }
    }

    //? if 1.21.11 {
    /*@WrapOperation(method = "method_62205", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/CloudRenderer;render(ILnet/minecraft/client/CloudStatus;FLnet/minecraft/world/phys/Vec3;JF)V"))
    private void animatium$renderLegacyClouds(final CloudRenderer instance, final int color, final CloudStatus cloudStatus, final float bottomY, final Vec3 cameraPosition, final long gameTime, final float tickDelta, final Operation<Void> original) {
    *///?} elif >=26.1 <26.3 {
    /*@WrapOperation(method = "lambda$addCloudsPass$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/CloudRenderer;render(ILnet/minecraft/client/CloudStatus;FILnet/minecraft/world/phys/Vec3;JF)V"))
    private void animatium$renderLegacyClouds(final CloudRenderer instance, final int color, final CloudStatus cloudStatus, final float bottomY, final int range, final Vec3 cameraPosition, final long gameTime, final float tickDelta, final Operation<Void> original) {
    *///?} else {
    @WrapOperation(method = "prepareTranslucents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/CloudRenderer;prepare(ILnet/minecraft/client/CloudStatus;FILnet/minecraft/world/phys/Vec3;JF)V"))
    private void animatium$prepareLegacyClouds(final CloudRenderer instance, final int color, final CloudStatus cloudStatus, final float bottomY, final int range, final Vec3 cameraPosition, final long gameTime, final float tickDelta, final Operation<Void> original) {
    //?}
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.oldCloudRendering) {
            //? if <26.3 {
            /*LegacyCloudRenderer.INSTANCE.render(color, cloudStatus, bottomY, cameraPosition, tickDelta);
            *///?} else {
            LegacyCloudRenderer.INSTANCE.prepare(color, cloudStatus, bottomY, cameraPosition, tickDelta);
            //?}
        } else {
            //? if 1.21.11 {
            /*original.call(instance, color, cloudStatus, bottomY, cameraPosition, gameTime, tickDelta);
            *///?} else {
            original.call(instance, color, cloudStatus, bottomY, range, cameraPosition, gameTime, tickDelta);
            //?}
        }
    }

    //? if >=26.3 {
    @WrapOperation(method = "executeClassicTransparency", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/CloudRenderer;render(Lnet/minecraft/client/CloudStatus;Lcom/mojang/renderpearl/api/commands/RenderPass;)V"))
    private void animatium$disableVanillaCloudRendering$normal(final CloudRenderer instance, final CloudStatus cloudStatus, final RenderPass renderPass, final Operation<Void> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.oldCloudRendering) {
            LegacyCloudRenderer.INSTANCE.render(renderPass);
        } else {
            original.call(instance, cloudStatus, renderPass);
        }
    }
    //?}

    //? if >=26.3 {
    @WrapWithCondition(method = "executeOit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/CloudRenderer;renderOit(Lnet/minecraft/client/CloudStatus;Lnet/minecraft/client/renderer/oit/OitStage;Lcom/mojang/renderpearl/api/textures/GpuTextureView;Lnet/minecraft/client/renderer/oit/OitRenderPassProvider$Parameters;)V"))
    private boolean animatium$disableVanillaCloudRendering$oit(final CloudRenderer instance, final CloudStatus cloudStatus, final OitStage stage, final GpuTextureView mainDepthTextureView, final OitRenderPassProvider.Parameters params) {
        return !Animatium.isEnabled() || !AnimatiumConfig.instance().other.oldCloudRendering;
    }
    //?}

    @WrapWithCondition(method = "endFrame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/CloudRenderer;endFrame()V"))
    private boolean animatium$endLegacyCloudsFrame(final CloudRenderer instance) {
        return !Animatium.isEnabled() || !AnimatiumConfig.instance().other.oldCloudRendering;
    }
}
