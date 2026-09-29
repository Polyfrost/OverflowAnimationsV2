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

package org.polyfrost.overflowanimations.mixins.v1.rendering.sky.cloud_height;

//? if <1.21.5 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer_LegacyCloudHeight {
    @Shadow
    private ClientLevel level;

    @WrapOperation(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/DimensionSpecialEffects;getCloudHeight()F"))
    private float overflowanimations$cloudHeight(final DimensionSpecialEffects instance, final Operation<Float> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.cloudHeight && !this.level.dimensionType().hasCeiling()) {
            return 128.0F;
        } else {
            return original.call(instance);
        }
    }
}
*///?}

//? if >=1.21.5 <1.21.11 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

import java.util.Optional;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer_LegacyCloudHeight {
    @WrapOperation(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/dimension/DimensionType;cloudHeight()Ljava/util/Optional;"))
    private Optional<Integer> overflowanimations$cloudHeight(final DimensionType instance, final Operation<Optional<Integer>> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.cloudHeight && !instance.hasCeiling()) {
            return Optional.of(128);
        } else {
            return original.call(instance);
        }
    }
}
*///?}

//? if >=1.21.11 <26.2 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.EnvironmentAttributeProbe;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
*///?}

//? if >=1.21.11 <26.2 {
/*@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer_LegacyCloudHeight {
    @Shadow
    private @Nullable ClientLevel level;
*///?}

    //? if >=1.21.11 <26.1 {
    /*@WrapOperation(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/attribute/EnvironmentAttributeProbe;getValue(Lnet/minecraft/world/attribute/EnvironmentAttribute;F)Ljava/lang/Object;", ordinal = 1))
    *///?} elif 26.1 {
    /*@WrapOperation(method = "extractLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/attribute/EnvironmentAttributeProbe;getValue(Lnet/minecraft/world/attribute/EnvironmentAttribute;F)Ljava/lang/Object;", ordinal = 1))
    *///?}
//? if >=1.21.11 <26.2 {
    /*private <Value> Value overflowanimations$cloudHeight(final EnvironmentAttributeProbe instance, final EnvironmentAttribute<Value> attribute, final float partialTicks, final Operation<Value> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.cloudHeight && !this.level.dimensionType().hasCeiling()) {
            return (Value) (Object) 128.0F;
        } else {
            return original.call(instance, attribute, partialTicks);
        }
    }
}
*///?}
