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

package org.polyfrost.overflowanimations.mixins.v1.general.network;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.moulberry.mixinconstraints.annotations.IfModAbsent;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.polyfrost.overflowanimations.handler.networking.ApolloCombat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Fabric allows one payload type and one receiver per channel, so Apollo channels stay unregistered for other mods and get read here.
@IfModAbsent(value = "ichor")
@Mixin(ClientboundCustomPayloadPacket.class)
public abstract class MixinClientboundCustomPayloadPacket_ApolloChannels {
    @WrapOperation(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/codec/StreamCodec;map(Ljava/util/function/Function;Ljava/util/function/Function;)Lnet/minecraft/network/codec/StreamCodec;", ordinal = 0))
    private static StreamCodec<RegistryFriendlyByteBuf, ClientboundCustomPayloadPacket> overflowanimations$tapApolloChannels(final StreamCodec<RegistryFriendlyByteBuf, CustomPacketPayload> codec, final Function<CustomPacketPayload, ClientboundCustomPayloadPacket> to, final Function<ClientboundCustomPayloadPacket, CustomPacketPayload> from, final Operation<StreamCodec<RegistryFriendlyByteBuf, ClientboundCustomPayloadPacket>> original) {
        final StreamCodec<RegistryFriendlyByteBuf, ClientboundCustomPayloadPacket> mapped = original.call(codec, to, from);
        return StreamCodec.of(mapped, buf -> {
            ApolloCombat.INSTANCE.tap(buf, Minecraft.getInstance());
            return mapped.decode(buf);
        });
    }
}
