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

package org.polyfrost.overflowanimations.mixins.v1.entity.particles.smooth;

//? if >=26.1 {
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleResources;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
//?}

//? if >=26.1 {
import java.util.List;
//?}

//? if >=26.1 {
@Mixin(ParticleResources.MutableSpriteSet.class)
public abstract class MixinParticleResources$MutableSpriteSet_Interpolate {
    @Shadow
    private List<TextureAtlasSprite> sprites;
//?}

//? if >=26.1 {
    @WrapMethod(method = "get(II)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;")
    private TextureAtlasSprite overflowanimations$interpolateSpriteIndex(final int age, final int lifetime, final Operation<TextureAtlasSprite> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().fixes.smoothParticles) {
            final int frames = this.sprites.size() - 1;
            final float tickDelta = Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false); // TODO/NOTE: Find better way to do this?
            return this.sprites.get(Mth.clamp((int) ((age + tickDelta) * ((float) frames) / lifetime), 0, frames)); // TODO/NOTE: Why do I need to clamp? Figure it out.
        } else {
            return original.call(age, lifetime);
        }
    }
}
//?}
