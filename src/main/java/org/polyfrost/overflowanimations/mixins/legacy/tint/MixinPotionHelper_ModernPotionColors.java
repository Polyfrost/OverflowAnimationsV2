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

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.living.effect.PotionHelper;
import net.minecraft.entity.living.effect.StatusEffect;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(PotionHelper.class)
public abstract class MixinPotionHelper_ModernPotionColors {
    @Unique
    private static final int[] overflowanimations$MODERN_COLORS = {
            0, 3402751, 9154528, 14270531, 4866583, 16762624, 16262179, 11101546, 16646020, 5578058, 13458603, 9520880,
            16750848, 10017472, 16185078, 2039587, 12779366, 5797459, 4738376, 8889187, 7561558, 16284963, 2445989, 16262179
    };

    @Unique
    private static boolean overflowanimations$cachedModern = false;

    @Shadow
    @Final
    private static Map<Integer, Integer> COLOR_CACHE;

    @WrapOperation(method = "getColor(Ljava/util/Collection;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/effect/StatusEffect;getPotionColor()I"))
    private static int overflowanimations$modernColor(final StatusEffect effect, final Operation<Integer> original) {
        if (OverflowAnimationsConfig.instance().items.modernPotionColors && effect.id > 0 && effect.id < overflowanimations$MODERN_COLORS.length) {
            return overflowanimations$MODERN_COLORS[effect.id];
        }

        return original.call(effect);
    }

    @Inject(method = "getColor(IZ)I", at = @At("HEAD"))
    private static void overflowanimations$clearCache(final int metadata, final boolean custom, final CallbackInfoReturnable<Integer> cir) {
        final boolean modern = OverflowAnimationsConfig.instance().items.modernPotionColors;
        if (modern != overflowanimations$cachedModern) {
            overflowanimations$cachedModern = modern;
            COLOR_CACHE.clear();
        }
    }
}
