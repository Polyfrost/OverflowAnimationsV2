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

package org.polyfrost.overflowanimations.renderer.uniform

// Built on the GPU device API (1.21.5) and dynamic uniforms / GUI render states (1.21.6)
//? if >=1.21.6 {
class UniformKey<T> {
    companion object {
        @JvmStatic
        fun <T> of(name: String, serializer: UniformSerializer<T>) = UniformKey(name, serializer)

        @JvmStatic
        fun Integer(name: String) = of(name, UniformSerializer.INTEGER)

        @JvmStatic
        fun Float(name: String) = of(name, UniformSerializer.FLOAT)

        @JvmStatic
        fun Double(name: String) = of(name, UniformSerializer.DOUBLE)

        @JvmStatic
        fun Boolean(name: String) = of(name, UniformSerializer.BOOLEAN)

        @JvmStatic
        fun Matrix4f(name: String) = of(name, UniformSerializer.MATRIX4F)

        @JvmStatic
        fun Vector2f(name: String) = of(name, UniformSerializer.VECTOR2F)

        @JvmStatic
        fun Vector2i(name: String) = of(name, UniformSerializer.VECTOR2I)

        @JvmStatic
        fun Vector3f(name: String) = of(name, UniformSerializer.VECTOR3F)

        @JvmStatic
        fun Vector3i(name: String) = of(name, UniformSerializer.VECTOR3I)

        @JvmStatic
        fun Vector4f(name: String) = of(name, UniformSerializer.VECTOR4F)

        @JvmStatic
        fun Vector4i(name: String) = of(name, UniformSerializer.VECTOR4I)
    }

    val name: String
    val serializer: UniformSerializer<T>

    private constructor(name: String, serializer: UniformSerializer<T>) {
        this.name = name
        this.serializer = serializer
    }
}
//?}
