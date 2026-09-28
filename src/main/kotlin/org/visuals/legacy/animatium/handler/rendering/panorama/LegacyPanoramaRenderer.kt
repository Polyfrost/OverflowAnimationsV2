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

package org.visuals.legacy.animatium.handler.rendering.panorama

//? if 26.2 {
/*import com.mojang.blaze3d.GpuFormat
*///?}
import com.mojang.blaze3d.pipeline.MainTarget
import com.mojang.blaze3d.systems.RenderSystem
//? if <26.3 {
/*import com.mojang.blaze3d.textures.FilterMode
import com.mojang.blaze3d.textures.GpuTexture
import com.mojang.blaze3d.textures.GpuTextureView
*///?}
//? if <26.2 {
/*import com.mojang.blaze3d.textures.TextureFormat
*///?} elif >=26.3 {
import com.mojang.renderpearl.api.GpuFormat
import com.mojang.renderpearl.api.textures.FilterMode
import com.mojang.renderpearl.api.textures.GpuTexture
import com.mojang.renderpearl.api.textures.GpuTextureView
//?}
import net.minecraft.client.Minecraft
//? if 1.21.11 {
/*import net.minecraft.client.gui.GuiGraphics
*///?} else {
import net.minecraft.client.gui.GuiGraphicsExtractor
//?}
import net.minecraft.resources.Identifier
import net.minecraft.util.ARGB
import net.minecraft.util.Mth
import org.joml.Matrix3x2f
import org.joml.Matrix4f
//? if >=26.2 {
import org.joml.Vector4f
//?}
import org.visuals.legacy.animatium.handler.rendering.copyTextureToTexture
import org.visuals.legacy.animatium.handler.rendering.pipeline.AnimatiumPipelines
import org.visuals.legacy.animatium.renderer.DynamicTransforms
import org.visuals.legacy.animatium.renderer.RenderDescriptor
import org.visuals.legacy.animatium.renderer.buffer.Geometry
import org.visuals.legacy.animatium.renderer.buffer.IndexedGeometry
import org.visuals.legacy.animatium.renderer.impl.DeferredRenderer
import org.visuals.legacy.animatium.renderer.vertex.VertexLayouts
import org.visuals.legacy.animatium.util.profile
import org.visuals.legacy.animatium.util.toRadians
import java.util.*

// Ported code of the old <=1.12.2 panorama renderer (w/ blur)
class LegacyPanoramaRenderer : AutoCloseable {
    companion object {
        private const val SAMPLES = 64
        private val VIEWPORT = RenderDescriptor.Area(256, 256)
        //? if <26.2 {
        /*private val CLEAR_COLOR = ARGB.colorFromFloat(1.0F, 0.0F, 0.0F, 0.0F)
        *///?} else {
        private val CLEAR_COLOR = Vector4f(0.0F, 0.0F, 0.0F, 1.0F)
        //?}
        private val CUBE_MAP_LOCATION = Identifier.withDefaultNamespace("textures/gui/title/background/panorama")
        private val CUBE_MAP_PROJECTION = Matrix4f().setPerspective(toRadians(120.0F), 1.0F, 0.05F, 10.0F)

        private val CUBE_MAP_GEOMETRY =
            IndexedGeometry.compilePersistent(VertexLayouts.POSITIONED_QUAD, 24) { vertexConsumer ->
                for (panoramaIdx in 0..<6) {
                    val pose = Matrix4f()
                    when (panoramaIdx) {
                        1 -> pose.rotateY(toRadians(90.0F))
                        2 -> pose.rotateY(toRadians(180.0F))
                        3 -> pose.rotateY(toRadians(-90.0F))
                        4 -> pose.rotateX(toRadians(90.0F))
                        5 -> pose.rotateX(toRadians(-90.0F))
                    }

                    vertexConsumer.addVertex(pose, -1.0F, -1.0F, 1.0F)
                    vertexConsumer.addVertex(pose, 1.0F, -1.0F, 1.0F)
                    vertexConsumer.addVertex(pose, 1.0F, 1.0F, 1.0F)
                    vertexConsumer.addVertex(pose, -1.0F, 1.0F, 1.0F)
                }
            }

        @JvmField
        val INSTANCE = LegacyPanoramaRenderer()
    }

    private val panoramaTarget = MainTarget(256, 256)
    private val backgroundTexture: GpuTexture
    private val backgroundTextureView: GpuTextureView

    private var state: LegacyPanoramaRenderState? = null

