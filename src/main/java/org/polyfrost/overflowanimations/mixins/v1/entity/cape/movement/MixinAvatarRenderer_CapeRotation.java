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

package org.polyfrost.overflowanimations.mixins.v1.entity.cape.movement;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
//? if <1.21.9 {
/*import net.minecraft.client.player.AbstractClientPlayer;
*///?} else {
import net.minecraft.client.entity.ClientAvatarEntity;
//?}
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
//? if <1.21.9 {
/*import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
*///?} else {
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
//?}
import net.minecraft.util.Mth;
//? if >=1.21.9
import net.minecraft.world.entity.Avatar;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

//? if <1.21.9 {
/*@Mixin(PlayerRenderer.class)
public abstract class MixinAvatarRenderer_CapeRotation extends LivingEntityRenderer<AbstractClientPlayer, PlayerRenderState, PlayerModel> {
*///?} else {
@Mixin(AvatarRenderer.class)
public abstract class MixinAvatarRenderer_CapeRotation<AvatarLikeEntity extends Avatar & ClientAvatarEntity> extends LivingEntityRenderer<AvatarLikeEntity, AvatarRenderState, PlayerModel> {
//?}
    public MixinAvatarRenderer_CapeRotation(final EntityRendererProvider.Context context, final PlayerModel model, final float shadow) {
        super(context, model, shadow);
    }

    @WrapOperation(method = "extractCapeState", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;rotLerp(FFF)F"))
    private static float overflowanimations$changeLerpMethod(final float delta, final float start, final float end, final Operation<Float> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().movement.oldCapeMovement) {
            return Mth.lerp(delta, start, end);
        } else {
            return original.call(delta, start, end);
        }
    }

    @ModifyArg(method = "extractCapeState", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(FFF)F", ordinal = 1), index = 2)
    private static float overflowanimations$uncapRotation(final float original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().movement.disableCapeLean) {
            return Float.MAX_VALUE;
        } else {
            return original;
        }
    }

    //? if <1.21.9 {
    /*@WrapWithCondition(method = "extractCapeState", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;capeLean:F", ordinal = 1, opcode = Opcodes.PUTFIELD))
    private static boolean overflowanimations$dontAssignLeanField(final PlayerRenderState instance, final float value) {
    *///?} else {
    @WrapWithCondition(method = "extractCapeState", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;capeLean:F", ordinal = 1, opcode = Opcodes.PUTFIELD))
    private static boolean overflowanimations$dontAssignLeanField(final AvatarRenderState instance, final float value) {
    //?}
        return !OverflowAnimations.isEnabled() || !OverflowAnimationsConfig.instance().movement.oldCapeMovement;
    }

    //? if <1.21.9 {
    /*@WrapWithCondition(method = "extractCapeState", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;capeLean2:F", ordinal = 1, opcode = Opcodes.PUTFIELD))
    private static boolean overflowanimations$dontAssignLean2Field(final PlayerRenderState instance, final float value) {
    *///?} else {
    @WrapWithCondition(method = "extractCapeState", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;capeLean2:F", ordinal = 1, opcode = Opcodes.PUTFIELD))
    private static boolean overflowanimations$dontAssignLean2Field(final AvatarRenderState instance, final float value) {
    //?}
        return !OverflowAnimations.isEnabled() || !OverflowAnimationsConfig.instance().movement.oldCapeMovement;
    }
}
