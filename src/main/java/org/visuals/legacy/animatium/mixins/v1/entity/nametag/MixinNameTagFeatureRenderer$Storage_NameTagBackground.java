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

package org.visuals.legacy.animatium.mixins.v1.entity.nametag;

//? if <26.2 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
*///?}
//? if 1.21.11 {
/*import net.minecraft.client.Options;
*///?}
//? if <26.2 {
/*import net.minecraft.client.renderer.feature.NameTagFeatureRenderer;
*///?}
//? if 26.1 {
/*import net.minecraft.client.renderer.state.OptionsRenderState;
*///?}
//? if <26.2 {
/*import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
*///?}

//? if <26.2 {
/*@Mixin(NameTagFeatureRenderer.Storage.class)
public abstract class MixinNameTagFeatureRenderer$Storage_NameTagBackground {
*///?}
    //? if 1.21.11 {
    /*@WrapOperation(method = "add", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Options;getBackgroundOpacity(F)F"))
    private float animatium$nameTagBackground(final Options instance, final float defaultOpacity, final Operation<Float> original) {
    *///?} elif 26.1 {
    /*@WrapOperation(method = "add", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/state/OptionsRenderState;getBackgroundOpacity(F)F"))
    private float animatium$nameTagBackground(final OptionsRenderState instance, final float defaultOpacity, final Operation<Float> original) {
    *///?}
//? if <26.2 {
        /*if (Animatium.isEnabled() && AnimatiumConfig.instance().extras.hideNameTagBackground) {
            return 0F;
        } else {
            return original.call(instance, defaultOpacity);
        }
    }
}
*///?}
