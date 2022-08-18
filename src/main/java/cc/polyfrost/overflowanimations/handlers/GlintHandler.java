package cc.polyfrost.overflowanimations.handlers;

import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import cc.polyfrost.overflowanimations.mixin.RenderItemAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.ResourceLocation;

public class GlintHandler {
    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

    public static boolean renderGlint(RenderItem instance, IBakedModel model) {
        if (!OldAnimationsSettings.enchantmentGlint) {
            return false;
        }
        GlStateManager.depthMask(false);
        GlStateManager.depthFunc(514);
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(768, 1);
        Minecraft.getMinecraft().renderEngine.bindTexture(RES_ITEM_GLINT);
        GlStateManager.matrixMode(5890);

        // first
            /*?
            GlStateManager.pushMatrix();
            GlStateManager.scale(8.0, 8.0, 8.0);
            GlStateManager.translate((Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
            ((RenderItemAccessor) instance).invokeRenderModel(model, -8372020);
            GlStateManager.popMatrix();
             */


        // second
        GlStateManager.pushMatrix();
        GlStateManager.scale(8.0, 8.0, 8.0);
        GlStateManager.translate((Minecraft.getSystemTime() % 4873L) / 4873.0F / 8.0F, 0.0F, 0.0F);
        GlStateManager.rotate(10.0F, 0.0F, 0.0F, 1.0F);
        ((RenderItemAccessor) instance).invokeRenderModel(model, -8372020);
        GlStateManager.popMatrix();

        GlStateManager.matrixMode(5888);
        GlStateManager.blendFunc(770, 771);
        GlStateManager.enableLighting();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        return true;
    }
}