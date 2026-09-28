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

import dev.isxander.yacl3.api.ConfigCategory
import dev.isxander.yacl3.api.Option
import org.polyfrost.overflowanimations.handler.config.bundle.entry.*
import org.polyfrost.overflowanimations.handler.config.category.Category
import java.awt.Color
import java.util.function.BiConsumer

abstract class Bundle {
    abstract fun install(builder: ConfigCategory.Builder, defaults: Category, config: Category)

    open fun booleanEntry(name: String, listener: BiConsumer<Option<Boolean>, Boolean>) =
        this.entry(BooleanEntry(name, listener))

    fun booleanEntry(name: String) = this.booleanEntry(name) { opt, value -> }

    open fun intRange(name: String, min: Int, max: Int, step: Int) =
        this.entry(IntRangeEntry(name, null, min, max, step))

    fun intRange(name: String, min: Int, max: Int) = this.intRange(name, min, max, 1)

    open fun floatRange(name: String, min: Float, max: Float, step: Float) =
        this.entry(FloatRangeEntry(name, null, min, max, step))

    fun floatRange(name: String, min: Float, max: Float) = this.floatRange(name, min, max, 0.1F)

    open fun floatEntry(name: String) =
        this.entry(FloatEntry(name, null))

    open fun <S : Enum<S>> enumEntry(name: String, enumClazz: Class<S>, listener: BiConsumer<Option<S>, S>) =
        this.entry(EnumEntry(name, listener, enumClazz))

    fun <S : Enum<S>> enumEntry(name: String, enumClazz: Class<S>) = this.enumEntry(name, enumClazz) { opt, value -> }

    open fun colorEntry(name: String, listener: BiConsumer<Option<Color>, Color>) =
        this.entry(ColorEntry(name, listener))

    fun colorEntry(name: String) = this.colorEntry(name) { opt, value -> }

    abstract fun <T> entry(entry: OptionEntrySupplier<T>): Bundle
}