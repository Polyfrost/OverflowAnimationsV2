package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityOtherPlayerMP.class, priority = 980)
public abstract class EntityOtherPlayerMPMixin extends EntityLivingBaseMixin {
    public EntityOtherPlayerMPMixin(World worldIn) {
        super(worldIn);
    }

    @Inject(method = "onLivingUpdate", at = @At("HEAD"))
    public void overflowAnimations$updateHeadYaw(CallbackInfo ci) {
        if (!OldAnimationsSettings.INSTANCE.enabled || !OldAnimationsSettings.headYawFix) return;
        if (overflowAnimations$headYawLerpWeight <= 0) return;
        rotationYawHead += MathHelper.wrapAngleTo180_float(overflowAnimations$newHeadYaw - rotationYawHead) / overflowAnimations$headYawLerpWeight;
        rotationYawHead = MathHelper.wrapAngleTo180_float(rotationYawHead);
        overflowAnimations$headYawLerpWeight--;
    }
}