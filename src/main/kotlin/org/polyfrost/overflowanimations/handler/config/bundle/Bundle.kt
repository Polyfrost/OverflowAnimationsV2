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

package org.polyfrost.overflowanimations.handler.config.bundle

import org.polyfrost.oneconfig.api.config.v1.Visualizer
import org.polyfrost.overflowanimations.OverflowAnimationsConstants
import java.util.function.Consumer

class Entry(
    val name: String,
    val visualizer: Class<out Visualizer>,
    val listener: Consumer<*>? = null,
    val metadata: Map<String, Any> = emptyMap()
)

abstract class Bundle {
    abstract fun entry(entry: Entry): Bundle

    @JvmOverloads
    fun booleanEntry(name: String, listener: Consumer<Boolean>? = null) =
        this.entry(Entry(name, Visualizer.SwitchVisualizer::class.java, listener))

    @JvmOverloads
    fun intRange(name: String, min: Int, max: Int, step: Int = 1) =
        this.floatRange(name, min.toFloat(), max.toFloat(), step.toFloat())

    @JvmOverloads
    fun floatRange(name: String, min: Float, max: Float, step: Float = 0.1F) =
        this.entry(Entry(name, Visualizer.SliderVisualizer::class.java, null, mapOf("min" to min, "max" to max, "step" to step)))

    fun floatEntry(name: String) =
        this.entry(Entry(name, Visualizer.NumberVisualizer::class.java, null, mapOf("min" to -360.0F, "max" to 360.0F)))

    @JvmOverloads
    fun <S : Enum<S>> enumEntry(name: String, enumClazz: Class<S>, listener: Consumer<S>? = null): Bundle {
        val keys = enumClazz.enumConstants.map { "${OverflowAnimationsConstants.MOD_ID}.enum.${enumClazz.simpleName}.${it.name}" }.toTypedArray()
        return this.entry(Entry(name, Visualizer.DropdownVisualizer::class.java, listener, mapOf("options" to keys, "optionsKey" to keys)))
    }

    @JvmOverloads
    fun colorEntry(name: String, listener: Consumer<*>? = null) =
        this.entry(Entry(name, Visualizer.ColorVisualizer::class.java, listener))
}
