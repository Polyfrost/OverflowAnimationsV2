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

package org.polyfrost.overflowanimations.handler.networking.payloads

import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import org.polyfrost.overflowanimations.OverflowAnimations.animatiumLocation
import org.polyfrost.overflowanimations.OverflowAnimations.location
import org.polyfrost.overflowanimations.handler.server_features.ServerFeature
import org.polyfrost.overflowanimations.handler.server_features.ServerFeatures
import java.util.*

data class SetServerFeaturesPayload(
    val features: Set<ServerFeature>,
    val payloadType: CustomPacketPayload.Type<SetServerFeaturesPayload>
) : CustomPacketPayload {
    companion object {
        val TYPES = listOf(
            CustomPacketPayload.Type<SetServerFeaturesPayload>(location("set_server_features")),
            CustomPacketPayload.Type(animatiumLocation("set_server_features"))
        )

        val FEATURE_SET_CODEC = object : StreamCodec<FriendlyByteBuf, Set<ServerFeature>> {
            override fun encode(
                buffer: FriendlyByteBuf,
                features: Set<ServerFeature>
            ) {
                val bitSet = BitSet()
                for (feature in features) {
                    bitSet.set(feature.raw)
                }

                buffer.writeBitSet(bitSet)
            }

            override fun decode(buffer: FriendlyByteBuf): Set<ServerFeature> {
                val bytes = ByteArray(buffer.readableBytes())
                buffer.readBytes(bytes)

                val bitSet = BitSet.valueOf(bytes)
                val features = hashSetOf<ServerFeature>()
                for (index in 0..<ServerFeatures.totalFeatures()) {
                    if (bitSet.get(index)) {
                        val feature = ServerFeatures.byRawId(index) ?: continue
                        if (feature == ServerFeatures.ALL) {
                            features.addAll(ServerFeatures.allFeatures())
                            break
                        }

                        features.add(feature)
                    }
                }

                return features
            }
        }

        fun streamCodec(type: CustomPacketPayload.Type<SetServerFeaturesPayload>) = StreamCodec.composite(
            FEATURE_SET_CODEC,
            SetServerFeaturesPayload::features
        ) { features -> SetServerFeaturesPayload(features, type) }
    }

    override fun type() = payloadType
}