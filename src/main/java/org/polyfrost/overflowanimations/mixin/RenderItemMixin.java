package org.polyfrost.overflowanimations.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockSnow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiFlatPresets;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.GlintModelHook;
import org.polyfrost.overflowanimations.hooks.TransformTypeHook;
import org.polyfrost.overflowanimations.init.CustomModelBakery;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(RenderItem.class)
public abstract class RenderItemMixin {

    @Unique
    public IBakedModel overflowAnimations$model;
    @Unique
    private EntityLivingBase overflowAnimations$entityLivingBase;

    @Inject(method = "renderModel(Lnet/minecraft/client/resources/model/IBakedModel;ILnet/minecraft/item/ItemStack;)V", at = @At("HEAD"))
    private void overflowAnimations$setModel(IBakedModel model, int color, ItemStack stack, CallbackInfo ci) {
        overflowAnimations$model = model;
    }

    @Inject(method = "renderItemModelForEntity", at = @At("HEAD"))
    public void overflowAnimations$getLastEntity(ItemStack stack, EntityLivingBase entityToRenderFor, ItemCameraTransforms.TransformType cameraTransformType, CallbackInfo ci) {
        overflowAnimations$entityLivingBase = entityToRenderFor;
    }

    @ModifyArg(method = "renderModel(Lnet/minecraft/client/resources/model/IBakedModel;ILnet/minecraft/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderQuads(Lnet/minecraft/client/renderer/WorldRenderer;Ljava/util/List;ILnet/minecraft/item/ItemStack;)V", ordinal = 1), index = 1)
    private List<BakedQuad> overflowAnimations$changeToSprite(List<BakedQuad> quads) {
        if (OldAnimationsSettings.itemSprites && OldAnimationsSettings.INSTANCE.enabled && !overflowAnimations$model.isGui3d() && TransformTypeHook.shouldBeSprite()) {
            return quads.stream().filter(baked -> baked.getFace() == EnumFacing.SOUTH).collect(Collectors.toList());
        }
        return quads;
    }

    @Redirect(method = "putQuadNormal", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;putNormal(FFF)V"))
    private void overflowAnimations$modifyNormalValue(WorldRenderer instance, float x, float y, float z, WorldRenderer renderer, BakedQuad quad) {
        if (OldAnimationsSettings.INSTANCE.enabled && !overflowAnimations$model.isGui3d() && !(Minecraft.getMinecraft().currentScreen instanceof GuiFlatPresets)) {
            if (OldAnimationsSettings.itemSprites && OldAnimationsSettings.itemSpritesColor && TransformTypeHook.shouldNotHaveGlint()) {
                instance.putNormal(x, z, y);
            }
        } else {
            instance.putNormal(x, y, z);
        }
    }

    @Inject(method = "renderEffect", at = @At(value = "HEAD"), cancellable = true)
    public void overflowAnimations$disableGlintOnSprite(IBakedModel model, CallbackInfo ci) {
        if (OldAnimationsSettings.itemSprites && OldAnimationsSettings.spritesGlint && OldAnimationsSettings.INSTANCE.enabled && TransformTypeHook.shouldNotHaveGlint()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V"))
    private void overflowAnimations$translateSprite(ItemStack stack, IBakedModel model, CallbackInfo ci) {
        if (OldAnimationsSettings.itemSprites && OldAnimationsSettings.INSTANCE.enabled && !model.isGui3d() && TransformTypeHook.shouldNotHaveGlint()) {
            GlStateManager.translate(0.0F, 0.0F, -0.0625F);
        }
    }

    @Inject(method = "renderItemModelTransform", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V"))
    public void overflowAnimations$modifyModelPosition(ItemStack stack, IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType, CallbackInfo ci) {
        if (OldAnimationsSettings.INSTANCE.enabled && !(stack.getItem() instanceof ItemBanner)) {
                boolean isRod = stack.getItem().shouldRotateAroundWhenRendering();
                boolean isBlock = stack.getItem() instanceof ItemBlock;
                boolean isCarpet = false;
                if (isBlock) {
                    Block block = ((ItemBlock) stack.getItem()).getBlock();
                    isCarpet = block instanceof BlockCarpet || block instanceof BlockSnow;
                }
                if (cameraTransformType == ItemCameraTransforms.TransformType.FIRST_PERSON) {
                    if (OldAnimationsSettings.fishingRodPosition && isRod) {
                        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                        GlStateManager.rotate(50.0F, 0.0F, 0.0F, 1.0F);
                    } else if (OldAnimationsSettings.firstTransformations &&  OldAnimationsSettings.firstPersonCarpetPosition && isCarpet) {
                        GlStateManager.translate(0.0F, -5.25F * 0.0625F, 0.0F);
                    }
                } else if (OldAnimationsSettings.thirdTransformations && cameraTransformType == ItemCameraTransforms.TransformType.THIRD_PERSON
                        && (overflowAnimations$entityLivingBase instanceof EntityPlayer || !OldAnimationsSettings.entityTransforms)) {
                    if (isRod) {
                        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                        GlStateManager.rotate(110.0F, 0.0F, 0.0F, 1.0F);
                        GlStateManager.translate(0.002F, 0.037F, -0.003F);
                    } else if (OldAnimationsSettings.thirdPersonCarpetPosition && isCarpet) {
                        GlStateManager.translate(0.0F, -0.25F, 0.0F);
                    }
                    if (isBlock) {
                        if (Block.getBlockFromItem(stack.getItem()).getRenderType() != 2) {
                            GlStateManager.translate(-0.0285F, -0.0375F, 0.0285F);
                            GlStateManager.rotate(-5.0f, 1.0f, 0.0f, 0.0f);
                            GlStateManager.rotate(-5.0f, 0.0f, 0.0f, 1.0f);
                        }
                        GlStateManager.scale(-1.0F, 1.0F, -1.0F);
                    }
                }
            }
    }

    @Inject(method = "renderItemAndEffectIntoGUI", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItemIntoGUI(Lnet/minecraft/item/ItemStack;II)V"))
    public void overflowAnimations$fixGlint(ItemStack stack, int xPosition, int yPosition, CallbackInfo ci) {
        if (OldAnimationsSettings.INSTANCE.enabled) {
            GlStateManager.enableDepth();
        }
    }

    @Redirect(method = "renderItemIntoGUI", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemModelMesher;getItemModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/resources/model/IBakedModel;"))
    public IBakedModel overflowAnimations$rodBowModelTexture(ItemModelMesher instance, ItemStack stack) {
        EntityPlayer entityplayer = Minecraft.getMinecraft().thePlayer;
        if (entityplayer != null && OldAnimationsSettings.rodBowGuiFix && OldAnimationsSettings.INSTANCE.enabled) {
            Item item = stack.getItem();
            String inventory = "inventory";
            if (stack == entityplayer.getHeldItem()) {
                ModelResourceLocation modelresourcelocation = null;
                if (item instanceof ItemBow && entityplayer.getItemInUse() != null) {
                    int i = stack.getMaxItemUseDuration() - entityplayer.getItemInUseCount();
                    if (i >= 18) {
                        modelresourcelocation = new ModelResourceLocation("bow_pulling_2", inventory);
                    } else if (i > 13) {
                        modelresourcelocation = new ModelResourceLocation("bow_pulling_1", inventory);
                    } else if (i > 0) {
                        modelresourcelocation = new ModelResourceLocation("bow_pulling_0", inventory);
                    }
                } else if (item instanceof ItemFishingRod && entityplayer.fishEntity != null) {
                    modelresourcelocation = new ModelResourceLocation("fishing_rod_cast", inventory);
                } else {
                    modelresourcelocation = item.getModel(stack, entityplayer, entityplayer.getItemInUseCount());
                } if (modelresourcelocation != null) {
                    return instance.getModelManager().getModel(modelresourcelocation);
                }
            }
        }
        return instance.getItemModel(stack);
    }

    @Shadow
    public abstract void renderItem(ItemStack stack, IBakedModel model);
    @Shadow @Final
    private static ResourceLocation RES_ITEM_GLINT;
    @Unique private static final ThreadLocal<ItemStack> mixcesAnimations$stack = ThreadLocal.withInitial(() -> null);

    @Inject(
            method = "renderEffect",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    public void abc(CallbackInfo ci) {
        if (!OldAnimationsSettings.enchantmentGlint || !OldAnimationsSettings.INSTANCE.enabled) return;
        if (GlintModelHook.INSTANCE.getTransformType() != ItemCameraTransforms.TransformType.GUI) return;
        ci.cancel();
    }

    @Inject(
            method = "renderItemIntoGUI",
            at = @At(
                    value = "TAIL"
            )
    )
    public void offGUI(ItemStack stack, int x, int y, CallbackInfo ci) {
        if (!OldAnimationsSettings.enchantmentGlint || !OldAnimationsSettings.INSTANCE.enabled) return;
        if (!stack.hasEffect()) return;
        GlintModelHook.INSTANCE.renderGlintGui(x, y, RES_ITEM_GLINT);
    }

    @ModifyArg(
            method = "renderEffect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;I)V"
            ),
            index = 0
    )
    public IBakedModel mixcesAnimations$replaceModel(IBakedModel model) {
        if (!OldAnimationsSettings.enchantmentGlint || !OldAnimationsSettings.INSTANCE.enabled) return model;
        return GlintModelHook.INSTANCE.getGlint(model);
    }

    @ModifyVariable(
            method = "renderEffect",
            at = @At(
                    value = "STORE"
            ),
            index = 2
    )
    private float mixcesAnimations$modifyF(float f) {
        if (!OldAnimationsSettings.enchantmentGlint || !OldAnimationsSettings.INSTANCE.enabled) return f;
        return f * 64.0F;
    }

    @ModifyVariable(
            method = "renderEffect",
            at = @At(
                    value = "STORE"
            ),
            index = 3
    )
    private float mixcesAnimations$modifyF1(float f1) {
        if (!OldAnimationsSettings.enchantmentGlint || !OldAnimationsSettings.INSTANCE.enabled) return f1;
        return f1 * 64.0F;
    }

    @ModifyArgs(
            method = "renderEffect",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GlStateManager;scale(FFF)V"
            )
    )
    public void mixcesAnimations$modifyScale(Args args) {
        if (!OldAnimationsSettings.enchantmentGlint || !OldAnimationsSettings.INSTANCE.enabled) return;
        for (int i : new int[]{0, 1, 2}) {
            args.set(i, 1 / (float) args.get(i));
        }
    }

    @ModifyVariable(
            method = "renderItemModelTransform",
            at = @At(
                    value = "HEAD",
                    ordinal = 0
            ),
            index = 1,
            argsOnly = true
    )
    private ItemStack mixcesAnimations$captureStack(ItemStack stack) {
        if (!OldAnimationsSettings.INSTANCE.enabled || !OldAnimationsSettings.oldPotions) return stack;
        mixcesAnimations$stack.set(stack);
        return stack;
    }

    @ModifyArg(
            method = "renderItemModelTransform",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V"
            ),
            index = 1
    )
    private IBakedModel mixcesAnimations$swapToCustomModel(IBakedModel model) {
        if (!OldAnimationsSettings.INSTANCE.enabled || !OldAnimationsSettings.oldPotions) return model;
        if (mixcesAnimations$stack.get().getItem() instanceof ItemPotion) {
            return CustomModelBakery.BOTTLE_OVERLAY.getBakedModel();
        }
        return model;
    }

    @Inject(
            method = "renderItemModelTransform",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void mixcesAnimations$renderCustomBottle(ItemStack stack, IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType, CallbackInfo ci) {
        if (!OldAnimationsSettings.INSTANCE.enabled || !OldAnimationsSettings.oldPotions) return;

        if (stack.getItem() instanceof ItemPotion) {
            renderItem(new ItemStack(Items.glass_bottle), mixcesAnimations$getBottleModel(stack));
        }
    }

    @Unique
    private IBakedModel mixcesAnimations$getBottleModel(ItemStack stack) {
        return ItemPotion.isSplash(stack.getMetadata()) ? CustomModelBakery.BOTTLE_SPLASH_EMPTY.getBakedModel() : CustomModelBakery.BOTTLE_DRINKABLE_EMPTY.getBakedModel();
    }

}
