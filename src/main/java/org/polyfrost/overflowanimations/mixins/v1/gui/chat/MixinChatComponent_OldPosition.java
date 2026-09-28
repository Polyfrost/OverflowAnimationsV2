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

package org.polyfrost.overflowanimations.mixins.v1.gui.chat;

import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.components.ChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

@Mixin(ChatComponent.class)
public abstract class MixinChatComponent_OldPosition {
    @Unique
    private static final int overflowanimations$oldChatY = 28;

    @Expression("40")
    //? if 1.21.11 {
    /*@ModifyExpressionValue(method = "render(Lnet/minecraft/client/gui/components/ChatComponent$ChatGraphicsAccess;IIZ)V", at = @At("MIXINEXTRAS:EXPRESSION"))
    *///?} else {
    @ModifyExpressionValue(method = "extractRenderState(Lnet/minecraft/client/gui/components/ChatComponent$ChatGraphicsAccess;IILnet/minecraft/client/gui/components/ChatComponent$DisplayMode;)V", at = @At("MIXINEXTRAS:EXPRESSION"))
    //?}
    private int overflowanimations$oldChatPosition$render(final int original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.oldChatPosition) {
            return overflowanimations$oldChatY;
        } else {
            return original;
        }
    }

    // TODO
    /*@Expression("40.0")
    @ModifyExpressionValue(method = "handleChatQueueClicked", at = @At("MIXINEXTRAS:EXPRESSION"))
    private double overflowanimations$oldChatPosition$handleChatQueueClicked(double original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.oldChatPosition) {
            return overflowanimations$oldChatY;
        } else {
            return original;
        }
    }

    @Expression("40.0")
    @ModifyExpressionValue(method = "screenToChatY", at = @At("MIXINEXTRAS:EXPRESSION"))
    private double overflowanimations$oldChatPosition$screenToChatY(double original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.oldChatPosition) {
            return overflowanimations$oldChatY;
        } else {
            return original;
        }
    }*/
}
