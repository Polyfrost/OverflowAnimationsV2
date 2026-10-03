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

package org.polyfrost.overflowanimations.mixins.legacy.firstperson;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.ClientPlayerInteractionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleManager;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.network.packet.c2s.play.ArmSwingC2SPacket;
import net.minecraft.world.HitResult;
import org.polyfrost.overflowanimations.config.category.ItemsConfigCategory;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.legacy.firstperson.LegacyFirstPerson;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft_Swinging {
    @Shadow
    public LocalClientPlayerEntity player;

    @Shadow
    public ClientWorld world;

    @Shadow
    public HitResult crosshairTarget;

    @Shadow
    public ParticleManager particleManager;

    @Shadow
    public ClientPlayerInteractionManager interactionManager;

    @Shadow
    public GameRenderer gameRenderer;

    @Shadow
    private int attackCooldown;

    @Unique
    private Block overflowanimations$targetedBlock() {
        if (this.crosshairTarget == null || this.crosshairTarget.type != HitResult.Type.BLOCK) {
            return null;
        }
        final Block block = this.world.getBlockState(this.crosshairTarget.getPos()).getBlock();
        return block.getMaterial() == Material.AIR ? null : block;
    }

    @Inject(method = "handleMouseDown", at = @At("HEAD"))
    private void overflowanimations$usageSwinging(final boolean holdingAttack, final CallbackInfo ci) {
        final ItemsConfigCategory items = LegacyFirstPerson.items();
        if (!items.itemUsageSwinging || !holdingAttack || this.attackCooldown > 0 || !this.player.hasItemInUse()) {
            return;
        }
        final Block block = this.overflowanimations$targetedBlock();
        final boolean adventure = LegacyFirstPerson.cannotMine(this.player, block);
        if (block != null) {
            if (items.usageSwingingParticles && !(adventure && items.disableAdventureUsageParticles)) {
                this.particleManager.addBlockMiningParticles(this.crosshairTarget.getPos(), this.crosshairTarget.face);
            }
        } else if (!items.alwaysUsageSwing) {
            return;
        }
        if (!(adventure && items.disableAdventureUsageSwinging)) {
            LegacyFirstPerson.fakeSwing(this.player);
        }
    }

    @Inject(method = "doAttack", at = @At("HEAD"))
    private void overflowanimations$fakeMissPenaltySwing(final CallbackInfo ci) {
        if (!LegacyFirstPerson.items().fakeMissPenaltySwing || this.attackCooldown <= 0 || this.crosshairTarget == null || !this.interactionManager.hasAttackCooldown()) {
            return;
        }
        if (this.crosshairTarget.type == HitResult.Type.BLOCK && this.overflowanimations$targetedBlock() == null) {
            return;
        }
        LegacyFirstPerson.fakeSwing(this.player);
    }

    @WrapOperation(method = "doAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/living/player/LocalClientPlayerEntity;swingArm()V"))
    private void overflowanimations$disableAdventureSwing(final LocalClientPlayerEntity instance, final Operation<Void> original) {
        final Block block = this.overflowanimations$targetedBlock();
        if (LegacyFirstPerson.items().disableAdventureSwing && block != null && LegacyFirstPerson.cannotMine(instance, block)) {
            instance.networkHandler.sendPacket(new ArmSwingC2SPacket());
        } else {
            original.call(instance);
        }
    }

    @ModifyExpressionValue(method = "doUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/ClientPlayerInteractionManager;isMiningBlock()Z"))
    private boolean overflowanimations$blockHitWhileMining(final boolean original) {
        if (!original || !LegacyFirstPerson.items().blockHitWhileMining) {
            return original;
        }
        final ItemStack stack = this.player.getItemInHand();
        return stack == null || (stack.getUseAction() == UseAction.NONE && !(stack.getItem() instanceof BlockItem));
    }

    @Inject(method = "doUse", at = @At("HEAD"))
    private void overflowanimations$captureUsing(final CallbackInfo ci, @Share("wasUsing") final LocalBooleanRef wasUsing) {
        wasUsing.set(this.player.hasItemInUse());
    }

    @Inject(method = "doUse", at = @At("TAIL"))
    private void overflowanimations$itemUseCooldownAnimation(final CallbackInfo ci, @Share("wasUsing") final LocalBooleanRef wasUsing) {
        if (!OverflowAnimationsConfig.instance().fixes.useEquipAnimation.isLegacy() && !wasUsing.get() && this.player.hasItemInUse()) {
            this.gameRenderer.itemInHandRenderer.onItemUsed();
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/living/player/LocalClientPlayerEntity;dropItem(Z)Lnet/minecraft/entity/ItemEntity;"))
    private void overflowanimations$dropItemSwing(final CallbackInfo ci) {
        if (!LegacyFirstPerson.items().dropSwing.isLegacy() && this.player.getItemInHand() != null) {
            LegacyFirstPerson.fakeSwing(this.player);
        }
    }
}
