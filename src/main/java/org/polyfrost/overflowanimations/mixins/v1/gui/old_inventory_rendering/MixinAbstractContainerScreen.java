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

package org.polyfrost.overflowanimations.mixins.v1.gui.old_inventory_rendering;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
//? if 1.21.11 {
/*import net.minecraft.client.gui.GuiGraphics;
*///?} else {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?}
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

@Mixin(AbstractContainerScreen.class)
public abstract class MixinAbstractContainerScreen {
    @Shadow
    @Nullable
    protected Slot hoveredSlot;

    //? if 1.21.11 {
    /*@WrapWithCondition(method = "renderContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderSlotHighlightBack(Lnet/minecraft/client/gui/GuiGraphics;)V"))
    private boolean overflowanimations$slotHoverStyleRendering$disableBack(final AbstractContainerScreen<?> instance, final GuiGraphics graphics) {
    *///?} else {
    @WrapWithCondition(method = "extractContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;extractSlotHighlightBack(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V"))
    private boolean overflowanimations$slotHoverStyleRendering$disableBack(final AbstractContainerScreen<?> instance, final GuiGraphicsExtractor graphics) {
    //?}
        return !OverflowAnimations.isEnabled() || !OverflowAnimationsConfig.instance().screen.slotHoverStyleRendering;
    }

    //? if 1.21.11 {
    /*@WrapOperation(method = "renderContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderSlotHighlightFront(Lnet/minecraft/client/gui/GuiGraphics;)V"))
    private void overflowanimations$slotHoverStyleRendering(final AbstractContainerScreen<?> instance, final GuiGraphics graphics, final Operation<Void> original) {
    *///?} else {
    @WrapOperation(method = "extractContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;extractSlotHighlightFront(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V"))
    private void overflowanimations$slotHoverStyleRendering(final AbstractContainerScreen<?> instance, final GuiGraphicsExtractor graphics, final Operation<Void> original) {
    //?}
        final Slot slot = this.hoveredSlot;
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.slotHoverStyleRendering && slot != null && slot.isHighlightable()) {
            graphics.fillGradient(slot.x, slot.y, slot.x + 16, slot.y + 16, -2130706433, -2130706433);
        } else {
            original.call(instance, graphics);
        }
    }
}
