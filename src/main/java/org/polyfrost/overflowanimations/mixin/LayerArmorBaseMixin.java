package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.mixin.interfaces.RendererLivingEntityInvoker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LayerArmorBase.class)
public abstract class LayerArmorBaseMixin<T extends ModelBase> implements LayerRenderer<EntityLivingBase> {

    @Shadow @Final private RendererLivingEntity<?> renderer;
    @Unique private static ModelBase overflowAnimations$t = null;

    @ModifyVariable(
            method = "renderLayer", at = @At(value = "STORE"),
            index = 12
    )
    private T overflowAnimations$captureT(T t) {
        overflowAnimations$t = t;
        return t;
    }

    @Inject(method = "renderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V", shift = At.Shift.AFTER))
    public void overflowAnimations$addRender(EntityLivingBase entitylivingbaseIn, float p_177182_2_, float p_177182_3_, float partialTicks, float p_177182_5_, float p_177182_6_, float p_177182_7_, float scale, int armorSlot, CallbackInfo ci) {
        if (OldAnimationsSettings.INSTANCE.armorDamageTintStyle == 3 && OldAnimationsSettings.INSTANCE.enabled && ((RendererLivingEntityInvoker) renderer).invokeSetDoRenderBrightness(entitylivingbaseIn, partialTicks)) {
            overflowAnimations$t.render(entitylivingbaseIn, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, scale);
            ((RendererLivingEntityInvoker) renderer).invokeUnsetBrightness();
        }
    }

    @Inject(method = "shouldCombineTextures", at = @At(value = "HEAD"), cancellable = true)
    public void overflowAnimations$allowCombine(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue((OldAnimationsSettings.INSTANCE.armorDamageTintStyle == 2 || OldAnimationsSettings.INSTANCE.armorDamageTintStyle == 4) && OldAnimationsSettings.INSTANCE.enabled);
    }

}
