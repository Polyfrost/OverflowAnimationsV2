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

package org.polyfrost.overflowanimations.mixins.v1.rendering.items.usage;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
//? if <26.3 {
/*import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemInHandRenderer;
*///?}
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
//? if >=26.3 {
import net.minecraft.world.item.component.SwingAnimation;
//?}
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.util.EntityUtilKt;
import org.polyfrost.overflowanimations.util.ItemUtilKt;
import org.polyfrost.overflowanimations.util.SwingUtilKt;
import org.polyfrost.overflowanimations.util.enums.EquipAnimationVersionSetting;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft_EquipUseLogic {
    @Shadow
    @Nullable
    public LocalPlayer player;

    @Shadow
    @Final
    public Options options;

    @Shadow
    @Nullable
    public HitResult hitResult;

    @Shadow
    @Nullable
    public ClientLevel level;

    @Shadow
    @Nullable
    public MultiPlayerGameMode gameMode;

    //? if <26.3 {
    /*@Shadow
    @Final
    public GameRenderer gameRenderer;
    *///?}

    //? if <26.1 {
    /*@ModifyVariable(method = "startUseItem", at = @At("STORE"), ordinal = 0)
    *///?} else {
    @ModifyVariable(method = "startUseItem", at = @At("STORE"), name = "heldItem")
    //?}
    private ItemStack overflowanimations$fixCopyStackUseItem(final ItemStack heldItem) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.equipAnimationVersion != EquipAnimationVersionSetting.VANILLA) {
            // Update the stack to match mutations to the stack in other classes
            return heldItem.copy();
        } else {
            return heldItem;
        }
    }

    //? if <26.3 {
    /*@WrapOperation(method = "startUseItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;swing(Lnet/minecraft/world/InteractionHand;)V", ordinal = 2))
    *///?}
    //? if <26.1 {
    /*private void overflowanimations$swingOnUse(final LocalPlayer instance, final InteractionHand hand, final Operation<Void> original, @Local(ordinal = 0) final ItemStack heldItem) {
    *///?} elif >=26.1 <26.3 {
    /*private void overflowanimations$swingOnUse(final LocalPlayer instance, final InteractionHand hand, final Operation<Void> original, @Local(name = "heldItem") final ItemStack heldItem) {
    *///?} else {
    @WrapOperation(method = "startUseItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;swing(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/component/SwingAnimation;Z)Z", ordinal = 2))
    private boolean overflowanimations$swingOnUse(final LocalPlayer instance, final InteractionHand hand, final SwingAnimation animation, final boolean sendToSwingingEntity, final Operation<Boolean> original, @Local(name = "heldItem") final ItemStack heldItem) {
    //?}
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.disableSwingOnUse && ItemUtilKt.isSwingItemBlacklisted(heldItem)) {
            //? if <26.3 {
            /*SwingUtilKt.sendSwingPacket(instance, hand);
            *///?} else {
            return SwingUtilKt.sendSwingPacket(instance, hand, animation);
            //?}
        } else {
            //? if <26.3 {
            /*original.call(instance, hand);
            *///?} else {
            return original.call(instance, hand, animation, sendToSwingingEntity);
            //?}
        }
    }

    //? if <26.3 {
    /*@WrapOperation(method = "handleKeybinds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;swing(Lnet/minecraft/world/InteractionHand;)V"))
    private void overflowanimations$swingOnDrop(final LocalPlayer instance, final InteractionHand hand, final Operation<Void> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.disableSwingOnDrop) {
            SwingUtilKt.sendSwingPacket(instance, hand);
    *///?} else {
    @WrapOperation(method = "startUseItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;swing(Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/component/SwingAnimation;Z)Z", ordinal = 0))
    private boolean overflowanimations$swingOnEntityInteract(final LocalPlayer instance, final InteractionHand hand, final SwingAnimation animation, final boolean sendToSwingingEntity, final Operation<Boolean> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.disableSwingOnEntityInteract) {
            return SwingUtilKt.sendSwingPacket(instance, hand, animation);
    //?}
        } else {
    //? if <26.3 {
            /*original.call(instance, hand);
        }
    }
    *///?}

    //? if <26.3 {
    /*@WrapOperation(method = "startUseItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;swing(Lnet/minecraft/world/InteractionHand;)V", ordinal = 0))
    private void overflowanimations$swingOnEntityInteract(final LocalPlayer instance, final InteractionHand hand, final Operation<Void> original) {
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().items.disableSwingOnEntityInteract) {
            SwingUtilKt.sendSwingPacket(instance, hand);
        } else {
            original.call(instance, hand);
    *///?} else {
            return original.call(instance, hand, animation, sendToSwingingEntity);
    //?}
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void overflowanimations$applySwingWhilstMining(final CallbackInfo ci) {
        if (OverflowAnimations.isEnabled()
                && OverflowAnimationsConfig.instance().items.itemUsageSwinging
                && this.player != null
                && !(this.player.getItemInHand(this.player.getUsedItemHand()).isEmpty() || !this.player.isUsingItem() || !this.options.keyAttack.isDown())) {
            EntityUtilKt.applySwingWhilstMining(this.level, this.player, this.hitResult);
        }
    }

    //? if <26.3 {
    /*@WrapWithCondition(method = "startUseItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;itemUsed(Lnet/minecraft/world/InteractionHand;)V"))
    private boolean overflowanimations$equipAnimationOnItemUse(final ItemInHandRenderer instance, final InteractionHand hand) {
    *///?} else {
    @WrapWithCondition(method = "startUseItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;itemUsed(Lnet/minecraft/world/InteractionHand;)V"))
    private boolean overflowanimations$equipAnimationOnItemUse(final LocalPlayer instance, final InteractionHand hand) {
    //?}
        // TODO: This fixes projectile equip, but it isn't going to be 100% accurate in some other areas. This needs to be worked on :)
        if (OverflowAnimations.isEnabled() && OverflowAnimationsConfig.instance().fixes.fixEquipAnimationOnItemUse) {
            // The equip animation plays when right-clicking blocks in creative mode in <1.8.x
            final boolean isAimedAtBlock = this.hitResult != null && this.hitResult.getType() == HitResult.Type.BLOCK;
            // This might need to be revamped a bit. We are already checking for creative mode in the actual method,
            // however this seems to narrow things down
            final boolean isInCreative = this.gameMode != null && this.gameMode.getPlayerMode().isCreative();
            return isAimedAtBlock && isInCreative;
        } else {
            return true;
        }
    }

    //? if <26.1 {
    /*@Definition(id = "useResult", local = @Local(type = InteractionResult.class, ordinal = 1))
    *///?} else {
    @Definition(id = "useResult", local = @Local(type = InteractionResult.class, name = "useResult"))
    //?}
    @Definition(id = "Fail", type = InteractionResult.Fail.class)
    @Expression("useResult instanceof Fail")
    @Inject(method = "startUseItem", at = @At(value = "MIXINEXTRAS:EXPRESSION", shift = At.Shift.BEFORE))
    //? if <26.1 {
    /*private void overflowanimations$oldEquipUse(final CallbackInfo ci, @Local(ordinal = 0) final ItemStack heldItem, @Local(ordinal = 0) final int oldCount, @Local(ordinal = 0) final InteractionHand hand) {
    *///?} else {
    private void overflowanimations$oldEquipUse(final CallbackInfo ci, @Local(name = "heldItem") final ItemStack heldItem, @Local(name = "oldCount") final int oldCount, @Local(name = "hand") final InteractionHand hand) {
    //?}
        if (OverflowAnimations.isEnabled()
                && OverflowAnimationsConfig.instance().items.equipAnimationVersion != EquipAnimationVersionSetting.VANILLA
                && !heldItem.isEmpty()
                && this.player != null
                && (heldItem.getCount() != oldCount || this.player.hasInfiniteMaterials())) {
            //? if <26.3 {
            /*this.gameRenderer.itemInHandRenderer.itemUsed(hand);
            *///?} else {
            this.player.itemUsed(hand);
            //?}
        }
    }
}
