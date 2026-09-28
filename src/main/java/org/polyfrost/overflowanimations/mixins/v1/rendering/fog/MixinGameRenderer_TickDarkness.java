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

package org.polyfrost.overflowanimations.mixins.v1.rendering.fog;

import net.minecraft.client.Camera;
//? if 1.21.11 {
/*import net.minecraft.client.Minecraft;
*///?}
import net.minecraft.client.renderer.GameRenderer;
//? if >=26.1 {
import net.minecraft.client.renderer.state.GameRenderState;
//?}
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.handler.rendering.LegacyFogDarkness;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer_TickDarkness {
    @Shadow
    @Final
    private Camera mainCamera;

    @Shadow
    @Final
    //? if 1.21.11 {
    /*private Minecraft minecraft;
    *///?} else {
    private GameRenderState gameRenderState;
    //?}

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;tick()V", shift = At.Shift.AFTER))
    private void overflowanimations$tickFogDarkness(final CallbackInfo ci) {
        final Entity entity = this.mainCamera.entity();
        if (entity != null) {
            //? if 1.21.11 {
            /*LegacyFogDarkness.tick(entity, this.minecraft.options.renderDistance().get());
            *///?} else {
            LegacyFogDarkness.tick(entity, this.gameRenderState.optionsRenderState.renderDistance);
            //?}
        }
    }
}
