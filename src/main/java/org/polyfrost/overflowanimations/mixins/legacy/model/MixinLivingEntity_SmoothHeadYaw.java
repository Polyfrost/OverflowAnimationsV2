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
import org.polyfrost.overflowanimations.legacy.model.HeadYawLerp;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity_SmoothHeadYaw implements HeadYawLerp {
    @Shadow
    public float headYaw;

    @Unique
    private float overflowanimations$lerpHeadYaw;

    @Unique
    private int overflowanimations$lerpHeadSteps;

    @Override
    public void overflowanimations$lerpHeadTo(final float headYaw, final int steps) {
        this.overflowanimations$lerpHeadYaw = headYaw;
        this.overflowanimations$lerpHeadSteps = steps;
    }

    @Override
    public void overflowanimations$tickHeadLerp() {
        if (this.overflowanimations$lerpHeadSteps > 0) {
            this.headYaw += MathHelper.wrapDegrees(this.overflowanimations$lerpHeadYaw - this.headYaw) / this.overflowanimations$lerpHeadSteps;
            this.overflowanimations$lerpHeadSteps--;
        }
    }

    @Inject(method = "mobTick", at = @At("HEAD"))
    private void overflowanimations$onMobTick(final CallbackInfo ci) {
        this.overflowanimations$tickHeadLerp();
    }
}
