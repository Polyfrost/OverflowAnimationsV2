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

package org.polyfrost.overflowanimations.mixins.legacy.tint;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.layer.AbstractArmorLayer;
import net.minecraft.client.render.model.Model;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.LivingEntity;
import net.minecraft.item.ItemStack;
import org.polyfrost.overflowanimations.config.category.OtherConfigCategory;
import org.polyfrost.overflowanimations.legacy.tint.LegacyDamageTint;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractArmorLayer.class)
public abstract class MixinAbstractArmorLayer_DamageTint {
    @Shadow
    @Final
    private LivingEntityRenderer<?> parent;

    @Shadow
    private boolean hasColor;

    @ModifyReturnValue(method = "colorsWhenDamaged", at = @At("RETURN"))
    private boolean overflowanimations$tintArmor(final boolean original) {
        final OtherConfigCategory config = LegacyDamageTint.config();
        return original || config.damageTintArmor && !LegacyDamageTint.flatTint();
    }

    @WrapOperation(method = "renderArmor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/Model;render(Lnet/minecraft/entity/Entity;FFFFFF)V", ordinal = 1))
    private void overflowanimations$flatTintArmor(final Model model, final Entity entity, final float walkAnimationProgress, final float walkAnimationSpeed, final float bob, final float yaw, final float pitch, final float scale, final Operation<Void> original, @Local(argsOnly = true, ordinal = 2) final float tickDelta, @Local(ordinal = 0) final ItemStack stack) {
        original.call(model, entity, walkAnimationProgress, walkAnimationSpeed, bob, yaw, pitch, scale);
        final OtherConfigCategory config = LegacyDamageTint.config();
        final boolean tintAfterGlint = config.glintAffectsArmorTint && !this.hasColor && stack.hasEnchantments();
        if (config.damageTintArmor && !tintAfterGlint && LegacyDamageTint.beginFlatPass(entity, tickDelta)) {
            original.call(model, entity, walkAnimationProgress, walkAnimationSpeed, bob, yaw, pitch, scale);
            LegacyDamageTint.endFlatPass();
        }
    }

    @WrapOperation(method = "renderArmor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/layer/AbstractArmorLayer;renderEnchantmentGlint(Lnet/minecraft/entity/living/LivingEntity;Lnet/minecraft/client/render/model/Model;FFFFFFF)V"))
    private void overflowanimations$glintTint(final AbstractArmorLayer<?> instance, final LivingEntity entity, final Model model, final float walkAnimationProgress, final float walkAnimationSpeed, final float tickDelta, final float bob, final float yaw, final float pitch, final float scale, final Operation<Void> original) {
        final OtherConfigCategory config = LegacyDamageTint.config();
        final boolean suspendCombiners = config.damageTintArmor && !config.glintAffectsArmorTint && !LegacyDamageTint.flatTint() && LegacyDamageTint.isHurt(entity);
        if (suspendCombiners) {
            this.overflowanimations$parent().overflowanimations$tint$tearDownOverlayColor();
        }

        original.call(instance, entity, model, walkAnimationProgress, walkAnimationSpeed, tickDelta, bob, yaw, pitch, scale);
        if (suspendCombiners) {
            this.overflowanimations$parent().overflowanimations$tint$setupOverlayColor(entity, tickDelta, true);
        }

        if (config.damageTintArmor && config.glintAffectsArmorTint && LegacyDamageTint.beginFlatPass(entity, tickDelta)) {
            model.render(entity, walkAnimationProgress, walkAnimationSpeed, bob, yaw, pitch, scale);
            LegacyDamageTint.endFlatPass();
        }
    }

    @Unique
    private MixinLivingEntityRenderer_DamageTintInvoker overflowanimations$parent() {
        return (MixinLivingEntityRenderer_DamageTintInvoker) this.parent;
    }
}
