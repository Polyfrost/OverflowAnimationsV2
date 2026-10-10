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

package org.polyfrost.overflowanimations.mixins.legacy.hud;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.particle.ParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.legacy.hud.VoidFog;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Random;

@Mixin(ClientWorld.class)
public abstract class MixinClientWorld_VoidParticles {
    @WrapOperation(method = "doRandomDisplayTicks", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;randomDisplayTick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/BlockState;Ljava/util/Random;)V"))
    private void overflowanimations$voidParticles(final Block instance, final World world, final BlockPos pos, final BlockState state, final Random random, final Operation<Void> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.voidFog.hasParticles() && instance.getMaterial() == Material.AIR) {
            if (world.random.nextInt(8) > pos.getY() && VoidFog.hasVoidFog(world)) {
                world.addParticle(ParticleType.SUSPENDED_DEPTH, pos.getX() + world.random.nextFloat(), pos.getY() + world.random.nextFloat(), pos.getZ() + world.random.nextFloat(), 0.0, 0.0, 0.0);
            }
        } else {
            original.call(instance, world, pos, state, random);
        }
    }
}
