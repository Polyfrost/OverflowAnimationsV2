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

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GameGui;
import net.minecraft.client.render.Window;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.objectweb.asm.Opcodes;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.legacy.hud.DebugCrosshair;
import org.polyfrost.overflowanimations.util.enums.DebugCrosshairSetting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameGui.class)
public abstract class MixinGameGui_CrosshairAndHearts {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private long lastHealthAnimationTime;

    @ModifyVariable(method = "renderStatusBars", at = @At(value = "STORE", ordinal = 0), index = 4)
    private boolean overflowanimations$useOldHealthLogic(boolean value) { // thanks mixces <3
        /* taken straight from 1.7 */
        /* MC-2930 aimed to revert this... MC-73438 wants to bring this back.... there is no winning :/ */
        if (OverflowAnimationsConfig.instance().screen.disableHeartFlash) {
            boolean i3 = minecraft.player.getMaxHealth() / 3 % 2 == 1;
            if (minecraft.player.getMaxHealth() < 10) {
                i3 = false;
            }
            return i3;
        } else return value;
    }

    @ModifyExpressionValue(method = "hasCrosshair", at = @At(value = "FIELD", target = "Lnet/minecraft/client/options/GameOptions;debugEnabled:Z"))
    private boolean overflowanimations$debugCrosshairStyle(final boolean debugEnabled) {
        final DebugCrosshairSetting style = OverflowAnimationsConfig.instance().screen.debugCrosshairStyle;
        return debugEnabled && (style == DebugCrosshairSetting.V1_8 || style == DebugCrosshairSetting.VANILLA);
    }

    @ModifyReturnValue(method = "hasCrosshair", at = @At("RETURN"))
    private boolean overflowanimations$hideCrosshairInThirdPerson(final boolean original) {
        return original && (OverflowAnimationsConfig.instance().screen.thirdPersonCrosshair.isLegacy() || this.minecraft.options.perspective == 0);
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GameGui;hasCrosshair()Z"))
    private boolean overflowanimations$axisCrosshair(final GameGui instance, final Operation<Boolean> original, final float tickDelta, @Local final Window window) {
        final boolean hasCrosshair = original.call(instance);
        if (hasCrosshair && OverflowAnimationsConfig.instance().screen.debugCrosshairStyle == DebugCrosshairSetting.V1_12 && DebugCrosshair.isDebugCrosshairVisible(this.minecraft) && this.minecraft.options.perspective == 0) {
            DebugCrosshair.render(this.minecraft, window, tickDelta);
            return false;
        }
        return hasCrosshair;
    }
}
