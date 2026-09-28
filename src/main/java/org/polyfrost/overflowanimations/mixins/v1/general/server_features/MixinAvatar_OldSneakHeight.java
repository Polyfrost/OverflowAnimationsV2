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

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.handler.server_features.ServerFeatureManager;
import org.polyfrost.overflowanimations.handler.server_features.ServerFeatures;

import java.util.Map;

@Mixin(Avatar.class)
public abstract class MixinAvatar_OldSneakHeight {
    @WrapOperation(method = "getDefaultDimensions", at = @At(value = "INVOKE", target = "Ljava/util/Map;getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private <V> V overflowanimations$oldSneakHeight(final Map<Pose, EntityDimensions> instance, final Object pose, final V defaultValue, final Operation<EntityDimensions> original) {
        final EntityDimensions entityDimensions = original.call(instance, pose, defaultValue);
        if (ServerFeatureManager.isPresent(ServerFeatures.OLD_SNEAK_HEIGHT) && pose == Pose.CROUCHING) {
            return (V) new EntityDimensions(entityDimensions.width(), 1.65F, 1.54F, entityDimensions.attachments(), entityDimensions.fixed());
        } else {
            return (V) entityDimensions;
        }
    }
}
