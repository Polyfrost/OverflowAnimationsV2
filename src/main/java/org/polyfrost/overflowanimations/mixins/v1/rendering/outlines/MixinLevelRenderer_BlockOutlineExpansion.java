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

package org.polyfrost.overflowanimations.mixins.v1.rendering.outlines;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.LevelRenderer;
//? if <1.21.9 {
/*import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
*///?} elif <26.1 {
/*import net.minecraft.client.renderer.state.BlockOutlineRenderState;
*///?} else {
import net.minecraft.client.renderer.state.level.BlockOutlineRenderState;
//?}
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.util.UtilsKt;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer_BlockOutlineExpansion {
    //? if <1.21.9 {
    /*@WrapOperation(method = "renderHitOutline", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;"))
    private VoxelShape overflowanimations$blockOutlineRendering(final BlockState instance, final BlockGetter level, final BlockPos pos, final CollisionContext context, final Operation<VoxelShape> original) {
        final VoxelShape shape = original.call(instance, level, pos, context);
    *///?} elif <26.1 {
    /*@WrapOperation(method = "renderHitOutline", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/state/BlockOutlineRenderState;shape()Lnet/minecraft/world/phys/shapes/VoxelShape;"))
    *///?} elif 26.1 {
    /*@WrapOperation(method = "renderHitOutline", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/state/level/BlockOutlineRenderState;shape()Lnet/minecraft/world/phys/shapes/VoxelShape;"))
    *///?} else {
    @WrapOperation(method = "submitHitOutline", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/state/level/BlockOutlineRenderState;shape()Lnet/minecraft/world/phys/shapes/VoxelShape;"))
    //?}
    //? if >=1.21.9 {
    private VoxelShape overflowanimations$blockOutlineRendering(final BlockOutlineRenderState instance, final Operation<VoxelShape> original) {
        final VoxelShape shape = original.call(instance);
    //?}
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.blockOutlineRendering) {
            return UtilsKt.expandVoxelShape(shape, 0.0020000000949949026D); // Value sourced from older minecraft version
        } else {
            return shape;
        }
    }
}