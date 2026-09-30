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

package org.polyfrost.overflowanimations.mixins.v1.gui.debug;

//? if <1.21.9 {
/*import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.gui.Gui;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.util.enums.DebugCrosshairSetting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Gui.class)
public abstract class MixinGui_DebugCrosshairStyle {
    //? if <1.21.6 {
    /^@ModifyExpressionValue(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/DebugScreenOverlay;showDebugScreen()Z"))
    private boolean overflowanimations$debugCrosshairStyle(final boolean showDebugScreen) {
        return showDebugScreen && !overflowanimations$is(DebugCrosshairSetting.V1_7);
    }

    @WrapWithCondition(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;renderCrosshair(I)V"))
    private boolean overflowanimations$hideModernDebugCrosshair(final int size) {
        return !overflowanimations$is(DebugCrosshairSetting.V1_8);
    }
    ^///?} else {
    @ModifyReturnValue(method = "shouldRenderDebugCrosshair", at = @At("RETURN"))
    private boolean overflowanimations$debugCrosshairStyle(final boolean shouldRender) {
        return shouldRender && !overflowanimations$is(DebugCrosshairSetting.V1_7);
    }
    //?}

    @Unique
    private static boolean overflowanimations$is(final DebugCrosshairSetting setting) {
        return OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.debugCrosshairStyle == setting;
    }
}
*///?}
