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

package org.visuals.legacy.animatium.mixins.v1.rendering.fog;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
//? if <26.2 {
/*import com.mojang.blaze3d.buffers.GpuBuffer;
*///?} elif 26.2 {
/*import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
*///?}
//? if <26.3 {
/*import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderPass;
*///?}
import com.mojang.blaze3d.systems.RenderSystem;
//? if <26.2 {
/*import com.mojang.blaze3d.vertex.VertexFormat;
*///?} elif >=26.3 {
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import com.mojang.renderpearl.api.commands.RenderPass;
import com.mojang.renderpearl.api.pipeline.CompiledRenderPipeline;
import com.mojang.renderpearl.api.pipeline.PrimitiveTopology;
//?}
import net.minecraft.client.renderer.SkyRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
//? if >=26.3 {
import org.visuals.legacy.animatium.handler.rendering.LegacySkyRenderer;
//?}
//? if >=26.2 {
import org.visuals.legacy.animatium.handler.rendering.pipeline.AnimatiumPipelines;
//?}
//? if >=26.1 <26.3 {
/*import org.visuals.legacy.animatium.handler.rendering.LegacySkyRenderer;
*///?}
//? if <26.2 {
/*import org.visuals.legacy.animatium.handler.rendering.pipeline.AnimatiumPipelines;
*///?}
//? if 1.21.11 {
/*import org.visuals.legacy.animatium.handler.rendering.LegacySkyRenderer;
*///?}

@Mixin(SkyRenderer.class)
public abstract class MixinSkyRenderer_PlanarFogSky {
    @Unique
    private static RenderSystem.AutoStorageIndexBuffer animatium$skyIndexBuffer;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void animatium$initSkyRenderer(final CallbackInfo ci) {
        // Load them before anything (Static Variables don't load until used, which would cause a issue in the RenderPass)
        //? if <26.3 {
        /*LegacySkyRenderer.TOP_GEOMETRY.getVertexBuffer();
        LegacySkyRenderer.BOTTOM_GEOMETRY.getVertexBuffer();
        *///?}
        //? if <26.2 {
        /*animatium$skyIndexBuffer = RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS);
        *///?} elif >=26.3 {
        LegacySkyRenderer.initialize();
        //?}
        //? if >=26.2 {
        animatium$skyIndexBuffer = RenderSystem.getSequentialBuffer(PrimitiveTopology.QUADS);
        //?}
    }

    //? if <26.3 {
    /*@WrapOperation(method = "renderSkyDisc", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderPass;setPipeline(Lcom/mojang/blaze3d/pipeline/RenderPipeline;)V"))
    private void animatium$planarFogPipeline$skyDisc(final RenderPass instance, final RenderPipeline renderPipeline, final Operation<Void> original) {
        RenderPipeline pipeline = renderPipeline;
    *///?} else {
    @WrapOperation(method = "renderSkyDisc", at = @At(value = "INVOKE", target = "Lcom/mojang/renderpearl/api/commands/RenderPass;setPipeline(Lcom/mojang/renderpearl/api/pipeline/CompiledRenderPipeline;)V"))
    private void animatium$planarFogPipeline$skyDisc(final RenderPass instance, final CompiledRenderPipeline renderPipeline, final Operation<Void> original) {
        CompiledRenderPipeline pipeline = renderPipeline;
    //?}
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.planarSkyFog) {
            //? if <26.3 {
            /*pipeline = AnimatiumPipelines.LEGACY_SKY_PLANAR_FOG;
            *///?} else {
            pipeline = RenderSystem.getCompiledPipeline(AnimatiumPipelines.LEGACY_SKY_PLANAR_FOG);
            //?}
        }

