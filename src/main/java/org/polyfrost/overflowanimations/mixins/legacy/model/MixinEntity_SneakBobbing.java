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
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.util.enums.SneakBobbingSetting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class MixinEntity_SneakBobbing {
    @Inject(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;makesSteps()Z"))
    private void overflowanimations$modernSneakBobbing(final double dx, final double dy, final double dz, final CallbackInfo ci, @Local(ordinal = 3) final double startX, @Local(ordinal = 5) final double startZ, @Local(ordinal = 0) final boolean sneakingOnGround) {
        final Entity self = (Entity) (Object) this;
        if (sneakingOnGround && self instanceof LocalClientPlayerEntity && OverflowAnimationsConfig.instance().movement.sneakBobbing == SneakBobbingSetting.V1_21_2) {
            final double movedX = self.x - startX;
            final double movedZ = self.z - startZ;
            self.walkDistance = (float) (self.walkDistance + MathHelper.sqrt(movedX * movedX + movedZ * movedZ) * 0.6);
        }
    }
}
