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

package org.polyfrost.overflowanimations.mixins.v1.gui.centered_widgets;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
//? if 1.21.11 {
/*import net.minecraft.client.gui.GuiGraphics;
*///?} else {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?}
import net.minecraft.client.gui.components.AbstractScrollArea;
import net.minecraft.client.gui.components.AbstractSelectionList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

@Mixin(AbstractSelectionList.class)
public abstract class MixinAbstractSelectionList<E extends AbstractSelectionList.Entry<E>> {
    //? if 1.21.11 {
    /*@Inject(method = "renderItem", at = @At("HEAD"))
    private void overflowanimations$updateScroll(final GuiGraphics graphics, final int mouseX, final int mouseY, final float tickDelta, final E entry, final CallbackInfo ci) {
    *///?} else {
    @Inject(method = "extractItem", at = @At("HEAD"))
    private void overflowanimations$updateScroll(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float tickDelta, final E entry, final CallbackInfo ci) {
    //?}
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.centerScrollableListWidgets) {
            ((AbstractScrollArea) (Object) this).refreshScrollAmount();
        }
    }

    //? if 1.21.11 {
    /*@WrapOperation(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;isFocused()Z"))
    *///?} else {
    @WrapOperation(method = "extractItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractSelectionList;isFocused()Z"))
    //?}
    private boolean overflowanimations$listWidgetSelectedBorderColor(AbstractSelectionList<?> instance, Operation<Boolean> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.listWidgetSelectedBorderColor) {
            return false;
        } else {
            return original.call(instance);
        }
    }
}
