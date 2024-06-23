package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import org.polyfrost.overflowanimations.config.MainModSettings;
import org.polyfrost.overflowanimations.hooks.ItemPositionsHook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    //todo: rewrite lunar feature

    @Shadow private ItemStack itemToRender;
    @Shadow @Final private Minecraft mc;
    @Shadow @Final private RenderItem itemRenderer;
    @Shadow private int equippedItemSlot;
    @Shadow private float equippedProgress;
    @Shadow protected abstract void rotateWithPlayerRotations(EntityPlayerSP entityplayerspIn, float partialTicks);
    @Unique private static final ThreadLocal<Float> overflowAnimations$f1 = ThreadLocal.withInitial(() -> 0.0F);

    @ModifyVariable(
            method = "renderItemInFirstPerson",
            at = @At(
                    value = "STORE"
            ),
            index = 4
    )
    private float overflowAnimations$captureF1(float f1) {
        overflowAnimations$f1.set(f1);
        return f1;
    }

    @ModifyArg(
            method = "renderItemInFirstPerson",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V"
            ),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/client/renderer/ItemRenderer;performDrinking(Lnet/minecraft/client/entity/AbstractClientPlayer;F)V"
                    ),
                    to = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/client/renderer/ItemRenderer;doBowTransformations(FLnet/minecraft/client/entity/AbstractClientPlayer;)V"
                    )
            ),
            index = 1
    )
    private float overflowAnimations$useF1(float swingProgress) {
        if (MainModSettings.INSTANCE.getOldSettings().getOldBlockhitting() && MainModSettings.INSTANCE.getOldSettings().enabled) {
            return overflowAnimations$f1.get();
        }
        return swingProgress;
    }

    @Inject(
            method = "doBowTransformations",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V"
            )
    )
    private void overflowAnimations$preBowTransform(float partialTicks, AbstractClientPlayer clientPlayer, CallbackInfo ci) {
        if (!MainModSettings.INSTANCE.getOldSettings().getFirstTransformations() || !MainModSettings.INSTANCE.getOldSettings().enabled) { return; }
        GlStateManager.rotate(-335.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(-50.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0F, 0.5F, 0.0F);
    }

    @Inject(
            method = "doBowTransformations",
            at = @At(
                    value = "TAIL"

            ))
    private void overflowAnimations$postBowTransform(float partialTicks, AbstractClientPlayer clientPlayer, CallbackInfo ci) {
        if (!MainModSettings.INSTANCE.getOldSettings().getFirstTransformations() || !MainModSettings.INSTANCE.getOldSettings().enabled) { return; }
        GlStateManager.translate(0.0F, -0.5F, 0.0F);
        GlStateManager.rotate(50.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(335.0F, 0.0F, 0.0F, 1.0F);
    }

    @Inject(
            method = "renderItemInFirstPerson",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItem(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"
            )
    )
    private void overflowAnimations$firstPersonItemPos(float partialTicks, CallbackInfo ci) {
        ItemPositionsHook.INSTANCE.firstPersonItemTransforms(itemToRender, itemRenderer);
    }

    @Redirect(
            method = "renderItemInFirstPerson",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/ItemRenderer;rotateWithPlayerRotations(Lnet/minecraft/client/entity/EntityPlayerSP;F)V"
            )
    )
    private void overflowAnimations$removeRotations(ItemRenderer instance, EntityPlayerSP entityPlayerSP, float v) {
        if (MainModSettings.INSTANCE.getOldSettings().getOldItemRotations() && MainModSettings.INSTANCE.getOldSettings().enabled) { return; }
        rotateWithPlayerRotations(entityPlayerSP, v);
    }

    // todo: re-write re-equip
    @ModifyArg(
            method = "updateEquippedItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/MathHelper;clamp_float(FFF)F"
            ),
            index = 0
    )
    private float overflowAnimations$oldItemSwitch(float num) {
        if (MainModSettings.INSTANCE.getOldSettings().getFixReequip() || MainModSettings.INSTANCE.getOldSettings().getDisableReequip()) {
            return num;
        }
        if (MainModSettings.INSTANCE.getOldSettings().getItemSwitch() && MainModSettings.INSTANCE.getOldSettings().enabled) {
            ItemStack itemstack = mc.thePlayer.inventory.getCurrentItem();
            boolean flag = equippedItemSlot == mc.thePlayer.inventory.currentItem && itemstack == itemToRender;
            if (itemToRender == null && itemstack == null) {
                flag = true;
            }
            if (itemstack != null && itemToRender != null && itemstack != itemToRender && itemstack.getItem() == itemToRender.getItem() && itemstack.getItemDamage() == itemToRender.getItemDamage()) {
                itemToRender = itemstack;
                flag = true;
            }
            return (flag ? 1.0f : 0.0f) - equippedProgress;
        }
        return num;
    }

    // todo: make modify variable
    @ModifyConstant(method = "updateEquippedItem", constant = @Constant(floatValue = 0.4F))
    private float overflowAnimations$changeEquipSpeed(float original) {
        return MainModSettings.INSTANCE.getOldSettings().enabled ? MainModSettings.INSTANCE.getOldSettings().getReequipSpeed() : original;
    }

}
