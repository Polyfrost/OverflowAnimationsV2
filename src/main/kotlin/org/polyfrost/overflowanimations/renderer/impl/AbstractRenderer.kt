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

package org.polyfrost.overflowanimations.renderer.impl

// Built on the GPU device API (1.21.5) and dynamic uniforms / GUI render states (1.21.6)
//? if >=1.21.6 {
//? if <26.3 {
/*import com.mojang.blaze3d.buffers.GpuBuffer
import com.mojang.blaze3d.buffers.GpuBufferSlice
*///?}
//? if 26.2 {
/*import com.mojang.blaze3d.pipeline.BindGroupLayout
*///?}
//? if <26.3 {
/*import com.mojang.blaze3d.pipeline.RenderPipeline
*///?}
//? if <26.2 {
/*import com.mojang.blaze3d.pipeline.RenderPipeline.UniformDescription
*///?}
//? if <26.3 {
/*import com.mojang.blaze3d.systems.RenderPass
*///?} elif 26.4 {
import com.mojang.blaze3d.pipeline.RenderPipeline
//?}
import com.mojang.blaze3d.systems.RenderSystem
//? if <1.21.11 {
/*import com.mojang.blaze3d.textures.GpuTextureView
*///?} elif <26.3 {
/*import com.mojang.blaze3d.textures.GpuSampler
import com.mojang.blaze3d.textures.GpuTextureView
*///?} else {
import com.mojang.renderpearl.api.buffers.GpuBuffer
import com.mojang.renderpearl.api.buffers.GpuBufferSlice
import com.mojang.renderpearl.api.commands.RenderPass
import com.mojang.renderpearl.api.pipeline.BindGroupLayout
//?}
//? if 26.3 {
/*import com.mojang.renderpearl.api.pipeline.RenderPipeline
*///?}
//? if >=26.3 {
import com.mojang.renderpearl.api.pipeline.UniformType
import com.mojang.renderpearl.api.textures.GpuSampler
import com.mojang.renderpearl.api.textures.GpuTextureView
//?}
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import net.minecraft.resources.Identifier
import org.polyfrost.overflowanimations.handler.compatibility.IrisPipeline
import org.polyfrost.overflowanimations.handler.compatibility.IrisUtil
import org.polyfrost.overflowanimations.renderer.DynamicTransforms
import org.polyfrost.overflowanimations.renderer.buffer.Geometry
import org.polyfrost.overflowanimations.renderer.texture.TextureAndSampler

abstract class AbstractRenderer : AutoCloseable {
    //? if >=26.3 {
    companion object {
        private fun flattenSamplers(groups: List<BindGroupLayout>): List<String> {
            return BindGroupLayout.flattenUniforms(groups)
                .stream()
                .filter { it.type == UniformType.COMBINED_IMAGE_SAMPLER }
                .map(BindGroupLayout.UniformDescription::name)
                .toList()
        }
    }
    //?}

    protected var pipeline: RenderPipeline? = null
    protected val textures = Object2ObjectOpenHashMap<String, TextureAndSampler>()
    protected val uniforms = Object2ObjectOpenHashMap<String, GpuBufferSlice>()

    fun setPipeline(pipeline: RenderPipeline, irisPipeline: IrisPipeline): AbstractRenderer {
        this.pipeline = pipeline
        IrisUtil.assignPipeline(pipeline, irisPipeline)
        return this
    }

    fun setPipeline(pipeline: RenderPipeline): AbstractRenderer {
        //? if <26.2 {
        /*val samplers = pipeline.samplers
        *///?} elif 26.2 {
        /*val samplers = BindGroupLayout.flattenSamplers(pipeline.bindGroupLayouts)
        *///?} else {
        val samplers = flattenSamplers(pipeline.bindGroupLayouts)
        //?}
        return this.setPipeline(
            pipeline,
            if (samplers.contains("Sampler0")) {
                IrisPipeline.TEXTURED
            } else {
                IrisPipeline.BASIC
            }
        )
    }

    fun setTexture(name: String, textureAndSampler: TextureAndSampler): AbstractRenderer {
        this.textures[name] = textureAndSampler
        return this
    }

    //? if <1.21.11 {
    /*fun setTexture(name: String, textureView: GpuTextureView) = this.setTexture(name, TextureAndSampler(textureView))
    *///?} else {
    fun setTexture(name: String, textureView: GpuTextureView, sampler: GpuSampler) = this.setTexture(name, TextureAndSampler(textureView, sampler))
    //?}

    fun setTexture(name: String, location: Identifier) = this.setTexture(name, TextureAndSampler.get(location))

    fun setUniform(name: String, data: GpuBufferSlice): AbstractRenderer {
        this.uniforms[name] = data
        return this
    }

    fun setUniform(name: String, data: GpuBuffer) = this.setUniform(name, data.slice())

    abstract fun draw(geometry: Geometry)

    protected fun render(pass: RenderPass, geometry: Geometry, dynamicTransforms: GpuBufferSlice) {
        val pipeline = this.pipeline ?: throw RuntimeException("Cannot render, pipeline is null!")
        if (geometry.isClosed()) {
            throw RuntimeException("Cannot render, the provided geometry has already been closed!")
        } else {
            //? if <26.3 {
            /*pass.setPipeline(pipeline)
            *///?} else {
            pass.setPipeline(RenderSystem.getCompiledPipeline(pipeline))
            //?}

            //? if <26.2 {
            /*val descriptions = pipeline.uniforms
            *///?} else {
            val bindGroupLayouts = pipeline.bindGroupLayouts
            val descriptions = BindGroupLayout.flattenUniforms(bindGroupLayouts)
            //?}
                .stream()
                //? if <26.2 {
                /*.map(UniformDescription::name)
                *///?} elif >=26.3 {
                .filter { it.type == UniformType.UNIFORM_BUFFER }
                //?}
                //? if >=26.2 {
                .map(BindGroupLayout.UniformDescription::name)
                //?}
                .toList()

            //? if <26.2 {
            /*val indexBuffer = RenderSystem.getSequentialBuffer(pipeline.vertexFormatMode)
            *///?} else {
            val indexBuffer = RenderSystem.getSequentialBuffer(pipeline.primitiveTopology)
            //?}
            RenderSystem.bindDefaultUniforms(pass)
            pass.setUniform(DynamicTransforms.KEY, dynamicTransforms)
            for (entry in this.uniforms) {
                val name = entry.key
                if (name == DynamicTransforms.KEY) {
                    continue // Special Handling Above
                }

                if (descriptions.contains(name)) {
                    pass.setUniform(entry.key, entry.value)
                }
            }

            //? if <26.2 {
            /*val samplers = pipeline.samplers
            *///?} elif 26.2 {
            /*val samplers = BindGroupLayout.flattenSamplers(bindGroupLayouts)
            *///?} else {
            val samplers = flattenSamplers(bindGroupLayouts)
            //?}
            for (entry in this.textures) {
                val name = entry.key
                if (samplers.contains(name)) {
                    //? if <1.21.11 {
                    /*pass.bindSampler(name, entry.value.textureView)
                    *///?} elif <26.3 {
                    /*pass.bindTexture(name, entry.value.textureView, entry.value.sampler)
                    *///?} else {
                    pass.setUniform(name, entry.value.textureView, entry.value.sampler)
                    //?}
                }
            }

            geometry.bind(pass, indexBuffer)
            geometry.draw(pass)
        }
    }

    override fun close() {
        this.textures.clear()
        this.uniforms.clear()
    }
}
//?}
