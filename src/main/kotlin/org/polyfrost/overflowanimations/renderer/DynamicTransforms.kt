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

package org.polyfrost.overflowanimations.renderer

// Built on the GPU device API (1.21.5) and dynamic uniforms / GUI render states (1.21.6)
//? if >=1.21.6 {
import com.mojang.blaze3d.systems.RenderSystem
//? if >=26.3 {
import com.mojang.renderpearl.api.buffers.GpuBufferSlice
//?}
import net.minecraft.util.ARGB
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f

object DynamicTransforms {
    const val KEY = "DynamicTransforms"

    @JvmStatic
    fun builder() = Builder()

    @JvmStatic
    fun current() = builder().build()

    class Builder {
        private var modelViewMatrix: Matrix4f? = null
        private var textureMatrix: Matrix4f? = null
        private var shaderColor = Vector4f(1.0F)
        private var modelOffset = Vector3f()

        fun withModelViewMatrix(matrix4f: Matrix4f): Builder {
            this.modelViewMatrix = matrix4f
            return this
        }

        fun withTextureMatrix(matrix4f: Matrix4f): Builder {
            this.textureMatrix = matrix4f
            return this
        }

        fun withShaderColor(vector4f: Vector4f): Builder {
            this.shaderColor = vector4f
            return this
        }

        fun withShaderColor(red: Float, green: Float, blue: Float, alpha: Float) =
            this.withShaderColor(Vector4f(red, green, blue, alpha))

        fun withShaderColor(red: Float, green: Float, blue: Float) =
            this.withShaderColor(red, green, blue, 1.0F)

        fun withShaderColor(color: Int) = this.withShaderColor(
            ARGB.redFloat(color),
            ARGB.greenFloat(color),
            ARGB.blueFloat(color),
            ARGB.alphaFloat(color)
        )

        fun withModelOffset(vector3f: Vector3f): Builder {
            this.modelOffset = vector3f
            return this
        }

        fun build() = RenderSystem.getDynamicUniforms().writeTransform(
            //? if <26.2 {
            /*this.modelViewMatrix ?: Matrix4f(RenderSystem.getModelViewMatrix()),
            *///?} else {
            this.modelViewMatrix ?: RenderSystem.getModelViewMatrixCopy(),
            //?}
            this.shaderColor,
            this.modelOffset,
            //? if <1.21.11 {
            /*this.textureMatrix ?: Matrix4f(),
            RenderSystem.getShaderLineWidth()
            *///?} else {
            this.textureMatrix ?: Matrix4f()
            //?}
        )
    }
}
//?}
