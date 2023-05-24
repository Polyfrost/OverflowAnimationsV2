package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.OverflowAnimations;
import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    @Final
    @Unique
    private final Minecraft mc = Minecraft.getMinecraft();
    @Unique
    private float height;
    @Unique
    private float previousHeight;

    @Redirect(method = "orientCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getEyeHeight()F"))
    public float modifyEyeHeight(Entity entity, float partialTicks) {
        return OldAnimationsSettings.smoothSneaking && OverflowAnimations.oldAnimationsSettings.enabled ?
                previousHeight + (height - previousHeight) * partialTicks : entity.getEyeHeight();
    }

    @Redirect(method = "renderWorldDirections", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getEyeHeight()F"))
    public float syncCrossHair(Entity entity, float partialTicks) {
        return modifyEyeHeight(entity, partialTicks);
    }

    @Inject(method = "updateRenderer", at = @At("HEAD"))
    private void interpolateHeight(CallbackInfo ci) {
        Entity entity = mc.getRenderViewEntity();
        float eyeHeight = entity.getEyeHeight();
        previousHeight = height;
        if (eyeHeight < height)
            height = eyeHeight;
        else
            height += (eyeHeight - height) * 0.5f;
        OverflowAnimations.eyeHeight = height;
    }

    @Inject(method = "renderWorldDirections", at = {@At("HEAD")}, cancellable = true)
    public void renderCrosshair(float partialTicks, CallbackInfo ci) {
        if (OldAnimationsSettings.oldDebugCrosshair && OverflowAnimations.oldAnimationsSettings.enabled) {
            ci.cancel();
        }
    }
}
