package org.polyfrost.overflowanimations.mixin.layers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.HitColorHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RendererLivingEntity.class)
public abstract class RendererLivingEntityMixin<T extends EntityLivingBase> extends Render<T> {

    @Shadow protected ModelBase mainModel;
    @Shadow protected abstract boolean setBrightness(T entitylivingbaseIn, float partialTicks, boolean combineTextures);
    @Shadow protected abstract float handleRotationFloat(T livingBase, float partialTicks);
    @Shadow protected abstract float interpolateRotation(float par1, float par2, float par3);
    @Shadow protected abstract boolean setDoRenderBrightness(T entityLivingBaseIn, float partialTicks);

    protected RendererLivingEntityMixin(RenderManager renderManager) {
        super(renderManager);
    }

    @Redirect(method = "doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RendererLivingEntity;setDoRenderBrightness(Lnet/minecraft/entity/EntityLivingBase;F)Z"))
    public boolean overflowAnimations$disableBrightness(RendererLivingEntity<?> instance, T entityLivingBaseIn, float partialTicks) {
        return (OldAnimationsSettings.INSTANCE.armorDamageTintStyle != 1 || !OldAnimationsSettings.INSTANCE.enabled) && setDoRenderBrightness(entityLivingBaseIn, partialTicks);
    }

    @Redirect(method = "renderLayers", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RendererLivingEntity;setBrightness(Lnet/minecraft/entity/EntityLivingBase;FZ)Z"))
    public boolean overflowAnimations$disableLayerBrightness(RendererLivingEntity<?> instance, T f2, float f3, boolean f4) {
        return (OldAnimationsSettings.INSTANCE.armorDamageTintStyle != 1 || !OldAnimationsSettings.INSTANCE.enabled) && setBrightness(f2, f3, f4);
    }

    @Inject(method = "doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;depthMask(Z)V"))
    public void overflowAnimations$renderHitColor(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (OldAnimationsSettings.INSTANCE.armorDamageTintStyle == 1 && OldAnimationsSettings.INSTANCE.enabled) {
            float f = interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
            float f1 = interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
            float f2 = f1 - f;
            float f7 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
            float f8 = handleRotationFloat(entity, partialTicks);
            float f5 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialTicks;
            float f6 = entity.limbSwing - entity.limbSwingAmount * (1.0f - partialTicks);
            if (entity.isChild()) {
                f6 *= 3.0f;
            }
            if (f5 > 1.0f) {
                f5 = 1.0f;
            }
            boolean bl = entity.hurtTime > 0 || entity.deathTime > 0;
            HitColorHook.renderHitColorPre(entity, bl, partialTicks, (RendererLivingEntity<?>) (Object) this);
            if (bl) {
                mainModel.render(entity, f6, f5, f8, f2, f7, 0.0625f);
            }
            HitColorHook.renderHitColorPost(bl);
        }
    }

    @ModifyArg(
            method = "setBrightness",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/nio/FloatBuffer;put(F)Ljava/nio/FloatBuffer;",
                    ordinal = 3
            ),
            index = 0
    )
    private float overflowAnimations$orangesHitColor(float f) {
        if (OldAnimationsSettings.INSTANCE.armorDamageTintStyle == 4 && OldAnimationsSettings.INSTANCE.enabled)
            return 0.5f; /* not sure where he got this value from ?!? */
        return f;
    }

}
