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

package org.polyfrost.overflowanimations.mixins.legacy.network;

import net.minecraft.client.network.handler.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import org.polyfrost.overflowanimations.handler.networking.ApolloCombat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class MixinClientPlayNetworkHandler_ApolloChannels {
    @Unique
    private boolean overflowanimations$respawned;

    @Inject(method = "handlePlayerRespawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketUtils;ensureOnSameThread(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/handler/PacketHandler;Lnet/minecraft/util/BlockableEventLoop;)V", shift = At.Shift.AFTER))
    private void overflowanimations$trackRespawn(final PlayerRespawnS2CPacket packet, final CallbackInfo ci) {
        this.overflowanimations$respawned = true;
    }

    @Inject(method = "handleCustomPayload", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketUtils;ensureOnSameThread(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/handler/PacketHandler;Lnet/minecraft/util/BlockableEventLoop;)V", shift = At.Shift.AFTER))
    private void overflowanimations$tapApolloChannels(final CustomPayloadS2CPacket packet, final CallbackInfo ci) {
        if (this.overflowanimations$respawned && "MC|Brand".equals(packet.getChannel())) {
            this.overflowanimations$respawned = false;
            ApolloCombat.INSTANCE.reset();
        }
        final var handler = ApolloCombat.INSTANCE.getCHANNELS().get(packet.getChannel());
        if (handler != null) {
            final PacketByteBuf buf = packet.getData();
            final byte[] data = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), data);
            handler.invoke(data);
        }
    }
}
