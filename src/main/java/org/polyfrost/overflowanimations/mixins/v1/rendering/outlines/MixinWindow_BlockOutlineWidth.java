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

package org.polyfrost.overflowanimations.mixins.v1.rendering.outlines;

//? if >=1.21.11
import com.mojang.blaze3d.platform.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

//? if <1.21.11 {
/*@Mixin(targets = "net.minecraft.client.renderer.RenderStateShard$LineStateShard")
*///?} else {
@Mixin(Window.class)
//?}
public abstract class MixinWindow_BlockOutlineWidth {
    //? if <1.21.11 {
    /*@ModifyConstant(method = "*", constant = @Constant(floatValue = 2.5F)) // Line width lambda in the constructor
    private static float overflowanimations$oldBlockOutline(final float lineWidth) {
    *///?} else {
    @ModifyConstant(method = "getAppropriateLineWidth", constant = @Constant(floatValue = 2.5F))
    private float overflowanimations$oldBlockOutline(final float lineWidth) {
    //?}
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.blockOutlineRendering) {
            return 2.0F;
        } else {
            return lineWidth;
        }
    }
}
