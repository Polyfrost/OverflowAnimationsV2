package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.OverflowAnimations;
import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import cc.polyfrost.overflowanimations.handlers.AnimationHandler;
import net.minecraft.block.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public class RenderItemMixin {

    @Unique
    private EntityLivingBase lastEntityToRenderFor = null;

    @Inject(method = "renderItemModelForEntity", at = @At("HEAD"))
    public void renderItemModelForEntity(ItemStack stack, EntityLivingBase entityToRenderFor,
                                         ItemCameraTransforms.TransformType cameraTransformType, CallbackInfo ci) {
        lastEntityToRenderFor = entityToRenderFor;
    }

    @Inject(method = "renderItemModelTransform", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(" +
                    "Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V")
    )
    public void renderItemModelForEntity_renderItem(ItemStack stack, IBakedModel model,
                                                    ItemCameraTransforms.TransformType cameraTransformType, CallbackInfo ci) {
        if (cameraTransformType == ItemCameraTransforms.TransformType.THIRD_PERSON &&
                lastEntityToRenderFor instanceof EntityPlayer && OverflowAnimations.oldAnimationsSettings.enabled) {
            EntityPlayer p = (EntityPlayer) lastEntityToRenderFor;
            ItemStack heldStack = p.getHeldItem();
            if (heldStack != null && p.getItemInUseCount() > 0 &&
                    heldStack.getItemUseAction() == EnumAction.BLOCK) {
                AnimationHandler.getInstance().doSwordBlock3rdPersonTransform();
            }
        }
    }

    @Inject(method = "renderItemModelTransform", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;pushMatrix()V", shift = At.Shift.AFTER))
    private void setRenderingStack(ItemStack stack, IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType, CallbackInfo ci) {
        if (cameraTransformType == ItemCameraTransforms.TransformType.THIRD_PERSON) {
            OverflowAnimations.renderingStack = stack;
        } else if (OldAnimationsSettings.mixcesFirstPersonAnimations && OldAnimationsSettings.itemTransformations && stack.getItem() != null && !model.isBuiltInRenderer() && OverflowAnimations.oldAnimationsSettings.enabled) {
            GlStateManager.translate(-0.01, 0.002, 0.0005);
            GlStateManager.rotate(0, 0, 1, 0);
            GlStateManager.rotate(0, 1, 0, 0);
            GlStateManager.rotate(0, 0, 0, 1);
            GlStateManager.scale(1, 1, 1);
            if (stack.getItem() instanceof ItemBlock) {
                Block block = ((ItemBlock) stack.getItem()).getBlock();
                if (OldAnimationsSettings.firstPersonCarpetPosition && (block instanceof BlockCarpet || block instanceof BlockSnow)) {
                    GlStateManager.translate(0.015, -0.331, -0.0005);
                    GlStateManager.rotate(0, 0, 1, 0);
                    GlStateManager.rotate(0, 1, 0, 0);
                    GlStateManager.rotate(0, 0, 0, 1);
                    GlStateManager.scale(1, 1, 1);
                } else if (block instanceof BlockLadder || block instanceof BlockPane || block instanceof BlockRail || block instanceof BlockRailPowered || block instanceof BlockRailDetector || block instanceof BlockVine || block instanceof BlockWeb || block instanceof BlockLever || block instanceof BlockBush || block instanceof BlockTorch || block instanceof BlockTripWireHook) {
                    GlStateManager.translate(0, 0, 0.0005);
                    GlStateManager.rotate(0, 0, 1, 0);
                    GlStateManager.rotate(0, 1, 0, 0);
                    GlStateManager.rotate(0, 0, 0, 1);
                    GlStateManager.scale(1, 1, 1);
                } else if (block instanceof BlockFurnace || block instanceof BlockDispenser || block instanceof BlockPumpkin || block instanceof BlockChest || block instanceof BlockEnderChest || block instanceof BlockFence || block instanceof BlockFenceGate) {
                    GlStateManager.translate(0.015, -0.004, -0.0005);
                    GlStateManager.rotate(0, 0, 1, 0);
                    GlStateManager.rotate(0, 1, 0, 0);
                    GlStateManager.rotate(0, 0, 0, 1);
                    GlStateManager.scale(1, 1, 1);
                } else if (block != null) {
                    GlStateManager.translate(0.015, -0.004, -0.0005);
                    GlStateManager.rotate(90, 0, 1, 0);
                    GlStateManager.rotate(0, 1, 0, 0);
                    GlStateManager.rotate(0, 0, 0, 1);
                    GlStateManager.scale(1, 1, 1);
                }
            }
        }
    }

}
