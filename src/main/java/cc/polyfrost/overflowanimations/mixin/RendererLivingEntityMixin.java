package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.OverflowAnimations;
import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import cc.polyfrost.overflowanimations.handlers.AnimationHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RendererLivingEntity.class)
public abstract class RendererLivingEntityMixin<T extends EntityLivingBase> {
    @Shadow
    protected abstract float getSwingProgress(T livingBase, float partialTickTime);

    @Redirect(method = "doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RendererLivingEntity;getSwingProgress(Lnet/minecraft/entity/EntityLivingBase;F)F"))
    private float redirectSwing(RendererLivingEntity<T> instance, T livingBase, float partialTickTime) {
        final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (OldAnimationsSettings.oldSwordBlock3 && player.capabilities.allowEdit && OverflowAnimations.oldAnimationsSettings.enabled) {
            return AnimationHandler.getInstance().getSwingProgress(partialTickTime);
        } else {
            return getSwingProgress(livingBase, partialTickTime);
        }
    }
}