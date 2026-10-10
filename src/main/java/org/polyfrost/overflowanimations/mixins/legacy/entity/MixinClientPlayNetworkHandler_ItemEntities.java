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

package org.polyfrost.overflowanimations.mixins.legacy.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.ParticleManager;
import net.minecraft.client.entity.particle.Particle;
import net.minecraft.client.network.handler.ClientPlayNetworkHandler;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class MixinClientPlayNetworkHandler_ItemEntities {
    @ModifyExpressionValue(method = "handleAddExperienceOrb", at = @At(value = "CONSTANT", args = "doubleValue=32.0"))
    private double overflowanimations$xpOrbPosition(final double original) {
        return OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.xpOrbPosition ? 1.0 : original;
    }

    @WrapWithCondition(method = "handleEntityPickup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/ParticleManager;add(Lnet/minecraft/client/entity/particle/Particle;)V"))
    private boolean overflowanimations$disableItemPickupAnimation(final ParticleManager instance, final Particle particle) {
        return !(OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.disableItemPickupAnimation);
    }
}
