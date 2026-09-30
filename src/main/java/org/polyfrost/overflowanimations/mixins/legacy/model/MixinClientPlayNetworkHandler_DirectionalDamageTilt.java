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

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.handler.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.network.packet.s2c.play.EntityVelocityS2CPacket;
import net.minecraft.util.math.MathHelper;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class MixinClientPlayNetworkHandler_DirectionalDamageTilt {
    @Inject(method = "handleEntityVelocity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;lerpVelocity(DDD)V"))
    private void overflowanimations$directionalDamageTilt(final EntityVelocityS2CPacket packet, final CallbackInfo ci, @Local final Entity entity) {
        if (!OverflowAnimationsConfig.instance().movement.directionalDamageTilt || entity != Minecraft.getInstance().player || ((LivingEntity) entity).damagedTimer <= 0) {
            return;
        }

        final double pushX = packet.getVelocityX() / 8000.0 - entity.velocityX * 0.5;
        final double pushZ = packet.getVelocityZ() / 8000.0 - entity.velocityZ * 0.5;
        if (pushX * pushX + pushZ * pushZ > 1.0E-4) {
            ((LivingEntity) entity).damagedSwingDir = (float) (MathHelper.fastAtan2(-pushZ, -pushX) * 180.0 / Math.PI - entity.yaw);
        }
    }
}
