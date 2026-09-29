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

import net.fabricmc.fabric.api.client.networking.v1.*
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import org.polyfrost.overflowanimations.OverflowAnimationsConstants
import org.polyfrost.overflowanimations.handler.networking.payloads.InfoPayload
import org.polyfrost.overflowanimations.handler.networking.payloads.SetServerFeaturesPayload
import org.polyfrost.overflowanimations.handler.server_features.ServerFeatureManager

object OverflowAnimationsNetworking {
    fun bootstrap() {
        ClientLoginConnectionEvents.DISCONNECT.register { _, _ -> ServerFeatureManager.ENABLED_SERVER_FEATURES.clear() }
        ClientConfigurationConnectionEvents.DISCONNECT.register { _, _ -> ServerFeatureManager.ENABLED_SERVER_FEATURES.clear() }
        ClientPlayConnectionEvents.DISCONNECT.register { _, _ -> ServerFeatureManager.ENABLED_SERVER_FEATURES.clear() }

        for (type in InfoPayload.TYPES) {
            //? if <26.1 {
            /*PayloadTypeRegistry.playC2S()
            *///?} else {
            PayloadTypeRegistry.serverboundPlay()
            //?}
                .register(type, InfoPayload.streamCodec(type))
            ServerPlayNetworking.registerGlobalReceiver(type) { _, _ -> /* NO-OP */ }
        }
        ClientPlayConnectionEvents.JOIN.register { _, sender, _ ->
            InfoPayload.TYPES.firstOrNull(ClientPlayNetworking::canSend)?.let { type ->
                sender.sendPacket(OverflowAnimationsConstants.INFO_PAYLOAD.copy(payloadType = type))
            }
        }

        for (type in SetServerFeaturesPayload.TYPES) {
            //? if <26.1 {
            /*PayloadTypeRegistry.configurationS2C()
            *///?} else {
            PayloadTypeRegistry.clientboundConfiguration()
            //?}
                .register(type, SetServerFeaturesPayload.streamCodec(type))
            ClientConfigurationNetworking.registerGlobalReceiver(type) { payload, context ->
                context.client().schedule {
                    ServerFeatureManager.ENABLED_SERVER_FEATURES.clear()
                    ServerFeatureManager.ENABLED_SERVER_FEATURES.addAll(payload.features)
                }
            }

            //? if <26.1 {
            /*PayloadTypeRegistry.playS2C()
            *///?} else {
            PayloadTypeRegistry.clientboundPlay()
            //?}
                .register(type, SetServerFeaturesPayload.streamCodec(type))
            ClientPlayNetworking.registerGlobalReceiver(type) { payload, context ->
                context.client().schedule {
                    ServerFeatureManager.ENABLED_SERVER_FEATURES.clear()
                    ServerFeatureManager.ENABLED_SERVER_FEATURES.addAll(payload.features)
                }
            }
        }
    }
}