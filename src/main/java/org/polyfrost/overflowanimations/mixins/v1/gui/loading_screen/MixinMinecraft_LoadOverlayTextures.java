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

package org.polyfrost.overflowanimations.mixins.v1.gui.loading_screen;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.renderer.texture.TextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft_LoadOverlayTextures {
    @Unique
    private final ThreadLocal<Runnable> overflowanimations$loadOverlayTextures = ThreadLocal.withInitial(() -> null);

    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/LoadingOverlay;registerTextures(Lnet/minecraft/client/renderer/texture/TextureManager;)V"))
    private void overflowanimations$disableVanillaLoading(final TextureManager textureManager, final Operation<Void> original) {
        final Runnable runnable = () -> original.call(textureManager);
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.legacyLoadingScreen) {
            this.overflowanimations$loadOverlayTextures.set(runnable);
        } else {
            runnable.run();
        }
    }

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/resources/ReloadableResourceManager;createReload(Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;Ljava/util/List;)Lnet/minecraft/server/packs/resources/ReloadInstance;", shift = At.Shift.AFTER))
    private void overflowanimations$loadOverlayTextures(final GameConfig gameConfig, final CallbackInfo ci) {
        final Runnable runnable = this.overflowanimations$loadOverlayTextures.get();
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.legacyLoadingScreen && runnable != null) {
            runnable.run();
        }
    }
}
