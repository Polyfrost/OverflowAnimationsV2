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

package org.visuals.legacy.animatium.mixins.v1.entity.particles;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.ARGB;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.util.EntityUtilKt;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity_Particles extends Entity {
    @Shadow
    @Final
    private Map<Holder<MobEffect>, MobEffectInstance> activeEffects;

    public MixinLivingEntity_Particles(final EntityType<?> entityType, final Level level) {
        super(entityType, level);
    }

    @WrapWithCondition(method = "tickEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    private boolean animatium$hideFirstPersonParticles(final Level instance, final ParticleOptions particle, final double x, final double y, final double z, final double xd, final double yd, final double zd) {
        final Minecraft client = Minecraft.getInstance();
        return !Animatium.isEnabled() || !AnimatiumConfig.instance().extras.disableFirstPersonParticles || !EntityUtilKt.isSelf(this) || !client.options.getCameraType().isFirstPerson();
    }

    @WrapOperation(method = "tickEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    //? if 1.21.11 {
    /*private void animatium$blendPotionParticleColors(final Level instance, final ParticleOptions particle, final double x, final double y, final double z, final double velocityX, final double velocityY, final double velocityZ, final Operation<Void> original, @Local(ordinal = 0) final boolean hasAmbience) {
    *///?} else {
    private void animatium$blendPotionParticleColors(final Level instance, final ParticleOptions particle, final double x, final double y, final double z, final double velocityX, final double velocityY, final double velocityZ, final Operation<Void> original, @Local(name = "isAmbient") final boolean hasAmbience) {
    //?}
        ParticleOptions options = particle;
        double red = velocityX;
        double green = velocityY;
        double blue = velocityZ;
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.restoreParticleBlending) {
            int color;
            if (this.activeEffects.isEmpty()) {
                color = 0xFF385DC6;
            } else {
                color = PotionContents.getColorOptional(this.activeEffects.values()).orElse(0);
            }

            if (color == 0) {
                return; // No potion particles are visible
            }

            options = ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, ARGB.color(hasAmbience ? 0.15F : 1.0F, color));
            red = ARGB.redFloat(color);
            green = ARGB.greenFloat(color);
            blue = ARGB.blueFloat(color);
        }

        original.call(instance, options, x, y, z, red, green, blue);
    }
}

