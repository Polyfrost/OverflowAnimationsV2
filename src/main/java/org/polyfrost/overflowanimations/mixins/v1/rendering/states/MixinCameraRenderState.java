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

//? if 1.21.11 {
/*import net.minecraft.client.renderer.state.CameraRenderState;
*///?} else {
import net.minecraft.client.renderer.state.level.CameraRenderState;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.polyfrost.overflowanimations.util.states.CameraUtilityRenderState;

@Mixin(CameraRenderState.class)
public abstract class MixinCameraRenderState implements CameraUtilityRenderState {
    @Unique
    private float overflowanimations$partialTickTime = 0.0F;

    @Unique
    private float overflowanimations$oldEyeHeight = 0.0F;

    @Unique
    private float overflowanimations$eyeHeight = 0.0F;

    @Unique
    private float overflowanimations$yRot = 0.0F;

    @Unique
    private float overflowanimations$xRot = 0.0F;

    @Override
    public float overflowanimations$getPartialTickTime() {
        return this.overflowanimations$partialTickTime;
    }

    @Override
    public void overflowanimations$setPartialTickTime(final float partialTickTime) {
        this.overflowanimations$partialTickTime = partialTickTime;
    }

    @Override
    public float overflowanimations$getOldEyeHeight() {
        return this.overflowanimations$oldEyeHeight;
    }

    @Override
    public void overflowanimations$setOldEyeHeight(final float oldEyeHeight) {
        this.overflowanimations$oldEyeHeight = oldEyeHeight;
    }

    @Override
    public float overflowanimations$getEyeHeight() {
        return this.overflowanimations$eyeHeight;
    }

    @Override
    public void overflowanimations$setEyeHeight(final float eyeHeight) {
        this.overflowanimations$eyeHeight = eyeHeight;
    }

    @Override
    public float overflowanimations$getYRot() {
        return this.overflowanimations$yRot;
    }

    @Override
    public void overflowanimations$setYRot(final float yRot) {
        this.overflowanimations$yRot = yRot;
    }

    @Override
    public float overflowanimations$getXRot() {
        return this.overflowanimations$xRot;
    }

    @Override
    public void overflowanimations$setXRot(final float xRot) {
        this.overflowanimations$xRot = xRot;
    }
}
