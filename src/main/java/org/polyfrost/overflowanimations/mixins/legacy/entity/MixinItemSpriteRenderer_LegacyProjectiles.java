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

import net.minecraft.client.render.entity.ItemSpriteRenderer;
import net.minecraft.client.render.platform.GlStateManager;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.config.category.ItemsConfigCategory;
import org.polyfrost.overflowanimations.legacy.entity.LegacyItemRendering;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemSpriteRenderer.class)
public abstract class MixinItemSpriteRenderer_LegacyProjectiles {
    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/platform/GlStateManager;rotatef(FFFF)V", ordinal = 0), index = 0)
    private float overflowanimations$mirrorYaw(final float angle) {
        return overflowanimations$shouldMirror() ? angle + 180.0F : angle;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/platform/GlStateManager;rotatef(FFFF)V", ordinal = 1), index = 0)
    private float overflowanimations$mirrorPitch(final float angle) {
        return overflowanimations$shouldMirror() ? -angle : angle;
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/ItemRenderer;renderItemInHand(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resource/model/ModelTransformations$Type;)V"))
    private void overflowanimations$projectileTransforms(final CallbackInfo ci) {
        if (!OverflowAnimations.isEnabled()) {
            return;
        }
        final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
        if (items.legacyProjectiles) {
            GlStateManager.translatef(0.0F, 0.25F, 0.0F);
        }

        LegacyItemRendering.applyTransform(items.projectilePositionX, items.projectilePositionY, items.projectilePositionZ, items.projectileRotationX, items.projectileRotationY, items.projectileRotationZ, items.projectileScale);
    }

    @Unique
    private static boolean overflowanimations$shouldMirror() {
        final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
        return OverflowAnimations.isEnabled() && (items.legacyProjectiles || items.itemDrops2D);
    }
}
