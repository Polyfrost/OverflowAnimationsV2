package org.polyfrost.overflowanimations.hooks

import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.entity.RenderItem
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemSword
import org.polyfrost.overflowanimations.config.MainModSettings

object ItemPositionsHook {

    private fun commonItemTransforms() {
        val scale = 1.5f / 1.7f
        GlStateManager.scale(scale, scale, scale)
        GlStateManager.rotate(5.0f, 0.0f, 1.0f, 0.0f)
        GlStateManager.translate(-0.29f, 0.149f, -0.0328f)
    }

    fun firstPersonItemTransforms(itemToRender: ItemStack, itemRenderer: RenderItem) {
        val settings = MainModSettings.oldSettings
        val isFishingRod = settings.fishingRodPosition && itemToRender.item.shouldRotateAroundWhenRendering()
        val isValidItem = settings.firstTransformations && (itemToRender.item !is ItemSword || !settings.lunarBlockhit)

        if (!settings.enabled || itemRenderer.shouldRenderItemIn3D(itemToRender)) { return }

        if (isFishingRod) {
            GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f)
            commonItemTransforms()
        } else if (isValidItem) {
            commonItemTransforms()
        }
    }

}