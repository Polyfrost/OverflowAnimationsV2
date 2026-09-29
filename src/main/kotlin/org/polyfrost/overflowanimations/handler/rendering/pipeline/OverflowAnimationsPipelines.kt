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

package org.polyfrost.overflowanimations.handler.rendering.pipeline

// Built on the GPU device API (1.21.5) and dynamic uniforms / GUI render states (1.21.6)
//? if >=1.21.6 {
//? if <26.2 {
/*import com.mojang.blaze3d.pipeline.BlendFunction
*///?}
//? if 26.1 {
/*import com.mojang.blaze3d.pipeline.ColorTargetState
import com.mojang.blaze3d.pipeline.DepthStencilState
*///?}
//? if <26.2 {
/*import com.mojang.blaze3d.pipeline.RenderPipeline
*///?}
//? if <26.1 {
/*import com.mojang.blaze3d.platform.DepthTestFunction
*///?} elif 26.2 {
/*import com.mojang.blaze3d.GpuFormat
import com.mojang.blaze3d.PrimitiveTopology
import com.mojang.blaze3d.pipeline.*
import com.mojang.blaze3d.platform.BlendFactor
*///?}
//? if >=26.1 <26.3 {
/*import com.mojang.blaze3d.platform.CompareOp
*///?}
//? if <26.2 {
/*import com.mojang.blaze3d.platform.DestFactor
import com.mojang.blaze3d.platform.SourceFactor
*///?}
//? if <26.3 {
/*import com.mojang.blaze3d.shaders.UniformType
*///?} elif 26.4 {
import com.mojang.blaze3d.pipeline.RenderPipeline
//?}
import com.mojang.blaze3d.vertex.DefaultVertexFormat
//? if <26.3 {
/*import com.mojang.blaze3d.vertex.VertexFormat
*///?}
//? if <26.2 {
/*import com.mojang.blaze3d.vertex.VertexFormatElement
*///?} elif 26.4 {
import com.mojang.blaze3d.vertex.VertexFormat
//?}
//? if >=26.3 {
import com.mojang.renderpearl.api.GpuFormat
import com.mojang.renderpearl.api.pipeline.*
//?}
//? if 26.3 {
/*import com.mojang.renderpearl.api.vertex.VertexFormat
*///?}
//? if >=26.2 {
import net.minecraft.client.renderer.BindGroupLayouts
//?}
import net.minecraft.client.renderer.RenderPipelines
import org.polyfrost.overflowanimations.OverflowAnimations.location
import org.polyfrost.overflowanimations.handler.rendering.clouds.CloudPipelineSet
//? if >=26.1 {
import java.util.*
//?}

object OverflowAnimationsPipelines {
    @JvmField
    //? if <26.1 {
    /*val NO_DEPTH_WRITE = RenderPipeline.builder()
        .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
        .withDepthWrite(false)
        .buildSnippet()
    *///?} elif 26.1 {
    /*val NO_DEPTH_WRITE = DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, false)
    *///?} else {
    val NO_DEPTH_WRITE = DepthStencilState(CompareOp.GREATER_THAN_OR_EQUAL, false)
    //?}

    // Panorama
    @JvmField
    val PANORAMA_BLEND = BlendFunction(
        //? if <26.2 {
        /*SourceFactor.SRC_ALPHA,
        DestFactor.ONE_MINUS_SRC_ALPHA,
        SourceFactor.ONE,
        DestFactor.ZERO
        *///?} else {
        BlendFactor.SRC_ALPHA,
        BlendFactor.ONE_MINUS_SRC_ALPHA,
        BlendFactor.ONE,
        BlendFactor.ZERO
        //?}
    )

    //? if >=26.1 {
    fun panoramaBlendState(colorMask: @ColorTargetState.WriteMask Int) =
    //?}
        //? if 26.1 {
        /*ColorTargetState(Optional.of(PANORAMA_BLEND), colorMask)
        *///?} elif >=26.2 {
        ColorTargetState(Optional.of(PANORAMA_BLEND), GpuFormat.RGBA8_UNORM, colorMask)
        //?}

    @JvmField
    //? if <26.2 {
    /*val TEXTURED_QUAD = RenderPipeline.builder(RenderPipelines.MATRICES_PROJECTION_SNIPPET)
        .withSampler("Sampler0")
    *///?} else {
    val TEXTURED_QUAD = RenderPipeline.builder(RenderPipelines.GLOBALS_SNIPPET)
    //?}
        //? if 26.2 {
        /*.withBindGroupLayouts(BindGroupLayouts.MATRICES_PROJECTION, BindGroupLayouts.SAMPLER0)
        *///?} elif >=26.3 {
        .withBindGroupLayouts(
            BindGroupLayouts.DYNAMIC_TRANSFORMS,
            BindGroupLayouts.PROJECTION,
            BindGroupLayouts.SAMPLER0
        )
        //?}
        //? if >=26.2 {
        .withPrimitiveTopology(PrimitiveTopology.QUADS)
        //?}
        .buildSnippet()

