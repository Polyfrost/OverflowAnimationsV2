package org.polyfrost.overflowanimations.hooks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class DebugCrosshairHook {

    public static void renderDirections(float partialTicks, float zLevel, ScaledResolution res, Minecraft mc) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)(res.getScaledWidth() / 2), (float)(res.getScaledHeight() / 2), zLevel);
        Entity entity = mc.getRenderViewEntity();
        GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, -1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(-1.0F, -1.0F, -1.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        GL11.glLineWidth(4.0F);
        worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(0.0, 0.0, 0.0).color(0, 0, 0, 255).endVertex();
        worldRenderer.pos(10, 0.0, 0.0).color(0, 0, 0, 255).endVertex();
        worldRenderer.pos(0.0, 0.0, 0.0).color(0, 0, 0, 255).endVertex();
        worldRenderer.pos(0.0, 10, 0.0).color(0, 0, 0, 255).endVertex();
        worldRenderer.pos(0.0, 0.0, 0.0).color(0, 0, 0, 255).endVertex();
        worldRenderer.pos(0.0, 0.0, 10).color(0, 0, 0, 255).endVertex();
        tessellator.draw();

        GL11.glLineWidth(2.0F);
        worldRenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(0.0, 0.0, 0.0).color(255, 0, 0, 255).endVertex();
        worldRenderer.pos(10, 0.0, 0.0).color(255, 0, 0, 255).endVertex();
        worldRenderer.pos(0.0, 0.0, 0.0).color(0, 255, 0, 255).endVertex();
        worldRenderer.pos(0.0, 10, 0.0).color(0, 255, 0, 255).endVertex();
        worldRenderer.pos(0.0, 0.0, 0.0).color(127, 127, 255, 255).endVertex();
        worldRenderer.pos(0.0, 0.0, 10).color(127, 127, 255, 255).endVertex();
        tessellator.draw();

        GL11.glLineWidth(1.0F);
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

}
