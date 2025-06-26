package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumAction;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.SwingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow
    public MovingObjectPosition objectMouseOver;

    @Shadow
    public EffectRenderer effectRenderer;

    @Shadow
    public EntityPlayerSP thePlayer;

    @Shadow
    public WorldClient theWorld;

    @Shadow
    private int leftClickCounter;

    @Shadow
    public GameSettings gameSettings;

    @Shadow
    public EntityRenderer entityRenderer;

    @Shadow
    private static Minecraft theMinecraft;

    @Inject(method = "sendClickBlockToController", at = @At("HEAD"))
    private void overflowAnimations$blockHitAnimation(boolean leftClick, CallbackInfo ci) {
        if (OldAnimationsSettings.INSTANCE.enabled && OldAnimationsSettings.oldBlockhitting && OldAnimationsSettings.punching) {
            if (this.thePlayer != null && !(this.thePlayer.getHeldItem() == null || !this.thePlayer.isUsingItem() || !theMinecraft.gameSettings.keyBindAttack.isKeyDown())) {
                final boolean isBlockObject = this.objectMouseOver != null && this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK;
                if (isBlockObject) {
                    // TODO: Fix adventure, pretty sure I forgot something idk
                    final BlockPos blockPos = this.objectMouseOver.getBlockPos();
                    if ((this.thePlayer.isAllowEdit() || !OldAnimationsSettings.adventureParticles) && OldAnimationsSettings.punchingParticles) {
                        this.effectRenderer.addBlockHitEffects(blockPos, this.objectMouseOver.sideHit);
                    }

                    if ((this.thePlayer.isAllowEdit() || !OldAnimationsSettings.adventureBlockHit)) {
                        SwingHook.swingItem();
                    }
                }
            }
        }
    }

    @Inject(method = "clickMouse", at = @At(value = "TAIL"))
    private void overflowAnimations$onHitParticles(CallbackInfo ci) {
        if (OldAnimationsSettings.visualSwing && OldAnimationsSettings.INSTANCE.enabled && leftClickCounter > 0) {
            if (objectMouseOver != null && objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && !objectMouseOver.entityHit.hitByEntity(thePlayer) && objectMouseOver.entityHit instanceof EntityLivingBase) {
                if (thePlayer.fallDistance > 0.0F &&
                        !thePlayer.onGround &&
                        !thePlayer.isOnLadder() &&
                        !thePlayer.isInWater() &&
                        !thePlayer.isPotionActive(Potion.blindness) &&
                        thePlayer.ridingEntity == null) {
                    thePlayer.onCriticalHit(objectMouseOver.entityHit);
                }

                if (EnchantmentHelper.getModifierForCreature(thePlayer.getHeldItem(), ((EntityLivingBase) objectMouseOver.entityHit).getCreatureAttribute()) > 0.0F) {
                    thePlayer.onEnchantmentCritical(objectMouseOver.entityHit);
                }
            }

            SwingHook.swingItem();
        }
    }

    @Redirect(method = "rightClickMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;getIsHittingBlock()Z"))
    private boolean overflowAnimations$enabledRightClick(PlayerControllerMP instance) {
        return (!(OldAnimationsSettings.oldBlockhitting && OldAnimationsSettings.leftRightClickItemUsage) || !OldAnimationsSettings.INSTANCE.enabled) && instance.getIsHittingBlock();
    }

    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;isPressed()Z", ordinal = 7))
    private void overflowAnimations$fakeBlockHit(CallbackInfo ci) {
        if (OldAnimationsSettings.INSTANCE.enabled && OldAnimationsSettings.fakeBlockHit) {
            while (gameSettings.keyBindAttack.isPressed()) {
                SwingHook.swingItem();
            }
        }
    }

    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;dropOneItem(Z)Lnet/minecraft/entity/item/EntityItem;", shift = At.Shift.AFTER))
    private void overflowAnimations$dropItemSwing(CallbackInfo ci) {
        if (OldAnimationsSettings.INSTANCE.enabled && OldAnimationsSettings.modernDropSwing && thePlayer.getHeldItem() != null) {
            SwingHook.swingItem();
        }
    }

    @Inject(method = "rightClickMouse", at = @At(value = "HEAD"))
    private void overflowAnimations$funnyFidgetyThing(CallbackInfo ci) {
        if (OldAnimationsSettings.INSTANCE.enabled &&
                OldAnimationsSettings.funnyFidget &&
                thePlayer != null &&
                thePlayer.getHeldItem() != null &&
                thePlayer.getHeldItem().getItemUseAction() != EnumAction.NONE) {
            entityRenderer.itemRenderer.resetEquippedProgress();
        }
    }
}
