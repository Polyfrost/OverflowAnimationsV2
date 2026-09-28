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

package org.visuals.legacy.animatium.mixins.v1.rendering.states;

//? if 1.21.11 {
/*import net.minecraft.client.renderer.state.SkyRenderState;
*///?} else {
import net.minecraft.client.renderer.state.level.SkyRenderState;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.visuals.legacy.animatium.util.states.SkyUtilityState;

@Mixin(SkyRenderState.class)
public abstract class MixinSkyRenderState implements SkyUtilityState {
    @Unique
    private double animatium$height = 0.0D;

    @Override
    public double animatium$getHorizonHeight() {
        return this.animatium$height;
    }

    @Override
    public void animatium$setHorizonHeight(final double height) {
        this.animatium$height = height;
    }
}
