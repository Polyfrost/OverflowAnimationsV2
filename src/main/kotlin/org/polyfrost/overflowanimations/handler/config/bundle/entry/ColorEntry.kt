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

package org.polyfrost.overflowanimations.handler.config.bundle.entry

import dev.isxander.yacl3.api.Option
import org.polyfrost.overflowanimations.handler.config.category.Category
import org.polyfrost.overflowanimations.handler.config.category.option.ColorOptions
import org.polyfrost.overflowanimations.handler.config.category.option.OptionBuilder
import java.awt.Color
import java.util.function.BiConsumer

data class ColorEntry(
    val name: String,
    val listener: BiConsumer<Option<Color>, Color>?
) : OptionEntrySupplier<Color> {
    override fun create(defaults: Category, config: Category): Option<Color> {
        val option = OptionBuilder(this.name, ColorOptions.WITH_ALPHA)
        this.listener?.let { option.instant().listener(it) }
        return option.build(defaults, config)
    }

    override fun name() = this.name
}