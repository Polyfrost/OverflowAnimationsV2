/**
 * Animatium
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

package org.visuals.legacy.animatium.mixins.v1.general.camera.sneaking;

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
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.mixins.accessor.PlayerAccessor;
import org.visuals.legacy.animatium.util.EntityUtilKt;
import org.visuals.legacy.animatium.util.enums.SneakAnimationSetting;

@Mixin(Camera.class)
public abstract class MixinCamera_Sneaking {
    @Shadow
    private float eyeHeightOld;

    @Shadow
    private float eyeHeight;

    @Shadow
    private Entity entity;

    //? if 1.21.11 {
    /*@Inject(method = "setup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;setRotation(FF)V", shift = At.Shift.AFTER))
    *///?} else {
    @Inject(method = "alignWithEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;setRotation(FF)V", shift = At.Shift.AFTER))
    //?}
    private void animatium$removeSmoothSneaking(final CallbackInfo ci) {
        if (Animatium.isEnabled() && !AnimatiumConfig.instance().movement.sneakAnimation.isSmooth()) {
            this.eyeHeightOld = this.eyeHeight;
            this.eyeHeight = this.animatium$getSneakingEyeHeight();
        }
    }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getEyeHeight()F"))
    private float animatium$useOldEyeHeight(final Entity instance, final Operation<Float> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().movement.fakeOldSneakEyeHeight) {
            return this.animatium$getSneakingEyeHeight();
        } else {
            return original.call(instance);
        }
    }

    @WrapOperation(method = "tick", at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/client/Camera;eyeHeight:F"))
    private void animatium$oldSneakAnimationInterpolation(final Camera instance, final float value, final Operation<Void> original) {
        final SneakAnimationSetting sneakAnimation = AnimatiumConfig.instance().movement.sneakAnimation;
        if (Animatium.isEnabled() && sneakAnimation != SneakAnimationSetting.VANILLA && this.entity.isCrouching()) {
            if (sneakAnimation == SneakAnimationSetting.V1_7 && this.entity.getEyeHeight() < this.eyeHeight) {
                this.eyeHeight = this.animatium$getSneakingEyeHeight();
                return;
            } else if (!AnimatiumConfig.instance().movement.longUnsneak && this.entity.getEyeHeight() > this.eyeHeight) {
                this.eyeHeight = this.entity.getEyeHeight(Pose.STANDING) * EntityUtilKt.getScale(this.entity);
                return;
            }
        }

        original.call(instance, value);
    }

    @Unique
    private float animatium$getSneakingEyeHeight() {
        final float currentEyeHeight = this.entity.getEyeHeight();
        if (Animatium.isEnabled() &&
                AnimatiumConfig.instance().movement.fakeOldSneakEyeHeight &&
                this.entity.hasPose(Pose.CROUCHING) &&
                this.entity instanceof Player player &&
                ((PlayerAccessor) player).animatium$canChangeIntoPose(Pose.STANDING)) {
            return 1.54F * player.getScale();
        } else {
            return currentEyeHeight;
        }
    }
}
