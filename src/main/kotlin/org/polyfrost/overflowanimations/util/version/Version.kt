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

package org.polyfrost.overflowanimations.util.version

import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import org.polyfrost.overflowanimations.OverflowAnimationsConstants
import java.lang.Byte.parseByte

data class Version(val major: Byte, val minor: Byte, val patch: Byte) {
    val packedValue = (this.major.toInt() shl MAJOR) or (this.minor.toInt() shl MINOR) or this.patch.toInt()

    companion object {
        const val MAJOR = 8
        const val MINOR = 16

        val BOGUS = Version(42, 69, 67)

        val STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            Version::packedValue
        ) { unpack(it) }

        fun unpack(value: Int) = Version(
            ((value shr MAJOR) and 0xFF).toByte(),
            ((value shr MINOR) and 0xFF).toByte(),
            (value and 0xFF).toByte()
        )

        fun parse(input: String): Version? {
            if (input.isBlank()) return null

            val parts = input.split('.')
            if (parts.size > 3) return null

            try {
                val major = parts[0]
                val minor = parts[1]
                val patch = if (parts.size == 3) parts[2] else "0"
                return Version(parseByte(major), parseByte(minor), parseByte(patch))
            } catch (_: Exception) {
                return null
            }
        }
    }

    override fun toString() = if (this.patch > 0) {
        "$major.$minor.$patch"
    } else {
        "$major.$minor"
    } + (if (OverflowAnimationsConstants.IS_DEVELOPMENT) " (${this.packedValue})" else "")
}