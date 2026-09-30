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
import net.minecraft.client.gui.GuiElement;
import net.minecraft.client.entity.living.player.ClientPlayerEntity;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.Window;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.WorldChunk;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.config.category.ScreenConfigCategory;

public final class LegacyDebugScreen {
    private static final String[] FACINGS = {"SOUTH", "WEST", "NORTH", "EAST"};
    private static final int WHITE = 16777215;
    private static final int GRAY = 14737632;

    private LegacyDebugScreen() {
    }

    public static void drawGameInfo(final Minecraft minecraft, final TextRenderer textRenderer) {
        final String fps = minecraft.fpsDebugInfo.replaceFirst(" \\((\\d+) chunk updates?\\).*", ", $1 chunk updates");
        draw(textRenderer, "Minecraft 1.8.9 (" + fps + ")", 2, 2, WHITE);
        draw(textRenderer, minecraft.worldRenderer.getChunkDebugInfo(), 2, 12, WHITE);
        draw(textRenderer, minecraft.worldRenderer.getEntityDebugInfo(), 2, 22, WHITE);
        draw(textRenderer, "P: " + minecraft.particleManager.getDebugInfo() + ". T: " + minecraft.world.getDebugInfo(), 2, 32, WHITE);
        draw(textRenderer, minecraft.world.getChunkSourceDebugInfo(), 2, 42, WHITE);

        final ClientPlayerEntity player = minecraft.player;
        if (player.hasReducedDebugInfo() || minecraft.options.reducedDebugInfo) {
            return;
        }

        final double eyeY = player.y + player.getEyeHeight();
        final int x = MathHelper.floor(player.x);
        final int y = MathHelper.floor(eyeY);
        final int z = MathHelper.floor(player.z);
        draw(textRenderer, String.format("x: %.5f (%d) // c: %d (%d)", player.x, x, x >> 4, x & 15), 2, 64, GRAY);
        draw(textRenderer, String.format("y: %.3f (feet pos, %.3f eyes pos)", player.getShape().minY, eyeY), 2, 72, GRAY);
        draw(textRenderer, String.format("z: %.5f (%d) // c: %d (%d)", player.z, z, z >> 4, z & 15), 2, 80, GRAY);
        final int facing = MathHelper.floor(player.yaw * 4.0F / 360.0F + 0.5) & 3;
        draw(textRenderer, "f: " + facing + " (" + FACINGS[facing] + ") / " + MathHelper.wrapDegrees(player.yaw), 2, 88, GRAY);
        final BlockPos pos = new BlockPos(x, y, z);
        if (minecraft.world.isChunkLoaded(pos)) {
            final WorldChunk chunk = minecraft.world.getChunk(pos);
            draw(textRenderer, "lc: " + (chunk.getHighestSectionOffset() + 15)
                    + " b: " + chunk.getBiome(pos, minecraft.world.getBiomeSource()).name
                    + " bl: " + chunk.getLight(LightType.BLOCK, pos)
                    + " sl: " + chunk.getLight(LightType.SKY, pos)
                    + " rl: " + chunk.getLight(pos, 0), 2, 96, GRAY);
        }
        draw(textRenderer, String.format("ws: %.3f, fs: %.3f, g: %b, fl: %d", player.abilities.getWalkSpeed(), player.abilities.getFlySpeed(), player.onGround, minecraft.world.getHeight(pos).getY()), 2, 104, GRAY);
        if (minecraft.gameRenderer != null && minecraft.gameRenderer.hasShader()) {
            draw(textRenderer, String.format("shader: %s", minecraft.gameRenderer.getShader().getName()), 2, 112, GRAY);
        }
    }

    public static void drawSystemInfo(final TextRenderer textRenderer, final Window window) {
        final long max = Runtime.getRuntime().maxMemory();
        final long total = Runtime.getRuntime().totalMemory();
        final long used = total - Runtime.getRuntime().freeMemory();
        final String usedMemory = "Used memory: " + used * 100L / max + "% (" + used / 1024L / 1024L + "MB) of " + max / 1024L / 1024L + "MB";
        final String allocatedMemory = "Allocated memory: " + total * 100L / max + "% (" + total / 1024L / 1024L + "MB)";
        draw(textRenderer, usedMemory, window.getWidth() - textRenderer.getWidth(usedMemory) - 2, 2, GRAY);
        draw(textRenderer, allocatedMemory, window.getWidth() - textRenderer.getWidth(allocatedMemory) - 2, 12, GRAY);
    }

    private static void draw(final TextRenderer textRenderer, final String text, final int x, final int y, final int color) {
        final ScreenConfigCategory config = OverflowAnimationsConfig.instance().screen;
        if (!config.disableDebugHudBackground) {
            GuiElement.fill(x - 1, y - 1, x + textRenderer.getWidth(text) + 1, y + textRenderer.fontHeight - 1, -1873784752);
        }
        textRenderer.draw(text, x, y, color, config.debugHudTextShadow);
    }
}
