package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerArmorBase.class)
public abstract class LayerArmorBaseMixin<T extends ModelBase> implements LayerRenderer<EntityLivingBase> {

    @Shadow(remap = false) protected abstract T getArmorModelHook(EntityLivingBase entity, ItemStack itemStack, int slot, T model);
    @Shadow @Final private RendererLivingEntity<?> renderer;
    @Unique private T overflowAnimations$render;

    @Redirect(method = "renderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/LayerArmorBase;getArmorModelHook(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;ILnet/minecraft/client/model/ModelBase;)Lnet/minecraft/client/model/ModelBase;"), remap = false)
    public T overflowAnimations$getModelBase(LayerArmorBase<T> instance, EntityLivingBase entity, ItemStack itemStack, int slot, T model) {
        return overflowAnimations$render = getArmorModelHook(entity, itemStack, slot, model);
    }

    @Inject(method = "renderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V", shift = At.Shift.AFTER))
    public void overflowAnimations$addRender(EntityLivingBase entitylivingbaseIn, float p_177182_2_, float p_177182_3_, float partialTicks, float p_177182_5_, float p_177182_6_, float p_177182_7_, float scale, int armorSlot, CallbackInfo ci) {
        if (OldAnimationsSettings.redArmor && OldAnimationsSettings.INSTANCE.enabled && ((RendererLivingEntityInvoker) renderer).invokeSetDoRenderBrightness(entitylivingbaseIn, partialTicks)) {
            overflowAnimations$render.render(entitylivingbaseIn, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, scale);
            ((RendererLivingEntityInvoker) renderer).invokeUnsetBrightness();
        }
    }

}
