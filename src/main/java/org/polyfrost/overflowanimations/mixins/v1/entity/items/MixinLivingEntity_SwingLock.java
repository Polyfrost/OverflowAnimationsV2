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

//? if >=1.21.11 <26.3 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SwingAnimation;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.util.duck.SwingLockExt;
import org.polyfrost.overflowanimations.util.enums.SwapSwingAnimationSetting;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity_SwingLock implements SwingLockExt {
    @Shadow
    public boolean swinging;

    @Unique
    private SwingAnimation overflowanimations$lockedSwing;

    @Inject(method = "swing(Lnet/minecraft/world/InteractionHand;Z)V", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/LivingEntity;swingingArm:Lnet/minecraft/world/InteractionHand;", opcode = Opcodes.PUTFIELD))
    private void overflowanimations$lockSwingAnimation(final InteractionHand hand, final boolean sendToSwingingEntity, final CallbackInfo ci) {
        this.overflowanimations$lockSwing(hand);
    }

    @Override
    public void overflowanimations$lockSwing(final @NotNull InteractionHand hand) {
        this.overflowanimations$lockedSwing = ((LivingEntity) (Object) this).getItemInHand(hand).getSwingAnimation();
    }

    @WrapOperation(method = "getCurrentSwingDuration", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getSwingAnimation()Lnet/minecraft/world/item/component/SwingAnimation;"))
    private SwingAnimation overflowanimations$lockedSwingDuration(final ItemStack instance, final Operation<SwingAnimation> original) {
        return this.overflowanimations$swingAnimation(original.call(instance));
    }

    @Override
    public @NotNull SwingAnimation overflowanimations$swingAnimation(final @NotNull SwingAnimation held) {
        final boolean locked = this.swinging
                && this.overflowanimations$lockedSwing != null
                && ((LivingEntity) (Object) this).level().isClientSide()
                && OverflowAnimations.isEnabled()
                && OverflowAnimationsConfig.instance().items.swapSwingAnimation == SwapSwingAnimationSetting.V26_3;
        return locked ? this.overflowanimations$lockedSwing : held;
    }
}
*///?}
