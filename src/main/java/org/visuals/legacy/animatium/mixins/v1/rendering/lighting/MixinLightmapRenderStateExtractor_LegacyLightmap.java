/**
 * Animatium
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

package org.visuals.legacy.animatium.mixins.v1.rendering.lighting;

//? if >=26.1 {
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightmapRenderStateExtractor;
import net.minecraft.client.renderer.state.LightmapRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.handler.rendering.lighting.lightmap.LegacyLightmapExtractor;
import org.visuals.legacy.animatium.handler.rendering.lighting.lightmap.LegacyLightmapState;
import org.visuals.legacy.animatium.handler.rendering.lighting.lightmap.LightmapStateExtension;
//?}

//? if >=26.1 {
@Mixin(LightmapRenderStateExtractor.class)
public abstract class MixinLightmapRenderStateExtractor_LegacyLightmap {
    @Shadow
    @Final
    private Minecraft minecraft;
//?}

    //? if >=26.1 {
    @Shadow
    private float blockLightFlicker;
    //?}

    //? if >=26.1 {
    @Unique
    private final LegacyLightmapExtractor animatium$extractor = new LegacyLightmapExtractor();
    //?}

    //? if >=26.1 {
    @ModifyExpressionValue(method = "tick", at = @At(value = "CONSTANT", args = "floatValue=0.1"))
    private float animatium$legacyLightmap$changeFlickerDifference(final float original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.legacyLightmap) {
            return 1.0F;
        } else {
            return original;
        }
    }
    //?}

    //? if >=26.1 {
    @Inject(method = "tick", at = @At("TAIL"))
    private void animatium$legacyLightmap$tick(final CallbackInfo ci) {
        this.animatium$extractor.tick(this.blockLightFlicker);
    }
    //?}

//? if >=26.1 {
    @WrapMethod(method = "extract")
    private void animatium$legacyLightmap$extract(final LightmapRenderState renderState, final float tickDelta, final Operation<Void> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.legacyLightmap) {
            final LegacyLightmapState state = new LegacyLightmapState();
            this.animatium$extractor.extract(this.minecraft, state, tickDelta);
            ((LightmapStateExtension) renderState).animatium$setState(state);
        } else {
            original.call(renderState, tickDelta);
        }
    }
}
//?}
