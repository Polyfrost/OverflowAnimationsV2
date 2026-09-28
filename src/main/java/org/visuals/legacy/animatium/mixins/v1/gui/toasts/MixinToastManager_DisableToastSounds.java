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

package org.visuals.legacy.animatium.mixins.v1.gui.toasts;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.moulberry.mixinconstraints.annotations.IfModAbsent;
import net.minecraft.client.gui.components.toasts.RecipeToast;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.sounds.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@IfModAbsent(value = "sodium-extra")
@Mixin(ToastManager.class)
public abstract class MixinToastManager_DisableToastSounds {
    //? if 1.21.11 {
    /*@WrapWithCondition(method = "method_61991", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/toasts/Toast$Visibility;playSound(Lnet/minecraft/client/sounds/SoundManager;)V"))
    private boolean animatium$disableToastSounds(final Toast.Visibility instance, final SoundManager manager, @Local(argsOnly = true, ordinal = 0) final ToastManager.ToastInstance<Toast> toastInstance) {
    *///?} else {
    @WrapWithCondition(method = "lambda$update$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/toasts/Toast$Visibility;playSound(Lnet/minecraft/client/sounds/SoundManager;)V"))
    private boolean animatium$disableToastSounds(final Toast.Visibility instance, final SoundManager manager, @Local(argsOnly = true, name = "toast") final ToastManager.ToastInstance<Toast> toastInstance) {
    //?}
        final Toast toast = toastInstance.getToast();
        return !Animatium.isEnabled() || !AnimatiumConfig.instance().extras.disableRecipeAndTutorialToasts || (!(toast instanceof RecipeToast) && !(toast instanceof TutorialToast));
    }
}
