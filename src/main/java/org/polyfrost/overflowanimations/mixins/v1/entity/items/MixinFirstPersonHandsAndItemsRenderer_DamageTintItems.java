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

package org.polyfrost.overflowanimations.mixins.v1.entity.items;

//? if >=26.3 {
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.FirstPersonHandsAndItemsRenderer;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
//?}

//? if >=26.3 {
@Mixin(FirstPersonHandsAndItemsRenderer.class)
public abstract class MixinFirstPersonHandsAndItemsRenderer_DamageTintItems {
    @ModifyExpressionValue(method = "submitArmWithItem", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/texture/OverlayTexture;NO_OVERLAY:I", opcode = Opcodes.GETSTATIC))
    private int overflowanimations$damageTintItems(final int original, @Local(argsOnly = true, name = "playerState") final PlayerRenderState playerState) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().extras.damageTintItems && playerState.avatarRenderState != null) {
            return OverlayTexture.pack(0, OverlayTexture.v(playerState.avatarRenderState.hasRedOverlay));
        } else {
            return original;
        }
    }
}
//?}
