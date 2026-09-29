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
//? if 26.2 {
/*import com.mojang.blaze3d.pipeline.BindGroupLayout
*///?}
//? if <26.3 {
/*import com.mojang.blaze3d.pipeline.RenderPipeline
*///?}
//? if 26.2 {
/*import com.mojang.blaze3d.vertex.VertexFormat
*///?} elif 26.4 {
import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.vertex.VertexFormat
//?}
//? if >=26.3 {
import com.mojang.renderpearl.api.pipeline.BindGroupLayout
//?}
//? if 26.3 {
/*import com.mojang.renderpearl.api.pipeline.RenderPipeline
*///?}
//? if >=26.3 {
import com.mojang.renderpearl.api.pipeline.ShaderType
//?}
//? if 26.3 {
/*import com.mojang.renderpearl.api.vertex.VertexFormat
*///?}
//? if >=26.1 {
import java.util.*
//?}

//? if >=26.2 {
fun RenderPipeline.Builder.withVertexFormat(vertexFormat: VertexFormat) = withVertexBinding(0, vertexFormat)
//?}

//? if >=26.2 {
fun RenderPipeline.Builder.withBindGroupLayouts(vararg layouts: BindGroupLayout): RenderPipeline.Builder {
    for (layout in layouts) {
        this.withBindGroupLayout(layout)
    }
//?}

//? if >=26.2 {
    return this
}
//?}

//? if <26.3 {
/*fun RenderPipeline.builder() = RenderPipeline.builder().apply {
    this.withVertexShader(vertexShader)
    this.withFragmentShader(fragmentShader)
*///?} else {
fun RenderPipeline.builder() = builderIgnoreDefines()
//?}

//? if >=26.3 {
fun RenderPipeline.builderIgnoreDefines(vararg ignoreDefines: String) = RenderPipeline.builder().apply {
    for ((type, path) in shaders) {
        if (type == ShaderType.VERTEX) {
            this.withVertexShader(path)
        } else if (type == ShaderType.FRAGMENT) {
            this.withFragmentShader(path)
        }
    }
//?}

    this.withPolygonMode(polygonMode)
    //? if <26.1 {
    /*this.withColorWrite(isWriteColor, isWriteAlpha)
    this.withDepthWrite(isWriteDepth)
    this.withDepthTestFunction(depthTestFunction)
    this.withDepthBias(depthBiasScaleFactor, depthBiasConstant)
    *///?} elif 26.1 {
    /*this.withColorTargetState(colorTargetState)
    *///?} else {
    this.withCull(isCull)
    //?}

    //? if >=26.1 {
    this.withDepthStencilState(Optional.ofNullable(depthStencilState))
    //?}
    //? if <26.2 {
    /*this.withCull(isCull)
    this.withVertexFormat(vertexFormat, vertexFormatMode)
    *///?}

    //? if <26.1 {
    /*if (blendFunction.isPresent) {
        this.withBlend(blendFunction.get())
    } else {
        this.withoutBlend()
    }
    *///?} elif >=26.2 {
    for ((index, state) in colorTargetStates.withIndex()) {
        if (state != null) {
            this.withColorTargetState(index, state)
        } else {
            this.withUnusedColorTargetState(index)
        }
    }
    //?}

    //? if >=26.2 {
    this.withPrimitiveTopology(primitiveTopology)
    for ((binding, vertexFormat) in vertexFormatBindings.withIndex()) {
        if (vertexFormat != null) {
            this.withVertexBinding(binding, vertexFormat)
        }
    }
    //?}

    for (define in shaderDefines.values) {
        //? if >=26.3 {
        if (define.key in ignoreDefines) {
            continue
        }
        //?}

        this.withShaderDefine(define.key) // TODO: Int/Float value
    }

    //? if <26.2 {
    /*for (description in uniforms) {
        this.withUniform(description.name, description.type)
    }
    *///?}

    //? if <26.2 {
    /*for (sampler in samplers) {
        this.withSampler(sampler)
    *///?} else {
    for (layout in bindGroupLayouts) {
        this.withBindGroupLayout(layout)
    //?}
    }
}
//?}
