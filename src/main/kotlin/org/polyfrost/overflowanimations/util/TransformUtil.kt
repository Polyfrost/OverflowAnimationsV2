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

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import kotlin.math.exp

fun applyLegacyTransform(poseStack: PoseStack, x: Float, y: Float, z: Float, rotationX: Float, rotationY: Float, rotationZ: Float, scale: Float) {
    poseStack.translate(x, y, z)
    //? if <26.3 {
    /*poseStack.mulPose(Axis.XP.rotationDegrees(rotationX))
    poseStack.mulPose(Axis.YP.rotationDegrees(rotationY))
    poseStack.mulPose(Axis.ZP.rotationDegrees(rotationZ))
    *///?} else {
    poseStack.rotate(Axis.XP.rotationDegrees(rotationX))
    poseStack.rotate(Axis.YP.rotationDegrees(rotationY))
    poseStack.rotate(Axis.ZP.rotationDegrees(rotationZ))
    //?}
    val factor = exp(scale)
    poseStack.scale(factor, factor, factor)
}
