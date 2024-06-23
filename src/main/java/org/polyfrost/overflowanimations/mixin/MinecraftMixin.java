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
import org.polyfrost.overflowanimations.config.MainModSettings;
import org.polyfrost.overflowanimations.hooks.SwingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    //todo: simplify shit

    @Shadow public MovingObjectPosition objectMouseOver;
    @Shadow public EffectRenderer effectRenderer;
    @Shadow public EntityPlayerSP thePlayer;
    @Shadow public WorldClient theWorld;
    @Shadow private int leftClickCounter;
    @Shadow public GameSettings gameSettings;
    @Shadow public EntityRenderer entityRenderer;

    @Inject(
            method = "sendClickBlockToController",
            at = @At(
                    value = "HEAD"
            )
    )
    public void overflowAnimations$blockHitAnimation(boolean leftClick, CallbackInfo ci) {
        if (MainModSettings.INSTANCE.getOldSettings().getOldBlockhitting() && MainModSettings.INSTANCE.getOldSettings().getPunching() && MainModSettings.INSTANCE.getOldSettings().enabled && gameSettings.keyBindUseItem.isKeyDown()) {
            if (leftClickCounter <= 0 && leftClick && objectMouseOver != null && objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
                    && (thePlayer.isUsingItem() || MainModSettings.INSTANCE.getOldSettings().getAdventurePunching())) {
                BlockPos posBlock = objectMouseOver.getBlockPos();
                if (!theWorld.isAirBlock(posBlock)) {
                    if ((thePlayer.isAllowEdit() || MainModSettings.INSTANCE.getOldSettings().getAdventureParticles()) && MainModSettings.INSTANCE.getOldSettings().getPunchingParticles()) {
                        effectRenderer.addBlockHitEffects(posBlock, objectMouseOver.sideHit);
                    }
                    if ((thePlayer.isAllowEdit() || !MainModSettings.INSTANCE.getOldSettings().getAdventureBlockHit())) {
                        SwingHook.swingItem();
                    }
                }
            }
        }
    }

    @Inject(
            method = "clickMouse",
            at = @At(
                    value = "TAIL"
            )
    )
    public void overflowAnimations$fakeMissSwing(CallbackInfo ci) {
        if (MainModSettings.INSTANCE.getOldSettings().getVisualSwing() && MainModSettings.INSTANCE.getOldSettings().enabled && leftClickCounter > 0) {
            if (objectMouseOver != null && objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY &&
                    !objectMouseOver.entityHit.hitByEntity(thePlayer) && objectMouseOver.entityHit instanceof EntityLivingBase) {
                if (thePlayer.fallDistance > 0.0F && !thePlayer.onGround && !thePlayer.isOnLadder() &&
                        !thePlayer.isInWater() && !thePlayer.isPotionActive(Potion.blindness) && thePlayer.ridingEntity == null) {
                    thePlayer.onCriticalHit(objectMouseOver.entityHit);
                }
                if (EnchantmentHelper.getModifierForCreature(thePlayer.getHeldItem(),
                        ((EntityLivingBase)objectMouseOver.entityHit).getCreatureAttribute()) > 0.0F) {
                    thePlayer.onEnchantmentCritical(objectMouseOver.entityHit);
                }
            }
            SwingHook.swingItem();
        }
    }

    @Redirect(
            method = "rightClickMouse",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;getIsHittingBlock()Z"
            )
    )
    public boolean overflowAnimations$enableRightClick(PlayerControllerMP instance) {
        return (!MainModSettings.INSTANCE.getOldSettings().getOldBlockhitting() || !MainModSettings.INSTANCE.getOldSettings().enabled) && instance.getIsHittingBlock();
    }

    //todo: should i keep?
    @Inject(
            method = "runTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/settings/KeyBinding;isPressed()Z",
                    ordinal = 7
            )
    )
    public void overflowAnimations$fakeBlockHit(CallbackInfo ci) {
        if (MainModSettings.INSTANCE.getOldSettings().getFakeBlockHit() && MainModSettings.INSTANCE.getOldSettings().enabled) {
            while (gameSettings.keyBindAttack.isPressed()) {
                SwingHook.swingItem();
            }
        }
    }

    @Inject(
            method = "runTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/entity/EntityPlayerSP;dropOneItem(Z)Lnet/minecraft/entity/item/EntityItem;",
                    shift = At.Shift.AFTER
            )
    )
    public void overflowAnimations$dropItemSwing(CallbackInfo ci) {
        if (MainModSettings.INSTANCE.getOldSettings().getModernDropSwing() && MainModSettings.INSTANCE.getOldSettings().enabled && thePlayer.getHeldItem() != null) {
            SwingHook.swingItem();
        }
    }

    @Inject(method = "rightClickMouse", at = @At(value = "HEAD"))
    public void overflowAnimations$funnyFidgetyThing(CallbackInfo ci) {
        if (MainModSettings.INSTANCE.getOldSettings().getFunnyFidget() && MainModSettings.INSTANCE.getOldSettings().enabled && thePlayer != null && thePlayer.getHeldItem() != null && thePlayer.getHeldItem().getItemUseAction() != EnumAction.NONE) {
            entityRenderer.itemRenderer.resetEquippedProgress();
        }
    }

}
