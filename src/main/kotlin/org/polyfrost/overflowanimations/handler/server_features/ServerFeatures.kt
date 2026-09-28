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

package org.polyfrost.overflowanimations.handler.server_features

import net.minecraft.resources.Identifier
import org.polyfrost.overflowanimations.OverflowAnimations.location

object ServerFeatures {
    private val REGISTRY = hashMapOf<Identifier, ServerFeature>()
    private var LAST_ID = 0

    @JvmField
    val ALL = register(location("all")) // Internal use, on read in packet, adds everything in the REGISTRY

    @JvmField
    val MISS_PENALTY = register(location("miss_penalty"))

    @JvmField
    val LEFT_CLICK_ITEM_USAGE = register(location("left_click_item_usage"))

    @JvmField
    val MINING_ITEM_USAGE = register(location("mining_item_usage"))

    @JvmField
    val HIDE_FIRST_PERSON_ROD_BOBBER = register(location("hide_rod_bobber"))

    @JvmField
    val PICK_INFLATION = register(location("pick_inflation"))

    @JvmField
    val OLD_SNEAK_HEIGHT = register(location("old_sneak_height"))

    @JvmField
    val CLIENTSIDE_ENTITIES = register(location("clientside_entities"))

    @JvmField
    val FIX_SPRINT_ITEM_USE = register(location("disable_sprint_item_use"))

    @JvmField
    val FIX_SPRINT_SNEAKING = register(location("disable_sprint_sneaking"))

    @JvmStatic
    fun allFeatures() = REGISTRY.values.toList()

    @JvmStatic
    fun totalFeatures() = REGISTRY.size

    @JvmStatic
    fun byRawId(raw: Int): ServerFeature? {
        for (entry in REGISTRY) {
            val feature = entry.value
            if (feature.raw == raw) {
                return feature
            }
        }

        return null
    }

    private fun register(id: Identifier): ServerFeature {
        val feature = ServerFeature(id, LAST_ID++)
        REGISTRY[id] = feature
        return feature
    }
}