package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import org.polyfrost.overflowanimations.config.ItemPositionSettings;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.config.MainModSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSnowball.class)
public abstract class RenderSnowballMixin_CustomPositions<T extends Entity> extends Render<T> {

    //todo: fucked

    public RenderSnowballMixin_CustomPositions(RenderManager renderManager) {
        super(renderManager);
    }


    @Inject(method = "doRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderSnowball;bindTexture(Lnet/minecraft/util/ResourceLocation;)V", shift = At.Shift.AFTER))
    public void overflowAnimations$projectileTransforms(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        OldAnimationsSettings settings = MainModSettings.INSTANCE.getOldSettings();
        ItemPositionSettings advanced = MainModSettings.INSTANCE.getPositionSettings();
        if (settings.getGlobalPositions() && settings.enabled) {
            GlStateManager.translate(
                    advanced.getProjectilePositionX(),
                    advanced.getProjectilePositionY(),
                    advanced.getProjectilePositionZ()
            );
            GlStateManager.rotate(advanced.getProjectileRotationPitch(), 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(advanced.getProjectileRotationYaw(), 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(advanced.getProjectileRotationRoll(), 0.0f, 0.0f, 1.0f);
            GlStateManager.scale(
                    1.0f * Math.exp(advanced.getProjectileScale()),
                    1.0f * Math.exp(advanced.getProjectileScale()),
                    1.0f * Math.exp(advanced.getProjectileScale())
            );
        }
    }

}
