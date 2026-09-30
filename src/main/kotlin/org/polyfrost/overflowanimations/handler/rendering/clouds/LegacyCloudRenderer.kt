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

package org.polyfrost.overflowanimations.handler.rendering.clouds

// Built on the GPU device API (1.21.5) and dynamic uniforms / GUI render states (1.21.6)
//? if >=1.21.6 {
//? if <26.3 {
/*import com.mojang.blaze3d.buffers.GpuBuffer
import com.mojang.blaze3d.pipeline.RenderPipeline
*///?} elif 26.4 {
/*import com.mojang.blaze3d.pipeline.RenderPipeline
*///?}
import com.mojang.blaze3d.platform.NativeImage
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.ByteBufferBuilder
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexConsumer
//? if >=26.3 {
import com.mojang.renderpearl.api.buffers.GpuBuffer
import com.mojang.renderpearl.api.commands.RenderPass
//?}
//? if 26.3 {
import com.mojang.renderpearl.api.pipeline.RenderPipeline
//?}
import net.minecraft.client.CloudStatus
//? if <26.3 {
/*import net.minecraft.client.Minecraft
*///?}
import net.minecraft.client.renderer.CloudRenderer
import net.minecraft.client.renderer.CloudRenderer.RelativeCameraPos
import net.minecraft.client.renderer.CloudRenderer.TextureData
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.SimplePreparableReloadListener
import net.minecraft.util.ARGB
import net.minecraft.util.Mth
import net.minecraft.util.profiling.ProfilerFiller
import net.minecraft.world.phys.Vec3
import org.joml.Vector3f
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig
import org.polyfrost.overflowanimations.handler.rendering.pipeline.OverflowAnimationsPipelines
import org.polyfrost.overflowanimations.renderer.DynamicTransforms
import org.polyfrost.overflowanimations.renderer.buffer.IndexedGeometry
//? if <26.3 {
/*import org.polyfrost.overflowanimations.renderer.impl.DeferredRenderer
*///?} else {
import org.polyfrost.overflowanimations.renderer.impl.WrappedRenderer
//?}
import org.polyfrost.overflowanimations.renderer.vertex.VertexLayouts
//? if <26.3 {
/*import org.polyfrost.overflowanimations.util.profile
*///?}
import java.io.IOException
import java.util.*
import kotlin.math.abs

/**
 * CODE SOURCED FROM 1.21.5 AND MODIFIED TO WORK IN LATEST
 * THIS CODE WAS MADE BY MOJANG STUDIOS
 */
class LegacyCloudRenderer : SimplePreparableReloadListener<Optional<TextureData>>(), AutoCloseable {
    companion object {
        @JvmField
        val INSTANCE = LegacyCloudRenderer()
    }

    private var needsRebuild = true
    private var prevRelativeCameraPos = RelativeCameraPos.INSIDE_CLOUDS
    private lateinit var prevType: CloudStatus
    private var prevCellX = Integer.MIN_VALUE
    private var prevCellZ = Integer.MIN_VALUE
    private var textureData: TextureData? = null

    private var geometry: IndexedGeometry? = null
    private var vertexBuffer: GpuBuffer? = null
    private var indexCount = 0

    private fun setupMesh(
        cellX: Int,
        cellZ: Int,
        cloudStatus: CloudStatus,
        relativeCameraPos: RelativeCameraPos
    ) {
        val colorA = ARGB.colorFromFloat(0.8F, 0.7F, 0.7F, 0.7F)
        val colorB = ARGB.colorFromFloat(0.8F, 1.0F, 1.0F, 1.0F)
        val colorC = ARGB.colorFromFloat(0.8F, 0.9F, 0.9F, 0.9F)
        val colorD = ARGB.colorFromFloat(0.8F, 0.8F, 0.8F, 0.8F)
        ByteBufferBuilder(DefaultVertexFormat.POSITION_COLOR.vertexSize * 212992).use { byteBufferBuilder ->
            val builder = VertexLayouts.POSITIONED_COLOR_QUAD.buffer(byteBufferBuilder)
            this.buildMesh(relativeCameraPos, builder, cellX, cellZ, colorA, colorB, colorC, colorD, cloudStatus == CloudStatus.FANCY)
            builder.build().use { meshData ->
                val indexCount = meshData?.drawState()?.indexCount() ?: 0
                if (meshData != null) {
                    if (this.vertexBuffer != null && this.vertexBuffer!!.size() >= meshData.vertexBuffer().remaining()) {
                        RenderSystem.getDevice().createCommandEncoder().writeToBuffer(this.vertexBuffer!!.slice(), meshData.vertexBuffer())
                    } else {
                        if (this.geometry != null) {
                            this.geometry?.close()
                        }

                        this.vertexBuffer = RenderSystem.getDevice()
                            .createBuffer({ "Legacy Cloud vertex buffer" }, GpuBuffer.USAGE_VERTEX or GpuBuffer.USAGE_COPY_DST, meshData.vertexBuffer())
                        this.geometry = IndexedGeometry(VertexLayouts.POSITIONED_COLOR_QUAD, this.vertexBuffer!!, indexCount, true)
                    }
                }

                this.indexCount = indexCount
            }
        }
    }

