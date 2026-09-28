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

package org.polyfrost.overflowanimations.mixins.v1.rendering.fog;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

@Mixin(ClientLevel.class)
public abstract class MixinClientLevel_DontTickSkyBrightness extends Level {
    //? if <26.4 {
    /*protected MixinClientLevel_DontTickSkyBrightness(final WritableLevelData levelData, final ResourceKey<Level> dimension, final RegistryAccess registryAccess, final Holder<DimensionType> dimensionTypeRegistration, final boolean isClientSide, final boolean isDebug, final long biomeZoomSeed, final int maxChainedNeighborUpdates) {
        super(levelData, dimension, registryAccess, dimensionTypeRegistration, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
    *///?} else {
    protected MixinClientLevel_DontTickSkyBrightness(final WritableLevelData levelData, final ResourceKey<Level> dimension, final RegistryAccess registryAccess, final Holder<DimensionType> dimensionTypeRegistration, final boolean isClientSide, final boolean isDebug, final int maxChainedNeighborUpdates) {
        super(levelData, dimension, registryAccess, dimensionTypeRegistration, isClientSide, isDebug, maxChainedNeighborUpdates);
    //?}
    }

    // In 1.21.2, they fixed (https://mojira.dev/MC-259651), so this reverts that bug fix
    @WrapWithCondition(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;updateSkyBrightness()V"))
    private boolean overflowanimations$disableBrightnessUpdate(final ClientLevel instance) {
        return !OverflowAnimations.isEnabled() || !OverflowAnimationsConfig.instance().other.legacyFogDarkness;
    }
}