    @JvmField
    val LEGACY_PANORAMA_SNIPPET = RenderPipeline.builder(TEXTURED_QUAD)
        .withVertexShader(location("core/legacy_panorama"))
        .withFragmentShader(location("core/legacy_panorama"))
        //? if <26.1 {
        /*.withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST) // Required by 1.21.11 to render
        .withDepthWrite(false)
        *///?}
        .withCull(false)
        //? if <26.2 {
        /*.withVertexFormat(DefaultVertexFormat.POSITION, VertexFormat.Mode.QUADS)
        *///?} else {
        .withVertexFormat(DefaultVertexFormat.POSITION)
        //?}
        .buildSnippet()

    @JvmField
    val LEGACY_PANORAMA_1 = RenderPipelines.register(
        RenderPipeline.builder(LEGACY_PANORAMA_SNIPPET)
            .withLocation(location("pipeline/legacy_panorama_1"))
            //? if <26.1 {
            /*.withBlend(PANORAMA_BLEND)
            *///?} else {
            .withColorTargetState(panoramaBlendState(ColorTargetState.WRITE_ALL))
            //?}
            .build()
    )

    @JvmField
    val LEGACY_PANORAMA_2 = RenderPipelines.register(
        RenderPipeline.builder(LEGACY_PANORAMA_SNIPPET)
            .withLocation(location("pipeline/legacy_panorama_2"))
            //? if <26.1 {
            /*.withBlend(PANORAMA_BLEND)
            .withColorWrite(true, false)
            *///?} else {
            .withColorTargetState(panoramaBlendState(ColorTargetState.WRITE_COLOR))
            //?}
            .build()
    )

    @JvmField
    val LEGACY_PANORAMA_BLUR = RenderPipelines.register(
        RenderPipeline.builder(TEXTURED_QUAD)
            .withLocation(location("pipeline/legacy_panorama_blur"))
            .withVertexShader(location("core/legacy_panorama_blur"))
            .withFragmentShader(location("core/legacy_panorama_blur"))
            //? if <26.1 {
            /*.withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST) // Required by 1.21.11 to render
            .withDepthWrite(false)
            .withBlend(PANORAMA_BLEND)
            .withColorWrite(true, false)
            *///?} else {
            .withColorTargetState(panoramaBlendState(ColorTargetState.WRITE_COLOR))
            //?}
            //? if <26.2 {
            /*.withVertexFormat(DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS)
            *///?} else {
            .withVertexFormat(DefaultVertexFormat.POSITION_TEX)
            //?}
            .build()
    )

    // Sky
    @JvmField
    val VOID_BOX_SNIPPET =
        //? if <26.1 {
        /*RenderPipeline.builder(RenderPipelines.MATRICES_FOG_SNIPPET, NO_DEPTH_WRITE)
        *///?} else {
        RenderPipeline.builder(RenderPipelines.MATRICES_FOG_SNIPPET)
        //?}
            .withVertexShader("core/position_color")
            .withFragmentShader("core/position_color")
            //? if >=26.1 {
            .withDepthStencilState(NO_DEPTH_WRITE)
            //?}
            //? if <26.2 {
            /*.withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS)
            *///?} elif >=26.3 {
            .withColorTargetState(ColorTargetState.DEFAULT)
            //?}
            //? if >=26.2 {
            .withVertexFormat(DefaultVertexFormat.POSITION_COLOR)
            .withPrimitiveTopology(PrimitiveTopology.QUADS)
            //?}
            .buildSnippet()

    @JvmField
    val VOID_BOX = RenderPipelines.register(
        RenderPipeline.builder(VOID_BOX_SNIPPET)
            .withLocation(location("pipeline/void_box"))
            .build()
    )

    @JvmField
    val LEGACY_SKY_SNIPPET =
        //? if <26.1 {
        /*RenderPipeline.builder(RenderPipelines.MATRICES_FOG_SNIPPET, NO_DEPTH_WRITE)
        *///?} else {
        RenderPipeline.builder(RenderPipelines.MATRICES_FOG_SNIPPET)
        //?}
            .withLocation(location("pipeline/legacy_sky"))
            .withVertexShader(location("core/legacy_sky"))
            .withFragmentShader(location("core/legacy_sky"))
            //? if >=26.1 {
            .withDepthStencilState(NO_DEPTH_WRITE)
            //?}
            //? if <26.2 {
            /*.withVertexFormat(DefaultVertexFormat.POSITION, VertexFormat.Mode.QUADS)
            *///?} elif >=26.3 {
            .withColorTargetState(ColorTargetState.DEFAULT)
            //?}
            //? if >=26.2 {
            .withVertexFormat(DefaultVertexFormat.POSITION)
            .withPrimitiveTopology(PrimitiveTopology.QUADS)
            //?}
            .buildSnippet()

