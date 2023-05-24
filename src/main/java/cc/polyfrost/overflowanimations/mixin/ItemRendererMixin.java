package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.OverflowAnimations;
import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Shadow
    private ItemStack itemToRender;
    @Shadow
    private float prevEquippedProgress;
    @Shadow
    private float equippedProgress;
    @Shadow
    private int equippedItemSlot;
    @Shadow
    @Final
    private Minecraft mc;

    @ModifyConstant(method = "renderItemInFirstPerson", constant = @Constant(floatValue = 0.0f))
    public float fixAnimation(float original, float partialTicks) {
        AbstractClientPlayer abstractclientplayer = mc.thePlayer;
        return OldAnimationsSettings.oldBlockhitting && OverflowAnimations.oldAnimationsSettings.enabled ?
                abstractclientplayer.getSwingProgress(partialTicks) : original;
    }

    @Redirect(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItem(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"))
    public void oldRod(ItemRenderer instance, EntityLivingBase entityIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform) {
        if (OldAnimationsSettings.itemTransformations && OverflowAnimations.oldAnimationsSettings.enabled &&
                !mc.getRenderItem().shouldRenderItemIn3D(heldStack)) {
            if (heldStack.getItem().shouldRotateAroundWhenRendering()) {
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            }
            GlStateManager.translate(0.58800083f, 0.06999986f, -0.77000016f);
            GlStateManager.scale(1.5f, 1.5f, 1.5f);
            GlStateManager.rotate(50.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(335.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(-0.9375F, -0.0625F, 0.0F);
            GlStateManager.scale(-2, 2, -2);
            GlStateManager.scale(0.5f, 0.5f, 0.5f);
            instance.renderItem(entityIn, heldStack, ItemCameraTransforms.TransformType.NONE);
        } else {
            instance.renderItem(entityIn, heldStack, ItemCameraTransforms.TransformType.FIRST_PERSON);
        }
    }

    @Inject(method = "updateEquippedItem", at = @At("HEAD"), cancellable = true)
    private void handleItemSwitch(CallbackInfo ci) {
        if (OldAnimationsSettings.itemSwitch && OverflowAnimations.oldAnimationsSettings.enabled) {
            ci.cancel();
            prevEquippedProgress = equippedProgress;
            EntityPlayerSP player = mc.thePlayer;
            ItemStack itemstack = player.inventory.getCurrentItem();
            boolean flag = equippedItemSlot == player.inventory.currentItem && itemstack == itemToRender;
            if (itemToRender == null && itemstack == null) {
                flag = true;
            }
            if (itemstack != null && itemToRender != null && itemstack != itemToRender && itemstack.getItem() == itemToRender.getItem() && itemstack.getItemDamage() == itemToRender.getItemDamage()) {
                itemToRender = itemstack;
                flag = true;
            }
            float f = 0.4F;
            float f1 = flag ? 1.0F : 0.0F;
            float f2 = f1 - equippedProgress;
            if (f2 < -f) {
                f2 = -f;
            }
            if (f2 > f) {
                f2 = f;
            }
            equippedProgress += f2;
            if (equippedProgress < 0.1F) {
                itemToRender = itemstack;
                equippedItemSlot = player.inventory.currentItem;
            }
        }
    }
}
