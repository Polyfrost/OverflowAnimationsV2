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

package org.polyfrost.overflowanimations.mixins.v1.gui;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
//? if <1.21.6 {
/*import net.minecraft.client.renderer.rendertype.RenderType;
import java.util.function.Function;
*///?} elif 26.3 {
/*import com.mojang.renderpearl.api.pipeline.RenderPipeline;
*///?} else {
import com.mojang.blaze3d.pipeline.RenderPipeline;
//?}
import net.minecraft.client.CameraType;
//? if <26.2 {
/*import net.minecraft.client.gui.Gui;
*///?}
//? if <26.1 {
/*import net.minecraft.client.gui.GuiGraphics;
*///?} else {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?}
//? if >=26.2 {
import net.minecraft.client.gui.Hud;
//?}
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

//? if <26.2 {
/*@Mixin(Gui.class)
*///?} else {
@Mixin(Hud.class)
//?}
public abstract class MixinHud_CrosshairAndHearts {
    //? if <26.1 {
    /*@WrapOperation(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/CameraType;isFirstPerson()Z"))
    *///?} else {
    @WrapOperation(method = "extractCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/CameraType;isFirstPerson()Z"))
    //?}
    private boolean overflowanimations$crosshairInThirdPerson(final CameraType instance, final Operation<Boolean> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.crosshairInThirdPerson) {
            return true;
        } else {
            return original.call(instance);
        }
    }

    //? if <1.21.6 {
    /*@WrapWithCondition(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/Identifier;IIII)V", ordinal = 2))
    private boolean overflowanimations$fixHighAttackSpeedIndicator(final GuiGraphics instance, final Function<Identifier, RenderType> renderType, final Identifier location, final int x, final int y, final int width, final int height, @Local(ordinal = 0) final float attackStrengthScale) {
    *///?} elif <26.1 {
    /*@WrapWithCondition(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V", ordinal = 2))
    private boolean overflowanimations$fixHighAttackSpeedIndicator(final GuiGraphics instance, final RenderPipeline renderPipeline, final Identifier location, final int x, final int y, final int width, final int height, @Local(ordinal = 0) final float attackStrengthScale) {
    *///?} else {
    //? if 26.3 {
    /*@WrapWithCondition(method = "extractCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V", ordinal = 2))
    *///?} else {
    @WrapWithCondition(method = "extractCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V", ordinal = 2))
    //?}
    private boolean overflowanimations$fixHighAttackSpeedIndicator(final GuiGraphicsExtractor instance, final RenderPipeline renderPipeline, final Identifier location, final int x, final int y, final int width, final int height, @Local(name = "attackStrengthScale") final float attackStrengthScale) {
    //?}
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().fixes.fixHighAttackSpeedIndicator) {
            return (int) (attackStrengthScale * 17.0F) != 0;
        } else {
            return true;
        }
    }

    //? if <26.1 {
    /*@WrapWithCondition(method = "renderHearts", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIZZZ)V"))
    private boolean overflowanimations$heartFlash(final Gui instance, final GuiGraphics graphics, final Gui.HeartType type, final int xo, final int yo, final boolean isHardcore, final boolean blinks, final boolean half) {
        return !OverflowAnimations.isEnabled() || !OverflowAnimationsConfig.instance().screen.disableHeartFlash || !blinks || type == Gui.HeartType.CONTAINER;
    *///?} elif 26.1 {
    /*@WrapWithCondition(method = "extractHearts", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;extractHeart(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/gui/Gui$HeartType;IIZZZ)V"))
    private boolean overflowanimations$heartFlash(final Gui instance, final GuiGraphicsExtractor graphics, final Gui.HeartType type, final int xo, final int yo, final boolean isHardcore, final boolean blinks, final boolean half) {
        return !OverflowAnimations.isEnabled() || !OverflowAnimationsConfig.instance().screen.disableHeartFlash || !blinks || type == Gui.HeartType.CONTAINER;
    *///?} else {
    @WrapWithCondition(method = "extractHearts", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Hud;extractHeart(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/gui/Hud$HeartType;IIZZZ)V"))
    private boolean overflowanimations$heartFlash(final Hud instance, final GuiGraphicsExtractor graphics, final Hud.HeartType type, final int xo, final int yo, final boolean isHardcore, final boolean blinks, final boolean half) {
        return !OverflowAnimations.isEnabled() || !OverflowAnimationsConfig.instance().screen.disableHeartFlash || !blinks || type == Hud.HeartType.CONTAINER;
    //?}
    }
}