        original.call(instance, pipeline);
    }

    //? if <26.2 {
    /*@WrapOperation(method = "renderSkyDisc", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderPass;setVertexBuffer(ILcom/mojang/blaze3d/buffers/GpuBuffer;)V", ordinal = 0))
    private void animatium$planarFogPipeline$skyDisc$vertexBuffer(final RenderPass instance, final int slot, final GpuBuffer vertexBuffer, final Operation<Void> original) {
    *///?} elif 26.2 {
    /*@WrapOperation(method = "renderSkyDisc", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderPass;setVertexBuffer(ILcom/mojang/blaze3d/buffers/GpuBufferSlice;)V", ordinal = 0))
    *///?} else {
    @WrapOperation(method = "renderSkyDisc", at = @At(value = "INVOKE", target = "Lcom/mojang/renderpearl/api/commands/RenderPass;setVertexBuffer(ILcom/mojang/renderpearl/api/buffers/GpuBufferSlice;)V", ordinal = 0))
    //?}
    //? if >=26.2 {
    private void animatium$planarFogPipeline$skyDisc$vertexBuffer(final RenderPass instance, final int slot, final GpuBufferSlice vertexBuffer, final Operation<Void> original) {
    //?}
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.planarSkyFog) {
            LegacySkyRenderer.TOP_GEOMETRY.bind(instance, animatium$skyIndexBuffer);
        } else {
            original.call(instance, slot, vertexBuffer);
        }
    }

    //? if <26.3 {
    /*@WrapOperation(method = "renderDarkDisc", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderPass;setPipeline(Lcom/mojang/blaze3d/pipeline/RenderPipeline;)V"))
    private void animatium$planarFogPipeline$darkSkyDisc(final RenderPass instance, final RenderPipeline renderPipeline, final Operation<Void> original) {
        RenderPipeline pipeline = renderPipeline;
    *///?} else {
    @WrapOperation(method = "renderDarkDisc", at = @At(value = "INVOKE", target = "Lcom/mojang/renderpearl/api/commands/RenderPass;setPipeline(Lcom/mojang/renderpearl/api/pipeline/CompiledRenderPipeline;)V"))
    private void animatium$planarFogPipeline$darkSkyDisc(final RenderPass instance, final CompiledRenderPipeline renderPipeline, final Operation<Void> original) {
        CompiledRenderPipeline pipeline = renderPipeline;
    //?}
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.planarSkyFog) {
            //? if <26.3 {
            /*pipeline = AnimatiumPipelines.LEGACY_SKY_PLANAR_FOG;
            *///?} else {
            pipeline = RenderSystem.getCompiledPipeline(AnimatiumPipelines.LEGACY_SKY_PLANAR_FOG);
            //?}
        }

        original.call(instance, pipeline);
    }

    //? if <26.2 {
    /*@WrapOperation(method = "renderSkyDisc", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderPass;draw(II)V", ordinal = 0))
    private void animatium$planarFogPipeline$skyDisc$draw(final RenderPass instance, final int firstVertex, final int vertexCount, final Operation<Void> original) {
    *///?} elif 26.2 {
    /*@WrapOperation(method = "renderSkyDisc", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderPass;draw(IIII)V", ordinal = 0))
    *///?} else {
    @WrapOperation(method = "renderSkyDisc", at = @At(value = "INVOKE", target = "Lcom/mojang/renderpearl/api/commands/RenderPass;draw(IIII)V", ordinal = 0))
    //?}
    //? if >=26.2 {
    private void animatium$planarFogPipeline$skyDisc$draw(final RenderPass instance, final int vertexCount, final int instanceCount, final int firstVertex, final int firstInstance, final Operation<Void> original) {
    //?}
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.planarSkyFog) {
            LegacySkyRenderer.TOP_GEOMETRY.draw(instance);
        } else {
            //? if <26.2 {
            /*original.call(instance, firstVertex, vertexCount);
            *///?} else {
            original.call(instance, vertexCount, instanceCount, firstVertex, firstInstance);
            //?}
        }
    }

    //? if <26.2 {
    /*@WrapOperation(method = "renderDarkDisc", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderPass;setVertexBuffer(ILcom/mojang/blaze3d/buffers/GpuBuffer;)V", ordinal = 0))
    private void animatium$planarFogPipeline$darkSkyDisc$vertexBuffer(final RenderPass instance, final int slot, final GpuBuffer vertexBuffer, final Operation<Void> original) {
    *///?} elif 26.2 {
    /*@WrapOperation(method = "renderDarkDisc", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderPass;setVertexBuffer(ILcom/mojang/blaze3d/buffers/GpuBufferSlice;)V", ordinal = 0))
    *///?} else {
    @WrapOperation(method = "renderDarkDisc", at = @At(value = "INVOKE", target = "Lcom/mojang/renderpearl/api/commands/RenderPass;setVertexBuffer(ILcom/mojang/renderpearl/api/buffers/GpuBufferSlice;)V", ordinal = 0))
    //?}
    //? if >=26.2 {
    private void animatium$planarFogPipeline$darkSkyDisc$vertexBuffer(final RenderPass instance, final int slot, final GpuBufferSlice vertexBuffer, final Operation<Void> original) {
    //?}
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.planarSkyFog) {
            LegacySkyRenderer.BOTTOM_GEOMETRY.bind(instance, animatium$skyIndexBuffer);
        } else {
            original.call(instance, slot, vertexBuffer);
        }
    }

    //? if <26.2 {
    /*@WrapOperation(method = "renderDarkDisc", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderPass;draw(II)V", ordinal = 0))
    private void animatium$planarFogPipeline$darkSkyDisc$draw(final RenderPass instance, final int firstVertex, final int vertexCount, final Operation<Void> original) {
    *///?} elif 26.2 {
    /*@WrapOperation(method = "renderDarkDisc", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderPass;draw(IIII)V", ordinal = 0))
    *///?} else {
    @WrapOperation(method = "renderDarkDisc", at = @At(value = "INVOKE", target = "Lcom/mojang/renderpearl/api/commands/RenderPass;draw(IIII)V", ordinal = 0))
    //?}
    //? if >=26.2 {
    private void animatium$planarFogPipeline$darkSkyDisc$draw(final RenderPass instance, final int vertexCount, final int instanceCount, final int firstVertex, final int firstInstance, final Operation<Void> original) {
    //?}
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.planarSkyFog) {
            LegacySkyRenderer.BOTTOM_GEOMETRY.draw(instance);
        } else {
            //? if <26.2 {
            /*original.call(instance, firstVertex, vertexCount);
            *///?} else {
            original.call(instance, vertexCount, instanceCount, firstVertex, firstInstance);
            //?}
        }
    }
}
