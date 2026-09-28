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
import net.minecraft.client.renderer.state.LightmapRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.visuals.legacy.animatium.handler.rendering.lighting.lightmap.LegacyLightmapState;
import org.visuals.legacy.animatium.handler.rendering.lighting.lightmap.LightmapStateExtension;
//?}

//? if >=26.1 {
@Mixin(LightmapRenderState.class)
public abstract class MixinLightmapRenderState_StoreLegacy implements LightmapStateExtension {
    @Unique
    private LegacyLightmapState animatium$legacyState;
//?}

    //? if >=26.1 {
    @Override
    public LegacyLightmapState animatium$getState() {
        return this.animatium$legacyState;
    }
    //?}

//? if >=26.1 {
    @Override
    public void animatium$setState(final LegacyLightmapState state) {
        this.animatium$legacyState = state;
    }
}
//?}
