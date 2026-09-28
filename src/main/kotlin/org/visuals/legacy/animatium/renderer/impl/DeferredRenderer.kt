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

package org.visuals.legacy.animatium.renderer.impl

import com.mojang.blaze3d.ProjectionType
import com.mojang.blaze3d.pipeline.RenderTarget
import com.mojang.blaze3d.systems.RenderSystem
//? if <26.3 {
/*import com.mojang.blaze3d.textures.GpuTextureView
*///?} else {
import com.mojang.renderpearl.api.textures.GpuTextureView
//?}
import net.minecraft.client.Minecraft
//? if 1.21.11 {
/*import net.minecraft.client.renderer.PerspectiveProjectionMatrixBuffer
*///?} else {
import net.minecraft.client.renderer.ProjectionMatrixBuffer
//?}
import org.joml.Matrix4f
import org.visuals.legacy.animatium.renderer.DynamicTransforms
import org.visuals.legacy.animatium.renderer.RenderDescriptor
import org.visuals.legacy.animatium.renderer.buffer.Geometry

class DeferredRenderer(private val descriptor: RenderDescriptor) : AbstractRenderer() {
    companion object {
        @JvmStatic
        fun of(descriptor: RenderDescriptor) = DeferredRenderer(descriptor)

        @JvmStatic
        fun of(name: String, colorTextureView: GpuTextureView, depthTextureView: GpuTextureView?, area: RenderDescriptor.Area) =
            of(RenderDescriptor.builder { name }
                .withColorTexture(colorTextureView)
                .withDepthTexture(depthTextureView)
                .withArea(area)
                .build())

        @JvmStatic
        fun of(name: String, colorTextureView: GpuTextureView, depthTextureView: GpuTextureView?) =
            of(name, colorTextureView, depthTextureView, RenderDescriptor.Area(colorTextureView))

        @JvmStatic
        fun of(name: String, renderTarget: RenderTarget) =
            of(RenderDescriptor.builder { name }
                //? if <26.3 {
                /*.withRenderTarget(renderTarget, false)
                *///?} else {
                .withRenderTarget(renderTarget)
                //?}
                .withArea(RenderDescriptor.Area(renderTarget))
                .build())

        @JvmStatic
        //? if <26.2 {
        /*fun of(name: String) = of(name, Minecraft.getInstance().mainRenderTarget)
        *///?} else {
        fun of(name: String) = of(name, Minecraft.getInstance().gameRenderer.mainRenderTarget())
        //?}
    }

    // Data
    private val name: String = descriptor.name.get()
    private var projectionMatrix: Matrix4f? = null
    //? if 1.21.11 {
    /*private var projectionMatrixBuffer: PerspectiveProjectionMatrixBuffer? = null
    *///?} else {
    private var projectionMatrixBuffer: ProjectionMatrixBuffer? = null
    //?}

    fun setProjectionMatrix(matrix4f: Matrix4f): AbstractRenderer {
        if (this.projectionMatrixBuffer == null) {
            //? if 1.21.11 {
            /*this.projectionMatrixBuffer = PerspectiveProjectionMatrixBuffer("Immediate Projection Buffer for " + this.name)
            *///?} else {
            this.projectionMatrixBuffer = ProjectionMatrixBuffer("Immediate Projection Buffer for " + this.name)
            //?}
        }

        this.projectionMatrix = matrix4f
        return this
    }

    override fun draw(geometry: Geometry) {
        val hasProjectionModifier = this.projectionMatrixBuffer != null && this.projectionMatrix != null
        if (hasProjectionModifier) {
            val properties = this.projectionMatrix!!.properties()
            val projectionType = if ((properties and Matrix4f.PROPERTY_PERSPECTIVE.toInt()) != 0) {
                ProjectionType.PERSPECTIVE
            } else {
                ProjectionType.ORTHOGRAPHIC
            }

            RenderSystem.backupProjectionMatrix()
            RenderSystem.setProjectionMatrix(this.projectionMatrixBuffer!!.getBuffer(this.projectionMatrix!!), projectionType)
        }

        //? if <26.2 {
        /*val dynamicTransforms = this.uniforms.getOrDefault(DynamicTransforms.KEY, DynamicTransforms.current())
        *///?}
        this.descriptor.createPass().use { pass ->
            //? if >=26.2 {
            val dynamicTransforms = this.uniforms.getOrDefault(DynamicTransforms.KEY, DynamicTransforms.current())
            //?}
            this.render(pass, geometry, dynamicTransforms)
            if (!geometry.persistent()) {
                geometry.close()
            }
        }

        if (hasProjectionModifier) {
            RenderSystem.restoreProjectionMatrix()
        }
    }

    fun drawGui(geometry: Geometry) {
        val window = Minecraft.getInstance().window
        this.setProjectionMatrix(
            Matrix4f().setOrtho(
                0.0F,
                window.width.toFloat() / window.guiScale.toFloat(),
                window.height.toFloat() / window.guiScale.toFloat(),
                0.0F,
                1000.0F,
                //? if 1.21.11 {
                /*11000.0F
                *///?} else {
                11000.0F,
                //?}
                //? if 26.1 {
                /*RenderSystem.getDevice().isZZeroToOne
                *///?} elif >=26.2 {
                RenderSystem.getDevice().deviceInfo.isZZeroToOne
                //?}
            )
        )
        this.setUniform(
            DynamicTransforms.KEY,
            DynamicTransforms.builder()
                .withModelViewMatrix(Matrix4f().setTranslation(0.0F, 0.0F, -11000.0F))
                .build()
        )
        this.draw(geometry)
    }

    override fun close() {
        super.close()
        if (this.projectionMatrixBuffer != null) {
            this.projectionMatrixBuffer?.close()
            this.projectionMatrixBuffer = null
        }
    }
}