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

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.handler.server_features.ServerFeatureManager;
import org.polyfrost.overflowanimations.handler.server_features.ServerFeatures;
import org.polyfrost.overflowanimations.util.SwingUtilKt;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft_MissPenalty {
    @Shadow
    @Nullable
    public HitResult hitResult;

    @Shadow
    @Nullable
    public LocalPlayer player;

    @Shadow
    protected int missTime;

    @Inject(method = "startAttack", at = @At(value = "RETURN", ordinal = 0))
    private void overflowanimations$fakeMissPenaltySwing(final CallbackInfoReturnable<Boolean> cir) {
        if (!OverflowAnimations.isEnabled() || this.player == null || this.missTime > 10) {
            return;
        }
        if (OverflowAnimationsConfig.instance().items.fakeMissPenaltySwing) {
            SwingUtilKt.fakeHandSwing(this.player, InteractionHand.MAIN_HAND);
        }
        if (OverflowAnimationsConfig.instance().items.fakeMissPenaltyParticles && this.hitResult instanceof EntityHitResult entityHit) {
            SwingUtilKt.fakeAttackParticles(this.player, entityHit.getEntity());
        }
    }

    @WrapOperation(method = "startAttack", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;missTime:I", ordinal = 0, opcode = Opcodes.GETFIELD))
    private int overflowanimations$disableSwingMissPenalty(final Minecraft instance, final Operation<Integer> original) {
        if (ServerFeatureManager.isPresent(ServerFeatures.MISS_PENALTY) && (this.hitResult != null && this.hitResult.getType() != HitResult.Type.BLOCK)) {
            return 0;
        } else {
            return original.call(instance);
        }
    }
}
