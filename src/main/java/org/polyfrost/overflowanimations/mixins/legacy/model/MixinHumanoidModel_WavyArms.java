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

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.render.model.ModelPart;
import net.minecraft.client.render.model.entity.HumanoidModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.objectweb.asm.Opcodes;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class MixinHumanoidModel_WavyArms {
    @Shadow
    public ModelPart rightArm;

    @Shadow
    public ModelPart leftArm;

    @Inject(method = "setupAnimation", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;cos(F)F", ordinal = 2))
    private void overflowanimations$wavyArms(final float walkAnimationProgress, final float walkAnimationSpeed, final float bob, final float yaw, final float pitch, final float scale, final Entity entity, final CallbackInfo ci) {
        if (OverflowAnimationsConfig.instance().movement.wavyArms) {
            this.rightArm.rotationX = MathHelper.cos(walkAnimationProgress * 0.6662F + (float) Math.PI) * 2.0F * walkAnimationSpeed;
            this.rightArm.rotationZ = (MathHelper.cos(walkAnimationProgress * 0.2312F) + 1.0F) * walkAnimationSpeed;
            this.leftArm.rotationX = MathHelper.cos(walkAnimationProgress * 0.6662F) * 2.0F * walkAnimationSpeed;
            this.leftArm.rotationZ = (MathHelper.cos(walkAnimationProgress * 0.2812F) - 1.0F) * walkAnimationSpeed;
        }
    }

    @WrapWithCondition(method = "setupAnimation", at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/client/render/model/ModelPart;rotationZ:F", ordinal = 2))
    private boolean overflowanimations$keepWavyArm(final ModelPart part, final float value) {
        return !OverflowAnimationsConfig.instance().movement.wavyArms;
    }
}
