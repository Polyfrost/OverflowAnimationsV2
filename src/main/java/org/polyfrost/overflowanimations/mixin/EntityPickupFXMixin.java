package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.particle.EntityPickupFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import org.objectweb.asm.Opcodes;
import org.polyfrost.overflowanimations.config.MainModSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPickupFX.class)
public class EntityPickupFXMixin {

    //todo: mixcesAnimations$factorInEyeHeight

    @Shadow private float field_174841_aA;
    @Shadow private Entity field_174843_ax;

    @Inject(
            method = "renderParticle",
            at = @At(
                    value = "FIELD",
                    opcode = Opcodes.GETFIELD,
                    target = "Lnet/minecraft/client/particle/EntityPickupFX;field_174841_aA:F"
            )
    )
    private void mixcesAnimations$factorInEyeHeight(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, CallbackInfo ci) {
        if (!MainModSettings.INSTANCE.getOldSettings().enabled) { return; }
        field_174841_aA = (field_174843_ax.getEyeHeight() / 2) + MainModSettings.INSTANCE.getOldSettings().getPickupPosition();
    }

    @Inject(
            method = "renderParticle",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void overflowAnimations$disableCollectParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, CallbackInfo ci) {
        if (!MainModSettings.INSTANCE.getOldSettings().getDisablePickup() || !MainModSettings.INSTANCE.getOldSettings().enabled) { return; }
        ci.cancel();
    }

}
