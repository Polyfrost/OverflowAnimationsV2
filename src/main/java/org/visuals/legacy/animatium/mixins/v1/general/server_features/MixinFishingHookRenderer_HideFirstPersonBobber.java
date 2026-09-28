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

package org.visuals.legacy.animatium.mixins.v1.general.server_features;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.handler.server_features.ServerFeatureManager;
import org.visuals.legacy.animatium.handler.server_features.ServerFeatures;
import org.visuals.legacy.animatium.util.EntityUtilKt;

@Mixin(FishingHookRenderer.class)
public abstract class MixinFishingHookRenderer_HideFirstPersonBobber {
    //? if <26.3 {
    /*@ModifyReturnValue(method = "shouldRender(Lnet/minecraft/world/entity/projectile/FishingHook;Lnet/minecraft/client/renderer/culling/Frustum;DDD)Z", at = @At("RETURN"))
    *///?}
    //? if 1.21.11 {
    /*private boolean animatium$hideBobberAttachedToSelf(final boolean original, @Local(argsOnly = true, ordinal = 0) final FishingHook entity) {
    *///?} elif >=26.3 {
    @ModifyReturnValue(method = "shouldRender(Lnet/minecraft/world/entity/projectile/FishingHook;Lnet/minecraft/client/renderer/culling/Frustum;DDDF)Z", at = @At("RETURN"))
    //?}
    //? if >=26.1 {
    private boolean animatium$hideBobberAttachedToSelf(final boolean original, @Local(argsOnly = true, name = "entity") final FishingHook entity) {
    //?}
        if (ServerFeatureManager.isPresent(ServerFeatures.HIDE_FIRST_PERSON_ROD_BOBBER) && entity.getHookedIn() instanceof Entity hook && EntityUtilKt.isSelf(hook)) {
            return false;
        } else {
            return original;
        }
    }
}
