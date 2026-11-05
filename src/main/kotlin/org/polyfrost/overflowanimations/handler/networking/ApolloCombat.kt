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

package org.polyfrost.overflowanimations.handler.networking

import com.mojang.logging.LogUtils
import org.polyfrost.overflowanimations.handler.server_features.ServerFeature
import org.polyfrost.overflowanimations.handler.server_features.ServerFeatures
import java.nio.ByteBuffer

// Combat module id and option keys are based on Apollo's CombatModule, licensed under the MIT License, Copyright (c) 2026 Moonsworth:
// https://github.com/LunarClient/Apollo/blob/63fda2ae735c6ca4c29e52cb5583ae9f89ac1cc3/api/src/main/java/com/lunarclient/apollo/module/combat/CombatModule.java
object ApolloCombat {
    private val LOGGER = LogUtils.getLogger()
    private const val SETTINGS_MESSAGE = "/lunarclient.apollo.configurable.v1.OverrideConfigurableSettingsMessage"
    private val OPTIONS = mapOf(
        ServerFeatures.MISS_PENALTY to "disable-miss-penalty",
        ServerFeatures.BLOCK_MISS_PENALTY to "disable-block-miss-penalty",
        ServerFeatures.MINING_ITEM_USAGE to "allow-dig-and-use"
    )

    private var enabled = false
    private val options = hashSetOf<String>()

    fun isPresent(feature: ServerFeature) = enabled && OPTIONS[feature] in options

    fun reset() {
        enabled = false
        options.clear()
    }

    fun handle(data: ByteArray) {
        try {
            val packet = ByteBuffer.wrap(data).fields()
            if (packet.string(1)?.endsWith(SETTINGS_MESSAGE) != true) return
            for (settings in packet.messages(2).flatMap { it.messages(1) }) {
                if (settings.string(2) != "combat") continue
                enabled = settings.bool(3)
                for (property in settings.messages(4)) {
                    val key = property.string(1) ?: continue
                    if (property.messages(2).any { it.bool(4) }) options.add(key) else options.remove(key)
                }
            }
        } catch (e: RuntimeException) {
            LOGGER.warn("Failed to read Apollo settings", e)
        }
    }

    private class Field(val number: Int, val varint: Long = 0, val bytes: ByteBuffer? = null)

    private fun List<Field>.string(number: Int) =
        firstOrNull { it.number == number }?.bytes?.let { Charsets.UTF_8.decode(it).toString() }

    private fun List<Field>.bool(number: Int) = any { it.number == number && it.varint != 0L }

    private fun List<Field>.messages(number: Int) = filter { it.number == number }.mapNotNull { it.bytes?.fields() }

    private fun ByteBuffer.skip(length: Int) {
        position(position() + length)
    }

    private fun ByteBuffer.varint(): Long {
        var result = 0L
        for (shift in 0..63 step 7) {
            val byte = get().toInt()
            result = result or ((byte and 0x7F).toLong() shl shift)
            if (byte >= 0) return result
        }
        error("Varint too long")
    }

    private fun ByteBuffer.fields(): List<Field> = buildList {
        while (hasRemaining()) {
            val tag = varint().toInt()
            val number = tag ushr 3
            when (tag and 7) {
                0 -> add(Field(number, varint = varint()))
                1 -> skip(8)
                2 -> {
                    val length = varint().toInt()
                    add(Field(number, bytes = slice(position(), length)))
                    skip(length)
                }
                5 -> skip(4)
                else -> error("Unknown wire type in tag $tag")
            }
        }
    }
}
