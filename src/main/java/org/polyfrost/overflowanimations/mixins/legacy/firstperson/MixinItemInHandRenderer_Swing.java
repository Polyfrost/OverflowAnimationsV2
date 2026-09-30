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

package org.polyfrost.overflowanimations.mixins.legacy.firstperson;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import net.minecraft.client.render.ItemInHandRenderer;
import net.minecraft.client.render.platform.GlStateManager;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.config.category.ItemsConfigCategory;
import org.polyfrost.overflowanimations.legacy.firstperson.LegacyFirstPerson;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class MixinItemInHandRenderer_Swing {
    @Unique
    private static final float overflowanimations$PIVOT_MULTIPLIER = 0.05F;

    @Shadow
    @Final
    private Minecraft minecraft;

    @ModifyArg(method = "renderInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/ItemInHandRenderer;applyFirstPersonTransform(FF)V"), index = 1)
    private float overflowanimations$itemUsageSwinging(final float swingProgress, @Local(argsOnly = true) final float tickDelta) {
        return LegacyFirstPerson.items().itemUsageSwinging ? this.minecraft.player.getAttackAnimationProgress(tickDelta) : swingProgress;
    }

    @ModifyExpressionValue(method = "renderInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/living/player/ClientPlayerEntity;getItemUseTimer()I"))
    private int overflowanimations$itemUsageVisualInGUI(final int original) {
        return OverflowAnimationsConfig.instance().fixes.fixItemUsageVisualInGUI && this.minecraft.screen != null ? 0 : original;
    }

    @WrapWithCondition(method = "renderInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/ItemInHandRenderer;applyHandSway(Lnet/minecraft/client/entity/living/player/LocalClientPlayerEntity;F)V"))
    private boolean overflowanimations$disableHandSway(final ItemInHandRenderer instance, final LocalClientPlayerEntity player, final float tickDelta) {
        return !LegacyFirstPerson.items().disableHandSway;
    }

    @WrapOperation(method = "applyArmSwing", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/platform/GlStateManager;translatef(FFF)V"))
    private void overflowanimations$swingTranslate(final float x, final float y, final float z, final Operation<Void> original) {
        final ItemsConfigCategory items = LegacyFirstPerson.items();
        if (items.disableSwingTranslate) {
            return;
        }
        float scaleX = 1.0F + items.swingPositionX;
        float scaleY = 1.0F - items.swingPositionY;
        float scaleZ = 1.0F + items.swingPositionZ;
        if (items.smartSwingScaling) {
            scaleX *= items.itemScaleX;
            scaleY *= items.itemScaleY;
            scaleZ *= items.itemScaleZ;
        }
        original.call(x * scaleX, y * scaleY, z * scaleZ);
    }

    @Inject(method = "applyFirstPersonTransform", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/platform/GlStateManager;rotatef(FFFF)V", ordinal = 1))
    private void overflowanimations$preSwingPivot(final float equipProgress, final float swingProgress, final CallbackInfo ci) {
        final ItemsConfigCategory items = LegacyFirstPerson.items();
        if (items.disableSwingPivot) {
            GlStateManager.translatef(items.itemOffsetX * overflowanimations$PIVOT_MULTIPLIER, items.itemOffsetY * overflowanimations$PIVOT_MULTIPLIER, items.itemOffsetZ * overflowanimations$PIVOT_MULTIPLIER);
        }
    }

    @Inject(method = "applyFirstPersonTransform", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/platform/GlStateManager;rotatef(FFFF)V", ordinal = 3, shift = At.Shift.AFTER))
    private void overflowanimations$postSwingPivot(final float equipProgress, final float swingProgress, final CallbackInfo ci) {
        final ItemsConfigCategory items = LegacyFirstPerson.items();
        if (items.disableSwingPivot) {
            GlStateManager.translatef(-items.itemOffsetX * overflowanimations$PIVOT_MULTIPLIER, -items.itemOffsetY * overflowanimations$PIVOT_MULTIPLIER, -items.itemOffsetZ * overflowanimations$PIVOT_MULTIPLIER);
        }
    }
}
