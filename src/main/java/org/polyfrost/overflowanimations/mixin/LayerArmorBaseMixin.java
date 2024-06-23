package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import org.polyfrost.overflowanimations.config.MainModSettings;
import org.polyfrost.overflowanimations.mixin.interfaces.RendererLivingEntityInvoker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerArmorBase.class)
public abstract class LayerArmorBaseMixin<T extends ModelBase> implements LayerRenderer<EntityLivingBase> {

    //todo: introduce different methods for hit color

    @Shadow @Final private RendererLivingEntity<?> renderer;
    @Unique private static final ThreadLocal<ModelBase> overflowAnimations$t = ThreadLocal.withInitial(() -> null);

    @ModifyVariable(
            method = "renderLayer",
            at = @At(
                    value = "STORE"
            ),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/client/model/ModelBase;setLivingAnimations(Lnet/minecraft/entity/EntityLivingBase;FFF)V"
                    ),
                    to = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/client/renderer/entity/layers/LayerArmorBase;setModelPartVisible(Lnet/minecraft/client/model/ModelBase;I)V"
                    )
            ),
            index = 12
    )
    private T overflowAnimations$captureT(T t) {
        overflowAnimations$t.set(t);
        return t;
    }

    @Inject(
            method = "renderLayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V",
                    shift = At.Shift.AFTER
            )
    )
    public void overflowAnimations$addRender(EntityLivingBase entitylivingbaseIn, float p_177182_2_, float p_177182_3_, float partialTicks, float p_177182_5_, float p_177182_6_, float p_177182_7_, float scale, int armorSlot, CallbackInfo ci) {
        if (!MainModSettings.INSTANCE.getOldSettings().getRedArmor() || !MainModSettings.INSTANCE.getOldSettings().enabled) { return; }
        if (((RendererLivingEntityInvoker) renderer).invokeSetDoRenderBrightness(entitylivingbaseIn, partialTicks)) {
            overflowAnimations$t.get().render(entitylivingbaseIn, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, scale);
            ((RendererLivingEntityInvoker) renderer).invokeUnsetBrightness();
        }
    }

}
