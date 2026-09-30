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
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.BowItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.PotionItem
import net.minecraft.world.level.block.CarpetBlock
import net.minecraft.world.level.block.SnowLayerBlock

private fun rotate(poseStack: PoseStack, axis: Axis, degrees: Float) {
    //? if <26.3 {
    /*poseStack.mulPose(axis.rotationDegrees(degrees))
    *///?} else {
    poseStack.rotateDegrees(axis, degrees)
    //?}
}

fun applyMirroredRotation(poseStack: PoseStack, direction: Int, x: Float, y: Float, z: Float) {
    rotate(poseStack, Axis.XP, x)
    rotate(poseStack, Axis.YP, direction * y)
    rotate(poseStack, Axis.ZP, direction * z)
}

fun applyLunarItemPosition(poseStack: PoseStack, stack: ItemStack, direction: Int) {
    val item = stack.item
    if (isSwordItem(stack)) {
        poseStack.translate(0.0F, 0.0F, -0.02F)
        rotate(poseStack, Axis.ZP, direction * -1.0F)
    } else if (item is PotionItem) {
        poseStack.translate(direction * -0.0225F, -0.02F, 0.0F)
        rotate(poseStack, Axis.ZP, direction * 1.0F)
    } else if (isFishingRodItem(stack)) {
        poseStack.translate(direction * 0.08F, -0.0275F, -0.33F)
        poseStack.scale(0.95F, 1.0F, 1.0F)
    } else if (item is BowItem) {
        rotate(poseStack, Axis.YP, direction * 0.9F)
    } else if (item is BlockItem && (item.block is CarpetBlock || item.block is SnowLayerBlock)) {
        poseStack.translate(0.0F, -0.25F, 0.0F)
    }
}

fun applyLunarBowPosition(poseStack: PoseStack, direction: Int) {
    rotate(poseStack, Axis.YP, direction * 45.0F)
    poseStack.translate(direction * -0.2F * 0.4F, 0.0F, -0.175F * 0.4F)
    rotate(poseStack, Axis.ZP, direction * -1.0F)
    rotate(poseStack, Axis.YP, direction * -45.0F)
}

fun applyLunarBlockHitPosition(poseStack: PoseStack, direction: Int) {
    poseStack.translate(direction * -0.55F, 0.2F, 0.1F)
    poseStack.scale(0.85F, 0.85F, 0.85F)
    rotate(poseStack, Axis.ZP, direction * -1.0F)
    rotate(poseStack, Axis.XP, 1.0F)
    rotate(poseStack, Axis.YP, direction * 2.0F)
}
