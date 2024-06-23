package org.polyfrost.overflowanimations.handler

import cc.polyfrost.oneconfig.utils.dsl.mc
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.OpenGlHelper
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.WorldRenderer
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.resources.model.IBakedModel
import net.minecraft.item.Item
import net.minecraft.item.ItemPotion
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import java.awt.Color

object SpriteHandler {

    private val RES_ITEM_GLINT = ResourceLocation("textures/misc/enchanted_item_glint.png")
    private val tessellator: Tessellator = Tessellator.getInstance()
    private val worldrenderer: WorldRenderer = Tessellator.getInstance().worldRenderer

    private fun renderSpriteWithLayer(model: IBakedModel, stack: ItemStack, item: Item) {
        val layer0 = model.particleTexture
        val layer1 = getOverlay(model, stack)
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL)
        drawSprite(
            layer0.minU.toDouble(),
            layer0.maxU.toDouble(),
            layer0.minV.toDouble(),
            layer0.maxV.toDouble(),
            Color(item.getColorFromItemStack(stack, 0))
        )
        layer1?.let {
            drawSprite(
                it.minU.toDouble(),
                it.maxU.toDouble(),
                it.minV.toDouble(),
                it.maxV.toDouble(),
                Color(item.getColorFromItemStack(stack, 1))
            )
        }
        tessellator.draw()
    }

    private fun drawSprite(uMin: Double, uMax: Double, vMin: Double, vMax: Double, color: Color) {
        worldrenderer.pos(-0.5, -0.25, 0.0) .tex(uMin, vMax).color(color.red, color.green, color.blue, color.alpha).normal(0.0f, 1.0f, 0.0f).endVertex()
        worldrenderer.pos(0.5, -0.25, 0.0)  .tex(uMax, vMax).color(color.red, color.green, color.blue, color.alpha).normal(0.0f, 1.0f, 0.0f).endVertex()
        worldrenderer.pos(0.5, 0.75, 0.0)   .tex(uMax, vMin).color(color.red, color.green, color.blue, color.alpha).normal(0.0f, 1.0f, 0.0f).endVertex()
        worldrenderer.pos(-0.5, 0.75, 0.0)  .tex(uMin, vMin).color(color.red, color.green, color.blue, color.alpha).normal(0.0f, 1.0f, 0.0f).endVertex()
    }

    private fun getOverlay(model: IBakedModel, stack: ItemStack): TextureAtlasSprite? {
        val textureMap = mc.textureMapBlocks
        val updatedSpriteName = when (val spriteName = model.particleTexture.iconName) {
            "minecraft:items/potion_overlay" -> {
                "minecraft:items/potion_bottle" + if (ItemPotion.isSplash(stack.metadata)) "_splash" else "_drinkable"
            }
            "minecraft:items/leather_helmet",
            "minecraft:items/leather_chestplate",
            "minecraft:items/leather_leggings",
            "minecraft:items/leather_boots",
            "minecraft:items/spawn_egg",
            "minecraft:items/fireworks_charge" -> {
                spriteName + "_overlay"
            }
            else -> null
        }

        return updatedSpriteName?.let { textureMap.getAtlasSprite(it) }
    }

    fun renderSpriteLayersWithGlint(stack: ItemStack, model: IBakedModel) {
        GlStateManager.scale(0.5f, 0.5f, 0.5f)
        GlStateManager.translate(0.0f, -0.25f, 0.0f)
        renderSpriteWithLayer(model, stack, stack.item)
        if (stack.hasEffect()) {
            GlStateManager.depthFunc(514)
            GlStateManager.disableLighting()
            GlStateManager.depthMask(false)
            mc.textureManager.bindTexture(RES_ITEM_GLINT)
            GlStateManager.enableAlpha()
            GlStateManager.enableBlend()
            GlStateManager.pushMatrix()
            renderGlintGui(Color(-8372020))
            GlStateManager.popMatrix()
            OpenGlHelper.glBlendFunc(770, 771, 1, 0)
            GlStateManager.depthMask(true)
            GlStateManager.enableLighting()
            GlStateManager.depthFunc(515)
        }
    }

    private fun renderGlintGui(color: Color) {
        val var7 = 0.00390625
        val currentTime = Minecraft.getSystemTime()
        OpenGlHelper.glBlendFunc(772, 1, 0, 0)

        for (var6 in 0..1) {
            val var9 = (currentTime % (3000 + var6 * 1873).toLong()) / (3000.0f + (var6 * 1873).toFloat()) * 256.0f
            val var12 = if ((var6 == 1)) -1.0 else 4.0

            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR)
            worldrenderer.pos(-0.5, -0.25, 0.0) .tex(((var9 + 20.0f * var12) * var7), (20.0f * var7)).color(color.red, color.green, color.blue, color.alpha).endVertex()
            worldrenderer.pos(0.5, -0.25, 0.0)  .tex(((var9 + 20.0f + 20.0f * var12) * var7), (20.0f * var7)).color(color.red, color.green, color.blue, color.alpha).endVertex()
            worldrenderer.pos(0.5, 0.75, 0.0)   .tex(((var9 + 20.0f) * var7), 0.0).color(color.red, color.green, color.blue, color.alpha).endVertex()
            worldrenderer.pos(-0.5, 0.75, 0.0)  .tex(((var9) * var7), 0.0).color(color.red, color.green, color.blue, color.alpha).endVertex()
            tessellator.draw()
        }
    }

}