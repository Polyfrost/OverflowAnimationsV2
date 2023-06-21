package cc.polyfrost.overflowanimations.mixin;

import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import cc.polyfrost.overflowanimations.hooks.TransformTypeHook;
import net.minecraft.block.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public class RenderItemMixin {

    @Unique private EntityLivingBase overflow$entityToRenderFor;

    @Inject(method = "renderItemModelForEntity", at = @At(value = "HEAD"))
    public void getEntity(ItemStack stack, EntityLivingBase entityToRenderFor, ItemCameraTransforms.TransformType cameraTransformType, CallbackInfo ci) {
        overflow$entityToRenderFor = entityToRenderFor;
    }

    @Inject(method = "renderItemModelTransform", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;pushMatrix()V", shift = At.Shift.AFTER))
    private void setRenderingStack(ItemStack stack, IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType, CallbackInfo ci) {
        if ((OldAnimationsSettings.itemTransformations && cameraTransformType != ItemCameraTransforms.TransformType.THIRD_PERSON) ||
                (OldAnimationsSettings.thirdTransformations && cameraTransformType == ItemCameraTransforms.TransformType.THIRD_PERSON) &&
                        (overflow$entityToRenderFor instanceof EntityPlayer || !OldAnimationsSettings.entityTransforms) &&
                        OldAnimationsSettings.INSTANCE.enabled && stack.getItem() != null && !model.isBuiltInRenderer()) {
            GlStateManager.translate(-0.01, 0.002, 0.0005);
            if (stack.getItem() instanceof ItemBlock && !(OldAnimationsSettings.itemTransformations && cameraTransformType != ItemCameraTransforms.TransformType.THIRD_PERSON) &&
                    !(OldAnimationsSettings.thirdTransformations || cameraTransformType == ItemCameraTransforms.TransformType.THIRD_PERSON)) {
                Block block = ((ItemBlock) stack.getItem()).getBlock();
                if (!(block instanceof BlockBanner)) {
                    if (OldAnimationsSettings.firstPersonCarpetPosition && block instanceof BlockCarpet || block instanceof BlockSnow) {
                        GlStateManager.translate(0.015, -0.331, -0.0005);
                    } else if (block instanceof BlockLadder || block instanceof BlockPane || block instanceof BlockRail || block instanceof BlockRailPowered || block instanceof BlockRailDetector || block instanceof BlockVine || block instanceof BlockWeb || block instanceof BlockLever || block instanceof BlockBush || block instanceof BlockTorch || block instanceof BlockTripWireHook) {
                        GlStateManager.translate(0, 0, 0.0005);
                    } else if (block instanceof BlockFurnace || block instanceof BlockDispenser || block instanceof BlockPumpkin || block instanceof BlockChest || block instanceof BlockEnderChest || block instanceof BlockFence || block instanceof BlockFenceGate) {
                        GlStateManager.translate(0.015, -0.004, -0.0005);
                    } else if (block != null) {
                        GlStateManager.translate(0.015, -0.004, -0.0005);
                        GlStateManager.rotate(90, 0, 1, 0);
                    }
                }
            }
        }
    }

    @ModifyConstant(method = "renderEffect", constant = @Constant(floatValue = 8.0f))
    private float modifyScale(float original) {
        return OldAnimationsSettings.enchantmentGlint && OldAnimationsSettings.INSTANCE.enabled ? 6.0f : original;
    }

    @ModifyConstant(method = "renderEffect", constant = @Constant(floatValue = -50F))
    private float modifyEffect(float original) {
        return OldAnimationsSettings.enchantmentGlint && OldAnimationsSettings.INSTANCE.enabled ?
                TransformTypeHook.transform != ItemCameraTransforms.TransformType.GUI ? -5.0f : 0.0f : original;
    }

    @ModifyConstant(method = "renderEffect", constant = @Constant(floatValue = 10.0F))
    private float modifyEffect2(float original) {
        return OldAnimationsSettings.enchantmentGlint && OldAnimationsSettings.INSTANCE.enabled ?
                TransformTypeHook.transform != ItemCameraTransforms.TransformType.GUI ? 205.0f : 0.0f : original;
    }

    @ModifyArg(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", ordinal = 1), index = 0)
    private float modifyEffect3(float original) {
        return (OldAnimationsSettings.enchantmentGlint && OldAnimationsSettings.INSTANCE.enabled ? -1F : 1F) * original;
    }

    @ModifyConstant(method = "renderEffect", constant = @Constant(longValue = 4873L))
    private long modifyEffectSpeed3(long original) {
        return OldAnimationsSettings.enchantmentGlint && OldAnimationsSettings.INSTANCE.enabled &&
                TransformTypeHook.transform != ItemCameraTransforms.TransformType.GUI ? 2000L : original;
    }

    @ModifyConstant(method = "renderEffect", constant = @Constant(floatValue = 4873.0f))
    private float modifyEffectSpeed4(float original) {
        return OldAnimationsSettings.enchantmentGlint && OldAnimationsSettings.INSTANCE.enabled &&
                TransformTypeHook.transform != ItemCameraTransforms.TransformType.GUI ? 2000.0f : original;
    }

    @Inject(method = "renderEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", shift = At.Shift.AFTER))
    public void modifyEffect4(IBakedModel model, CallbackInfo ci) {
        if (OldAnimationsSettings.enchantmentGlint && OldAnimationsSettings.INSTANCE.enabled &&
                TransformTypeHook.transform == ItemCameraTransforms.TransformType.GUI) {
            GlStateManager.rotate(70.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
        }
    }

    @ModifyConstant(method = "renderEffect", constant = @Constant(intValue = -8372020))
    private int modifyEffect6(int original) {
        return OldAnimationsSettings.enchantmentGlint && OldAnimationsSettings.INSTANCE.enabled ?
                TransformTypeHook.transform == ItemCameraTransforms.TransformType.GUI ? -6788658 : -10799734 : original;
    }

    @Inject(method = "renderEffect", at = @At(value = "HEAD"), cancellable = true)
    public void modifyEffect5(IBakedModel model, CallbackInfo ci) {
        if (OldAnimationsSettings.spritesGlint && OldAnimationsSettings.INSTANCE.enabled &&
                TransformTypeHook.transform == ItemCameraTransforms.TransformType.GROUND) {
            ci.cancel();
        }
    }
}
