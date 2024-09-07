package org.polyfrost.overflowanimations.hooks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import org.polyfrost.damagetint.config.DamageTintConfig;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.mixin.interfaces.RendererLivingEntityInvoker;

public class HitColorHook {

    public static void renderHitColorPre(EntityLivingBase entitylivingbaseIn, boolean bl, float partialTicks, RendererLivingEntity<?> instance) {
        float brightness = entitylivingbaseIn.getBrightness(partialTicks);
        int i = ((RendererLivingEntityInvoker) instance).invokeGetColorMultiplier(entitylivingbaseIn, brightness, partialTicks);
        boolean flag = (i >> 24 & 0xFF) > 0;

        boolean isDT = OverflowAnimations.isDamageTintPresent;
        float red = isDT ? DamageTintConfig.color.getRed() / 255.0F : brightness;
        float green = isDT ? DamageTintConfig.color.getGreen() / 255.0F : 0.0F;
        float blue = isDT ? DamageTintConfig.color.getBlue() / 255.0F : 0.0F;
        float alpha = isDT ? DamageTintConfig.color.getAlpha() / 255.0F : 0.4F;

        Minecraft.getMinecraft().entityRenderer.disableLightmap();
        if (flag || bl) {
            GlStateManager.disableTexture2D();
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.depthFunc(514);
            GlStateManager.color(red, green, blue, alpha);
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
