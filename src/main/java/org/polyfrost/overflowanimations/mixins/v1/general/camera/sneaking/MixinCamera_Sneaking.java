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

package org.polyfrost.overflowanimations.mixins.v1.general.camera.sneaking;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.mixins.accessor.PlayerAccessor;
import org.polyfrost.overflowanimations.util.EntityUtilKt;
import org.polyfrost.overflowanimations.util.enums.SneakAnimationSetting;

@Mixin(Camera.class)
public abstract class MixinCamera_Sneaking {
    @Shadow
    private float eyeHeightOld;

    @Shadow
    private float eyeHeight;

    @Shadow
    private Entity entity;

    //? if <26.1 {
    /*@Inject(method = "setup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;setRotation(FF)V", shift = At.Shift.AFTER))
    *///?} else {
    @Inject(method = "alignWithEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;setRotation(FF)V", shift = At.Shift.AFTER))
    //?}
    private void overflowanimations$removeSmoothSneaking(final CallbackInfo ci) {
        if (OverflowAnimations.isEnabled() && !OverflowAnimationsConfig.instance().movement.sneakAnimation.isSmooth()) {
            this.eyeHeightOld = this.eyeHeight;
            this.eyeHeight = this.overflowanimations$getSneakingEyeHeight();
        }
    }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getEyeHeight()F"))
    private float overflowanimations$useOldEyeHeight(final Entity instance, final Operation<Float> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().movement.fakeOldSneakEyeHeight) {
            return this.overflowanimations$getSneakingEyeHeight();
        } else {
            return original.call(instance);
        }
    }

    @WrapOperation(method = "tick", at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/client/Camera;eyeHeight:F"))
    private void overflowanimations$oldSneakAnimationInterpolation(final Camera instance, final float value, final Operation<Void> original) {
        final SneakAnimationSetting sneakAnimation = OverflowAnimationsConfig.instance().movement.sneakAnimation;
        if (OverflowAnimations.isEnabled() && sneakAnimation != SneakAnimationSetting.VANILLA && sneakAnimation != SneakAnimationSetting.MODERN && this.entity.isCrouching()) {
            if (sneakAnimation == SneakAnimationSetting.V1_7 && this.entity.getEyeHeight() < this.eyeHeight) {
                this.eyeHeight = this.overflowanimations$getSneakingEyeHeight();
                return;
            } else if (!OverflowAnimationsConfig.instance().movement.longUnsneak && this.entity.getEyeHeight() > this.eyeHeight) {
                this.eyeHeight = this.entity.getEyeHeight(Pose.STANDING) * EntityUtilKt.getScale(this.entity);
                return;
            }
        }

        original.call(instance, value);
    }

    @Unique
    private float overflowanimations$getSneakingEyeHeight() {
        final float currentEyeHeight = this.entity.getEyeHeight();
        if (OverflowAnimations.isEnabled() &&
                OverflowAnimationsConfig.instance().movement.fakeOldSneakEyeHeight &&
                this.entity.hasPose(Pose.CROUCHING) &&
                this.entity instanceof Player player &&
                ((PlayerAccessor) player).overflowanimations$canChangeIntoPose(Pose.STANDING)) {
            return 1.54F * player.getScale();
        } else {
            return currentEyeHeight;
        }
    }
}
