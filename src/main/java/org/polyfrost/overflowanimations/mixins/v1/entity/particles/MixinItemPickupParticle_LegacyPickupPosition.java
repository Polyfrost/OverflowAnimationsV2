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

package org.polyfrost.overflowanimations.mixins.v1.entity.particles;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.particle.ItemPickupParticle;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

@Mixin(ItemPickupParticle.class)
public abstract class MixinItemPickupParticle_LegacyPickupPosition {
    @Shadow
    @Final
    private Entity target;

    @ModifyExpressionValue(method = "updatePosition", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getEyeY()D"))
    private double overflowanimations$itemPickupPosition(final double original) {
        // TODO: Check this setting, I think the description of it might of been wrong
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.itemPickupPosition) {
            return this.target.position().y;
        } else {
            return original;
        }
    }
}
