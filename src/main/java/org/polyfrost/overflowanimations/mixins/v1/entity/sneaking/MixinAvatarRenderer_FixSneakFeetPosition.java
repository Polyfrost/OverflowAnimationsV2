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

package org.polyfrost.overflowanimations.mixins.v1.entity.sneaking;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

@Mixin(AvatarRenderer.class)
public abstract class MixinAvatarRenderer_FixSneakFeetPosition<AvatarLikeEntity extends Avatar & ClientAvatarEntity> extends LivingEntityRenderer<AvatarLikeEntity, AvatarRenderState, PlayerModel> {
    public MixinAvatarRenderer_FixSneakFeetPosition(final EntityRendererProvider.Context context, final PlayerModel model, final float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @WrapOperation(method = "getRenderOffset(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;)Lnet/minecraft/world/phys/Vec3;", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;isCrouching:Z"))
    private boolean overflowanimations$fixSneakingFeetPosition(final AvatarRenderState instance, final Operation<Boolean> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().fixes.fixSneakingFeetPosition) {
            return false;
        } else {
            return original.call(instance);
        }
    }
}
