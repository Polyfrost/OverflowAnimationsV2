package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.OverflowAnimations;
import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderManager.class)
public class RenderManagerMixin {
    @Redirect(method = "renderDebugBoundingBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;drawOutlinedBoundingBox(Lnet/minecraft/util/AxisAlignedBB;IIII)V", ordinal = 1))
    private void cancelRenderGlobalCall(AxisAlignedBB boundingBox, int red, int green, int blue, int alpha) {
        if (!OverflowAnimations.oldAnimationsSettings.enabled || !OldAnimationsSettings.oldDebugHitbox)
            RenderGlobal.drawOutlinedBoundingBox(boundingBox, red, green, blue, alpha);
    }

    @Redirect(method = "renderDebugBoundingBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Tessellator;draw()V"))
    private void cancelTessellatorDraw(Tessellator tessellator) {
        if (!OverflowAnimations.oldAnimationsSettings.enabled || !OldAnimationsSettings.oldDebugHitbox) tessellator.draw();
        else tessellator.getWorldRenderer().finishDrawing();
    }
}
