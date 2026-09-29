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

package org.polyfrost.overflowanimations.mixins.v1.rendering.states;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Camera;
//? if >=26.1 {
import net.minecraft.client.Minecraft;
//?}
import net.minecraft.client.renderer.GameRenderer;
//? if >=1.21.9 <26.1 {
/*import net.minecraft.client.renderer.state.CameraRenderState;
*///?} elif >=26.1 {
import net.minecraft.client.renderer.state.level.CameraRenderState;
//?}
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.mixins.accessor.CameraAccessor;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {
    @Shadow
    @Final
    private Camera mainCamera;

    //? if >=26.1 {
    @Shadow
    @Final
    private Minecraft minecraft;
    //?}

    //? if >=1.21.9
    @Inject(method = "extractCamera", at = @At("TAIL"))
    //? if >=1.21.9 <26.1 {
    /*private void overflowanimations$setupCameraState(final CallbackInfo ci, @Local(ordinal = 0) final CameraRenderState cameraState) {
        cameraState.overflowanimations$setPartialTickTime(this.mainCamera.getPartialTickTime());
    *///?} elif >=26.1 {
    private void overflowanimations$setupCameraState(final CallbackInfo ci, @Local(name = "cameraState") final CameraRenderState cameraState) {
        cameraState.overflowanimations$setPartialTickTime(this.mainCamera.getCameraEntityPartialTicks(this.minecraft.getDeltaTracker()));
    //?}
    //? if >=1.21.9 {
        cameraState.overflowanimations$setOldEyeHeight(((CameraAccessor) this.mainCamera).overflowanimations$getOldEyeHeight());
        cameraState.overflowanimations$setEyeHeight(((CameraAccessor) this.mainCamera).overflowanimations$getEyeHeight());
    //?}
        //? if >=1.21.9 <1.21.11 {
        /*cameraState.overflowanimations$setYRot(this.mainCamera.getYRot());
        cameraState.overflowanimations$setXRot(this.mainCamera.getXRot());
        *///?} elif >=1.21.11 {
        cameraState.overflowanimations$setYRot(this.mainCamera.yRot());
        cameraState.overflowanimations$setXRot(this.mainCamera.xRot());
        //?}
    //? if >=1.21.9
    }
}