    @JvmField
    val LEGACY_SKY =
        RenderPipelines.register(
            RenderPipeline.builder(LEGACY_SKY_SNIPPET)
                .withLocation(location("pipeline/legacy_sky"))
                .build()
        )

    @JvmField
    val LEGACY_SKY_PLANAR_FOG: RenderPipeline =
        RenderPipelines.register(
            RenderPipeline.builder(LEGACY_SKY_SNIPPET)
                .withLocation(location("pipeline/legacy_sky_planar_fog"))
                .withShaderDefine("PLANAR_FOG")
                .build()
        )

    @JvmStatic
    fun getSkyPipeline(planar: Boolean) = if (planar)
        LEGACY_SKY_PLANAR_FOG
    else
        LEGACY_SKY

    // Clouds
    @JvmField
    val LEGACY_CLOUDS_SNIPPET = RenderPipeline.builder(RenderPipelines.MATRICES_FOG_SNIPPET)
        .withVertexShader(location("core/legacy_clouds"))
        .withFragmentShader(location("core/legacy_clouds"))
        //? if <26.1 {
        /*.withBlend(BlendFunction.TRANSLUCENT)
        *///?} else {
        .withDepthStencilState(DepthStencilState.DEFAULT)
        .withColorTargetState(ColorTargetState(BlendFunction.TRANSLUCENT))
        //?}
        //? if <26.2 {
        /*.withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS)
        *///?} else {
        .withVertexFormat(DefaultVertexFormat.POSITION_COLOR)
        .withPrimitiveTopology(PrimitiveTopology.QUADS)
        //?}
        .buildSnippet()

    @JvmField
    val LEGACY_CLOUDS = CloudPipelineSet.create(
        "legacy_clouds",
        LEGACY_CLOUDS_SNIPPET
    )

    @JvmField
    val LEGACY_CLOUDS_PLANAR = CloudPipelineSet.create(
        "legacy_clouds_planar",
        RenderPipeline.builder(LEGACY_CLOUDS_SNIPPET)
            .withShaderDefine("PLANAR_FOG")
            .buildSnippet()
    )

    @JvmStatic
    fun getCloudsSet(planar: Boolean) = if (planar)
        LEGACY_CLOUDS_PLANAR
    else
        LEGACY_CLOUDS

    // Lighting
    //? if >=26.2 {
    @JvmField
    val LEGACY_LIGHTMAP_INFO: BindGroupLayout = BindGroupLayout.builder()
        .withUniform("LightmapInfo", UniformType.UNIFORM_BUFFER)
        .build()
    //?}

    @JvmField
    val LEGACY_LIGHTMAP: RenderPipeline = RenderPipelines.register(
        RenderPipeline.builder()
            .withLocation(location("pipeline/legacy_lightmap"))
            .withVertexShader("core/screenquad")
            .withFragmentShader(location("core/legacy_lightmap"))
            //? if <26.2 {
            /*.withUniform("LightmapInfo", UniformType.UNIFORM_BUFFER)
            .withVertexFormat(DefaultVertexFormat.EMPTY, VertexFormat.Mode.TRIANGLES)
            *///?} else {
            .withBindGroupLayout(LEGACY_LIGHTMAP_INFO)
            //?}
            //? if >=26.3 {
            .withColorTargetState(ColorTargetState.DEFAULT)
            //?}
            //? if >=26.2 {
            .withPrimitiveTopology(PrimitiveTopology.TRIANGLES)
            //?}
            .build()
    )

    // Glint
    @JvmField
    //? if <26.2 {
    /*val POSITION_TEX_OVERLAY = VertexFormat.builder()
        .add("Position", VertexFormatElement.POSITION)
        .add("UV0", VertexFormatElement.UV0)
        .add("UV1", VertexFormatElement.UV1)
    *///?} else {
    val POSITION_TEX_OVERLAY = VertexFormat.builder(0)
        .addAttribute("Position", GpuFormat.RGB32_FLOAT)
        .addAttribute("UV0", GpuFormat.RG32_FLOAT)
        .addAttribute("UV1", GpuFormat.RG16_SINT)
    //?}
        .build()

    @JvmField
    val ARMOR_GLINT = RenderPipelines.GLINT.builder()
        .withLocation(location("pipeline/armor_glint"))
        .withVertexShader(location("core/armor_glint"))
        .withFragmentShader(location("core/armor_glint"))
        //? if <26.2 {
        /*.withSampler("Sampler1")
        .withVertexFormat(POSITION_TEX_OVERLAY, VertexFormat.Mode.QUADS)
        *///?} else {
        .withBindGroupLayout(BindGroupLayouts.SAMPLER1)
        .withVertexFormat(POSITION_TEX_OVERLAY)
        //?}
        .build()
}
//?}
