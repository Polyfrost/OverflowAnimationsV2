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

package org.polyfrost.overflowanimations.mixins.v1.general.network;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;

@Mixin(MultiPlayerGameMode.class)
public abstract class MixinMultiPlayerGameMode {
    @Shadow
    private float destroyProgress;

    @Shadow
    private GameType localPlayerMode;

    @Inject(method = "getDestroyStage", at = @At(value = "RETURN"), cancellable = true)
    private void overflowanimations$blockMiningProgress(final CallbackInfoReturnable<Integer> cir) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().other.blockMiningProgress.isLegacy()) {
            cir.setReturnValue(((int) (this.destroyProgress * 10.0F)) - 1);
        }
    }

    @WrapOperation(method = "performUseItemOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;"))
    private InteractionResult overflowanimations$fixFireballClientsideVisual(final ItemStack instance, final UseOnContext context, final Operation<InteractionResult> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().fixes.fixFireballClientsideVisual && !instance.isEmpty() && instance.is(Items.FIRE_CHARGE) && context.getLevel().isClientSide() && !this.localPlayerMode.isCreative()) {
            return InteractionResult.SUCCESS;
        } else {
            return original.call(instance, context);
        }
    }
}
