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

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.network.handler.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.config.category.MovementConfigCategory;
import org.polyfrost.overflowanimations.legacy.model.HeadYawLerp;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class MixinClientPlayNetworkHandler_SmoothHeadYaw {
    @WrapOperation(method = "handleEntityHeadAngles", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setHeadYaw(F)V"))
    private void overflowanimations$smoothHeadYaw(final Entity entity, final float headYaw, final Operation<Void> original) {
        final MovementConfigCategory config = OverflowAnimationsConfig.instance().movement;
        if (config.smoothHeadYaw && !config.disableHeadRotationInterpolation && entity instanceof HeadYawLerp) {
            ((HeadYawLerp) entity).overflowanimations$lerpHeadTo(headYaw, 3);
        } else {
            original.call(entity, headYaw);
        }
    }
}
