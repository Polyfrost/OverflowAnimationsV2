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

//? if 1.21.11 {
/*import net.minecraft.client.renderer.state.CameraRenderState;
*///?} else {
import net.minecraft.client.renderer.state.level.CameraRenderState;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.visuals.legacy.animatium.util.states.CameraUtilityRenderState;

@Mixin(CameraRenderState.class)
public abstract class MixinCameraRenderState implements CameraUtilityRenderState {
    @Unique
    private float animatium$partialTickTime = 0.0F;

    @Unique
    private float animatium$oldEyeHeight = 0.0F;

    @Unique
    private float animatium$eyeHeight = 0.0F;

    @Unique
    private float animatium$yRot = 0.0F;

    @Unique
    private float animatium$xRot = 0.0F;

    @Override
    public float animatium$getPartialTickTime() {
        return this.animatium$partialTickTime;
    }

    @Override
    public void animatium$setPartialTickTime(final float partialTickTime) {
        this.animatium$partialTickTime = partialTickTime;
    }

    @Override
    public float animatium$getOldEyeHeight() {
        return this.animatium$oldEyeHeight;
    }

    @Override
    public void animatium$setOldEyeHeight(final float oldEyeHeight) {
        this.animatium$oldEyeHeight = oldEyeHeight;
    }

    @Override
    public float animatium$getEyeHeight() {
        return this.animatium$eyeHeight;
    }

    @Override
    public void animatium$setEyeHeight(final float eyeHeight) {
        this.animatium$eyeHeight = eyeHeight;
    }

    @Override
    public float animatium$getYRot() {
        return this.animatium$yRot;
    }

    @Override
    public void animatium$setYRot(final float yRot) {
        this.animatium$yRot = yRot;
    }

    @Override
    public float animatium$getXRot() {
        return this.animatium$xRot;
    }

    @Override
    public void animatium$setXRot(final float xRot) {
        this.animatium$xRot = xRot;
    }
}
