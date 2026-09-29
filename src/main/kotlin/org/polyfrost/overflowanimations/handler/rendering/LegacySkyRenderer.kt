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

package org.polyfrost.overflowanimations.handler.rendering

// Built on the GPU device API (1.21.5) and dynamic uniforms / GUI render states (1.21.6)
//? if >=1.21.6 {
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.VertexConsumer
//? if >=26.3 {
import com.mojang.renderpearl.api.commands.RenderPass
//?}
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.util.ARGB
//? if <26.2 {
/*import org.joml.Matrix4f
*///?}
import org.joml.Vector4f
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig
import org.polyfrost.overflowanimations.handler.compatibility.IrisPipeline
import org.polyfrost.overflowanimations.handler.rendering.pipeline.OverflowAnimationsPipelines
import org.polyfrost.overflowanimations.renderer.DynamicTransforms
import org.polyfrost.overflowanimations.renderer.buffer.IndexedGeometry
//? if <26.3 {
/*import org.polyfrost.overflowanimations.renderer.impl.DeferredRenderer
*///?} else {
import org.polyfrost.overflowanimations.renderer.impl.WrappedRenderer
//?}
import org.polyfrost.overflowanimations.renderer.vertex.VertexLayouts
import org.polyfrost.overflowanimations.util.profile

object LegacySkyRenderer {
    private val GET_VOID_BOX_GEOMETRY = { offset: Float ->
        IndexedGeometry.compile(VertexLayouts.POSITIONED_COLOR_QUAD, 20) { vertexConsumer ->
            val color = ARGB.opaque(0)
            vertexConsumer.apply {
                // Left
                addVertex(-1.0F, offset, 1.0F).setColor(color)
                addVertex(1.0F, offset, 1.0F).setColor(color)
                addVertex(1.0F, -1.0F, 1.0F).setColor(color)
                addVertex(-1.0F, -1.0F, 1.0F).setColor(color)

                // Right
                addVertex(-1.0F, -1.0F, -1.0F).setColor(color)
                addVertex(1.0F, -1.0F, -1.0F).setColor(color)
                addVertex(1.0F, offset, -1.0F).setColor(color)
                addVertex(-1.0F, offset, -1.0F).setColor(color)

                // Back
                addVertex(1.0F, -1.0F, -1.0F).setColor(color)
                addVertex(1.0F, -1.0F, 1.0F).setColor(color)
                addVertex(1.0F, offset, 1.0F).setColor(color)
                addVertex(1.0F, offset, -1.0F).setColor(color)

                // Front
                addVertex(-1.0F, offset, -1.0F).setColor(color)
                addVertex(-1.0F, offset, 1.0F).setColor(color)
                addVertex(-1.0F, -1.0F, 1.0F).setColor(color)
                addVertex(-1.0F, -1.0F, -1.0F).setColor(color)

                // Bottom
                addVertex(-1.0F, -1.0F, -1.0F).setColor(color)
                addVertex(-1.0F, -1.0F, 1.0F).setColor(color)
                addVertex(1.0F, -1.0F, 1.0F).setColor(color)
                addVertex(1.0F, -1.0F, -1.0F).setColor(color)
            }
        }
    }

    //? if <26.3 {
    /*@JvmField
    val TOP_GEOMETRY = IndexedGeometry.compilePersistent(VertexLayouts.POSITIONED_QUAD, 676) { vertexConsumer ->
        buildSkyHalf(
            vertexConsumer,
            16.0F,
            false
        )
    }
    *///?} else {
    lateinit var TOP_GEOMETRY: IndexedGeometry
    //?}

    //? if <26.3 {
    /*@JvmField
    val BOTTOM_GEOMETRY = IndexedGeometry.compilePersistent(VertexLayouts.POSITIONED_QUAD, 676) { vertexConsumer ->
        buildSkyHalf(
            vertexConsumer,
            -16.0F,
            true
        )
    *///?} else {
    lateinit var BOTTOM_GEOMETRY: IndexedGeometry
    //?}

