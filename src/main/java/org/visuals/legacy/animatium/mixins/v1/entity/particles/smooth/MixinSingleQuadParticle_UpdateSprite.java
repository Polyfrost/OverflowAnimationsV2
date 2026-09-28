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

package org.visuals.legacy.animatium.mixins.v1.entity.particles.smooth;

//? if >=26.1 {
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
//?}

//? if >=26.1 {
@Mixin(SingleQuadParticle.class)
public abstract class MixinSingleQuadParticle_UpdateSprite extends Particle {
    @Shadow
    public abstract void setSpriteFromAge(final SpriteSet sprites);
//?}

    //? if >=26.1 {
    protected MixinSingleQuadParticle_UpdateSprite(final ClientLevel level, final double x, final double y, final double z) {
        super(level, x, y, z);
    }
    //?}

//? if >=26.1 {
    @Inject(method = "extract", at = @At("HEAD"))
    public void animatium$extractSprite(final QuadParticleRenderState quadParticleRenderState, final Camera camera, final float tickDelta, final CallbackInfo ci) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().fixes.smoothParticles && this instanceof SpritesAccessor spritesAccessor) {
            this.setSpriteFromAge(spritesAccessor.animatium$sprites());
        }
    }
}
//?}