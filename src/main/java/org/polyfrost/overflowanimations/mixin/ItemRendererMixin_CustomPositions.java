package org.polyfrost.overflowanimations.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import org.polyfrost.overflowanimations.config.ItemPositionAdvancedSettings;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin_CustomPositions {

    @Shadow private ItemStack itemToRender;

    @Shadow @Final private Minecraft mc;

    @Inject(method = "transformFirstPersonItem(FF)V", at = @At("HEAD"), cancellable = true)
    public void overflowAnimations$itemTransform(float equipProgress, float swingProgress, CallbackInfo ci) {
        OldAnimationsSettings settings = OldAnimationsSettings.INSTANCE;
        if (OldAnimationsSettings.globalPositions && settings.enabled) {
            GlStateManager.translate(
                    0.56f * (1.0F + settings.itemPositionX),
                    -0.52f * (1.0F - settings.itemPositionY),
                    -0.72f * (1.0F + settings.itemPositionZ)
            );
            GlStateManager.translate(0.0f, equipProgress * -0.6f, 0.0f);
            GlStateManager.rotate(settings.itemRotationPitch, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(settings.itemRotationYaw, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(settings.itemRotationRoll, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
            float f = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
            float f1 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
            GlStateManager.rotate(f * -20.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(f1 * -20.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(f1 * -80.0f, 1.0f, 0.0f, 0.0f);
            double scale = 0.4f * Math.exp(settings.itemScale);
            GlStateManager.scale(scale, scale, scale);
            ci.cancel();
        }
    }

    @Inject(method = "doItemUsedTransformations", at = @At("HEAD"), cancellable = true)
    public void overflowAnimations$swingTransformations(float swingProgress, CallbackInfo ci) {
        OldAnimationsSettings settings = OldAnimationsSettings.INSTANCE;
        ItemPositionAdvancedSettings advanced = OldAnimationsSettings.advancedSettings;
        if (OldAnimationsSettings.globalPositions && settings.enabled) {
            if (settings.swingSetting == 2) {
                ci.cancel();
            } else {
                float scale = (1.0F + (settings.swingSetting == 1 ? settings.itemScale : 0.0F));
                float f = (-0.4f * (1.0F + advanced.itemSwingPositionX)) * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI) * scale;
                float f1 = 0.2f * (1.0F - advanced.itemSwingPositionY) * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI * 2.0f) * scale;
                float f2 = -0.2f * (1.0F + advanced.itemSwingPositionZ) * MathHelper.sin(swingProgress * (float) Math.PI) * scale;
                GlStateManager.translate(f, f1, f2);
                ci.cancel();
            }
        }
    }

    @Inject(method = "doBlockTransformations", at = @At("HEAD"), cancellable = true)
    public void overflowAnimations$blockedItemTransform(CallbackInfo ci) {
        OldAnimationsSettings settings = OldAnimationsSettings.INSTANCE;
        ItemPositionAdvancedSettings advanced = OldAnimationsSettings.advancedSettings;
        if (OldAnimationsSettings.globalPositions && settings.enabled) {
            GlStateManager.translate(
                    -0.5f * (1.0F + advanced.blockedPositionX),
                    0.2f * (1.0F + advanced.blockedPositionY),
                    0.0f + advanced.blockedPositionZ
            );
            GlStateManager.rotate(advanced.blockedRotationPitch, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(advanced.blockedRotationYaw, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(advanced.blockedRotationRoll, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(30.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(-80.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(60.0f, 0.0f, 1.0f, 0.0f);
            if (OldAnimationsSettings.lunarBlockhit) {
                GlStateManager.translate(-0.55F, 0.2F, 0.1F);
                GlStateManager.scale(0.85F, 0.85F, 0.85F);
                GlStateManager.rotate(1.0F, 0.0F, 0.0F, -1.0F);
                GlStateManager.rotate(1.0F, 0.25F, 0.0F, 0.0F);
                GlStateManager.rotate(2.0F, 0.0F, 2.0F, 0.0F);
            }
            double scale = 1.0f * Math.exp(advanced.blockedScale);
            GlStateManager.scale(scale, scale, scale);
            ci.cancel();
        }
    }

    @Inject(method = "doBlockTransformations", at = @At("HEAD"))
    public void overflowAnimations$lunarTransform(CallbackInfo ci) {
        OldAnimationsSettings settings = OldAnimationsSettings.INSTANCE;
        if (OldAnimationsSettings.lunarBlockhit && !OldAnimationsSettings.globalPositions && settings.enabled) {
            GlStateManager.translate(-0.55F, 0.2F, 0.1F);
            GlStateManager.scale(0.85F, 0.85F, 0.85F);
            GlStateManager.rotate(1.0F, 0.0F, 0.0F, -1.0F);
            GlStateManager.rotate(1.0F, 0.25F, 0.0F, 0.0F);
            GlStateManager.rotate(2.0F, 0.0F, 2.0F, 0.0F);
        }
    }

    @ModifyArg(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V", ordinal = 2), index = 0)
    private float overflowAnimations$modifyBlockEquip(float original) {
        return OldAnimationsSettings.lunarBlockhit && OldAnimationsSettings.INSTANCE.enabled ? 0.2F : original;
    }

    // TODO: add customization for equip / swing progress 

    @Inject(method = "performDrinking", at = @At("HEAD"), cancellable = true)
    public void overflowAnimations$drinkingItemTransform(AbstractClientPlayer clientPlayer, float partialTicks, CallbackInfo ci) {
        OldAnimationsSettings settings = OldAnimationsSettings.INSTANCE;
        ItemPositionAdvancedSettings advanced = OldAnimationsSettings.advancedSettings;
        if (OldAnimationsSettings.globalPositions && settings.enabled) {
            float f = (float)clientPlayer.getItemInUseCount() - partialTicks + 1.0f;
            float f1 = f / (float) itemToRender.getMaxItemUseDuration();
            float f2 = MathHelper.abs(MathHelper.cos(f / 4.0f * (float)Math.PI) * 0.1f);
            if (f1 >= 0.8f) {
                f2 = 0.0f;
            }
            float posX = 0.56f * (1.0F + settings.itemPositionX);
            float posY = -0.52f * (1.0F - settings.itemPositionY);
            float posZ = -0.72f * (1.0F + settings.itemPositionZ);
            if (ItemPositionAdvancedSettings.shouldScaleEat) {
                GlStateManager.translate(-0.56f, 0.52f, 0.72f);
                GlStateManager.translate(posX, posY, posZ);
            }
            GlStateManager.translate(advanced.consumePositionX, advanced.consumePositionY, advanced.consumePositionZ);
            GlStateManager.translate(0.0f, f2 * (1.0F + advanced.consumeIntensity), 0.0f);
            float f3 = 1.0f - (float)Math.pow(f1, 27.0f * (1.0F + advanced.consumeSpeed));
            GlStateManager.translate(f3 * 0.6f, f3 * -0.5f, f3 * 0.0f);
            GlStateManager.rotate(advanced.consumeRotationPitch, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(advanced.consumeRotationYaw, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(advanced.consumeRotationRoll, 0.0f, 0.0f, 1.0f);
            GlStateManager.rotate(f3 * 90.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(f3 * 10.0f , 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(f3 * 30.0f, 0.0f, 0.0f, 1.0f);
            if (ItemPositionAdvancedSettings.shouldScaleEat) {
                GlStateManager.translate(0.56f, -0.52f, -0.72f);
                GlStateManager.translate(-posX, -posY, -posZ);
            }
            ci.cancel();
        }
    }

    @Inject(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V", ordinal = 1, shift = At.Shift.AFTER))
    public void overflowAnimations$drinkingItemScale(float partialTicks, CallbackInfo ci) {
        OldAnimationsSettings settings = OldAnimationsSettings.INSTANCE;
        if (OldAnimationsSettings.globalPositions && settings.enabled) {
            double scale = 1.0f * Math.exp(OldAnimationsSettings.advancedSettings.consumeScale);
            GlStateManager.scale(scale, scale, scale);
        }
    }

}
