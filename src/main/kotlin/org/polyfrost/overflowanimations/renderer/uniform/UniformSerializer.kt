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
import com.mojang.blaze3d.buffers.Std140Builder
import com.mojang.blaze3d.buffers.Std140SizeCalculator
import org.joml.*

abstract class UniformSerializer<T> {
    companion object {
        val INTEGER = object : UniformSerializer<Int>() {
            override fun put(builder: Std140Builder, value: Int) {
                builder.putInt(value)
            }

            override fun size(calculator: Std140SizeCalculator) {
                calculator.putInt()
            }
        }

        val FLOAT = object : UniformSerializer<Float>() {
            override fun put(builder: Std140Builder, value: Float) {
                builder.putFloat(value)
            }

            override fun size(calculator: Std140SizeCalculator) {
                calculator.putFloat()
            }
        }

        val DOUBLE = FLOAT.map(Double::toFloat)

        val BOOLEAN = INTEGER.map<Boolean> { if (it) 1 else 0 }

        val MATRIX4F = object : UniformSerializer<Matrix4f>() {
            override fun put(builder: Std140Builder, value: Matrix4f) {
                builder.putMat4f(value)
            }

            override fun size(calculator: Std140SizeCalculator) {
                calculator.putMat4f()
            }
        }

        val VECTOR2F = object : UniformSerializer<Vector2f>() {
            override fun put(builder: Std140Builder, value: Vector2f) {
                builder.putVec2(value)
            }

            override fun size(calculator: Std140SizeCalculator) {
                calculator.putVec2()
            }
        }

        val VECTOR2I = object : UniformSerializer<Vector2i>() {
            override fun put(builder: Std140Builder, value: Vector2i) {
                builder.putIVec2(value)
            }

            override fun size(calculator: Std140SizeCalculator) {
                calculator.putIVec2()
            }
        }

        val VECTOR3F = object : UniformSerializer<Vector3f>() {
            override fun put(builder: Std140Builder, value: Vector3f) {
                builder.putVec3(value)
            }

            override fun size(calculator: Std140SizeCalculator) {
                calculator.putVec3()
            }
        }

        val VECTOR3I = object : UniformSerializer<Vector3i>() {
            override fun put(builder: Std140Builder, value: Vector3i) {
                builder.putIVec3(value)
            }

            override fun size(calculator: Std140SizeCalculator) {
                calculator.putIVec3()
            }
        }

        val VECTOR4F = object : UniformSerializer<Vector4f>() {
            override fun put(builder: Std140Builder, value: Vector4f) {
                builder.putVec4(value)
            }

            override fun size(calculator: Std140SizeCalculator) {
                calculator.putVec4()
            }
        }

        val VECTOR4I = object : UniformSerializer<Vector4i>() {
            override fun put(builder: Std140Builder, value: Vector4i) {
                builder.putIVec4(value)
            }

            override fun size(calculator: Std140SizeCalculator) {
                calculator.putIVec4()
            }
        }
    }

    abstract fun put(builder: Std140Builder, value: T)

    abstract fun size(calculator: Std140SizeCalculator)

    fun <From> map(conversion: (value: From) -> T): UniformSerializer<From> {
        val outer = this
        return object : UniformSerializer<From>() {
            override fun put(builder: Std140Builder, value: From) {
                outer.put(builder, conversion(value))
            }

            override fun size(calculator: Std140SizeCalculator) {
                outer.size(calculator)
            }
        }
    }
}
//?}
