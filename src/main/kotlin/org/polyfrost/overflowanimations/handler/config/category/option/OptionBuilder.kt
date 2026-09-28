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

package org.polyfrost.overflowanimations.handler.config.category.option

import dev.isxander.yacl3.api.Binding
import dev.isxander.yacl3.api.Option
import dev.isxander.yacl3.api.OptionDescription
import dev.isxander.yacl3.api.StateManager
import net.minecraft.network.chat.Component
import org.polyfrost.overflowanimations.OverflowAnimationsConstants
import org.polyfrost.overflowanimations.handler.config.category.Category
import java.util.function.BiConsumer

class OptionBuilder<T>(private val name: String, private val options: Options<T>) {
    private var listener: BiConsumer<Option<T>, T>? = null
    private var instant = false

    fun listener(listener: BiConsumer<Option<T>, T>): OptionBuilder<T> {
        this.listener = listener
        return this
    }

    fun instant(): OptionBuilder<T> {
        this.instant = true
        return this
    }

    fun <CategoryLike : Category> build(defaults: CategoryLike, config: CategoryLike): Option<T> {
        val reference: Reference<T> = Reference.get(this.name, defaults, config)
        val binding: Binding<T> = Binding.generic(reference.defaultValue, {
            return@generic try {
                reference.field?.get(config) as T
            } catch (exception: IllegalAccessException) {
                exception.printStackTrace()
                reference.defaultValue
            }
        }, {
            try {
                reference.field?.set(config, it)
            } catch (exception: IllegalAccessException) {
                exception.printStackTrace()
            }
        })

        val builder = Option.createBuilder<T>()
        val id = "${OverflowAnimationsConstants.MOD_ID}.${this.name}"
        builder.name(Component.translatable(id))
        builder.description(OptionDescription.of(Component.translatable("$id.description")))
        builder.controller { this.options.createController(it) }
        if (this.listener != null) {
            builder.listener(this.listener!!)
        }

        if (this.instant) {
            builder.stateManager(StateManager.createInstant(binding))
        } else {
            builder.binding(binding)
        }

        return builder.build()
    }
}