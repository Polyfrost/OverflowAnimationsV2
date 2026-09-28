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

package org.polyfrost.overflowanimations.mixins.v1.gui.hover_text_color;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

@Mixin(AbstractButton.class)
public abstract class MixinAbstractButton_LegacyTextHoverColor extends AbstractWidget {
    public MixinAbstractButton_LegacyTextHoverColor(final int x, final int y, final int width, final int height, final Component message) {
        super(x, y, width, height, message);
    }

    //? if 1.21.11 {
    /*@WrapOperation(method = "renderDefaultLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractButton;getMessage()Lnet/minecraft/network/chat/Component;"))
    *///?} else {
    @WrapOperation(method = "extractDefaultLabel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractButton;getMessage()Lnet/minecraft/network/chat/Component;"))
    //?}
    private Component overflowanimations$renderWidget$old$textColor(final AbstractButton instance, final Operation<Component> original) {
        final Component component = original.call(instance);
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.legacyWidgetHoverTextColor) {
            return component.copy().withColor(!this.active ? 0xA0A0A0 : (this.isHoveredOrFocused() ? 0xFFFFA0 : 0xE0E0E0));
        } else {
            return component;
        }
    }
}