    //? if >=26.3 {
    @JvmStatic
    fun initialize() {
        TOP_GEOMETRY = IndexedGeometry.compilePersistent(VertexLayouts.POSITIONED_QUAD, 676) { vertexConsumer ->
            buildSkyHalf(
                vertexConsumer,
                16.0F,
                false
            )
        }
    //?}

        //? if >=26.3 {
        BOTTOM_GEOMETRY = IndexedGeometry.compilePersistent(VertexLayouts.POSITIONED_QUAD, 676) { vertexConsumer ->
            buildSkyHalf(
                vertexConsumer,
                -16.0F,
                true
            )
        }
        //?}
    }

    @JvmStatic
    //? if <26.3 {
    /*fun renderBlueVoid(skyColor: Int, depth: Double) {
    *///?} else {
    fun renderBlueVoid(pass: RenderPass, skyColor: Int, depth: Double) {
    //?}
        profile("blue_void") {
            //? if <26.3 {
            /*DeferredRenderer.of("Lower Sky Disc").use { renderer ->
            *///?}
            //? if <26.2 {
                /*val matrix = Matrix4f(RenderSystem.getModelViewMatrix()).translate(
            *///?} elif >=26.3 {
            WrappedRenderer.of(pass).use { renderer ->
                pass.pushDebugGroup({ "Lower Sky Disc" })
            //?}
                //? if >=26.2 {
                val matrix = RenderSystem.getModelViewMatrixCopy().translate(
                //?}
                    0.0F,
                    -((depth - 16.0).toFloat()),
                    0.0F
                )

                val color = Vector4f(
                    ARGB.redFloat(skyColor) * 0.2F + 0.04F,
                    ARGB.greenFloat(skyColor) * 0.2F + 0.04F,
                    ARGB.blueFloat(skyColor) * 0.6F + 0.1F,
                    1.0F
                )

                renderer.setPipeline(
                    OverflowAnimationsPipelines.getSkyPipeline(OverflowAnimationsConfig.instance().other.planarSkyFog),
                    IrisPipeline.SKY_BASIC
                )

                renderer.setUniform(
                    DynamicTransforms.KEY,
                    DynamicTransforms.builder()
                        .withModelViewMatrix(matrix)
                        .withShaderColor(color)
                        .build()
                )

                renderer.draw(BOTTOM_GEOMETRY)
                //? if >=26.3 {
                pass.popDebugGroup()
                //?}
            }
        }
    }

    @JvmStatic
    fun getHorizonEyeHeight(level: ClientLevel, tickDelta: Float) =
        Minecraft.getInstance().player!!.getEyePosition(tickDelta).y - level.getLevelData().getHorizonHeight(level)

    // TODO/NOTE: Figure out why its rendering differently than in 18w07a (last snapshot to have it)
    @JvmStatic
    //? if <26.3 {
    /*fun renderVoidBox(depth: Double) {
    *///?} else {
    fun renderVoidBox(pass: RenderPass, depth: Double) {
    //?}
        profile("player_void_box") {
            //? if <26.3 {
            /*DeferredRenderer.of("Player Void Box").use { renderer ->
            *///?} else {
            WrappedRenderer.of(pass).use { renderer ->
                pass.pushDebugGroup({ "Player Void Box" })
            //?}
                renderer.setPipeline(OverflowAnimationsPipelines.VOID_BOX)
                renderer.draw(GET_VOID_BOX_GEOMETRY(-((depth + 65.0).toFloat())))
                //? if >=26.3 {
                pass.popDebugGroup()
                //?}
            }
        }
    }

    private fun buildSkyHalf(vertexConsumer: VertexConsumer, y: Float, bottom: Boolean) {
        val width = 64
        for (k in -384..384 step width) {
            for (l in -384..384 step width) {
                var g = k
                var h = k + width
                if (bottom) {
                    // Swap them
                    val b = g
                    g = h
                    h = b
                }

                vertexConsumer.addVertex(g.toFloat(), y, l.toFloat())
                vertexConsumer.addVertex(h.toFloat(), y, l.toFloat())
                vertexConsumer.addVertex(h.toFloat(), y, (l + width).toFloat())
                vertexConsumer.addVertex(g.toFloat(), y, (l + width).toFloat())
            }
        }
    }

    @JvmStatic
    fun close() {
        TOP_GEOMETRY.close()
        BOTTOM_GEOMETRY.close()
    }
}
//?}
