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

package org.polyfrost.overflowanimations.mixins.v1.gui.debug;

//? if >=1.21.6 {
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
//? if <26.3 {
/*import com.mojang.blaze3d.buffers.GpuBuffer;
*///?} else {
import com.mojang.renderpearl.api.buffers.GpuBuffer;
//?}
//? if 26.2 {
/*import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.PrimitiveTopology;
*///?} elif >=26.3 {
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import com.mojang.renderpearl.api.pipeline.PrimitiveTopology;
//?}
//? if <26.2
//import com.mojang.blaze3d.vertex.VertexFormat;
//? if <1.21.11
//import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
//? if <26.2 {
/*import net.minecraft.client.gui.components.DebugScreenOverlay;
*///?} else {
import net.minecraft.client.renderer.DebugCrosshairRenderer;
//?}
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.util.enums.DebugCrosshairSetting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
//?}

//? if >=1.21.6 {
//? if <26.2 {
/*@Mixin(DebugScreenOverlay.class)
*///?} else {
@Mixin(DebugCrosshairRenderer.class)
//?}
public abstract class MixinDebugCrosshairRenderer_LegacyStyle {
    @Unique
    private static GpuBuffer overflowanimations$legacyBuffer;

    //? if <26.2 {
    /*@ModifyVariable(method = "render3dCrosshair", at = @At("STORE"), ordinal = 0)
    *///?} else {
    @ModifyVariable(method = "render", at = @At("STORE"), ordinal = 0)
    //?}
    private float overflowanimations$legacyScale(final float scale) {
        return overflowanimations$isLegacy() ? 1.0F : scale;
    }

    //? if <26.2 {
    /*@ModifyArg(method = "render3dCrosshair", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderPass;setVertexBuffer(ILcom/mojang/blaze3d/buffers/GpuBuffer;)V"), index = 1)
    private GpuBuffer overflowanimations$legacyBuffer(final GpuBuffer buffer) {
        return overflowanimations$isLegacy() ? overflowanimations$legacyBuffer() : buffer;
    }
    *///?} else {
    //? if 26.2 {
    /*@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderPass;setVertexBuffer(ILcom/mojang/blaze3d/buffers/GpuBufferSlice;)V"), index = 1)
    *///?} else {
    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/renderpearl/api/commands/RenderPass;setVertexBuffer(ILcom/mojang/renderpearl/api/buffers/GpuBufferSlice;)V"), index = 1)
    //?}
    private GpuBufferSlice overflowanimations$legacyBuffer(final GpuBufferSlice buffer) {
        return overflowanimations$isLegacy() ? overflowanimations$legacyBuffer().slice() : buffer;
    }
    //?}

    //? if <1.21.11 {
    /*@WrapWithCondition(method = "render3dCrosshair", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderPass;drawIndexed(IIII)V", ordinal = 0))
    private boolean overflowanimations$skipOutline(final RenderPass renderPass, final int baseVertex, final int firstIndex, final int indexCount, final int instanceCount) {
        return !overflowanimations$isLegacy();
    }

    @ModifyExpressionValue(method = "render3dCrosshair", at = @At(value = "CONSTANT", args = "floatValue=2.0F"))
    private float overflowanimations$legacyLineWidth(final float lineWidth) {
        return overflowanimations$isLegacy() ? 1.0F : lineWidth;
    }
    *///?}

    @Unique
    private static boolean overflowanimations$isLegacy() {
        return OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.debugCrosshairStyle == DebugCrosshairSetting.V1_8;
    }

    @Unique
    private static GpuBuffer overflowanimations$legacyBuffer() {
        if (overflowanimations$legacyBuffer == null) {
            //? if <1.21.11 {
            /*try (ByteBufferBuilder bytes = ByteBufferBuilder.exactlySized(DefaultVertexFormat.POSITION_COLOR_NORMAL.getVertexSize() * 6)) {
                final BufferBuilder builder = new BufferBuilder(bytes, VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
                overflowanimations$axes(builder);
            *///?} else {
            //? if <26.2 {
            /*try (ByteBufferBuilder bytes = ByteBufferBuilder.exactlySized(DefaultVertexFormat.POSITION_COLOR_NORMAL_LINE_WIDTH.getVertexSize() * 12)) {
                final BufferBuilder builder = new BufferBuilder(bytes, VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL_LINE_WIDTH);
            *///?} else {
            try (ByteBufferBuilder bytes = ByteBufferBuilder.exactlySized(DefaultVertexFormat.POSITION_COLOR_NORMAL_LINE_WIDTH.getVertexSize() * 12 * 2)) {
                final BufferBuilder builder = new BufferBuilder(bytes, PrimitiveTopology.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL_LINE_WIDTH);
            //?}
                overflowanimations$axes(builder);
                overflowanimations$axes(builder);
            //?}
                try (MeshData mesh = builder.buildOrThrow()) {
                    overflowanimations$legacyBuffer = RenderSystem.getDevice().createBuffer(() -> "Legacy crosshair vertex buffer", 32, mesh.vertexBuffer());
                }
            }
        }
        return overflowanimations$legacyBuffer;
    }

    @Unique
    private static void overflowanimations$axes(final BufferBuilder builder) {
        overflowanimations$line(builder, 0.05F, 0.0F, 0.0F, -65536);
        overflowanimations$line(builder, 0.0F, 0.033F, 0.0F, -16711936);
        overflowanimations$line(builder, 0.0F, 0.0F, 0.05F, -16776961);
    }

    @Unique
    private static void overflowanimations$line(final BufferBuilder builder, final float x, final float y, final float z, final int color) {
        //? if <1.21.11 {
        /*builder.addVertex(0.0F, 0.0F, 0.0F).setColor(color).setNormal(Math.signum(x), Math.signum(y), Math.signum(z));
        builder.addVertex(x, y, z).setColor(color).setNormal(Math.signum(x), Math.signum(y), Math.signum(z));
        *///?} else {
        builder.addVertex(0.0F, 0.0F, 0.0F).setColor(color).setNormal(Math.signum(x), Math.signum(y), Math.signum(z)).setLineWidth(1.0F);
        builder.addVertex(x, y, z).setColor(color).setNormal(Math.signum(x), Math.signum(y), Math.signum(z)).setLineWidth(1.0F);
        //?}
    }
}
//?}
