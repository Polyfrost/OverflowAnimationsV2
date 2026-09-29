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

package org.polyfrost.overflowanimations.handler.rendering.lighting

// Lighting entries were added in 1.21.6
//? if >=1.21.6 {
import com.mojang.blaze3d.platform.Lighting
import org.joml.Matrix4f
import org.joml.Vector3f
//? if >=26.2 {
import org.joml.Vector3fc
//?}
import org.polyfrost.overflowanimations.OverflowAnimations
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig
import java.util.function.BiConsumer

object LegacyDiffuseLighting {
    private val DIFFUSE_LIGHT_0 = (Vector3f(0.2F, 1.0F, -0.7F)).normalize()
    private val DIFFUSE_LIGHT_1 = (Vector3f(-0.2F, 1.0F, 0.7F)).normalize()

    private val INVENTORY_DIFFUSE_LIGHT_0 = (Vector3f(0.2F, -1.0F, 1.0F)).normalize()
    private val INVENTORY_DIFFUSE_LIGHT_1 = (Vector3f(-0.2F, -1.0F, 0.0F)).normalize()

    @JvmStatic
    var item3dPose: Matrix4f? = null

    @JvmStatic
    var updateLightingInvoker: BiConsumer<Lighting.Entry, Lights>? = null

    @JvmStatic
    fun refresh() {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.legacyDiffuseLighting) {
            val lights = Lights(
                item3dPose!!.transformDirection(DIFFUSE_LIGHT_0, Vector3f()),
                item3dPose!!.transformDirection(DIFFUSE_LIGHT_1, Vector3f())
            )
            updateLightingInvoker!!.accept(Lighting.Entry.ENTITY_IN_UI, lights)
            updateLightingInvoker!!.accept(Lighting.Entry.PLAYER_SKIN, lights)
        } else {
            // Vanilla Values (TODO/NOTE, Possibly a nicer way to do this?)
            updateLightingInvoker!!.accept(
                Lighting.Entry.ENTITY_IN_UI,
                Lights(INVENTORY_DIFFUSE_LIGHT_0, INVENTORY_DIFFUSE_LIGHT_1)
            )

            val playerSkinPose = Matrix4f()
            updateLightingInvoker!!.accept(
                Lighting.Entry.PLAYER_SKIN,
                Lights(
                    playerSkinPose.transformDirection(INVENTORY_DIFFUSE_LIGHT_0, Vector3f()),
                    playerSkinPose.transformDirection(INVENTORY_DIFFUSE_LIGHT_1, Vector3f())
                )
            )
        }
    }

    data class Lights(
        @JvmField
        //? if <26.2 {
        /*val light0: Vector3f,
        *///?} else {
        val light0: Vector3fc,
        //?}

        @JvmField
        //? if <26.2 {
        /*val light1: Vector3f
        *///?} else {
        val light1: Vector3fc
        //?}
    )
}
//?}
