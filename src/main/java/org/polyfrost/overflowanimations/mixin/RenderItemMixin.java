package org.polyfrost.overflowanimations.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockSnow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.EnumFacing;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;
import org.polyfrost.overflowanimations.hooks.TransformTypeHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(RenderItem.class)
public abstract class RenderItemMixin {

    @Unique
    public IBakedModel oldanimations$model;
    @Unique
    private EntityLivingBase oldanimations$entityLivingBase;

    @Inject(method = "renderModel(Lnet/minecraft/client/resources/model/IBakedModel;ILnet/minecraft/item/ItemStack;)V", at = @At("HEAD"))
    private void setModel(IBakedModel model, int color, ItemStack stack, CallbackInfo ci) {
        oldanimations$model = model;
    }

    @Inject(method = "renderItemModelForEntity", at = @At("HEAD"))
    public void getLastEntity(ItemStack stack, EntityLivingBase entityToRenderFor, ItemCameraTransforms.TransformType cameraTransformType, CallbackInfo ci) {
        oldanimations$entityLivingBase = entityToRenderFor;
    }

    @ModifyArg(method = "renderModel(Lnet/minecraft/client/resources/model/IBakedModel;ILnet/minecraft/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderQuads(Lnet/minecraft/client/renderer/WorldRenderer;Ljava/util/List;ILnet/minecraft/item/ItemStack;)V", ordinal = 1), index = 1)
    private List<BakedQuad> changeToSprite(List<BakedQuad> quads) {
        if (OldAnimationsSettings.itemSprites && OldAnimationsSettings.INSTANCE.enabled && !oldanimations$model.isGui3d() && TransformTypeHook.shouldBeSprite()) {
            return quads.stream().filter(baked -> baked.getFace() == EnumFacing.SOUTH).collect(Collectors.toList());
        }
        return quads;
    }

    @Redirect(method = "putQuadNormal", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;putNormal(FFF)V"))
    private void modifyNormalValue(WorldRenderer instance, float x, float y, float z, WorldRenderer renderer, BakedQuad quad) {
        if (OldAnimationsSettings.INSTANCE.enabled && !oldanimations$model.isGui3d()) {
            if (OldAnimationsSettings.itemSprites && OldAnimationsSettings.itemSpritesColor && TransformTypeHook.shouldNotHaveGlint()) {
                instance.putNormal(x, z, y);
            }
        } else {
            instance.putNormal(x, y, z);
        }
    }

    @Inject(method = "renderEffect", at = @At(value = "HEAD"), cancellable = true)
    public void disableGlintOnSprite(IBakedModel model, CallbackInfo ci) {
        if (OldAnimationsSettings.itemSprites && OldAnimationsSettings.spritesGlint && OldAnimationsSettings.INSTANCE.enabled && TransformTypeHook.shouldNotHaveGlint()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderModel(Lnet/minecraft/client/resources/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V"))
    private void translateSprite(ItemStack stack, IBakedModel model, CallbackInfo ci) {
        if (OldAnimationsSettings.itemSprites && OldAnimationsSettings.INSTANCE.enabled && !model.isGui3d() && TransformTypeHook.shouldNotHaveGlint()) {
            GlStateManager.translate(0.0F, 0.0F, -0.0625F);
        }
    }

    @Inject(method = "renderItemModelTransform", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/resources/model/IBakedModel;)V"))
    public void modifyModelPosition(ItemStack stack, IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType, CallbackInfo ci) {
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
                        && (oldanimations$entityLivingBase instanceof EntityPlayer || !OldAnimationsSettings.entityTransforms)) {
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
    public void fixGlint(ItemStack stack, int xPosition, int yPosition, CallbackInfo ci) {
        if (OldAnimationsSettings.INSTANCE.enabled) {
            GlStateManager.enableDepth();
        }
    }

    @Redirect(method = "renderItemIntoGUI", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemModelMesher;getItemModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/resources/model/IBakedModel;"))
    public IBakedModel rodBowModelTexture(ItemModelMesher instance, ItemStack stack) {
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

}
