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

package org.polyfrost.overflowanimations.legacy.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Window;
import net.minecraft.client.render.platform.GlStateManager;
import net.minecraft.client.render.vertex.BufferBuilder;
import net.minecraft.client.render.vertex.DefaultVertexFormat;
import net.minecraft.client.render.vertex.Tesselator;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public final class DebugCrosshair {
    private static final int LENGTH = 10;

    private DebugCrosshair() {
    }

    public static boolean isDebugCrosshairVisible(final Minecraft minecraft) {
        return minecraft.options.debugEnabled && !minecraft.options.hideGui && !minecraft.player.hasReducedDebugInfo() && !minecraft.options.reducedDebugInfo;
    }

    public static void render(final Minecraft minecraft, final Window window, final float tickDelta) {
        final Entity camera = minecraft.getCamera();
        GlStateManager.pushMatrix();
        GlStateManager.translatef(window.getWidth() / 2, window.getHeight() / 2, 0.0F);
        GlStateManager.rotatef(camera.lastPitch + (camera.pitch - camera.lastPitch) * tickDelta, -1.0F, 0.0F, 0.0F);
        GlStateManager.rotatef(camera.lastYaw + (camera.yaw - camera.lastYaw) * tickDelta, 0.0F, 1.0F, 0.0F);
        GlStateManager.scalef(-1.0F, -1.0F, -1.0F);
        GlStateManager.disableTexture();
        GlStateManager.depthMask(false);
        GL11.glLineWidth(4.0F);
        drawAxes(0, 0, 0, 0, 0, 0, 0, 0, 0);
        GL11.glLineWidth(2.0F);
        drawAxes(255, 0, 0, 0, 255, 0, 127, 127, 255);
        GL11.glLineWidth(1.0F);
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture();
        GlStateManager.popMatrix();
    }

    private static void drawAxes(final int xr, final int xg, final int xb, final int yr, final int yg, final int yb, final int zr, final int zg, final int zb) {
        final Tesselator tesselator = Tesselator.getInstance();
        final BufferBuilder buffer = tesselator.getBuffer();
        buffer.begin(GL11.GL_LINES, DefaultVertexFormat.POSITION_COLOR);
        buffer.vertex(0.0, 0.0, 0.0).color(xr, xg, xb, 255).nextVertex();
        buffer.vertex(LENGTH, 0.0, 0.0).color(xr, xg, xb, 255).nextVertex();
        buffer.vertex(0.0, 0.0, 0.0).color(yr, yg, yb, 255).nextVertex();
        buffer.vertex(0.0, LENGTH, 0.0).color(yr, yg, yb, 255).nextVertex();
        buffer.vertex(0.0, 0.0, 0.0).color(zr, zg, zb, 255).nextVertex();
        buffer.vertex(0.0, 0.0, LENGTH).color(zr, zg, zb, 255).nextVertex();
        tesselator.end();
    }
}
