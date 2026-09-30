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

package org.polyfrost.overflowanimations.mixins.legacy.model;

import net.minecraft.entity.living.LivingEntity;
import net.minecraft.util.math.MathHelper;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity_BackwardsWalking {
    @Shadow
    public float attackAnimationProgress;

    @ModifyArg(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/LivingEntity;bodyMovement(FF)F"), index = 0)
    private float overflowanimations$modernBackwardsWalking(final float bodyYaw) {
        final LivingEntity self = (LivingEntity) (Object) this;
        final double dx = self.x - self.lastX;
        final double dz = self.z - self.lastZ;
        if (!OverflowAnimationsConfig.instance().movement.modernBackwardsWalking || (float) (dx * dx + dz * dz) <= 0.0025000002F || this.attackAnimationProgress > 0.0F) {
            return bodyYaw;
        }

        final float difference = Math.abs(MathHelper.wrapDegrees(self.yaw) - bodyYaw);
        return 95.0F < difference && difference < 265.0F ? bodyYaw - 180.0F : bodyYaw;
    }
}
