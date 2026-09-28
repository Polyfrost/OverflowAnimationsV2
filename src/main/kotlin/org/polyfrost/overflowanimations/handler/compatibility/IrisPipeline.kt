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

package org.polyfrost.overflowanimations.handler.compatibility

enum class IrisPipeline {
    BASIC,
    TEXTURED,
    TERRAIN,
    TERRAIN_SOLID,
    TERRAIN_CUTOUT,
    TRANSLUCENT,
    SKY_BASIC,
    SKY_TEXTURED,
    ARMOR_GLINT,
    ENTITIES,
    ENTITIES_TRANSLUCENT,
    CLOUDS,
    BLOCK,
    BLOCK_TRANSLUCENT,
    HAND,
    HAND_TRANSLUCENT,
    PARTICLES,
    PARTICLES_TRANSLUCENT,
    EMISSIVE_ENTITIES,
    BEACON_BEAM,
    LINES;

    companion object {
        @JvmField
        val VALUES = entries.toTypedArray()
    }

    private var value: Enum<*>? = null

    fun <S : Enum<S>> initialize(clazz: Class<S>) {
        this.value = java.lang.Enum.valueOf(clazz, this.name)
    }

    fun internal() = this.value
}