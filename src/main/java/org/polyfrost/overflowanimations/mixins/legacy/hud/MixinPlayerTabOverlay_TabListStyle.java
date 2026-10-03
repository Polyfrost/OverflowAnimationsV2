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

package org.polyfrost.overflowanimations.mixins.legacy.hud;

import com.google.common.collect.Ordering;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiElement;
import net.minecraft.client.gui.overlay.PlayerTabOverlay;
import net.minecraft.client.network.PlayerInfo;
import net.minecraft.client.network.handler.ClientPlayNetworkHandler;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.platform.GlStateManager;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.Formatting;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.util.enums.TabListSetting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PlayerTabOverlay.class)
public abstract class MixinPlayerTabOverlay_TabListStyle {
    @Shadow
    @Final
    private static Ordering<PlayerInfo> PLAYER_ORDERING;

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    public abstract String getDisplayName(PlayerInfo player);

    @Shadow
    protected abstract void renderPing(int width, int x, int y, PlayerInfo player);

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void overflowanimations$legacyTabList(final int width, final Scoreboard scoreboard, final ScoreboardObjective objective, final CallbackInfo ci) {
        if (OverflowAnimationsConfig.instance().screen.tabListStyle != TabListSetting.V1_7) {
            return;
        }
        ci.cancel();
        final ClientPlayNetworkHandler networkHandler = this.minecraft.player.networkHandler;
        final List<PlayerInfo> players = PLAYER_ORDERING.sortedCopy(networkHandler.getOnlinePlayers());
        final TextRenderer textRenderer = this.minecraft.textRenderer;
        final int slots = Math.min(networkHandler.maxPlayerCount, 100);
        int rows = slots;
        int columns;
        for (columns = 1; rows > 20; rows = (slots + columns - 1) / columns) {
            columns++;
        }
        final int slotWidth = Math.min(300 / columns, 150);
        final int left = (width - columns * slotWidth) / 2;
        final int top = 10;
        GuiElement.fill(left - 1, top - 1, left + slotWidth * columns, top + 9 * rows, Integer.MIN_VALUE);
        for (int i = 0; i < slots; i++) {
            final int x = left + i % columns * slotWidth;
            final int y = top + i / columns * 9;
            GuiElement.fill(x, y, x + slotWidth - 1, y + 8, 553648127);
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableAlphaTest();
            if (i < players.size()) {
                final PlayerInfo player = players.get(i);
                final String name = this.getDisplayName(player);
                textRenderer.drawWithShadow(name, x, y, 16777215);
                if (objective != null) {
                    final int scoreLeft = x + textRenderer.getWidth(name) + 5;
                    final int scoreRight = x + slotWidth - 12 - 5;
                    if (scoreRight - scoreLeft > 5) {
                        final String score = Formatting.YELLOW + "" + scoreboard.getScore(player.getProfile().getName(), objective).get();
                        textRenderer.drawWithShadow(score, scoreRight - textRenderer.getWidth(score), y, 16777215);
                    }
                }
                this.renderPing(slotWidth - 1, x, y, player);
            }
        }
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @ModifyVariable(method = "render", at = @At("STORE"), ordinal = 0)
    private boolean overflowanimations$disablePlayerHeads(final boolean showHeads) {
        return showHeads && OverflowAnimationsConfig.instance().screen.tabListStyle != TabListSetting.NO_HEADS;
    }
}
