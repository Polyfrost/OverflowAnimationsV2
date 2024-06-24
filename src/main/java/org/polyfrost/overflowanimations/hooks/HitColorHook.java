package org.polyfrost.overflowanimations.hooks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;

public class HitColorHook {

    public static void renderHitColorPre(EntityLivingBase entitylivingbaseIn, boolean bl, float partialTicks) {
        float f12 = entitylivingbaseIn.getBrightness(partialTicks);
        Minecraft.getMinecraft().entityRenderer.disableLightmap();
        if (bl) {
            GlStateManager.disableTexture2D();
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.depthFunc(514);
            GlStateManager.color(f12, 0.0F, 0.0F, 0.4F);
        }
    }

    public static void renderHitColorPost(boolean bl) {
        if (bl) {
            GlStateManager.depthFunc(515);
            GlStateManager.disableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.enableTexture2D();
        }
        Minecraft.getMinecraft().entityRenderer.enableLightmap();
    }

}
