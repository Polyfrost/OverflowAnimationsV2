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

package org.polyfrost.overflowanimations.renderer.buffer

//? if <26.3 {
/*import com.mojang.blaze3d.buffers.GpuBuffer
import com.mojang.blaze3d.systems.RenderPass
*///?}
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.ByteBufferBuilder
import com.mojang.blaze3d.vertex.VertexConsumer
//? if >=26.3 {
import com.mojang.renderpearl.api.buffers.GpuBuffer
import com.mojang.renderpearl.api.commands.RenderPass
//?}
import org.polyfrost.overflowanimations.renderer.vertex.VertexLayout
import java.util.function.Consumer

data class IndexedGeometry(
    val vertexLayout: VertexLayout,
    val vertexBuffer: GpuBuffer,
    val indexCount: Int,
    val persistent: Boolean
) : Geometry {
    companion object {
        private fun compile(
            vertexLayout: VertexLayout,
            vertexCount: Int,
            vertexConsumer: Consumer<VertexConsumer>,
            persistent: Boolean
        ) = ByteBufferBuilder.exactlySized(vertexLayout.vertexFormat.vertexSize * vertexCount)
            .use { byteBufferBuilder ->
                val builder = vertexLayout.buffer(byteBufferBuilder)
                vertexConsumer.accept(builder)

                builder.buildOrThrow().use { meshData ->
                    val device = RenderSystem.getDevice()
                    val vertexBuffer = device.createBuffer(
                        { "Vertex buffer for " + vertexLayout.vertexFormat },
                        GpuBuffer.USAGE_VERTEX,
                        meshData.vertexBuffer()
                    )

                    IndexedGeometry(vertexLayout, vertexBuffer, meshData.drawState().indexCount, persistent)
                }
            }

        @JvmStatic
        fun compile(
            vertexLayout: VertexLayout,
            vertexCount: Int,
            vertexConsumer: Consumer<VertexConsumer>
        ) = compile(vertexLayout, vertexCount, vertexConsumer, false)

        @JvmStatic
        fun compilePersistent(
            vertexLayout: VertexLayout,
            vertexCount: Int,
            vertexConsumer: Consumer<VertexConsumer>
        ) = compile(vertexLayout, vertexCount, vertexConsumer, true)
    }

    override fun bind(pass: RenderPass, autoStorageIndexBuffer: RenderSystem.AutoStorageIndexBuffer) {
        //? if <26.2 {
        /*pass.setVertexBuffer(0, vertexBuffer)
        *///?} else {
        pass.setVertexBuffer(0, vertexBuffer.slice())
        //?}
        pass.setIndexBuffer(autoStorageIndexBuffer.getBuffer(indexCount), autoStorageIndexBuffer.type())
    }

    //? if <26.2 {
    /*override fun draw(pass: RenderPass) = pass.drawIndexed(0, 0, indexCount, 1)
    *///?} else {
    override fun draw(pass: RenderPass) = pass.drawIndexed(indexCount, 1, 0, 0, 0)
    //?}

    override fun persistent() = persistent

    override fun isClosed() = vertexBuffer.isClosed

    override fun close() = vertexBuffer.close()
}