    private fun buildMesh(
        relativeCameraPos: RelativeCameraPos,
        vertexConsumer: VertexConsumer,
        cellX: Int,
        cellZ: Int,
        colorA: Int,
        colorB: Int,
        colorC: Int,
        colorD: Int,
        fancy: Boolean
    ) {
        if (this.textureData != null) {
            val width = this.textureData!!.width()
            for (z in -32..32) {
                val modX = Math.floorMod(cellZ + z, this.textureData!!.height())
                for (x in -32..32) {
                    val modZ = Math.floorMod(cellX + x, width)
                    val cellData = this.textureData!!.cells()[modZ + modX * width]
                    if (cellData != 0L) {
                        val cellColor = (cellData shr 4 and 4294967295L).toInt()
                        if (fancy) {
                            val bottomColor = ARGB.multiply(colorA, cellColor)
                            val topColor = ARGB.multiply(colorB, cellColor)
                            val sideColor = ARGB.multiply(colorC, cellColor)
                            val frontColor = ARGB.multiply(colorD, cellColor)
                            val x0 = x * 12.0F
                            val x1 = x0 + 12.0F
                            val z0 = z * 12.0F
                            val z1 = z0 + 12.0F
                            if (relativeCameraPos != RelativeCameraPos.BELOW_CLOUDS) {
                                vertexConsumer.addVertex(x0, 4.0F, z0).setColor(topColor)
                                vertexConsumer.addVertex(x0, 4.0F, z1).setColor(topColor)
                                vertexConsumer.addVertex(x1, 4.0F, z1).setColor(topColor)
                                vertexConsumer.addVertex(x1, 4.0F, z0).setColor(topColor)
                            }

                            if (relativeCameraPos != RelativeCameraPos.ABOVE_CLOUDS) {
                                vertexConsumer.addVertex(x1, 0.0F, z0).setColor(bottomColor)
                                vertexConsumer.addVertex(x1, 0.0F, z1).setColor(bottomColor)
                                vertexConsumer.addVertex(x0, 0.0F, z1).setColor(bottomColor)
                                vertexConsumer.addVertex(x0, 0.0F, z0).setColor(bottomColor)
                            }

                            if (CloudRenderer.isNorthEmpty(cellData) && z > 0) {
                                vertexConsumer.addVertex(x0, 0.0F, z0).setColor(frontColor)
                                vertexConsumer.addVertex(x0, 4.0F, z0).setColor(frontColor)
                                vertexConsumer.addVertex(x1, 4.0F, z0).setColor(frontColor)
                                vertexConsumer.addVertex(x1, 0.0F, z0).setColor(frontColor)
                            }

                            if (CloudRenderer.isSouthEmpty(cellData) && z < 0) {
                                vertexConsumer.addVertex(x1, 0.0F, z1).setColor(frontColor)
                                vertexConsumer.addVertex(x1, 4.0F, z1).setColor(frontColor)
                                vertexConsumer.addVertex(x0, 4.0F, z1).setColor(frontColor)
                                vertexConsumer.addVertex(x0, 0.0F, z1).setColor(frontColor)
                            }

                            if (CloudRenderer.isWestEmpty(cellData) && x > 0) {
                                vertexConsumer.addVertex(x0, 0.0F, z1).setColor(sideColor)
                                vertexConsumer.addVertex(x0, 4.0F, z1).setColor(sideColor)
                                vertexConsumer.addVertex(x0, 4.0F, z0).setColor(sideColor)
                                vertexConsumer.addVertex(x0, 0.0F, z0).setColor(sideColor)
                            }

                            if (CloudRenderer.isEastEmpty(cellData) && x < 0) {
                                vertexConsumer.addVertex(x1, 0.0F, z0).setColor(sideColor)
                                vertexConsumer.addVertex(x1, 4.0F, z0).setColor(sideColor)
                                vertexConsumer.addVertex(x1, 4.0F, z1).setColor(sideColor)
                                vertexConsumer.addVertex(x1, 0.0F, z1).setColor(sideColor)
                            }

                            if (abs(x) <= 1 && abs(z) <= 1) {
                                vertexConsumer.addVertex(x1, 4.0F, z0).setColor(topColor)
                                vertexConsumer.addVertex(x1, 4.0F, z1).setColor(topColor)
                                vertexConsumer.addVertex(x0, 4.0F, z1).setColor(topColor)
                                vertexConsumer.addVertex(x0, 4.0F, z0).setColor(topColor)

                                vertexConsumer.addVertex(x0, 0.0F, z0).setColor(bottomColor)
                                vertexConsumer.addVertex(x0, 0.0F, z1).setColor(bottomColor)
                                vertexConsumer.addVertex(x1, 0.0F, z1).setColor(bottomColor)
                                vertexConsumer.addVertex(x1, 0.0F, z0).setColor(bottomColor)

                                vertexConsumer.addVertex(x1, 0.0F, z0).setColor(frontColor)
                                vertexConsumer.addVertex(x1, 4.0F, z0).setColor(frontColor)
                                vertexConsumer.addVertex(x0, 4.0F, z0).setColor(frontColor)
                                vertexConsumer.addVertex(x0, 0.0F, z0).setColor(frontColor)
                                vertexConsumer.addVertex(x0, 0.0F, z1).setColor(frontColor)
                                vertexConsumer.addVertex(x0, 4.0F, z1).setColor(frontColor)
                                vertexConsumer.addVertex(x1, 4.0F, z1).setColor(frontColor)
                                vertexConsumer.addVertex(x1, 0.0F, z1).setColor(frontColor)

                                vertexConsumer.addVertex(x0, 0.0F, z0).setColor(sideColor)
                                vertexConsumer.addVertex(x0, 4.0F, z0).setColor(sideColor)
                                vertexConsumer.addVertex(x0, 4.0F, z1).setColor(sideColor)
                                vertexConsumer.addVertex(x0, 0.0F, z1).setColor(sideColor)
                                vertexConsumer.addVertex(x1, 0.0F, z1).setColor(sideColor)
                                vertexConsumer.addVertex(x1, 4.0F, z1).setColor(sideColor)
                                vertexConsumer.addVertex(x1, 4.0F, z0).setColor(sideColor)
                                vertexConsumer.addVertex(x1, 0.0F, z0).setColor(sideColor)
                            }
                        } else {
                            val x0 = x * 12.0F
                            val x1 = x0 + 12.0F
                            val z0 = z * 12.0F
                            val z1 = z0 + 12.0F
                            vertexConsumer.addVertex(x0, 0.0F, z0).setColor(ARGB.multiply(colorB, cellColor))
                            vertexConsumer.addVertex(x0, 0.0F, z1).setColor(ARGB.multiply(colorB, cellColor))
                            vertexConsumer.addVertex(x1, 0.0F, z1).setColor(ARGB.multiply(colorB, cellColor))
                            vertexConsumer.addVertex(x1, 0.0F, z0).setColor(ARGB.multiply(colorB, cellColor))
                        }
                    }
                }
            }
        }
    }

