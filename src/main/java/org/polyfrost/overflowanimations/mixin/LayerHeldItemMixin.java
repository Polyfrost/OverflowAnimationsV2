package org.polyfrost.overflowanimations.mixin;

import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LayerHeldItem.class)
public abstract class LayerHeldItemMixin {

    @Inject(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBiped;postRenderArm(F)V"))
    private void applyOldSneaking(EntityLivingBase entitylivingbaseIn, float f, float g, float partialTicks, float h, float i, float j, float scale, CallbackInfo ci) {
        if (OldAnimationsSettings.smoothSneaking && OldAnimationsSettings.INSTANCE.enabled && entitylivingbaseIn.isSneaking()) {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
        }
    }

    @Redirect(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getRenderType()I"))
    private int cancelNewThirdPersonTransformations(Block block) {
        if (OldAnimationsSettings.thirdTransformations && OldAnimationsSettings.INSTANCE.enabled) return 3;
        return block.getRenderType();
    }

    @Redirect(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;isSneaking()Z"))
    private boolean cancelNewSneaking(EntityLivingBase instance) {
        return instance.isSneaking() && (!OldAnimationsSettings.smoothSneaking || !OldAnimationsSettings.INSTANCE.enabled);
    }

    @Inject(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItem(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void applyOldThirdPersonTransformations(EntityLivingBase entitylivingbaseIn, float f, float g, float partialTicks, float h, float i, float j, float scale, CallbackInfo ci, ItemStack itemStack, Item item) {
        if (OldAnimationsSettings.INSTANCE.enabled) {
            if (OldAnimationsSettings.thirdPersonBlock && entitylivingbaseIn instanceof AbstractClientPlayer && ((AbstractClientPlayer) entitylivingbaseIn).isBlocking()) {
                GlStateManager.translate(0.05F, 0.0F, -0.1F);
                GlStateManager.rotate(-50.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(-10.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(-60.0F, 0.0F, 0.0F, 1.0F);
            }

            if (OldAnimationsSettings.thirdTransformations && (entitylivingbaseIn instanceof EntityPlayer || !OldAnimationsSettings.entityTransforms)) {
                if (Minecraft.getMinecraft().getRenderItem().shouldRenderItemIn3D(itemStack) || item instanceof ItemSkull) {
                    GlStateManager.translate(0.0F, 0.1875F, -0.3125F);
                    GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.scale(-0.375F, -0.375F, 0.375F);
                    if (item instanceof ItemSkull)
                        GlStateManager.scale(0.625F, 0.625F, 0.625F);
                } else if (item instanceof ItemBow) {
                    GlStateManager.translate(0.0F, 0.125F, 0.3125F);
                    GlStateManager.rotate(-20.0F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.scale(-0.625F, -0.625F, 0.625F);
                    GlStateManager.rotate(-100.0F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotate(35.0F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.translate(0.07F, 0.0F, 0.06F);
                } else if (item.isFull3D()) {
                    if (item.shouldRotateAroundWhenRendering()) {
                        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
                        GlStateManager.translate(0.0F, -0.125F, 0.0F);
                    }
                    GlStateManager.translate(0.0F, 0.1875F, 0.0F);
                    GlStateManager.scale(-0.625F, -0.625F, 0.625F);
                    GlStateManager.rotate(-100.0F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotate(35.0F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.translate(0.07F, 0.0F, 0.06F);
                } else {
                    GlStateManager.translate(0.25F, 0.1875F, -0.1875F);
                    GlStateManager.scale(0.375F, 0.375F, 0.375F);
                    GlStateManager.rotate(60.0F, 0.0F, 0.0F, 1.0F);
                    GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotate(20.0F, 0.0F, 0.0F, 1.0F);
                }
                if (!Minecraft.getMinecraft().getRenderItem().shouldRenderItemIn3D(itemStack)) {
                    GlStateManager.translate(0.607F, 0.063F, -0.7935F);
                    GlStateManager.scale(1.5F, 1.5F, 1.5F);
                    GlStateManager.rotate(50.0F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(335.0F, 0.0F, 0.0F, 1.0F);
                    GlStateManager.translate(-0.9375F, -0.0625F, 0.0F);
                    GlStateManager.scale(-1.0F, 1.0F, -1.0F);
                }
            }
        }
    }

    @ModifyArg(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItem(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"), index = 2)
    private ItemCameraTransforms.TransformType cancelTransforms(EntityLivingBase entityIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform) {
        if (OldAnimationsSettings.thirdTransformations && OldAnimationsSettings.INSTANCE.enabled && (entityIn instanceof EntityPlayer || !OldAnimationsSettings.entityTransforms)) {
            return ItemCameraTransforms.TransformType.NONE;
        }
        return transform;
    }
}
