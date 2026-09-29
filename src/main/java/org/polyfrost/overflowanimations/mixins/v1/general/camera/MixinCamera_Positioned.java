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

package org.polyfrost.overflowanimations.mixins.v1.general.camera;

import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
//? if <26.1 {
/*import net.minecraft.world.level.Level;
*///?}
//? if <1.21.11
//import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.util.enums.CameraVersionSetting;

@Mixin(Camera.class)
public abstract class MixinCamera_Positioned {
    @Shadow
    private Entity entity;

    @Shadow
    private boolean detached;

    @Shadow
    protected abstract void move(final float forwards, final float up, final float right);

    //? if <1.21.11 {
    /*@Inject(method = "setup", at = @At(value = "TAIL"))
    private void overflowanimations$cameraVersion(final BlockGetter level, final Entity entity, final boolean detached, final boolean mirror, final float tickDelta, final CallbackInfo ci) {
    *///?} elif <26.1 {
    /*@Inject(method = "setup", at = @At(value = "TAIL"))
    private void overflowanimations$cameraVersion(final Level level, final Entity entity, final boolean detached, final boolean mirror, final float tickDelta, final CallbackInfo ci) {
    *///?} else {
    @Inject(method = "alignWithEntity", at = @At(value = "TAIL"))
    private void overflowanimations$cameraVersion(final float partialTicks, final CallbackInfo ci) {
    //?}
        // TODO: Fix bed/sleeping position
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().screen.cameraVersion != CameraVersionSetting.VANILLA && !this.detached && !(this.entity instanceof LivingEntity && ((LivingEntity) this.entity).isSleeping())) {
            final int ordinal = OverflowAnimationsConfig.instance().screen.cameraVersion.ordinal();
            if (ordinal <= CameraVersionSetting.V1_14_TO_V1_14_3.ordinal()) {
                // <= 1.14.3
                this.move(-0.05000000074505806F, 0.0F, 0.0F);
                // <= 1.13.2
                if (ordinal <= CameraVersionSetting.V1_9_TO_V1_13_2.ordinal()) {
                    this.move(0.1F, 0.0F, 0.0F);
                    // <= 1.8
                    if (ordinal == CameraVersionSetting.V1_8.ordinal()) {
                        this.move(-0.15F, 0, 0); // unfixing parallax
                    }
                }
            }
        }
    }
}
