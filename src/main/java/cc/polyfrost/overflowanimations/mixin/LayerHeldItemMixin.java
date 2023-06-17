package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraft.block.Block;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerHeldItem.class)
public abstract class LayerHeldItemMixin {

    @Mutable
    @Final
    @Shadow
    private final RendererLivingEntity<?> livingEntityRenderer;

    public LayerHeldItemMixin(RendererLivingEntity<?> rendererLivingEntity) {
        this.livingEntityRenderer = rendererLivingEntity;
    }

    @Inject(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBiped;postRenderArm(F)V"))
    private void applyOldSneaking(EntityLivingBase entitylivingbaseIn, float f, float g, float partialTicks, float h, float i, float j, float scale, CallbackInfo ci) {
        if (OldAnimationsSettings.smoothSneaking && OldAnimationsSettings.INSTANCE.enabled && entitylivingbaseIn.isSneaking()) {
            GlStateManager.translate(0.0F, 0.203125F, 0.0F);
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

    @Inject(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItem(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"))
    private void applyOldThirdPersonTransformations(EntityLivingBase entitylivingbaseIn, float f, float g, float partialTicks, float h, float i, float j, float scale, CallbackInfo ci) {
        ItemStack itemStack = entitylivingbaseIn.getHeldItem();
        if (entitylivingbaseIn instanceof EntityPlayer && ((EntityPlayer) entitylivingbaseIn).fishEntity != null) {
            itemStack = new ItemStack(Items.fishing_rod, 0);
        }
        Item item = itemStack.getItem();
        if (OldAnimationsSettings.oldBlockhitting && OldAnimationsSettings.INSTANCE.enabled) {
            AbstractClientPlayer player = null;
            if (entitylivingbaseIn instanceof AbstractClientPlayer) {
                player = (AbstractClientPlayer) entitylivingbaseIn;
            }
            EnumAction var26;
            if (player != null && player.getItemInUseCount() > 0) {
                var26 = itemStack.getItemUseAction();
                if (var26 == EnumAction.BLOCK) {
                    GlStateManager.translate(0.05F, 0.0F, -0.1F);
                    GlStateManager.rotate(-50.0F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(-10.0F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotate(-60.0F, 0.0F, 0.0F, 1.0F);
                }
            }

        }

        if (OldAnimationsSettings.thirdTransformations && OldAnimationsSettings.INSTANCE.enabled && !(item instanceof ItemSkull) && (entitylivingbaseIn instanceof EntityPlayer || !OldAnimationsSettings.entityTransforms)) {
            if (item instanceof ItemBlock) {
                float var7 = 0.5F;
                GlStateManager.translate(0.0F, 0.1875F, -0.3125F);
                var7 *= 0.75F;
                GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.scale(-var7, -var7, var7);
            } else if (item instanceof ItemBow) {
                float var7 = 0.625F;
                GlStateManager.translate(0.0F, 0.125F, 0.3125F);
                GlStateManager.rotate(-20.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.scale(var7, -var7, var7);
                GlStateManager.rotate(-100.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
            } else if (item.isFull3D()) {
                float var7 = 0.625F;

                if (item.shouldRotateAroundWhenRendering()) {
                    GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
                    GlStateManager.translate(0.0F, -0.125F, 0.0F);
                }

                GlStateManager.translate(0.0F, 0.1875F, 0.0F);
                GlStateManager.scale(var7, -var7, var7);
                GlStateManager.rotate(-100.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
            } else {
                float var7 = 0.375F;
                GlStateManager.translate(0.25F, 0.1875F, -0.1875F);
                GlStateManager.scale(var7, var7, var7);
                GlStateManager.rotate(60.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(20.0F, 0.0F, 0.0F, 1.0F);
            }
            if (!(item instanceof ItemBlock)) {
                GlStateManager.translate(0.58800083f, 0.06999986f, -0.77000016f);
                GlStateManager.scale(1.5f, 1.5f, 1.5f);
                GlStateManager.rotate(50.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(335.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.translate(-0.9375F, -0.0625F, 0.0F);
                GlStateManager.scale(-2, 2, item.isFull3D() || item instanceof ItemBow ? 2 : -2);
                GlStateManager.scale(0.5f, 0.5f, 0.5f);
            }
        }
    }

    @ModifyArg(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItem(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"), index = 2)
    private ItemCameraTransforms.TransformType cancelTransforms(EntityLivingBase entityIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform) {
        if (OldAnimationsSettings.thirdTransformations && OldAnimationsSettings.INSTANCE.enabled && !(heldStack.getItem() instanceof ItemSkull) && (entityIn instanceof EntityPlayer || !OldAnimationsSettings.entityTransforms)) {
            return ItemCameraTransforms.TransformType.NONE;
        }
        return transform;
    }
}