    //? if <26.3 {
    /*fun render(cloudColor: Int, cloudStatus: CloudStatus, height: Float, cameraOffset: Vec3, ticks: Float) {
        if (this.textureData != null) {
            var x = cameraOffset.x + ticks * 0.030000001F
            var z = cameraOffset.z + 3.96F
    *///?} else {
    data class CloudRenderState(val color: Int, val cloudStatus: CloudStatus, val bottomY: Float, val cameraPosition: Vec3, val tickDelta: Float)
    //?}

    //? if >=26.3 {
    private var state: CloudRenderState? = null
    //?}

    //? if >=26.3 {
    fun prepare(cloudColor: Int, cloudStatus: CloudStatus, height: Float, cameraOffset: Vec3, tickDelta: Float) {
        this.state = CloudRenderState(cloudColor, cloudStatus, height, cameraOffset, tickDelta)
        if (this.textureData == null) return
    //?}

        //? if >=26.3 {
        var x = cameraOffset.x + tickDelta * 0.030000001F
        var z = cameraOffset.z + 3.96F
        val scaledWidth = this.textureData!!.width() * 12.0
        val scaledHeight = this.textureData!!.height() * 12.0
        x -= Mth.floor(x / scaledWidth) * scaledWidth
        z -= Mth.floor(z / scaledHeight) * scaledHeight
        val cellX = Mth.floor(x / 12.0)
        val cellZ = Mth.floor(z / 12.0)
        //?}

        //? if >=26.3 {
        val offsetBottom = (height - cameraOffset.y).toFloat()
        val offsetTop = offsetBottom + 4.0F
        val relativeCameraPos =
            if (offsetTop < 0.0F) RelativeCameraPos.ABOVE_CLOUDS else (if (offsetBottom > 0.0F) RelativeCameraPos.BELOW_CLOUDS else RelativeCameraPos.INSIDE_CLOUDS)
        //?}

    //? if >=26.3 {
        if (this.needsRebuild || cellX != this.prevCellX || cellZ != this.prevCellZ || relativeCameraPos != this.prevRelativeCameraPos || cloudStatus != this.prevType) {
            this.needsRebuild = false
            this.prevRelativeCameraPos = relativeCameraPos
            this.prevType = cloudStatus
            this.prevCellX = cellX
            this.prevCellZ = cellZ
            this.setupMesh(cellX, cellZ, cloudStatus, relativeCameraPos)
        }
    }
    //?}

