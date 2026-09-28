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

import com.mojang.blaze3d.systems.RenderSystem
//? if <26.3 {
/*import com.mojang.blaze3d.textures.GpuTexture
*///?} else {
import com.mojang.renderpearl.api.textures.GpuTexture
//?}
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Font
//? if 1.21.11 {
/*import net.minecraft.client.gui.GuiGraphics
*///?} else {
import net.minecraft.client.gui.GuiGraphicsExtractor
//?}
import net.minecraft.client.renderer.texture.OverlayTexture
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig
import org.polyfrost.overflowanimations.mixins.accessor.GameRendererAccessor
import org.polyfrost.overflowanimations.mixins.accessor.OverlayTextureAccessor
import org.polyfrost.overflowanimations.util.enums.DamageTintSetting

fun copyTextureToTexture(source: GpuTexture, destination: GpuTexture) =
    RenderSystem.getDevice().createCommandEncoder().copyTextureToTexture(
        source,
        destination,
        0,
        0, 0,
        0, 0,
        source.getWidth(0), source.getHeight(0)
    )

//? if 1.21.11 {
/*fun GuiGraphics.fillVerticalLine(
*///?} else {
fun GuiGraphicsExtractor.fillVerticalLine(
//?}
    x: Int, y: Int,
    length: Int,
    color: Int
) = this.fill(x, y, x + 1, y + length, color)

//? if 1.21.11 {
/*fun GuiGraphics.fillVerticalGradientLine(
*///?} else {
fun GuiGraphicsExtractor.fillVerticalGradientLine(
//?}
    x: Int, y: Int,
    length: Int,
    startColor: Int,
    endColor: Int
) = this.fillGradient(x, y, x + 1, y + length, startColor, endColor)

//? if 1.21.11 {
/*fun GuiGraphics.fillHorizontalLine(
*///?} else {
fun GuiGraphicsExtractor.fillHorizontalLine(
//?}
    x: Int, y: Int,
    length: Int,
    color: Int
) = this.fill(x, y, x + length, y + 1, color)

//? if 1.21.11 {
/*fun GuiGraphics.fillRectangle(
*///?} else {
fun GuiGraphicsExtractor.fillRectangle(
//?}
    x: Int, y: Int,
    width: Int, height: Int,
    color: Int
) = this.fill(x, y, x + width, y + height, color)

//? if 1.21.11 {
/*fun GuiGraphics.fillFrameGradient(
*///?} else {
fun GuiGraphicsExtractor.fillFrameGradient(
//?}
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    startColor: Int,
    endColor: Int
) {
    this.fillVerticalGradientLine(x, y, height - 2, startColor, endColor)
    this.fillVerticalGradientLine(x + width - 1, y, height - 2, startColor, endColor)
    this.fillHorizontalLine(x, y - 1, width, startColor)
    this.fillHorizontalLine(x, y - 1 + height - 1, width, endColor)
}

//? if 1.21.11 {
/*fun GuiGraphics.drawScaledText(font: Font, text: String, x: Int, y: Int, scale: Float) {
*///?} else {
fun GuiGraphicsExtractor.drawScaledText(font: Font, text: String, x: Int, y: Int, scale: Float) {
//?}
    val stack = this.pose()
    stack.pushMatrix()
    val originX = stack.m20
    val originY = stack.m21
    stack.setTranslation(0.0F, 0.0F)
    stack.scale(scale, scale)
    stack.setTranslation(originX, originY)
    //? if 1.21.11 {
    /*this.drawCenteredString(font, text, (x / scale).toInt(), (y / scale).toInt(), 0xFFFFFFFF.toInt())
    *///?} else {
    this.centeredText(font, text, (x / scale).toInt(), (y / scale).toInt(), 0xFFFFFFFF.toInt())
    //?}
    stack.popMatrix()
}

/**
 * Please mojang, just make `overlay texture -> overlay color` and be a part of EntityRenderState :pray: :pray: :pray:
 */
fun resetOverlayTexture() {
    val gameRenderer = Minecraft.getInstance().gameRenderer
    gameRenderer.overlayTexture().close()
    (gameRenderer as GameRendererAccessor).`overflowanimations$setOverlayTexture`(OverlayTexture())
}

fun setOverlayColor(color: Int) {
    val overlayTexture = Minecraft.getInstance().gameRenderer.overlayTexture()
    val dynamicTexture = (overlayTexture as OverlayTextureAccessor).`overflowanimations$getDynamicTexture`()
    val pixels = dynamicTexture.pixels
    //? if 1.21.11 {
    /*if (pixels != null) {
        for (y in 0..<16) {
            for (x in 0..<16) {
                if (y < 8) {
                    pixels.setPixel(x, y, color)
                }
    *///?} else {
    for (y in 0..<16) {
        for (x in 0..<16) {
            if (y < 8) {
                pixels.setPixel(x, y, color)
    //?}
            }
        }
    //? if >=26.1 {
    }
    //?}

    //? if 1.21.11 {
        /*dynamicTexture.upload()
    }
    *///?} else {
    dynamicTexture.upload()
    //?}
}

fun updateOverlayTint(style: DamageTintSetting = OverflowAnimationsConfig.instance().other.damageTintStyle) {
    if (style == DamageTintSetting.VANILLA) {
        resetOverlayTexture()
    } else {
        setOverlayColor(style.getColor(1.0F))
    }
}