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

package org.polyfrost.overflowanimations.mixins.v1.general.server_features;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.Packet;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.handler.server_features.ServerFeatureManager;
import org.polyfrost.overflowanimations.handler.server_features.ServerFeatures;

@Mixin(MultiPlayerGameMode.class)
public abstract class MixinMultiPlayerGameMode_StopBlockMining {
    @Shadow
    private boolean isDestroying;

    @WrapOperation(method = "stopDestroyBlock", at = @At(value = "FIELD", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;isDestroying:Z", opcode = Opcodes.GETFIELD))
    private boolean overflowanimations$miningItemUsage$allowFullBlock(final MultiPlayerGameMode instance, final Operation<Boolean> original) {
        if (ServerFeatureManager.isPresent(ServerFeatures.MINING_ITEM_USAGE)) {
            return true;
        } else {
            return original.call(instance);
        }
    }

    @WrapWithCondition(method = "stopDestroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;send(Lnet/minecraft/network/protocol/Packet;)V"))
    private boolean overflowanimations$miningItemUsage$stopPacket(final ClientPacketListener instance, final Packet<?> packet) {
        if (ServerFeatureManager.isPresent(ServerFeatures.MINING_ITEM_USAGE)) {
            return this.isDestroying;
        } else {
            return true;
        }
    }

    @WrapWithCondition(method = "stopDestroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;resetAttackStrengthTicker()V"))
    private boolean overflowanimations$miningItemUsage$fixAttackProgress(final LocalPlayer instance) {
        if (ServerFeatureManager.isPresent(ServerFeatures.MINING_ITEM_USAGE)) {
            return this.isDestroying;
        } else {
            return true;
        }
    }
}