    //? if >=26.3 {
    fun render(pass: RenderPass) {
        if (this.textureData != null && this.indexCount != 0) {
            val state = this.state ?: return
            var x = state.cameraPosition.x + state.tickDelta * 0.030000001F
            var z = state.cameraPosition.z + 3.96F
    //?}
            val scaledWidth = this.textureData!!.width() * 12.0
            val scaledHeight = this.textureData!!.height() * 12.0
            x -= Mth.floor(x / scaledWidth) * scaledWidth
            z -= Mth.floor(z / scaledHeight) * scaledHeight
            val cellX = Mth.floor(x / 12.0)
            val cellZ = Mth.floor(z / 12.0)

            //? if <26.3 {
            /*val offsetBottom = (height - cameraOffset.y).toFloat()
            val offsetTop = offsetBottom + 4.0F
            val relativeCameraPos =
                if (offsetTop < 0.0F) RelativeCameraPos.ABOVE_CLOUDS else (if (offsetBottom > 0.0F) RelativeCameraPos.BELOW_CLOUDS else RelativeCameraPos.INSIDE_CLOUDS)
            *///?} else {
            val pipelineSet = OverflowAnimationsPipelines.getCloudsSet(OverflowAnimationsConfig.instance().other.planarSkyFog)
            val pipeline = pipelineSet.get(state.cloudStatus)
            //?}

            //? if <26.3 {
            /*val pipelineSet = OverflowAnimationsPipelines.getCloudsSet(OverflowAnimationsConfig.instance().other.planarSkyFog)
            val pipeline = pipelineSet.get(cloudStatus)
            if (this.needsRebuild || cellX != this.prevCellX || cellZ != this.prevCellZ || relativeCameraPos != this.prevRelativeCameraPos || cloudStatus != this.prevType) {
                this.needsRebuild = false
                this.prevRelativeCameraPos = relativeCameraPos
                this.prevType = cloudStatus
                this.prevCellX = cellX
                this.prevCellZ = cellZ
                this.setupMesh(cellX, cellZ, cloudStatus, relativeCameraPos)
            *///?} else {
            val offsetBottom = (state.bottomY - state.cameraPosition.y).toFloat()
            val offsetX = (x - cellX * 12.0F).toFloat()
            val offsetZ = (z - cellZ * 12.0F).toFloat()
            val offset = Vector3f(-offsetX, offsetBottom, -offsetZ)
            if (pipeline != pipelineSet.flatPipeline) {
                this.draw(pass, pipelineSet.depthOnlyPipeline, offset, state.color)
            //?}
            }

            //? if <26.3 {
            /*if (this.indexCount != 0) {
                profile("cloud_rendering") {
                    val offsetX = (x - cellX * 12.0F).toFloat()
                    val offsetZ = (z - cellZ * 12.0F).toFloat()
                    val offset = Vector3f(-offsetX, offsetBottom, -offsetZ)
                    if (pipeline != pipelineSet.flatPipeline) {
                        this.draw(pipelineSet.depthOnlyPipeline, offset, cloudColor)
                    }
            *///?}

            //? if <26.3 {
                    /*this.draw(pipeline, offset, cloudColor)
                }
            }
            *///?} else {
            this.draw(pass, pipeline, offset, state.color)
            //?}
        }
    }

