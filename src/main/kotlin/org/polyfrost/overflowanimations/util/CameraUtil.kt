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

package org.polyfrost.overflowanimations.util

import net.minecraft.client.Camera
//? if 1.21.11 {
/*import net.minecraft.client.renderer.state.CameraRenderState
*///?} else {
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.state.level.CameraRenderState
//?}
import net.minecraft.util.Mth
import org.polyfrost.overflowanimations.mixins.accessor.CameraAccessor

fun Camera.getPositionLerped(): Float {
    val cameraAccessor = this as CameraAccessor
    return Mth.lerp(
        //? if 1.21.11 {
        /*this.partialTickTime,
        *///?} else {
        this.getCameraEntityPartialTicks(Minecraft.getInstance().deltaTracker),
        //?}
        cameraAccessor.`overflowanimations$getOldEyeHeight`(),
        cameraAccessor.`overflowanimations$getEyeHeight`()
    )
}

fun CameraRenderState.getPositionLerped() = Mth.lerp(
    this.`overflowanimations$getPartialTickTime`(),
    this.`overflowanimations$getOldEyeHeight`(),
    this.`overflowanimations$getEyeHeight`()
)