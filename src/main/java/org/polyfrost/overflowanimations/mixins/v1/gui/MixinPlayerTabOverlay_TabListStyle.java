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

package org.polyfrost.overflowanimations.mixins.v1.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
//? if <26.1 {
/*import net.minecraft.client.gui.GuiGraphics;
*///?} else {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?}
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.numbers.StyledFormat;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.GameType;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.ReadOnlyScoreInfo;
import net.minecraft.world.scores.ScoreHolder;
import net.minecraft.world.scores.Scoreboard;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.util.enums.TabListSetting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PlayerTabOverlay.class)
public abstract class MixinPlayerTabOverlay_TabListStyle {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private Component header;

    @Shadow
    private Component footer;

    @Shadow
    protected abstract List<PlayerInfo> getPlayerInfos();

    @Shadow
    public abstract Component getNameForDisplay(final PlayerInfo info);

    //? if <26.1 {
    /*@Shadow
    protected abstract void renderPingIcon(final GuiGraphics graphics, final int slotWidth, final int xo, final int yo, final PlayerInfo info);

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void overflowanimations$legacyTabList(final GuiGraphics graphics, final int screenWidth, final Scoreboard scoreboard, final Objective objective, final CallbackInfo ci) {
    *///?} else {
    @Shadow
    protected abstract void extractPingIcon(final GuiGraphicsExtractor graphics, final int slotWidth, final int xo, final int yo, final PlayerInfo info);

    @Inject(method = "extractRenderState", at = @At("HEAD"), cancellable = true)
    private void overflowanimations$legacyTabList(final GuiGraphicsExtractor graphics, final int screenWidth, final Scoreboard scoreboard, final Objective objective, final CallbackInfo ci) {
    //?}
        if (!OverflowAnimations.isEnabled() || OverflowAnimationsConfig.instance().screen.tabListStyle != TabListSetting.V1_7) {
            return;
        }
        ci.cancel();
        final Font font = this.minecraft.font;
        final List<PlayerInfo> players = this.getPlayerInfos();
        final int slots = players.size();
        int rows = slots;
        int columns;
        for (columns = 1; rows > 20; rows = (slots + columns - 1) / columns) {
            columns++;
        }
        final int slotWidth = Math.min(300 / columns, 150);
        final int left = (screenWidth - columns * slotWidth) / 2;
        final List<FormattedCharSequence> headerLines = this.header == null ? null : font.split(this.header, screenWidth - 50);
        final List<FormattedCharSequence> footerLines = this.footer == null ? null : font.split(this.footer, screenWidth - 50);
        final int lineWidth = this.overflowanimations$maxWidth(font, footerLines, this.overflowanimations$maxWidth(font, headerLines, columns * slotWidth));
        int top = 10;
        if (headerLines != null) {
            top = this.overflowanimations$drawLines(graphics, font, headerLines, screenWidth, lineWidth, top) + 1;
        }
        graphics.fill(left - 1, top - 1, left + slotWidth * columns, top + 9 * rows, Integer.MIN_VALUE);
        final int background = this.minecraft.options.getBackgroundColor(553648127);
        for (int i = 0; i < slots; i++) {
            final PlayerInfo player = players.get(i);
            final int x = left + i % columns * slotWidth;
            final int y = top + i / columns * 9;
            final boolean spectator = player.getGameMode() == GameType.SPECTATOR;
            final Component name = this.getNameForDisplay(player);
            graphics.fill(x, y, x + slotWidth - 1, y + 8, background);
            this.overflowanimations$text(graphics, font, name.getVisualOrderText(), x, y, spectator ? -1862270977 : -1);
            if (objective != null && !spectator) {
                final int scoreLeft = x + font.width(name) + 5;
                final int scoreRight = x + slotWidth - 12 - 5;
                final ReadOnlyScoreInfo scoreInfo = scoreboard.getPlayerScoreInfo(ScoreHolder.fromGameProfile(player.getProfile()), objective);
                if (scoreRight - scoreLeft > 5 && scoreInfo != null) {
                    final Component score = scoreInfo.formatValue(objective.numberFormatOrDefault(StyledFormat.PLAYER_LIST_DEFAULT));
                    this.overflowanimations$text(graphics, font, score.getVisualOrderText(), scoreRight - font.width(score), y, -1);
                }
            }
            //? if <26.1 {
            /*this.renderPingIcon(graphics, slotWidth - 1, x, y, player);
            *///?} else {
            this.extractPingIcon(graphics, slotWidth - 1, x, y, player);
            //?}
        }
        if (footerLines != null) {
            this.overflowanimations$drawLines(graphics, font, footerLines, screenWidth, lineWidth, top + rows * 9 + 1);
        }
    }

    //? if <26.1 {
    /*@ModifyVariable(method = "render", at = @At("STORE"), ordinal = 0)
    *///?} else {
    @ModifyVariable(method = "extractRenderState", at = @At("STORE"), ordinal = 0)
    //?}
    private boolean overflowanimations$disablePlayerHeads(final boolean showHead) {
        return showHead && !(OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.tabListStyle == TabListSetting.NO_HEADS);
    }

    @Unique
    private int overflowanimations$maxWidth(final Font font, final List<FormattedCharSequence> lines, int width) {
        if (lines != null) {
            for (final FormattedCharSequence line : lines) {
                width = Math.max(width, font.width(line));
            }
        }
        return width;
    }

    @Unique
    //? if <26.1 {
    /*private int overflowanimations$drawLines(final GuiGraphics graphics, final Font font, final List<FormattedCharSequence> lines, final int screenWidth, final int lineWidth, int top) {
    *///?} else {
    private int overflowanimations$drawLines(final GuiGraphicsExtractor graphics, final Font font, final List<FormattedCharSequence> lines, final int screenWidth, final int lineWidth, int top) {
    //?}
        graphics.fill(screenWidth / 2 - lineWidth / 2 - 1, top - 1, screenWidth / 2 + lineWidth / 2 + 1, top + lines.size() * 9, Integer.MIN_VALUE);
        for (final FormattedCharSequence line : lines) {
            this.overflowanimations$text(graphics, font, line, screenWidth / 2 - font.width(line) / 2, top, -1);
            top += 9;
        }
        return top;
    }

    @Unique
    //? if <26.1 {
    /*private void overflowanimations$text(final GuiGraphics graphics, final Font font, final FormattedCharSequence text, final int x, final int y, final int color) {
        graphics.drawString(font, text, x, y, color);
    *///?} else {
    private void overflowanimations$text(final GuiGraphicsExtractor graphics, final Font font, final FormattedCharSequence text, final int x, final int y, final int color) {
        graphics.text(font, text, x, y, color);
    //?}
    }
}
