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

package org.polyfrost.overflowanimations.mixins.legacy.firstperson;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.ClientPlayerInteractionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.legacy.firstperson.LegacyFirstPerson;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class MixinClientPlayerInteractionManager_Mining {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private BlockPos target;

    @Shadow
    private float miningProgress;

    @Shadow
    private boolean isMiningBlock;

    @ModifyArg(method = "tickBlockMining", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;updateBlockMiningProgress(ILnet/minecraft/util/math/BlockPos;I)V"), index = 2)
    private int overflowanimations$modernBlockBreaking(final int progress) {
        if (OverflowAnimations.isEnabled() && !OverflowAnimationsConfig.instance().other.blockMiningProgress.isLegacy()) {
            return this.miningProgress > 0.0F ? (int) (this.miningProgress * 10.0F) : -1;
        }
        return progress;
    }

    @ModifyExpressionValue(method = "tickBlockMining", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/ClientPlayerInteractionManager;isMiningBlock(Lnet/minecraft/util/math/BlockPos;)Z"))
    private boolean overflowanimations$requireMiningState(final boolean original) {
        return original && (this.isMiningBlock || !(OverflowAnimations.isEnabled() && LegacyFirstPerson.items().resetMiningOnUse));
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void overflowanimations$resetMiningOnUse(final CallbackInfo ci) {
        if (OverflowAnimations.isEnabled() && this.isMiningBlock && LegacyFirstPerson.items().resetMiningOnUse && this.minecraft.player != null && this.minecraft.player.hasItemInUse()) {
            this.isMiningBlock = false;
            this.miningProgress = 0.0F;
            this.minecraft.world.updateBlockMiningProgress(this.minecraft.player.getNetworkId(), this.target, -1);
        }
    }
}
