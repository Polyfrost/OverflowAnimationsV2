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

package org.polyfrost.overflowanimations.mixins.v1.entity.rotations;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    @Shadow
    public float yBodyRot;

    public MixinLivingEntity(final EntityType<?> type, final Level level) {
        super(type, level);
    }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;abs(F)F"))
    private float overflowanimations$rotateBackwardsWalking(final float value, final Operation<Float> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().movement.backwardsWalking.isLegacy()) {
            return 0F;
        } else {
            return original.call(value);
        }
    }

    // TODO/NOTE: Might can be improved/shortened
    @WrapOperation(method = "tickHeadTurn", at = @At(value = "INVOKE", target = "Ljava/lang/Math;abs(F)F"))
    private float overflowanimations$backwardsWalkingHeadRotation(final float value, final Operation<Float> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().movement.backwardsWalking.isLegacy()) {
            final float rotation = Mth.clamp(value, -75.0F, 75.0F);
            this.yBodyRot = this.getYRot() - rotation;
            if (Math.abs(rotation) > 50.0F) {
                this.yBodyRot += rotation * 0.2F;
            }

            return Float.MIN_VALUE;
        } else {
            return original.call(value);
        }
    }

    @WrapOperation(method = "lerpHeadRotationStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;rotLerp(DDD)D"))
    public double overflowanimations$disableHeadRotationInterpolation(final double delta, final double start, final double end, final Operation<Double> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().movement.headRotationInterpolation.isLegacy()) {
            return end;
        } else {
            return original.call(delta, start, end);
        }
    }
}
