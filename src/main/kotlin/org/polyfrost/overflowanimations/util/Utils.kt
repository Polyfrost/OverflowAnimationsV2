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

import net.minecraft.client.Minecraft
import net.minecraft.util.Mth
import net.minecraft.util.profiling.Profiler
import net.minecraft.world.inventory.InventoryMenu
import net.minecraft.world.level.GameType
import net.minecraft.world.phys.shapes.BooleanOp
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import org.polyfrost.overflowanimations.OverflowAnimationsConstants
import org.polyfrost.overflowanimations.mixins.accessor.PlayerAccessor

fun toRadians(angle: Float) = angle * Mth.DEG_TO_RAD

fun expandVoxelShape(shape: VoxelShape, value: Double): VoxelShape {
    var voxelShape = Shapes.empty()
    shape.toAabbs().forEach { aabb ->
        voxelShape = Shapes.join(voxelShape, Shapes.create(aabb.inflate(value)), BooleanOp.OR)
    }
    return voxelShape
}

fun isSingleplayer(): Boolean {
    val server = Minecraft.getInstance().singleplayerServer
    return server != null && server.isSingleplayer
}

fun reinitializeInventorySlots() {
    val player = Minecraft.getInstance().player
    //? if <1.21.5 {
    /*if (player != null && !player.isCreative) {
    *///?} else {
    if (player != null && player.gameMode() != GameType.CREATIVE) {
    //?}
        // Re-initialize the inventory, to reset the slot positions modified by "Old Crafting Slots Position"
        (player as PlayerAccessor).`overflowanimations$setInventoryMenu`(
            InventoryMenu(
                player.inventory,
                !player.level().isClientSide,
                player
            )
        )
    }
}

fun profile(name: String, lambda: () -> Unit) {
    val profiler = Profiler.get()
    profiler.push("${OverflowAnimationsConstants.MOD_ID}_$name")
    lambda()
    profiler.pop()
}