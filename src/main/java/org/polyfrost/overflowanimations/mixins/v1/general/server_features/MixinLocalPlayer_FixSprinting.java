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

package org.polyfrost.overflowanimations.mixins.v1.general.server_features;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.handler.server_features.ServerFeatureManager;
import org.polyfrost.overflowanimations.handler.server_features.ServerFeatures;

@Mixin(LocalPlayer.class)
public abstract class MixinLocalPlayer_FixSprinting extends AbstractClientPlayer {
    public MixinLocalPlayer_FixSprinting(final ClientLevel clientLevel, final GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    //? if <1.21.9 {
    /*@ModifyReturnValue(method = "canStartSprinting", at = @At("RETURN"))
    *///?} else {
    @ModifyReturnValue(method = "isSprintingPossible", at = @At("RETURN"))
    //?}
    private boolean overflowanimations$fixItemUseSprinting(final boolean original) {
        if ((ServerFeatureManager.isPresent(ServerFeatures.FIX_SPRINT_ITEM_USE) && this.isUsingItem())
                || (ServerFeatureManager.isPresent(ServerFeatures.FIX_SPRINT_SNEAKING) && this.isCrouching())) {
            return false;
        } else {
            return original;
        }
    }
}
