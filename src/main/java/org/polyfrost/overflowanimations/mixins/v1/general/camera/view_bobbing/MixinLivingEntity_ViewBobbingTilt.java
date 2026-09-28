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

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.util.states.ViewBobbingStorage;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity_ViewBobbingTilt implements ViewBobbingStorage {
    @Unique
    private float overflowanimations$bobbingTilt = 0.0F;

    @Unique
    private float overflowanimations$previousBobbingTilt = 0.0F;

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;tickEffects()V", shift = At.Shift.BEFORE))
    private void overflowanimations$updatePreviousBobbingTiltValue(final CallbackInfo ci) {
        this.overflowanimations$previousBobbingTilt = this.overflowanimations$bobbingTilt;
    }

    @Override
    public void overflowanimations$setBobbingTilt(float bobbingTilt) {
        this.overflowanimations$bobbingTilt = bobbingTilt;
    }

    @Override
    public float overflowanimations$getBobbingTilt() {
        return this.overflowanimations$bobbingTilt;
    }

    @Override
    public float overflowanimations$getPreviousBobbingTilt() {
        return this.overflowanimations$previousBobbingTilt;
    }
}