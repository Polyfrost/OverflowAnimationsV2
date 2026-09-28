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
import net.minecraft.client.gui.components.AbstractScrollArea;
import net.minecraft.client.gui.components.AbstractSelectionList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.mixins.accessor.AbstractSelectionListAccessor;

@Mixin(AbstractScrollArea.class)
public abstract class MixinAbstractScrollArea_ScrollHandling {
    @Shadow
    private double scrollAmount;

    @Shadow
    protected abstract int contentHeight();

    @Shadow
    public abstract int maxScrollAmount();

    @Inject(method = "setScrollAmount", at = @At("HEAD"), cancellable = true)
    private void overflowanimations$allowNegativeScrolling(final double scrollAmount, final CallbackInfo ci) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.centerScrollableListWidgets && (Object) this instanceof AbstractSelectionList<?> abstractSelectionList) {
            ci.cancel();
            int maxScrollY = maxScrollAmount();
            if (maxScrollY < 0) {
                maxScrollY /= 2;
            }

            if (!((AbstractSelectionListAccessor) abstractSelectionList).overflowanimations$shouldCenterVertically() && maxScrollY < 0) {
                maxScrollY = 0;
            }

            this.scrollAmount = Math.min(Math.max(0, scrollAmount), maxScrollY);
        }
    }

    @WrapOperation(method = "maxScrollAmount", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(II)I"))
    public int overflowanimations$modifyMaxScroll(final int left, final int right, final Operation<Integer> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.centerScrollableListWidgets && (Object) this instanceof AbstractSelectionList<?> abstractSelectionList) {
            return this.contentHeight() - abstractSelectionList.getHeight();
        } else {
            return original.call(left, right);
        }
    }
}
