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

package org.polyfrost.overflowanimations.mixins.v1.entity.items;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.LivingEntity;
//? if >=26.3 {
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SwingAnimation;
//?}
import org.spongepowered.asm.mixin.Mixin;
//? if >=26.3 {
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.mixins.accessor.LivingEntityAccessor;
import org.polyfrost.overflowanimations.mixins.accessor.LivingEntity_SwingStateAccessor;
import org.polyfrost.overflowanimations.util.enums.SwapSwingAnimationSetting;
//?}
import org.polyfrost.overflowanimations.util.SwingUtilKt;
//? if >=26.3 {
import java.util.function.Function;
//?}

// TODO/FIX: Should not affect swing code, only visual, currently matches Legacy Animatium tho
@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity_ItemSwing {
    //? if <26.3 {
    /*@WrapMethod(method = "getCurrentSwingDuration")
    private int overflowanimations$customSwingAnimationSpeed(final Operation<Integer> original) {
        return SwingUtilKt.getItemSwingSpeed((LivingEntity) (Object) this, original.call());
    *///?} else {
    @WrapMethod(method = "getModifiedSwingDuration")
    private int overflowanimations$customSwingAnimationSpeed(final SwingAnimation animation, final Operation<Integer> original) {
        return SwingUtilKt.getItemSwingSpeed((LivingEntity) (Object) this, animation, original.call(animation));
    //?}
    }

    //? if >=26.3 {
    @Unique
    private LivingEntity.SwingDescription overflowanimations$trackedSwing;

    @Unique
    private Function<ItemStack, SwingAnimation> overflowanimations$swingSource;

    @Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity$SwingState;tick()V"))
    private void overflowanimations$swapSwingAnimation(final CallbackInfo ci) {
        final LivingEntity self = (LivingEntity) (Object) this;
        if (!self.level().isClientSide() || !OverflowAnimations.isEnabled() || OverflowAnimationsConfig.instance().items.swapSwingAnimation != SwapSwingAnimationSetting.V26_2) {
            return;
        }

        final LivingEntity_SwingStateAccessor swingState = (LivingEntity_SwingStateAccessor) ((LivingEntityAccessor) this).overflowanimations$getSwingState();
        final LivingEntity.SwingDescription swing = swingState.overflowanimations$getCurrentSwing();
        if (swing == null) {
            return;
        }

        final ItemStack stack = self.getItemInHand(swing.hand());
        if (swing != this.overflowanimations$trackedSwing) {
            this.overflowanimations$trackedSwing = swing;
            this.overflowanimations$swingSource = swing.animation().equals(stack.getAttackAnimation()) ? ItemStack::getAttackAnimation
                    : swing.animation().equals(stack.getInteractAnimation()) ? ItemStack::getInteractAnimation
                    : null;
        }

        if (this.overflowanimations$swingSource == null) {
            return;
        }

        final SwingAnimation animation = this.overflowanimations$swingSource.apply(stack);
        if (!animation.equals(swing.animation())) {
            this.overflowanimations$trackedSwing = new LivingEntity.SwingDescription(swing.hand(), animation, ((LivingEntityAccessor) this).overflowanimations$getModifiedSwingDuration(animation));
            swingState.overflowanimations$setCurrentSwing(this.overflowanimations$trackedSwing);
        }
    }
    //?}
}
