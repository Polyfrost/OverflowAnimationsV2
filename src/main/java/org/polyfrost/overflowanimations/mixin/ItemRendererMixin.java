package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow @Final private Minecraft mc;
    @Shadow private int equippedItemSlot;
    @Shadow private ItemStack itemToRender;
    @Shadow @Final private RenderItem itemRenderer;
    @Shadow protected abstract void rotateWithPlayerRotations(EntityPlayerSP entityplayerspIn, float partialTicks);
    @Unique private static float overflowAnimations$f1 = 0.0F;

    @ModifyVariable(
            method = "renderItemInFirstPerson",
            at = @At(
                    value = "STORE"
            ),
            index = 4
    )
    private float overflowAnimations$captureF1(float f1) {
        overflowAnimations$f1 = f1;
        return f1;
    }

    @ModifyArg(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V"),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;performDrinking(Lnet/minecraft/client/entity/AbstractClientPlayer;F)V"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;doBowTransformations(FLnet/minecraft/client/entity/AbstractClientPlayer;)V")
            ), index = 1
    )
    private float overflowAnimations$useF1(float swingProgress) {
        if (OldAnimationsSettings.oldBlockhitting && OldAnimationsSettings.INSTANCE.enabled) {
            return overflowAnimations$f1;
        }
        return swingProgress;
    }

    @Inject(method = "doBowTransformations", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V"))
    private void overflowAnimations$preBowTransform(float partialTicks, AbstractClientPlayer clientPlayer, CallbackInfo ci) {
        if (OldAnimationsSettings.firstTransformations && !OldAnimationsSettings.lunarPositions && OldAnimationsSettings.INSTANCE.enabled) {
            GlStateManager.rotate(-335.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(-50.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, 0.5F, 0.0F);
        }
    }

    @Inject(method = "doBowTransformations", at = @At(value = "TAIL"))
    private void overflowAnimations$postBowTransform(float partialTicks, AbstractClientPlayer clientPlayer, CallbackInfo ci) {
        if (OldAnimationsSettings.firstTransformations && !OldAnimationsSettings.lunarPositions && OldAnimationsSettings.INSTANCE.enabled) {
            GlStateManager.translate(0.0F, -0.5F, 0.0F);
            GlStateManager.rotate(50.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(335.0F, 0.0F, 0.0F, 1.0F);
        }
    }

    @Inject(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItem(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"))
    private void overflowAnimations$firstPersonItemPositions(float partialTicks, CallbackInfo ci) {
        if (OldAnimationsSettings.INSTANCE.enabled && !OldAnimationsSettings.lunarPositions && !itemRenderer.shouldRenderItemIn3D(itemToRender)) {
            if ((OldAnimationsSettings.fishingRodPosition && itemToRender.getItem().shouldRotateAroundWhenRendering())) {
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                overflowAnimations$itemTransforms();
            } else if (OldAnimationsSettings.firstTransformations && !(itemToRender.getItem() instanceof ItemSword && OldAnimationsSettings.lunarBlockhit)) {
                overflowAnimations$itemTransforms();
            }
        }
    }

    @Redirect(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;rotateWithPlayerRotations(Lnet/minecraft/client/entity/EntityPlayerSP;F)V"))
    private void overflowAnimations$removeRotations(ItemRenderer instance, EntityPlayerSP entityPlayerSP, float v) {
        if (!OldAnimationsSettings.oldItemRotations || !OldAnimationsSettings.INSTANCE.enabled) {
            rotateWithPlayerRotations(entityPlayerSP, v);
        }
    }

    @Unique
    private static void overflowAnimations$itemTransforms() {
        float scale = 1.5F / 1.7F;
        GlStateManager.scale(scale, scale, scale);
        GlStateManager.rotate(5.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(-0.29F, 0.149F, -0.0328F);
    }

    @ModifyConstant(method = "updateEquippedItem", constant = @Constant(floatValue = 0.4F))
    private float overflowAnimations$changeEquipSpeed(float original) {
        return OldAnimationsSettings.INSTANCE.enabled ? OldAnimationsSettings.INSTANCE.reequipSpeed : original;
    }

    @Inject(method = "resetEquippedProgress", at = @At(value = "HEAD"), cancellable = true)
    private void overflowAnimations$disableReEquip1(CallbackInfo ci) {
        if (OldAnimationsSettings.INSTANCE.itemSwitchMode == 0 && OldAnimationsSettings.INSTANCE.enabled) {
            ci.cancel();
        }
    }

    @Inject(method = "resetEquippedProgress2", at = @At(value = "HEAD"), cancellable = true)
    private void overflowAnimations$disableReEquip2(CallbackInfo ci) {
        if (OldAnimationsSettings.INSTANCE.itemSwitchMode == 0 && OldAnimationsSettings.INSTANCE.enabled) {
            ci.cancel();
        }
    }

    @ModifyVariable(
            method = "updateEquippedItem",
            at = @At(
                    value = "STORE",
                    ordinal = 3
            ),
            index = 3
    )
    public boolean overflowAnimations$disableReEquip(boolean flag) {
        if (OldAnimationsSettings.INSTANCE.itemSwitchMode == 0 && OldAnimationsSettings.INSTANCE.enabled) {
            EntityPlayer entityplayer = this.mc.thePlayer;
            this.itemToRender = entityplayer.inventory.getCurrentItem();
            this.equippedItemSlot = entityplayer.inventory.currentItem;
            return false;
        }
        return flag;
    }

}
