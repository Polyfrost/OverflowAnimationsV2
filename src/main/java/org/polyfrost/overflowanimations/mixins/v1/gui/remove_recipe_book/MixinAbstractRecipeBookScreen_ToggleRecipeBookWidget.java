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

package org.polyfrost.overflowanimations.mixins.v1.gui.remove_recipe_book;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

@Mixin(AbstractRecipeBookScreen.class)
public abstract class MixinAbstractRecipeBookScreen_ToggleRecipeBookWidget {
    @WrapOperation(method = "initButton", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractRecipeBookScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;"))
    private GuiEventListener overflowanimations$hideRecipeBook$button(final AbstractRecipeBookScreen<?> instance, final GuiEventListener guiEventListener, final Operation<GuiEventListener> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().extras.hideRecipeBook) {
            return null;
        } else {
            return original.call(instance, guiEventListener);
        }
    }

    @WrapOperation(method = "initButton", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractRecipeBookScreen;addWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;"))
    private GuiEventListener overflowanimations$hideRecipeBook$widget(final AbstractRecipeBookScreen<?> instance, final GuiEventListener guiEventListener, final Operation<GuiEventListener> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().extras.hideRecipeBook) {
            return null;
        } else {
            return original.call(instance, guiEventListener);
        }
    }
}
