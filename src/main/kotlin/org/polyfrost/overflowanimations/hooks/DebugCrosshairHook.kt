package org.polyfrost.overflowanimations.hooks

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import org.lwjgl.opengl.GL11
import org.polyfrost.overflowanimations.util.MathUtils.interpolate

object DebugCrosshairHook {

    fun renderDirections(partialTicks: Float, zLevel: Float, res: ScaledResolution, mc: Minecraft) {
        val tessellator = Tessellator.getInstance()
        val worldRenderer = tessellator.worldRenderer
        val entity = mc.renderViewEntity
        val pitch = interpolate(entity.prevRotationPitch, entity.rotationPitch, partialTicks)
        val yaw = interpolate(entity.prevRotationYaw, entity.rotationYaw, partialTicks)

        fun drawLine(x: Double, y: Double, z: Double, red: Int, green: Int, blue: Int) {
            worldRenderer.pos(0.0, 0.0, 0.0).color(red, green, blue, 255).endVertex()
            worldRenderer.pos(x, y, z).color(red, green, blue, 255).endVertex()
        }

        fun setupGlState() {
            GlStateManager.pushMatrix()
            GlStateManager.translate((res.scaledWidth / 2).toFloat(), (res.scaledHeight / 2).toFloat(), zLevel)
            GlStateManager.rotate(pitch, -1.0f, 0.0f, 0.0f)
            GlStateManager.rotate(yaw, 0.0f, 1.0f, 0.0f)
            GlStateManager.scale(-1.0f, -1.0f, -1.0f)
            GlStateManager.disableTexture2D()
            GlStateManager.depthMask(false)
        }

        fun resetGlState() {
            GL11.glLineWidth(1.0f)
            GlStateManager.depthMask(true)
            GlStateManager.enableTexture2D()
            GlStateManager.popMatrix()
        }

        fun drawLines(lineWidth: Float, lines: List<Triple<Double, Double, Double>>, colors: List<Triple<Int, Int, Int>>) {
            GL11.glLineWidth(lineWidth)
            worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR)
            for (i in lines.indices) {
                drawLine(lines[i].first, lines[i].second, lines[i].third, colors[i].first, colors[i].second, colors[i].third)
            }
            tessellator.draw()
        }

        setupGlState()

        drawLines(4.0f, listOf(
            Triple(10.0, 0.0, 0.0),
            Triple(0.0, 10.0, 0.0),
            Triple(0.0, 0.0, 10.0)
        ), listOf(
            Triple(0, 0, 0),
            Triple(0, 0, 0),
            Triple(0, 0, 0)
        ))

        drawLines(2.0f, listOf(
            Triple(10.0, 0.0, 0.0),
            Triple(0.0, 10.0, 0.0),
            Triple(0.0, 0.0, 10.0)
        ), listOf(
            Triple(255, 0, 0),
            Triple(0, 255, 0),
            Triple(127, 127, 255)
        ))

        resetGlState()
    }

}
