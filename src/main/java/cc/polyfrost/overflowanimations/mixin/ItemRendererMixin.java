package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.Item;
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
    @Final
    private Minecraft mc;

    @ModifyConstant(method = "renderItemInFirstPerson", constant = @Constant(floatValue = 0.0f))
    public float fixAnimation(float original, float partialTicks) {
        AbstractClientPlayer abstractclientplayer = mc.thePlayer;
        return OldAnimationsSettings.oldBlockhitting && OldAnimationsSettings.INSTANCE.enabled ?
                abstractclientplayer.getSwingProgress(partialTicks) : original;
    }

    @Inject(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItem(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"))
    private void transformOldRod(float partialTicks, CallbackInfo ci) {
        if (OldAnimationsSettings.itemTransformations && OldAnimationsSettings.INSTANCE.enabled &&
                !mc.getRenderItem().shouldRenderItemIn3D(itemToRender)) {
            if (itemToRender.getItem().shouldRotateAroundWhenRendering()) {
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            }
            GlStateManager.translate(0.58800083f, 0.06999986f, -0.77000016f);
            GlStateManager.scale(1.5f, 1.5f, 1.5f);
            GlStateManager.rotate(50.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(335.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(-0.9375F, -0.0625F, 0.0F);
            GlStateManager.scale(-2, 2, -2);
            GlStateManager.scale(0.5f, 0.5f, 0.5f);
        }
    }

    @ModifyArg(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItem(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"), index = 2)
    private ItemCameraTransforms.TransformType removeNewTransformations(ItemCameraTransforms.TransformType transform) {
        if (OldAnimationsSettings.itemTransformations && OldAnimationsSettings.INSTANCE.enabled &&
                !mc.getRenderItem().shouldRenderItemIn3D(itemToRender)) {
            return ItemCameraTransforms.TransformType.NONE;
        }
        return transform;
    }

    @Redirect(method = "updateEquippedItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getIsItemStackEqual(Lnet/minecraft/item/ItemStack;)Z"))
    private boolean useOldEquipCheck(ItemStack itemToRender, ItemStack itemstack) {
        if (OldAnimationsSettings.itemSwitch && OldAnimationsSettings.INSTANCE.enabled) {
            return itemToRender != null && itemstack != null && itemToRender == itemstack && itemToRender.getItem() == itemstack.getItem() && itemToRender.getItemDamage() == itemstack.getItemDamage();
        }
        return itemToRender.getIsItemStackEqual(itemstack);
    }

    @Redirect(method = "updateEquippedItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;shouldCauseReequipAnimation(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;Z)Z"))
    private boolean useOldEquipCheck2(Item instance, ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        if (OldAnimationsSettings.itemSwitch && OldAnimationsSettings.INSTANCE.enabled) {
            return slotChanged;
        }
        return instance.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
    }
}
