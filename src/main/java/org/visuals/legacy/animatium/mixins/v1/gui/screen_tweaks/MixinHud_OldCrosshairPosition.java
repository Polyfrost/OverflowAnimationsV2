/**
 * Animatium
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

package org.visuals.legacy.animatium.mixins.v1.gui.screen_tweaks;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
//? if <26.2 {
/*import net.minecraft.client.gui.Gui;
*///?}
//? if 1.21.11 {
/*import net.minecraft.client.gui.GuiGraphics;
*///?} else {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?}
//? if >=26.2 {
import net.minecraft.client.gui.Hud;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

//? if <26.2 {
/*@Mixin(Gui.class)
*///?} else {
@Mixin(Hud.class)
//?}
public abstract class MixinHud_OldCrosshairPosition {
    //? if 1.21.11 {
    /*@Definition(id = "graphics", local = @Local(type = GuiGraphics.class, argsOnly = true))
    @Definition(id = "guiWidth", method = "Lnet/minecraft/client/gui/GuiGraphics;guiWidth()I")
    @Expression("(graphics.guiWidth() - 15) / 2")
    @ModifyExpressionValue(method = "renderCrosshair", at = @At("MIXINEXTRAS:EXPRESSION"))
    *///?} else {
    @Definition(id = "graphics", local = @Local(type = GuiGraphicsExtractor.class, argsOnly = true))
    @Definition(id = "guiWidth", method = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;guiWidth()I")
    @Expression("(graphics.guiWidth() - 15) / 2")
    @ModifyExpressionValue(method = "extractCrosshair", at = @At("MIXINEXTRAS:EXPRESSION"))
    //?}
    private int animatium$oldCrosshairPosition(final int original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().screen.oldCrosshairPosition) {
            return original + 1;
        } else {
            return original;
        }
    }
}
