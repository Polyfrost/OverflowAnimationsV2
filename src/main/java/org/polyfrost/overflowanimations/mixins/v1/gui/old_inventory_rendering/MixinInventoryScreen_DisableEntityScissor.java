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

// Targets GUI render states added in 1.21.6
//? if >=1.21.6 {
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
//? if <26.1 {
/*import net.minecraft.client.gui.GuiGraphics;
*///?} else {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//?}
import net.minecraft.client.gui.navigation.ScreenRectangle;
//? if <26.1 {
/*import net.minecraft.client.gui.render.state.pip.GuiEntityRenderState;
*///?}
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
//? if >=26.1 {
import net.minecraft.client.renderer.state.gui.pip.GuiEntityRenderState;
//?}
//? if <26.2 {
/*import org.joml.Quaternionf;
import org.joml.Vector3f;
*///?} else {
import org.joml.Quaternionfc;
import org.joml.Vector3fc;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

@Mixin(InventoryScreen.class)
public abstract class MixinInventoryScreen_DisableEntityScissor {
    //? if <1.21.11 {
    /*@WrapOperation(method = "renderEntityInInventory", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;submitEntityRenderState(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;FLorg/joml/Vector3f;Lorg/joml/Quaternionf;Lorg/joml/Quaternionf;IIII)V"))
    *///?} elif <26.1 {
    /*@WrapOperation(method = "renderEntityInInventoryFollowsMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;submitEntityRenderState(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;FLorg/joml/Vector3f;Lorg/joml/Quaternionf;Lorg/joml/Quaternionf;IIII)V"))
    *///?}
    //? if <26.1 {
    /*private static void overflowanimations$disableEntityScissor(final GuiGraphics instance, final EntityRenderState renderState, final float scale, final Vector3f translation, final Quaternionf rotation, final Quaternionf overrideCameraAngle, final int x0, final int y0, final int x1, final int y1, final Operation<Void> original) {
    *///?} elif 26.1 {
    /*@WrapOperation(method = "extractEntityInInventoryFollowsMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;entity(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;FLorg/joml/Vector3f;Lorg/joml/Quaternionf;Lorg/joml/Quaternionf;IIII)V"))
    private static void overflowanimations$disableEntityScissor(final GuiGraphicsExtractor instance, final EntityRenderState renderState, final float scale, final Vector3f translation, final Quaternionf rotation, final Quaternionf overrideCameraAngle, final int x0, final int y0, final int x1, final int y1, final Operation<Void> original) {
    *///?} else {
    @WrapOperation(method = "extractEntityInInventoryFollowsMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;entity(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;FLorg/joml/Vector3fc;Lorg/joml/Quaternionfc;Lorg/joml/Quaternionfc;IIII)V"))
    private static void overflowanimations$disableEntityScissor(final GuiGraphicsExtractor instance, final EntityRenderState renderState, final float scale, final Vector3fc translation, final Quaternionfc rotation, final Quaternionfc overrideCameraAngle, final int x0, final int y0, final int x1, final int y1, final Operation<Void> original) {
    //?}
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.disableInventoryEntityScissor) {
            final ScreenRectangle bounds = new ScreenRectangle(0, 0, instance.guiWidth(), instance.guiHeight());
            final int expansion = 40;
            //? if <26.1 {
            /*instance.guiRenderState.submitPicturesInPictureState(new GuiEntityRenderState(renderState, translation, rotation, overrideCameraAngle, x0 - expansion, y0 - expansion, x1 + expansion, y1 + expansion, scale, null, bounds));
            *///?} else {
            instance.guiRenderState.addPicturesInPictureState(new GuiEntityRenderState(renderState, translation, rotation, overrideCameraAngle, x0 - expansion, y0 - expansion, x1 + expansion, y1 + expansion, scale, null, bounds));
            //?}
        } else {
            original.call(instance, renderState, scale, translation, rotation, overrideCameraAngle, x0, y0, x1, y1);
        }
    }
}
//?}
