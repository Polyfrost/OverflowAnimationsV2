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

package org.polyfrost.overflowanimations.util.enums

import net.minecraft.util.ARGB
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig

enum class DamageTintSetting(private val colorGetter: (brightness: Float) -> Int) {
    V1_7({ brightness -> ARGB.colorFromFloat(0.6F, brightness, 0.0F, 0.0F) }),
    V1_8_ORANGE_MARSHALL(ARGB.colorFromFloat(0.5F, 1.0F, 0.0F, 0.0F)),
    CUSTOM({ brightness ->
        val color = OverflowAnimationsConfig.instance().other.customTintColor
        ARGB.colorFromFloat(1.0F - (color.alpha / 255.0F), color.red / 255.0F, color.green / 255.0F, color.blue / 255.0F)
    }),
    VANILLA(-1);

    constructor(color: Int) : this({ color })

    fun getColor(brightness: Float) = this.colorGetter(brightness)
}