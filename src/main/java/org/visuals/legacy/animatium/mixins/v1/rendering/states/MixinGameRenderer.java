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

package org.visuals.legacy.animatium.mixins.v1.rendering.states;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Camera;
//? if >=26.1 {
import net.minecraft.client.Minecraft;
//?}
import net.minecraft.client.renderer.GameRenderer;
//? if 1.21.11 {
/*import net.minecraft.client.renderer.state.CameraRenderState;
*///?} else {
import net.minecraft.client.renderer.state.level.CameraRenderState;
//?}
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.mixins.accessor.CameraAccessor;

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

    @Inject(method = "extractCamera", at = @At("TAIL"))
    //? if 1.21.11 {
    /*private void animatium$setupCameraState(final CallbackInfo ci, @Local(ordinal = 0) final CameraRenderState cameraState) {
        cameraState.animatium$setPartialTickTime(this.mainCamera.getPartialTickTime());
    *///?} else {
    private void animatium$setupCameraState(final CallbackInfo ci, @Local(name = "cameraState") final CameraRenderState cameraState) {
        cameraState.animatium$setPartialTickTime(this.mainCamera.getCameraEntityPartialTicks(this.minecraft.getDeltaTracker()));
    //?}
        cameraState.animatium$setOldEyeHeight(((CameraAccessor) this.mainCamera).animatium$getOldEyeHeight());
        cameraState.animatium$setEyeHeight(((CameraAccessor) this.mainCamera).animatium$getEyeHeight());
        cameraState.animatium$setYRot(this.mainCamera.yRot());
        cameraState.animatium$setXRot(this.mainCamera.xRot());
    }
}
