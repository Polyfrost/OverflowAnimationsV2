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

package org.polyfrost.overflowanimations.mixins.v1.gui.screen_tweaks;

//? if <26.1 {
/*import net.minecraft.client.gui.GuiGraphics;
*///?} else {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?}
import net.minecraft.client.gui.components.AbstractContainerWidget;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

@Mixin(AbstractSelectionList.class)
public abstract class MixinAbstractSelectionList_ListGradientBackground extends AbstractContainerWidget {
    //? if <26.1 {
    /*public MixinAbstractSelectionList_ListGradientBackground(final int x, final int y, final int width, final int height, final Component message) {
        super(x, y, width, height, message);
    *///?} else {
    public MixinAbstractSelectionList_ListGradientBackground(final int x, final int y, final int width, final int height, final Component message, final ScrollbarSettings scrollbarSettings) {
        super(x, y, width, height, message, scrollbarSettings);
    //?}
    }

    //? if <1.21.6 {
    /*@Inject(method = "renderListBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Ljava/util/function/Function;Lnet/minecraft/resources/Identifier;IIFFIIII)V", shift = At.Shift.AFTER))
    private void overflowanimations$renderListBackgroundGradient(final GuiGraphics graphics, final CallbackInfo ci) {
    *///?} elif <26.1 {
    /*@Inject(method = "renderListBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIII)V", shift = At.Shift.AFTER))
    private void overflowanimations$renderListBackgroundGradient(final GuiGraphics graphics, final CallbackInfo ci) {
    *///?} elif >=26.1 <26.3 {
    /*@Inject(method = "extractListBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIII)V", shift = At.Shift.AFTER))
    *///?} elif 26.3 {
    @Inject(method = "extractListBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIII)V", shift = At.Shift.AFTER))
    //?} else {
    /*@Inject(method = "extractListBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIII)V", shift = At.Shift.AFTER))
    *///?}
    //? if >=26.1 {
    private void overflowanimations$renderListBackgroundGradient(final GuiGraphicsExtractor graphics, final CallbackInfo ci) {
    //?}
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.listBackgroundGradient) {
            graphics.fillGradient(this.getX(), this.getY(), this.getRight(), this.getY() + 4, -16777216, 0);
            graphics.fillGradient(this.getX(), this.getBottom() - 4, this.getRight(), this.getBottom(), 0, -16777216);
        }
    }
}
