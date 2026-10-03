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

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.widget.ListWidget;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ListWidget.class)
public abstract class MixinListWidget_CenteredLists {
    @Shadow
    protected float scrollAmount;

    @Shadow
    protected boolean centerAlongY;

    @Shadow
    public abstract int getMaxScroll();

    @WrapOperation(method = "getMaxScroll", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(II)I"))
    private int overflowanimations$allowNegativeMaxScroll(final int zero, final int maxScroll, final Operation<Integer> original) {
        return OverflowAnimationsConfig.instance().screen.centerScrollableListWidgets ? maxScroll : original.call(zero, maxScroll);
    }

    @Inject(method = "capScrolling", at = @At("HEAD"), cancellable = true)
    private void overflowanimations$legacyCapScrolling(final CallbackInfo ci) {
        if (!OverflowAnimationsConfig.instance().screen.centerScrollableListWidgets) {
            return;
        }
        ci.cancel();
        int maxScroll = this.getMaxScroll();
        if (maxScroll < 0) {
            maxScroll = this.centerAlongY ? maxScroll / 2 : 0;
        }
        this.scrollAmount = Math.min(Math.max(this.scrollAmount, 0.0F), maxScroll);
    }
}
