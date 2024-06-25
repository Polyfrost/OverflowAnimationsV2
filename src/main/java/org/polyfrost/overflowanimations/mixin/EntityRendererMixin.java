package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.renderer.GlStateManager;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.DebugCrosshairHook;
import org.polyfrost.overflowanimations.hooks.DebugOverlayHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import org.polyfrost.overflowanimations.hooks.SmoothSneakHook;
import org.polyfrost.overflowanimations.util.MathUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {

    @Shadow private Minecraft mc;

    @Shadow public abstract void setupOverlayRendering();

    @Unique
    @Final
    private final Minecraft overflowAnimations$mc = Minecraft.getMinecraft();
    @Unique
    private float overflow$height;
    @Unique
    private float overflow$previousHeight;

    @Inject(method = "setupCameraTransform", at = @At("HEAD"))
    protected void overflowAnimations$getInterpolatedEyeHeight(float partialTicks, int pass, CallbackInfo ci) {
        if (!OldAnimationsSettings.INSTANCE.enabled) { return; }
        float interpEyeHeight = MathUtils.interp(overflow$previousHeight, overflow$height, partialTicks);
        SmoothSneakHook.setSneakingHeight(interpEyeHeight);
    }

    @ModifyVariable(method = "orientCamera", at = @At(value = "STORE", ordinal = 0), index = 3)
    public float overflowAnimations$modifyEyeHeight(float eyeHeight) {
        return SmoothSneakHook.getSmoothSneak();
    }

    @ModifyArg(method = "renderWorldDirections", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V"), index = 1)
    public float overflowAnimations$syncCrossHair(float x) {
        return SmoothSneakHook.getSmoothSneak();
    }

    @Inject(method = "renderWorldDirections", at = @At("HEAD"), cancellable = true)
    public void overflowAnimations$renderCrosshair(float partialTicks, CallbackInfo ci) {
        if ((OldAnimationsSettings.INSTANCE.debugCrosshairMode != 1) && OldAnimationsSettings.INSTANCE.enabled)
            ci.cancel();
    }

    @Inject(method = "updateRenderer", at = @At("HEAD"))
    private void overflowAnimations$interpolateHeight(CallbackInfo ci) {
        if (!OldAnimationsSettings.INSTANCE.enabled) { return; }
        Entity entity = overflowAnimations$mc.getRenderViewEntity();
        float eyeHeight = entity.getEyeHeight();
        overflow$previousHeight = overflow$height;

        if (OldAnimationsSettings.longerUnsneak) {
            if (eyeHeight < overflow$height)
                overflow$height = eyeHeight;
            else
                overflow$height += (eyeHeight - overflow$height) * 0.5f;
        } else {
            overflow$height = eyeHeight;
        }
        DebugOverlayHook.setOverflowEyeHeight(overflow$height);
    }

    @Inject(method = "hurtCameraEffect", at = @At("HEAD"), cancellable = true)
    public void overflowAnimations$cancelHurtCamera(float partialTicks, CallbackInfo ci) {
        if (OldAnimationsSettings.noHurtCam && OldAnimationsSettings.INSTANCE.enabled)
            ci.cancel();
    }

    @Redirect(method = "setupViewBobbing", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V", ordinal = 2))
    public void overflowAnimations$modernBobbing(float angle, float x, float y, float z) {
        if (!OldAnimationsSettings.modernBobbing || !OldAnimationsSettings.INSTANCE.enabled) {
            GlStateManager.rotate(angle, x, y, z);
        }
    }

    @Inject(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;renderGameOverlay(F)V"))
    private void draw(float partialTicks, long nanoTime, CallbackInfo ci) {
        if (OldAnimationsSettings.INSTANCE.debugCrosshairMode == 2 &&
                OldAnimationsSettings.INSTANCE.enabled && mc.gameSettings.showDebugInfo && !mc.thePlayer.hasReducedDebug() &&
                !mc.gameSettings.reducedDebugInfo) {
            setupOverlayRendering();
            DebugCrosshairHook.renderDirections(partialTicks, mc);
        }
    }

}
