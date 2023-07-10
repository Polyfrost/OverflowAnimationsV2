package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import cc.polyfrost.overflowanimations.hooks.EyeHeightHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityRenderer.class, priority = 20000)
public class EntityRendererMixin {

    @Shadow
    private Minecraft mc;
    @Unique
    private float overflow$height;
    @Unique
    private float overflow$previousHeight;

    @Redirect(method = "orientCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getEyeHeight()F"))
    public float modifyEyeHeight(Entity entity, float partialTicks) {
        EyeHeightHook.getEyeHeight2 = overflow$previousHeight + (overflow$height - overflow$previousHeight) * partialTicks;
        return OldAnimationsSettings.smoothSneaking && OldAnimationsSettings.INSTANCE.enabled ? EyeHeightHook.getEyeHeight2 : entity.getEyeHeight();
    }

    @Redirect(method = "renderWorldDirections", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getEyeHeight()F"))
    public float syncCrossHair(Entity entity, float partialTicks) {
        return OldAnimationsSettings.smoothSneaking && OldAnimationsSettings.INSTANCE.enabled ? EyeHeightHook.getEyeHeight2 : entity.getEyeHeight();
    }

    @Inject(method = "updateRenderer", at = @At("HEAD"))
    private void getSneakInterpolation(CallbackInfo ci) {
        overflow$previousHeight = overflow$height;
        Entity entity = mc.getRenderViewEntity();
        float getEyeHeight1 = mc.thePlayer.isPlayerSleeping() ? 0.2F : entity.isSneaking() ? 1.54F : 1.62F;

        if (getEyeHeight1 < overflow$height)
            overflow$height = getEyeHeight1;
        else
            overflow$height += (getEyeHeight1 - overflow$height) * 0.5F;
    }

    @Inject(method = "renderWorldDirections", at = {@At("HEAD")}, cancellable = true)
    public void renderCrosshair(float partialTicks, CallbackInfo ci) {
        if (OldAnimationsSettings.oldDebugCrosshair && OldAnimationsSettings.INSTANCE.enabled)
            ci.cancel();
    }

    @Inject(method = "hurtCameraEffect", at = @At("HEAD"), cancellable = true)
    public void cancelHurtCamera(float partialTicks, CallbackInfo ci) {
        if (OldAnimationsSettings.noHurtCam && OldAnimationsSettings.INSTANCE.enabled)
            ci.cancel();
    }
}
