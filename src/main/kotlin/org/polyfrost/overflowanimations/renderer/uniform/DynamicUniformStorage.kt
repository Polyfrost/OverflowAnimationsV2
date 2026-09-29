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
//? if <26.3 {
/*import com.mojang.blaze3d.buffers.GpuBuffer
*///?}
import com.mojang.blaze3d.buffers.Std140Builder
import com.mojang.blaze3d.systems.RenderSystem
//? if >=26.3 {
import com.mojang.renderpearl.api.buffers.GpuBuffer
//?}
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap

class DynamicUniformStorage : UniformStorage, AutoCloseable {
    val name: String

    private val keys: List<UniformKey<*>>
    private val values = Object2ObjectOpenHashMap<UniformKey<*>, Any?>()
    private val size: Int

    //? if <26.2 {
    /*private var buffer: GpuBuffer
    *///?}

    var isClosed = false
        private set

    private constructor(name: String, keys: List<UniformKey<*>>, defaults: Map<UniformKey<*>, Any?>, size: Int) {
        this.name = name
        this.keys = keys
        this.size = size
        for (key in this.keys) {
            this.values[key] = defaults[key]
        }

        //? if <26.2 {
        /*this.buffer = RenderSystem.getDevice().createBuffer(
            { "$name Uniform Buffer" },
            GpuBuffer.USAGE_UNIFORM or GpuBuffer.USAGE_MAP_WRITE,
            //? if <1.21.11 {
            /*this.size
            *///?} else {
            this.size.toLong()
            //?}
        )
        *///?}
    }

    companion object {
        @JvmStatic
        fun builder(name: String) = Builder(name)
    }

    override fun name(): String = this.name

    override fun <T> set(key: UniformKey<T>, value: T) = if (this.isClosed) {
        throw RuntimeException("Cannot set value in Uniform Storage (${this.name}) as it has been closed!")
    } else if (!this.keys.contains(key)) {
        throw RuntimeException("Uniform storage does not contain key '${key.name}'!")
    } else {
        this.values[key] = value
        this
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> get(key: UniformKey<T>) = if (!this.keys.contains(key)) null else this.values[key] as T?

    @Suppress("UNCHECKED_CAST")
    override fun upload() = if (this.isClosed) {
        throw RuntimeException("Cannot upload Uniform Storage (${this.name}) as it has been closed!")
    } else {
        //? if <26.2 {
        /*RenderSystem.getDevice().createCommandEncoder().mapBuffer(this.buffer, false, true).use { view ->
            val builder = Std140Builder.intoBuffer(view.data())
        *///?} else {
        val device = RenderSystem.getDevice()
        val transientMemory = device.createCommandEncoder().transientMemory()
        val alignment = device.deviceInfo.limits.minUniformOffsetAlignment
        transientMemory.allocateGpuMapped(
            this.size.toLong(),
            alignment.toLong(),
            GpuBuffer.USAGE_UNIFORM
        ).use { view ->
            val builder = Std140Builder.intoBuffer(view.data)
        //?}
            for (key in this.keys) {
                val value = this.values[key]
                    ?: throw RuntimeException("Failed to bind \"${key.name}\" in Uniform Storage (${this.name}) as value is not set!")
                (key.serializer as UniformSerializer<Any>).put(builder, value)
            }

            //? if >=26.2 {
            view.slice
            //?}
        }

        //? if <26.2 {
        /*this.buffer.slice()
        *///?}
    }

    override fun close() {
        if (!this.isClosed) {
            this.isClosed = true
            this.values.clear()
            //? if <26.2 {
            /*this.buffer.close()
            *///?}
        }
    }

    class Builder(private val name: String) : UniformStorage.Builder() {
        override fun build(): DynamicUniformStorage {
            val size = this.calculator.get()
            if (size == 0) {
                throw RuntimeException("Cannot build Uniform Storage (${this.name}) as it contains no uniforms!")
            } else {
                return DynamicUniformStorage(this.name, this.keys.toList(), HashMap(this.defaults), size)
            }
        }
    }
}
//?}
