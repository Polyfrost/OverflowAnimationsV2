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

//? if >=26.1 {
import com.llamalad7.mixinextras.expression.Definition;
//?}
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
//? if >=26.1 {
import com.llamalad7.mixinextras.sugar.Local;
//?}
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.inventory.EffectsInInventory;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.mixins.accessor.AbstractRecipeBookScreenAccessor;

@Mixin(EffectsInInventory.class)
public abstract class MixinEffectsInInventory_EffectBackgroundWidthAndPosition {
    @Shadow
    @Final
    private AbstractContainerScreen<?> screen;

    // Older versions already draw effects at full width
    //? if >=1.21.11 {
    //? if >=1.21.11 <26.1 {
    /*@Expression("? - 7")
    @ModifyExpressionValue(method = "render", at = @At("MIXINEXTRAS:EXPRESSION"))
    *///?} else {
    @Definition(id = "availableWidth", local = @Local(type = int.class, name = "availableWidth"))
    @Expression("availableWidth - 7")
    @ModifyExpressionValue(method = "extractRenderState", at = @At("MIXINEXTRAS:EXPRESSION"))
    //?}
    private int overflowanimations$fullWidthInventoryEffects(final int original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.fullWidthInventoryEffects && !(this.screen instanceof AbstractRecipeBookScreen<?> recipeBookScreen && ((AbstractRecipeBookScreenAccessor) recipeBookScreen).overflowanimations$getRecipeBookComponent().isVisible())) {
            return 120;
        } else {
            return original;
        }
    }

    //? if >=1.21.11 <26.1 {
    /*@WrapOperation(method = "renderBackground", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I"))
    *///?} else {
    @WrapOperation(method = "extractBackground", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I"))
    //?}
    private int overflowanimations$fullWidthInventoryEffects$useOldWidth(final int min, final int max, final Operation<Integer> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.fullWidthInventoryEffects) {
            return min == 120 ? 120 : 32; // Hardcode old width values
        } else {
            return original.call(min, max);
        }
    }
    //?}

    //? if <1.21.11 {
    /*@WrapOperation(method = "renderEffects", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;imageWidth:I", opcode = Opcodes.GETFIELD))
    *///?} elif <26.1 {
    /*@WrapOperation(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;imageWidth:I", opcode = Opcodes.GETFIELD))
    *///?} else {
    @WrapOperation(method = "extractRenderState", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;imageWidth:I", opcode = Opcodes.GETFIELD))
    //?}
    private int overflowanimations$effectsInventoryPosition(final AbstractContainerScreen<?> instance, final Operation<Integer> original) {
        final int imageWidth = original.call(instance);
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.inventoryEffectsPosition && !(this.screen instanceof AbstractRecipeBookScreen<?> recipeBookScreen && ((AbstractRecipeBookScreenAccessor) recipeBookScreen).overflowanimations$getRecipeBookComponent().isVisible())) {
            return 0;
        } else {
            return imageWidth;
        }
    }

    //? if <1.21.11 {
    /*@ModifyExpressionValue(method = "renderEffects", at = @At(value = "CONSTANT", args = "intValue=2"))
    *///?} elif <26.1 {
    /*@ModifyExpressionValue(method = "render", at = @At(value = "CONSTANT", args = "intValue=2"))
    *///?} else {
    @ModifyExpressionValue(method = "extractRenderState", at = @At(value = "CONSTANT", args = "intValue=2"))
    //?}
    private int overflowanimations$effectsInventoryPosition(final int original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.inventoryEffectsPosition && !(this.screen instanceof AbstractRecipeBookScreen<?> recipeBookScreen && ((AbstractRecipeBookScreenAccessor) recipeBookScreen).overflowanimations$getRecipeBookComponent().isVisible())) {
            return OverflowAnimationsConfig.instance().screen.fullWidthInventoryEffects ? -124 : 0; // TODO: Modern
        } else {
            return original;
        }
    }
}
