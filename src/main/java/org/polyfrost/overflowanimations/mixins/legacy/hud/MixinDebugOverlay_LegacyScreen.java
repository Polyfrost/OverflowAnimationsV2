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

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.overlay.DebugOverlay;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.Window;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.legacy.hud.LegacyDebugScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugOverlay.class)
public abstract class MixinDebugOverlay_LegacyScreen {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    @Final
    private TextRenderer textRenderer;

    @Inject(method = "drawGameInfo", at = @At("HEAD"), cancellable = true)
    private void overflowanimations$legacyGameInfo(final CallbackInfo ci) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.legacyDebugScreen) {
            LegacyDebugScreen.drawGameInfo(this.minecraft, this.textRenderer);
            ci.cancel();
        }
    }

    @Inject(method = "drawSystemInfo", at = @At("HEAD"), cancellable = true)
    private void overflowanimations$legacySystemInfo(final Window window, final CallbackInfo ci) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.legacyDebugScreen) {
            LegacyDebugScreen.drawSystemInfo(this.textRenderer, window);
            ci.cancel();
        }
    }

    @WrapWithCondition(method = {"drawGameInfo", "drawSystemInfo"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/overlay/DebugOverlay;fill(IIIII)V"))
    private boolean overflowanimations$disableDebugHudBackground(final int x1, final int y1, final int x2, final int y2, final int color) {
        return !(OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.disableDebugHudBackground);
    }

    @WrapOperation(method = {"drawGameInfo", "drawSystemInfo"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TextRenderer;draw(Ljava/lang/String;III)I"))
    private int overflowanimations$debugHudTextShadow(final TextRenderer instance, final String text, final int x, final int y, final int color, final Operation<Integer> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.debugHudTextShadow) {
            return instance.draw(text, x, y, color, true);
        } else {
            return original.call(instance, text, x, y, color);
        }
    }
}
