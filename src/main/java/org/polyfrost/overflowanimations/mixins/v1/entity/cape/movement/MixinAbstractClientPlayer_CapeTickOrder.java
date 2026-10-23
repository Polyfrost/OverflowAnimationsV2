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

package org.polyfrost.overflowanimations.mixins.v1.entity.cape.movement;

//? if >=1.21.9 {
import net.minecraft.client.entity.ClientAvatarState;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.util.enums.CapeMovementSetting;

@Mixin(AbstractClientPlayer.class)
public abstract class MixinAbstractClientPlayer_CapeTickOrder {
    @Shadow
    @Final
    private ClientAvatarState clientAvatarState;

    @Unique
    private double overflowanimations$tickStartX;

    @Unique
    private double overflowanimations$tickStartY;

    @Unique
    private double overflowanimations$tickStartZ;

    @Inject(method = "tick", at = @At("HEAD"))
    private void overflowanimations$storeTickStartPosition(final CallbackInfo ci) {
        final AbstractClientPlayer self = (AbstractClientPlayer) (Object) this;
        this.overflowanimations$tickStartX = self.getX();
        this.overflowanimations$tickStartY = self.getY();
        this.overflowanimations$tickStartZ = self.getZ();
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void overflowanimations$moveCloakAfterMovement(final CallbackInfo ci) {
        if (!OverflowAnimations.isEnabled() || OverflowAnimationsConfig.instance().movement.capeMovement != CapeMovementSetting.V1_12) {
            return;
        }

        final AbstractClientPlayer self = (AbstractClientPlayer) (Object) this;
        final double x = self.getX() - this.overflowanimations$tickStartX;
        final double y = self.getY() - this.overflowanimations$tickStartY;
        final double z = self.getZ() - this.overflowanimations$tickStartZ;
        if (x > 10.0 || x < -10.0 || y > 10.0 || y < -10.0 || z > 10.0 || z < -10.0) {
            return;
        }

        this.clientAvatarState.xCloak += x * 0.25;
        this.clientAvatarState.yCloak += y * 0.25;
        this.clientAvatarState.zCloak += z * 0.25;
    }
}
//?}
