package org.polyfrost.overflowanimations.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.polyfrost.overflowanimations.config.MainModSettings;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
        value = EntityLivingBase.class,
        priority = 980
)
public abstract class EntityLivingBaseMixin extends Entity {

    //todo: overflowAnimations$modifySwingSpeed and overflowAnimations$modifyYaw

    public EntityLivingBaseMixin(World worldIn) {
        super(worldIn);
    }

    @Shadow public abstract boolean isPotionActive(Potion potionIn);
    @Shadow public abstract PotionEffect getActivePotionEffect(Potion potionIn);
    @Shadow public float swingProgress;
    @Shadow public float renderYawOffset;
    @Shadow public float rotationYawHead;
    @Unique protected float overflowAnimations$newHeadYaw;
    @Unique protected int overflowAnimations$headYawLerpWeight;

    @Inject(
            method = "setRotationYawHead",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    public void overflowAnimations$setAsNewHeadYaw(float rotation, CallbackInfo ci) {
        if (!MainModSettings.INSTANCE.getOldSettings().enabled || !MainModSettings.INSTANCE.getOldSettings().getHeadYawFix()) { return; }
        ci.cancel();
        overflowAnimations$newHeadYaw = MathHelper.wrapAngleTo180_float(rotation);
        overflowAnimations$headYawLerpWeight = 3;
    }

    @Inject(
            method = "onLivingUpdate",
            at = @At(
                    value = "HEAD"
            )
    )
    public void overflowAnimations$updateHeadYaw(CallbackInfo ci) {
        if (!MainModSettings.INSTANCE.getOldSettings().enabled || !MainModSettings.INSTANCE.getOldSettings().getHeadYawFix()) { return; }
        if (overflowAnimations$headYawLerpWeight <= 0) { return; }
        rotationYawHead += MathHelper.wrapAngleTo180_float(overflowAnimations$newHeadYaw - rotationYawHead) / overflowAnimations$headYawLerpWeight;
        rotationYawHead = MathHelper.wrapAngleTo180_float(rotationYawHead);
        overflowAnimations$headYawLerpWeight--;
    }

    @Inject(
            method = "getArmSwingAnimationEnd()I",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    public void overflowAnimations$modifySwingSpeed(CallbackInfoReturnable<Integer> cir) {
        OldAnimationsSettings settings = MainModSettings.INSTANCE.getOldSettings();
        if (!settings.getGlobalPositions() || !settings.enabled) { return; }
        if (isPotionActive(Potion.digSpeed) && !settings.getIgnoreHaste()) {
            cir.setReturnValue((6 - (1 + getActivePotionEffect(Potion.digSpeed).getAmplifier())) * Math.max((int) Math.exp(-settings.getItemSwingSpeedHaste()), 1));
        } else if (isPotionActive(Potion.digSlowdown) && !settings.getIgnoreFatigue()) {
            cir.setReturnValue((6 + (1 + getActivePotionEffect(Potion.digSlowdown).getAmplifier())) * 2 * Math.max((int) Math.exp(-settings.getItemSwingSpeedFatigue()), 1));
        } else {
            cir.setReturnValue(6 * Math.max((int) Math.exp(-settings.getItemSwingSpeed()), 1));
        }
    }

    @ModifyArg(
            method = "onUpdate",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/EntityLivingBase;updateDistance(FF)F"
            ),
            index = 0
    )
    public float overflowAnimations$modifyYaw(float p_1101461) {
        if (!MainModSettings.INSTANCE.getOldSettings().getModernMovement()) { return p_1101461; }
        double d0 = posX - prevPosX;
        double d1 = posZ - prevPosZ;
        float f = (float)(d0 * d0 + d1 * d1);
        float h = renderYawOffset;
        if (f > 0.0025000002F) {
            float f1 = (float) MathHelper.atan2(d1, d0) * 180.0F / 3.1415927F - 90.0F;
            float g = MathHelper.abs(MathHelper.wrapAngleTo180_float(rotationYaw) - f1);
            h = f1 - (95.0F < g && g < 265.0F ? 180.0F : 0.0F);
        }
        if (swingProgress > 0.0F) {
            h = rotationYaw;
        }
        return h;
    }

}
