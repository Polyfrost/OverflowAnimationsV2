package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSnowball.class)
public abstract class RenderSnowballMixin_CustomPositions<T extends Entity> extends Render<T> {
    public RenderSnowballMixin_CustomPositions(RenderManager renderManager) {
        super(renderManager);
    }


    @Inject(method = "doRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderSnowball;bindTexture(Lnet/minecraft/util/ResourceLocation;)V", shift = At.Shift.AFTER))
    public void projectileTransforms(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        OldAnimationsSettings settings = OldAnimationsSettings.INSTANCE;
        if (settings.enabled) {
            GlStateManager.translate(
                    settings.projectilePositionX,
                    settings.projectilePositionY,
                    settings.projectilePositionZ
            );
            GlStateManager.rotate(settings.projectileRotationPitch, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(settings.projectileRotationYaw, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(settings.projectileRotationRoll, 0.0f, 0.0f, 1.0f);
            GlStateManager.scale(
                    1.0f * Math.exp(settings.projectileScale),
                    1.0f * Math.exp(settings.projectileScale),
                    1.0f * Math.exp(settings.projectileScale)
            );
        }
    }

}