    //? if <26.3 {
    /*private fun draw(pipeline: RenderPipeline, offset: Vector3f, color: Int) {
    *///?}
        //? if <26.2 {
        /*var cloudsTarget = Minecraft.getInstance().levelRenderer.cloudsTarget
        *///?} elif 26.2 {
        /*var cloudsTarget = Minecraft.getInstance().levelRenderer.cloudsTarget()
        *///?}
        //? if <26.3 {
        /*if (cloudsTarget == null) {
        *///?}
            //? if <26.2 {
            /*cloudsTarget = Minecraft.getInstance().mainRenderTarget
            *///?} elif 26.2 {
            /*cloudsTarget = Minecraft.getInstance().gameRenderer.mainRenderTarget()
            *///?}
        //? if <26.3 {
        /*}
        *///?}

    //? if <26.3 {
        /*DeferredRenderer.of("Legacy Clouds (${pipeline.location})", cloudsTarget).use { renderer ->
    *///?} else {
    private fun draw(pass: RenderPass, pipeline: RenderPipeline, offset: Vector3f, color: Int) {
        WrappedRenderer.of(pass).use { renderer ->
            pass.pushDebugGroup({ "Legacy Clouds (${pipeline.location})" })
    //?}
            renderer.setPipeline(pipeline)
            renderer.setUniform(
                DynamicTransforms.KEY, DynamicTransforms.builder()
                    .withShaderColor(ARGB.color(1.0F, color))
                    .withModelOffset(offset)
                    .build()
            )
            renderer.draw(this.geometry!!)
            //? if >=26.3 {
            pass.popDebugGroup()
            //?}
        }
    }

    fun markForRebuild() {
        this.needsRebuild = true
    }

    protected override fun prepare(
        resourceManager: ResourceManager,
        profilerFiller: ProfilerFiller
    ): Optional<TextureData> {
        try {
            val optionalTextureData: Optional<TextureData>
            resourceManager.open(CloudRenderer.TEXTURE_LOCATION).use { inputStream ->
                NativeImage.read(inputStream).use { nativeImage ->
                    val width = nativeImage.width
                    val height = nativeImage.height
                    val cells = LongArray(width * height)
                    for (y in 0..<height) {
                        for (x in 0..<width) {
                            val pixel = nativeImage.getPixel(x, y)
                            if (!CloudRenderer.isCellEmpty(pixel)) {
                                cells[x + y * width] = CloudRenderer.packCellData(
                                    pixel,
                                    CloudRenderer.isCellEmpty(nativeImage.getPixel(x, Math.floorMod(y - 1, height))),
                                    CloudRenderer.isCellEmpty(nativeImage.getPixel(Math.floorMod(x + 1, height), y)),
                                    CloudRenderer.isCellEmpty(nativeImage.getPixel(x, Math.floorMod(y + 1, height))),
                                    CloudRenderer.isCellEmpty(nativeImage.getPixel(Math.floorMod(x - 1, height), y))
                                )
                            }
                        }
                    }

                    optionalTextureData = Optional.of(TextureData(cells, width, height))
                }
            }

            return optionalTextureData
        } catch (_: IOException) {
            return Optional.empty()
        }
    }

    protected override fun apply(
        optional: Optional<TextureData>,
        resourceManager: ResourceManager,
        profilerFiller: ProfilerFiller
    ) {
        this.textureData = optional.orElse(null)
        this.needsRebuild = true
    }

    override fun close() {
        if (this.geometry != null) {
            this.geometry?.close()
            this.geometry = null
        }
    }
}
//?}
