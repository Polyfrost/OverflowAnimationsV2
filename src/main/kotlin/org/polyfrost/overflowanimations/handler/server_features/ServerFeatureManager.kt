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

import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig
import org.polyfrost.overflowanimations.config.category.ExtrasConfigCategory
import org.polyfrost.overflowanimations.util.isSingleplayer

object ServerFeatureManager {
    @JvmField
    val ENABLED_SERVER_FEATURES: HashSet<ServerFeature> = hashSetOf()

    private val SINGLEPLAYER_FIELDS = ServerFeatures.allFeatures()
        .filter { it != ServerFeatures.ALL }
        .associateWith { ExtrasConfigCategory::class.java.getField(it.identifier.path) }

    @JvmStatic
    fun isPresent(feature: ServerFeature): Boolean {
        return if (isSingleplayer()) {
            SINGLEPLAYER_FIELDS[feature]?.getBoolean(OverflowAnimationsConfig.instance().extras) ?: false
        } else {
            ENABLED_SERVER_FEATURES.contains(feature)
        }
    }
}