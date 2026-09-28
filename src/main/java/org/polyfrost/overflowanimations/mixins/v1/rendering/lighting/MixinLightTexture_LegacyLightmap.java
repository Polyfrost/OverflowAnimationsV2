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

package org.polyfrost.overflowanimations.mixins.v1.rendering.lighting;

//? if 1.21.11 {
/*import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.textures.GpuTextureView;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.handler.rendering.lighting.lightmap.LegacyLightmapExtractor;
import org.polyfrost.overflowanimations.handler.rendering.lighting.lightmap.LegacyLightmapRenderer;
import org.polyfrost.overflowanimations.handler.rendering.lighting.lightmap.LegacyLightmapState;
*///?}

//? if 1.21.11 {
/*@Mixin(LightTexture.class)
public abstract class MixinLightTexture_LegacyLightmap {
    @Shadow
    @Final
    private GpuTextureView textureView;
*///?}

    //? if 1.21.11 {
    /*@Shadow
    @Final
    private Minecraft minecraft;
    *///?}

    //? if 1.21.11 {
    /*@Shadow
    private float blockLightRedFlicker;
    *///?}

    //? if 1.21.11 {
    /*@Unique
    private final LegacyLightmapExtractor overflowanimations$extractor = new LegacyLightmapExtractor();
    *///?}

    //? if 1.21.11 {
    /*@Unique
    private final LegacyLightmapRenderer overflowanimations$renderer = new LegacyLightmapRenderer();
    *///?}

    //? if 1.21.11 {
    /*@ModifyExpressionValue(method = "tick", at = @At(value = "CONSTANT", args = "floatValue=0.1"))
    private float overflowanimations$legacyLightmap$changeFlickerDifference(final float original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.legacyLightmap) {
            return 1.0F;
        } else {
            return original;
        }
    }
    *///?}

    //? if 1.21.11 {
    /*@Inject(method = "tick", at = @At("TAIL"))
    private void overflowanimations$legacyLightmap$tick(final CallbackInfo ci) {
        this.overflowanimations$extractor.tick(this.blockLightRedFlicker);
    }
    *///?}

    //? if 1.21.11 {
    /*@WrapMethod(method = "updateLightTexture")
    private void overflowanimations$legacyLightmap(final float tickDelta, final Operation<Void> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.legacyLightmap) {
            final LegacyLightmapState state = new LegacyLightmapState();
            this.overflowanimations$extractor.extract(this.minecraft, state, tickDelta);
            this.overflowanimations$renderer.render(state, this.textureView);
        } else {
            original.call(tickDelta);
        }
    }
    *///?}

//? if 1.21.11 {
    /*@Inject(method = "close", at = @At("TAIL"))
    private void overflowanimations$legacyLightmap$close(final CallbackInfo ci) {
        this.overflowanimations$renderer.close();
    }
}
*///?}
