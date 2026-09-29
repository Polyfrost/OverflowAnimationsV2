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

package org.polyfrost.overflowanimations.mixins.v1.rendering.sky;

// Needs the 1.21.5 GPU API / 1.21.6 rendering changes; compiled out on older versions
//? if >=1.21.6 {
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.handler.rendering.clouds.LegacyCloudRenderer;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft_LoadLegacyClouds {
    @Shadow
    @Final
    private ReloadableResourceManager resourceManager;

    @Definition(id = "resourceManager", field = "Lnet/minecraft/client/Minecraft;resourceManager:Lnet/minecraft/server/packs/resources/ReloadableResourceManager;")
    @Definition(id = "registerReloadListener", method = "Lnet/minecraft/server/packs/resources/ReloadableResourceManager;registerReloadListener(Lnet/minecraft/server/packs/resources/PreparableReloadListener;)V")
    @Definition(id = "levelRenderer", field = "Lnet/minecraft/client/Minecraft;levelRenderer:Lnet/minecraft/client/renderer/LevelRenderer;")
    //? if <26.2 {
    /*@Definition(id = "getCloudRenderer", method = "Lnet/minecraft/client/renderer/LevelRenderer;getCloudRenderer()Lnet/minecraft/client/renderer/CloudRenderer;")
    @Expression("this.resourceManager.registerReloadListener(this.levelRenderer.getCloudRenderer())")
    *///?} else {
    @Definition(id = "cloudRenderer", method = "Lnet/minecraft/client/renderer/LevelRenderer;cloudRenderer()Lnet/minecraft/client/renderer/CloudRenderer;")
    @Expression("this.resourceManager.registerReloadListener(this.levelRenderer.cloudRenderer())")
    //?}
    @Inject(method = "<init>", at = @At(value = "MIXINEXTRAS:EXPRESSION", shift = At.Shift.AFTER))
    private void overflowanimations$loadLegacyClouds(final GameConfig gameConfig, final CallbackInfo ci) {
        this.resourceManager.registerReloadListener(LegacyCloudRenderer.INSTANCE);
    }
}
//?}
