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

package org.polyfrost.overflowanimations.mixins.v1.general.camera.view_bobbing;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.util.enums.SneakBobbingSetting;
import org.polyfrost.overflowanimations.util.states.ViewBobbingStorage;

@Mixin(Entity.class)
public abstract class MixinEntity_HorizontalViewBobbing implements ViewBobbingStorage {
    @Unique
    private float overflowanimations$horizontalSpeed = 0.0F;

    @Unique
    private float overflowanimations$previousHorizontalSpeed = 0.0F;

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;handlePortal()V", shift = At.Shift.AFTER))
    private void overflowanimations$storePreviousHorizontalSpeed(final CallbackInfo ci) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().movement.sneakBobbing == SneakBobbingSetting.V1_21_1) {
            this.overflowanimations$previousHorizontalSpeed = this.overflowanimations$horizontalSpeed;
        }
    }

    @Inject(method = "applyMovementEmissionAndPlaySound", at = @At("HEAD"))
    private void overflowanimations$storeHorizontalSpeed(final Entity.MovementEmission emission, final Vec3 clippedMovement, final BlockPos effectPos, final BlockState effectState, final CallbackInfo ci) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().movement.sneakBobbing == SneakBobbingSetting.V1_21_1) {
            this.overflowanimations$horizontalSpeed = this.overflowanimations$horizontalSpeed + (float) clippedMovement.horizontalDistance() * 0.6F;
        }
    }

    @Override
    public float overflowanimations$getHorizontalSpeed() {
        return this.overflowanimations$horizontalSpeed;
    }

    @Override
    public float overflowanimations$getPreviousHorizontalSpeed() {
        return this.overflowanimations$previousHorizontalSpeed;
    }
}
