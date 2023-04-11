package cc.polyfrost.overflowanimations.handlers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

public class MicroSpriteHandler {

    public static void oldProjectileRender(RenderItem instance, ItemStack item) {
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        instance.renderItem(item, ItemCameraTransforms.TransformType.GUI);
        GlStateManager.enableCull();
        GlStateManager.disableAlpha();
        RenderHelper.enableStandardItemLighting();
    }

}