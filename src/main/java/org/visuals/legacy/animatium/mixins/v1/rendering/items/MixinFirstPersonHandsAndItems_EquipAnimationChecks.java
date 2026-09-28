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

package org.visuals.legacy.animatium.mixins.v1.rendering.items;

//? if >=26.3 {
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.player.FirstPersonHandsAndItems;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.state.level.FirstPersonHandsAndItemsRenderState;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;
import org.visuals.legacy.animatium.util.duck.FirstPersonHandsAndItemsRenderStateExt;
import org.visuals.legacy.animatium.util.enums.EquipAnimationVersionSetting;
//?}

//? if >=26.3 {
@Mixin(FirstPersonHandsAndItems.class)
public abstract class MixinFirstPersonHandsAndItems_EquipAnimationChecks {
    @Shadow
    private float mainHandHeight;
//?}

    //? if >=26.3 {
    @Shadow
    private ItemStack mainHandItem;
    //?}

    //? if >=26.3 {
    @Unique
    private int animatium$currentSlot = -1;
    //?}

    //? if >=26.3 {
    @Unique
    private ItemStack animatium$mainHandItem = ItemStack.EMPTY;
    //?}

    //? if >=26.3 {
    @ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/FirstPersonHandsAndItems;shouldInstantlyReplaceVisibleItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/player/LocalPlayer;)Z", ordinal = 0))
    private boolean animatium$disableEquipConstraint(final boolean original) {
        return (!Animatium.isEnabled() || !AnimatiumConfig.instance().items.equipAnimationVersion.useStackForRendering()) && original;
    }
    //?}

    //? if >=26.3 {
    @Inject(method = "shouldInstantlyReplaceVisibleItem", at = @At("HEAD"), cancellable = true)
    private void animatium$skipEquipAnimation(final ItemStack currentlyVisibleItem, final ItemStack expectedItem, final LocalPlayer player, final CallbackInfoReturnable<Boolean> cir) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().items.equipAnimationVersion == EquipAnimationVersionSetting.DISABLED && player != null) {
            cir.setReturnValue(true);
        }
    }
    //?}

    //? if >=26.3 {
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isHandsBusy()Z"))
    private boolean animatium$heldItemVisibilityInBoat(final LocalPlayer instance, final Operation<Boolean> original) {
        return (!Animatium.isEnabled() || !AnimatiumConfig.instance().items.heldItemVisibilityInBoat) && original.call(instance);
    }
    //?}

    //? if >=26.3 {
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getItemSwapScale(F)F"))
    private float animatium$legacySwingAnimation(final LocalPlayer instance, final float delta, final Operation<Float> original) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().extras.legacySwingAnimation) {
            return 1.0F;
        } else {
            return original.call(instance, delta);
        }
    }
    //?}

    //? if >=26.3 {
    // Fixes MC-262560
    @ModifyArg(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(FFF)F", ordinal = 2), index = 0)
    private float animatium$handleEquipLogic(final float value, @Local(argsOnly = true, name = "player") final LocalPlayer player, @Local(name = "attackAnim") float attackAnim) {
        final EquipAnimationVersionSetting setting = AnimatiumConfig.instance().items.equipAnimationVersion;
        if (Animatium.isEnabled() && setting != EquipAnimationVersionSetting.VANILLA && setting != EquipAnimationVersionSetting.DISABLED && player != null) {
            final float scale = (float) Math.pow(attackAnim, 3);
            final ItemStack stackCopy = player.getInventory().getSelectedItem().copy();
    //?}

            //? if >=26.3 {
            float mainHandTargetHeight = stackCopy == this.animatium$mainHandItem ? scale : 0;
            if (this.animatium$mainHandItem.isEmpty() && stackCopy.isEmpty()) {
                mainHandTargetHeight = scale;
            }
            //?}

            //? if >=26.3 {
            if (!stackCopy.isEmpty() && !this.animatium$mainHandItem.isEmpty() &&
                    stackCopy != this.animatium$mainHandItem && stackCopy.getItem() == this.animatium$mainHandItem.getItem() &&
                    stackCopy.getDamageValue() == this.animatium$mainHandItem.getDamageValue()) {
                this.animatium$mainHandItem = stackCopy;
                mainHandTargetHeight = scale;
            }
            //?}

            //? if >=26.3 {
            if (setting == EquipAnimationVersionSetting.V1_7 && this.animatium$currentSlot != player.getInventory().getSelectedSlot()) {
                mainHandTargetHeight = 0;
            }
            //?}

    //? if >=26.3 {
            return mainHandTargetHeight - this.mainHandHeight;
        } else {
            return value;
        }
    }
    //?}

    //? if >=26.3 {
    @Inject(method = "tick", at = @At("TAIL"))
    private void animatium$updateFakeItem(final LocalPlayer player, final CallbackInfo ci) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().items.equipAnimationVersion != EquipAnimationVersionSetting.VANILLA && AnimatiumConfig.instance().items.equipAnimationVersion != EquipAnimationVersionSetting.DISABLED && player != null && this.mainHandHeight < 0.1F) {
            this.animatium$mainHandItem = this.mainHandItem.copy();
            this.animatium$currentSlot = player.getInventory().getSelectedSlot();
        }
    }
    //?}

//? if >=26.3 {
    /**
     * @Mixces TODO/NOTE: Should we just override mainHandItem in the vanilla state instead or?
     * Me (lowercasebtw) tried porting what we had prior to the new code, might be possible to cleanup/make better
     */
    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void animatium$injectMainHandItem(final LocalPlayer player, final float partialTicks, final FirstPersonHandsAndItemsRenderState state, final CallbackInfo ci) {
        ((FirstPersonHandsAndItemsRenderStateExt) state).animatium$setMainHandItem(this.animatium$mainHandItem);
    }
}
//?}
