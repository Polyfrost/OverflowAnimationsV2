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

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
//? if 1.21.11 {
/*import net.minecraft.client.gui.GuiGraphics;
*///?} else {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?}
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.handler.rendering.RenderUtilsKt;
import org.polyfrost.overflowanimations.util.ItemUtilKt;

//? if 1.21.11 {
/*@Mixin(GuiGraphics.class)
*///?} else {
@Mixin(GuiGraphicsExtractor.class)
//?}
public abstract class MixinGuiGraphics_ToolTipItemBar {
    //? if 1.21.11 {
    /*@WrapOperation(method = "renderTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderTooltipBackground(Lnet/minecraft/client/gui/GuiGraphics;IIIILnet/minecraft/resources/Identifier;)V"))
    private void overflowanimations$tooltipStyleRendering(final GuiGraphics graphics, final int x, final int y, final int w, final int h, final Identifier style, final Operation<Void> original) {
    *///?} else {
    @WrapOperation(method = "tooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;extractTooltipBackground(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIIILnet/minecraft/resources/Identifier;)V"))
    private void overflowanimations$tooltipStyleRendering(final GuiGraphicsExtractor graphics, final int x, final int y, final int w, final int h, final Identifier style, final Operation<Void> original) {
    //?}
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.tooltipStyleRendering) {
            int n = x - 3;
            int o = y - 3;
            int p = w + 6;
            int q = h + 6;
            // TODO/NOTE: Figure out good names for these variables LOL
            final int lineColor = -267386864;
            RenderUtilsKt.fillHorizontalLine(graphics, n, o - 1, p, lineColor);
            RenderUtilsKt.fillHorizontalLine(graphics, n, o + q, p, lineColor);
            RenderUtilsKt.fillRectangle(graphics, n, o, p, q, lineColor);
            RenderUtilsKt.fillVerticalLine(graphics, n - 1, o, q, lineColor);
            RenderUtilsKt.fillVerticalLine(graphics, n + p, o, q, lineColor);
            RenderUtilsKt.fillFrameGradient(graphics, n, o + 1, p, q, 0x505000FF, 0x5028007F);
        } else {
            original.call(graphics, x, y, w, h, style);
        }
    }

    //? if 1.21.11 {
    /*@Inject(method = "renderItemBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(Lcom/mojang/blaze3d/pipeline/RenderPipeline;IIIII)V", ordinal = 0, shift = At.Shift.AFTER))
    private void overflowanimations$oldDurabilityBar(final ItemStack itemStack, final int x, final int y, final CallbackInfo ci, @Local(ordinal = 2) final int left, final @Local(ordinal = 3) int top) {
    *///?} elif >=26.1 <26.3 {
    /*@Inject(method = "itemBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;fill(Lcom/mojang/blaze3d/pipeline/RenderPipeline;IIIII)V", ordinal = 0, shift = At.Shift.AFTER))
    *///?} elif 26.3 {
    /*@Inject(method = "itemBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;fill(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;IIIII)V", ordinal = 0, shift = At.Shift.AFTER))
    *///?} else {
    @Inject(method = "itemBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;fill(Lcom/mojang/blaze3d/pipeline/RenderPipeline;IIIII)V", ordinal = 0, shift = At.Shift.AFTER))
    //?}
    //? if >=26.1 {
    private void overflowanimations$oldDurabilityBar(final ItemStack itemStack, final int x, final int y, final CallbackInfo ci, @Local(name = "left") final int left, final @Local(name = "top") int top) {
    //?}
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.durabilityBarColors && !(itemStack.getItem() instanceof BundleItem)) {
            final int color = ARGB.opaque(ARGB.color((255 - ItemUtilKt.getLegacyDurabilityColorValue(itemStack)) / 4, 64, 0));
            //? if 1.21.11 {
            /*RenderUtilsKt.fillRectangle((GuiGraphics) (Object) this, left, top, 12, 1, color);
            *///?} else {
            RenderUtilsKt.fillRectangle((GuiGraphicsExtractor) (Object) this, left, top, 12, 1, color);
            //?}
        }
    }
}