    init {
        val device = RenderSystem.getDevice()
        this.backgroundTexture = device.createTexture(
            { "Legacy Panorama Temp Texture" },
            GpuTexture.USAGE_TEXTURE_BINDING or GpuTexture.USAGE_RENDER_ATTACHMENT or GpuTexture.USAGE_COPY_SRC or GpuTexture.USAGE_COPY_DST,
            //? if <26.2 {
            /*TextureFormat.RGBA8,
            *///?} else {
            GpuFormat.RGBA8_UNORM,
            //?}
            this.panoramaTarget.width,
            this.panoramaTarget.height,
            1,
            1
        )
        this.backgroundTextureView = device.createTextureView(this.backgroundTexture)
        device.createCommandEncoder().clearColorAndDepthTextures(
            this.panoramaTarget.getColorTexture()!!,
            CLEAR_COLOR,
            this.panoramaTarget.getDepthTexture()!!,
            0.0
        )
        device.createCommandEncoder().clearColorTexture(this.backgroundTexture, CLEAR_COLOR)
    }

    fun render() {
        this.state?.let {
            profile("panorama") {
                val xRot = Mth.sin(it.spin / 400.0) * 25.0F + 20.0F
                val yRot = -it.spin * 0.1F
                this.renderCubeMap(xRot, yRot)
                this.rotateAndBlurCubeMap(it.pose, it.width, it.height)
            }
        }
    }

    //? if 1.21.11 {
    /*fun extractRenderState(graphics: GuiGraphics, width: Int, height: Int, tickDelta: Float) {
        val panoramaSpeed = Minecraft.getInstance().options.panoramaSpeed().get()
    *///?} else {
    fun extractRenderState(graphics: GuiGraphicsExtractor, width: Int, height: Int, tickDelta: Float) {
    //?}
        //? if 26.1 {
        /*val panoramaSpeed = Minecraft.getInstance().gameRenderer.gameRenderState.optionsRenderState.panoramaSpeed
        *///?} elif >=26.2 {
        val panoramaSpeed = Minecraft.getInstance().gameRenderer.gameRenderState().optionsRenderState.panoramaSpeed
        //?}

        val lastSpin = this.state?.spin ?: 0.0F
        val newSpin = (lastSpin + (tickDelta * panoramaSpeed)).toFloat()

        this.state = LegacyPanoramaRenderState(graphics.pose(), width, height, newSpin)
        //? if 1.21.11 {
        /*graphics.guiRenderState.submitGuiElement(
        *///?} else {
        graphics.guiRenderState.addGuiElement(
        //?}
            LegacyPanoramaBlitTexture(graphics.pose(), this.backgroundTextureView, width, height)
        )
    }

    private fun renderCubeMap(xRot: Float, yRot: Float) {
        DeferredRenderer.of(descriptor("Legacy Panorama Cubemap")).use { renderer ->
            renderer.setPipeline(AnimatiumPipelines.LEGACY_PANORAMA_1)
            renderer.setTexture("Sampler0", CUBE_MAP_LOCATION)
            renderer.setProjectionMatrix(CUBE_MAP_PROJECTION)
            for (layer in 0..<SAMPLES) {
                val x = (layer % 8 / 8.0F - 0.5F) / SAMPLES
                val y = (layer.toFloat() / 8 / 8.0F - 0.5F) / SAMPLES
                val modelViewMatrix = Matrix4f()
                    .rotateX(toRadians(180.0F))
                    .rotateZ(toRadians(90.0F))
                    .translate(x, y, 0.0F)
                    .rotateX(toRadians(xRot))
                    .rotateY(toRadians(yRot))
                renderer.setUniform(
                    DynamicTransforms.KEY,
                    DynamicTransforms.builder()
                        .withModelViewMatrix(modelViewMatrix)
                        .withShaderColor(ARGB.white(1.0F / (layer + 1.0F)))
                        .build()
                )
                renderer.draw(CUBE_MAP_GEOMETRY)
                if (layer == 0) {
                    renderer.setPipeline(AnimatiumPipelines.LEGACY_PANORAMA_2)
                }
            }
        }
    }

    private fun rotateAndBlurCubeMap(pose: Matrix3x2f, width: Int, height: Int) {
        for (pass in 0..<7) {
            copyTextureToTexture(
                Objects.requireNonNull(this.panoramaTarget.getColorTexture())!!,
                this.backgroundTexture
            )

            DeferredRenderer.of(descriptor("Legacy Panorama Blur")).use { renderer ->
                renderer.setPipeline(AnimatiumPipelines.LEGACY_PANORAMA_BLUR)
                renderer.setTexture(
                    "Sampler0",
                    this.backgroundTextureView,
                    RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR)
                )
                renderer.drawGui(Geometry.texturedScreenQuad(pose, width, height))
            }
        }
    }

    private fun descriptor(name: String) = RenderDescriptor.builder({ name })
        .withRenderTarget(this.panoramaTarget)
        .withArea(VIEWPORT)
        .build()

    override fun close() {
        CUBE_MAP_GEOMETRY.close()
        this.backgroundTextureView.close()
        this.backgroundTexture.close()
        this.panoramaTarget.destroyBuffers()
    }
}