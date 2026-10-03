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
import net.minecraft.client.render.entity.layer.EnderDragonEyesLayer;
import net.minecraft.client.render.entity.layer.EndermanEyesLayer;
import net.minecraft.client.render.entity.layer.SlimeOuterLayer;
import net.minecraft.client.render.entity.layer.SpiderEyesLayer;
import net.minecraft.client.render.entity.layer.WolfCollarLayer;
import net.minecraft.client.render.model.Model;
import net.minecraft.entity.Entity;
import org.polyfrost.overflowanimations.legacy.tint.LegacyDamageTint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({SpiderEyesLayer.class, EndermanEyesLayer.class, EnderDragonEyesLayer.class, SlimeOuterLayer.class, WolfCollarLayer.class})
public abstract class MixinMobLayers_DamageTint {
    @ModifyReturnValue(method = "colorsWhenDamaged", at = @At("RETURN"))
    private boolean overflowanimations$skipCombinerTint(final boolean original) {
        return original && !LegacyDamageTint.flatTint();
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/Model;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void overflowanimations$flatTint(final Model model, final Entity entity, final float walkAnimationProgress, final float walkAnimationSpeed, final float bob, final float yaw, final float pitch, final float scale, final Operation<Void> original, @Local(argsOnly = true, ordinal = 2) final float tickDelta) {
        original.call(model, entity, walkAnimationProgress, walkAnimationSpeed, bob, yaw, pitch, scale);
        if (LegacyDamageTint.beginFlatPass(entity, tickDelta)) {
            original.call(model, entity, walkAnimationProgress, walkAnimationSpeed, bob, yaw, pitch, scale);
            LegacyDamageTint.endFlatPass();
        }
    }
}
