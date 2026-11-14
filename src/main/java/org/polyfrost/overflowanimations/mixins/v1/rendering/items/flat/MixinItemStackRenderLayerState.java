/**
 * OverflowAnimations
 * The all-you-could-want legacy animations mod for modern minecraft versions.
 * Brings back animations from the 1.7/1.8 era and more.
 * <p>
 * Copyright (C) 2024-2027 lowercasebtw
 * Copyright (C) 2024-2027 mixces
 * Copyright (C) 2024-2027 Contributors to the project retain their copyright
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * "MINECRAFT" LINKING EXCEPTION TO THE GPL
 */

package org.polyfrost.overflowanimations.mixins.v1.rendering.items.flat;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
//? if <1.21.5 {
/*import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.resources.model.BakedModel;
*///?} elif <1.21.9 {
/*import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransform;
*///?} elif <26.1 {
/*import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransform;
*///?}
import net.minecraft.client.renderer.item.ItemStackRenderState;
//? if >=26.1 {
import net.minecraft.client.resources.model.cuboid.ItemTransform;
import net.minecraft.client.resources.model.geometry.BakedQuad;
//?}
//? if >=26.3 {
import net.minecraft.client.resources.model.geometry.ItemQuads;
//?}
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.util.ItemTransformOverrides;
import org.polyfrost.overflowanimations.util.ItemUtilKt;
import org.polyfrost.overflowanimations.util.enums.FishingRodVersionSetting;
//? if >=1.21.5
import org.polyfrost.overflowanimations.util.states.ModelIdHolder;
import org.joml.Quaternionf;
import org.joml.Vector3fc;

//? if >=1.21.5 {
import java.util.List;
import java.util.stream.Collectors;
//?}

@Mixin(ItemStackRenderState.LayerRenderState.class)
//? if <1.21.5 {
/*public abstract class MixinItemStackRenderLayerState {
*///?} else {
public abstract class MixinItemStackRenderLayerState implements ModelIdHolder {
//?}
    //? if <1.21.5 {
    /*@Shadow
    abstract ItemTransform transform();
    *///?} elif <26.1 {
    /*@Shadow
    ItemTransform transform;
    *///?} else {
    @Shadow
    private ItemTransform itemTransform;
    //?}

    //? if <26.1 {
    /*@Shadow(aliases = "field_55345")
    *///?} else {
    @Shadow(aliases = "this$0")
    //?}
    @Final
    ItemStackRenderState itemStackRenderState;

    //? if >=1.21.5 {
    @Shadow
    //? if <26.1 {
    /*boolean usesBlockLight;
    *///?} else {
    private boolean usesBlockLight;
    //?}

    //? if <1.21.9 {
    /*@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderItem(Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II[ILjava/util/List;Lnet/minecraft/client/renderer/rendertype/RenderType;Lnet/minecraft/client/renderer/item/ItemStackRenderState$FoilType;)V"), index = 6)
    *///?} elif <26.1 {
    /*@ModifyArg(method = "submit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitItem(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/item/ItemDisplayContext;III[ILjava/util/List;Lnet/minecraft/client/renderer/rendertype/RenderType;Lnet/minecraft/client/renderer/item/ItemStackRenderState$FoilType;)V"), index = 6)
    *///?} elif >=26.1 <26.3 {
    /*@ModifyArg(method = "submit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitItem(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/item/ItemDisplayContext;III[ILjava/util/List;Lnet/minecraft/client/renderer/item/ItemStackRenderState$FoilType;)V"), index = 6)
    *///?}
    //? if <26.3 {
    /*private List<BakedQuad> overflowanimations$itemDrops2D(final List<BakedQuad> quads) {
    *///?} else {
    @ModifyArg(method = "submit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitItem(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/item/ItemDisplayContext;III[ILnet/minecraft/client/resources/model/geometry/ItemQuads;Lnet/minecraft/client/renderer/item/ItemStackRenderState$FoilType;)V"), index = 6)
    private ItemQuads overflowanimations$itemDrops2D(final ItemQuads quads) {
    //?}
        if (OverflowAnimations.isEnabled() && overflowanimations$isTransformationModeValid() && !this.usesBlockLight) {
            //? if <26.3 {
            /*return quads.stream().filter(baked -> baked.direction() == Direction.SOUTH).collect(Collectors.toList());
            *///?} else {
            return new ItemQuads(overflowanimations$flatten(quads.all()), overflowanimations$flatten(quads.solid()), overflowanimations$flatten(quads.translucent()));
            //?}
        } else {
            return quads;
        }
    }
    //?}

    //? if <1.21.5 {
    /*@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderItem(Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II[ILnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/client/renderer/rendertype/RenderType;Lnet/minecraft/client/renderer/item/ItemStackRenderState$FoilType;)V"), index = 8)
    *///?} elif <1.21.9 {
    /*@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderItem(Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II[ILjava/util/List;Lnet/minecraft/client/renderer/rendertype/RenderType;Lnet/minecraft/client/renderer/item/ItemStackRenderState$FoilType;)V"), index = 8)
    *///?} elif <26.1 {
    /*@ModifyArg(method = "submit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitItem(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/item/ItemDisplayContext;III[ILjava/util/List;Lnet/minecraft/client/renderer/rendertype/RenderType;Lnet/minecraft/client/renderer/item/ItemStackRenderState$FoilType;)V"), index = 8)
    *///?} elif >=26.1 <26.3 {
    /*@ModifyArg(method = "submit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitItem(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/item/ItemDisplayContext;III[ILjava/util/List;Lnet/minecraft/client/renderer/item/ItemStackRenderState$FoilType;)V"), index = 7)
    *///?} else {
    @Unique
    private static List<BakedQuad> overflowanimations$flatten(final List<BakedQuad> quads) {
        return quads.stream().filter(baked -> baked.direction() == Direction.SOUTH).collect(Collectors.toList());
    }
    //?}

    //? if >=26.3 {
    @ModifyArg(method = "submit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitItem(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/item/ItemDisplayContext;III[ILnet/minecraft/client/resources/model/geometry/ItemQuads;Lnet/minecraft/client/renderer/item/ItemStackRenderState$FoilType;)V"), index = 7)
    //?}
    private ItemStackRenderState.FoilType overflowanimations$disableGlintOn2DItems(final ItemStackRenderState.FoilType foilType) {
        final boolean glintDropped = !OverflowAnimationsConfig.instance().items.glintOnItemDrops2D;
        final boolean glintFramed = !OverflowAnimationsConfig.instance().items.glintOnItemFramed2D;
        if (OverflowAnimations.isEnabled() && this.overflowanimations$isTransformationModeValid() && (
                (glintDropped && this.itemStackRenderState.displayContext == ItemDisplayContext.GROUND) ||
                (glintFramed && this.itemStackRenderState.displayContext == ItemDisplayContext.FIXED))) {
            return ItemStackRenderState.FoilType.NONE;
        } else {
            return foilType;
        }
    }

    //? if <1.21.5 {
    /*@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/model/ItemTransform;apply(ZLcom/mojang/blaze3d/vertex/PoseStack;)V"))
    private void overflowanimations$vanillaThirdPersonTransform(final ItemTransform transform, final boolean leftHand, final PoseStack pose, final Operation<Void> original) {
    *///?} else {
    //? if <1.21.9 {
    /*@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/model/ItemTransform;apply(ZLcom/mojang/blaze3d/vertex/PoseStack$Pose;)V"))
    *///?} elif <26.1 {
    /*@WrapOperation(method = "submit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/model/ItemTransform;apply(ZLcom/mojang/blaze3d/vertex/PoseStack$Pose;)V"))
    *///?} else {
    @WrapOperation(method = "applyTransform", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/cuboid/ItemTransform;apply(ZLcom/mojang/blaze3d/vertex/PoseStack$Pose;)V"))
    //?}
    private void overflowanimations$vanillaThirdPersonTransform(final ItemTransform transform, final boolean leftHand, final PoseStack.Pose pose, final Operation<Void> original) {
    //?}
        ItemTransform applied = transform;
        if (ItemTransformOverrides.legacyThirdPerson == this.itemStackRenderState) {
            final ItemTransform vanilla = ItemTransformOverrides.vanillaThirdPersonTransform(this.itemStackRenderState.overflowanimations$getItemStack(), this.overflowanimations$getModelId(), this.itemStackRenderState.displayContext);
            if (vanilla != null) {
                applied = vanilla;
            }
        }

        original.call(applied, leftHand, pose);
    }

    //? if <1.21.5 {
    /*@Shadow
    BakedModel model;

    @Unique
    private Identifier overflowanimations$getModelId() {
        return this.model == null ? null : ItemTransformOverrides.bakedModelIds.get(this.model);
    }
    *///?} else {
    @Unique
    private Identifier overflowanimations$modelId;

    @Unique
    private Identifier overflowanimations$getModelId() {
        return this.overflowanimations$modelId;
    }

    @Override
    public void overflowanimations$setModelId(final Identifier id) {
        this.overflowanimations$modelId = id;
    }

    @Inject(method = "clear", at = @At("HEAD"))
    private void overflowanimations$clearModelId(final CallbackInfo ci) {
        this.overflowanimations$modelId = null;
    }
    //?}

    // TODO/MOVE
    //? if <1.21.5 {
    /*@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/model/ItemTransform;apply(ZLcom/mojang/blaze3d/vertex/PoseStack;)V"))
    *///?} elif <1.21.9 {
    /*@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/model/ItemTransform;apply(ZLcom/mojang/blaze3d/vertex/PoseStack$Pose;)V"))
    *///?}
    //? if <1.21.9 {
    /*private void overflowanimations$itemPositions(final PoseStack poseStack, final MultiBufferSource bufferSource, final int packedLight, final int packedOverlay, final CallbackInfo ci) {
    *///?} elif <26.1 {
    /*@Inject(method = "submit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/model/ItemTransform;apply(ZLcom/mojang/blaze3d/vertex/PoseStack$Pose;)V"))
    private void overflowanimations$itemPositions(final PoseStack poseStack, final SubmitNodeCollector nodeCollector, final int packedLight, final int packedOverlay, final int outlineColor, final CallbackInfo ci) {
    *///?} else {
    @Inject(method = "applyTransform", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/cuboid/ItemTransform;apply(ZLcom/mojang/blaze3d/vertex/PoseStack$Pose;)V"))
    private void overflowanimations$itemPositions(final PoseStack.Pose localPose, final CallbackInfo ci) {
    //?}
        if (OverflowAnimations.isEnabled()) {
            final ItemStack stack = this.itemStackRenderState.overflowanimations$getItemStack();
            if (!stack.isEmpty()) {
                //? if <1.21.5 {
                /*final PoseStack localPose = poseStack;
                *///?} elif <26.1 {
                /*final PoseStack.Pose localPose = poseStack.last();
                *///?}
                final ItemDisplayContext itemDisplayContext = this.itemStackRenderState.displayContext;
                final boolean isGui = itemDisplayContext == ItemDisplayContext.GUI;
                final boolean isFirstPerson = itemDisplayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || itemDisplayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND;
                final boolean isThirdPerson = itemDisplayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND || itemDisplayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
                //? if <1.21.5 {
                /*final ItemTransform transform = this.transform();
                final Vector3fc translation = transform.translation, rotation = transform.rotation, scale = transform.scale;
                *///?} elif <26.1 {
                /*final ItemTransform transform = this.transform;
                *///?} else {
                final ItemTransform transform = this.itemTransform;
                //?}
                //? if >=1.21.5 {
                final Vector3fc translation = transform.translation(), rotation = transform.rotation(), scale = transform.scale();
                //?}
                final float x = translation.x();
                final float y = translation.y();
                final float z = translation.z();
                if (OverflowAnimationsConfig.instance().items.fishingRodVersion != FishingRodVersionSetting.VANILLA && ItemUtilKt.isFishingRodItem(stack) && isFirstPerson) {
                    final int ordinal = OverflowAnimationsConfig.instance().items.fishingRodVersion.ordinal();
                    if (ordinal <= FishingRodVersionSetting.V1_8.ordinal()) {
                        localPose.translate(0.070625F, 0.1F, 0.020625F);
                    }

                    localPose.translate(x, y, z);
                    if (ordinal == FishingRodVersionSetting.V1_7.ordinal()) {
                        overflowanimations$rotate(localPose, Axis.YP.rotationDegrees(180));
                    }

                    localPose.translate(-x, -y, -z);
                }

                if (OverflowAnimationsConfig.instance().items.thinBlockPositions && ItemUtilKt.isThinBlockItem(stack)) {
                    if (isFirstPerson) {
                        localPose.translate(0.0F, -4.2F * 0.0625F, 0.0F);
                    } else if (isThirdPerson) {
                        localPose.translate(0.0F, 0.0F, -2.0F * 0.0625F);
                    }
                }

                // TODO/NEED TO FIX
                if (OverflowAnimationsConfig.instance().items.skullPosition && ItemUtilKt.isSkullBlock(stack) && isGui) {
                    localPose.translate(x, y, z);
                    overflowanimations$rotate(localPose, Axis.XP.rotationDegrees(rotation.z()));
                    overflowanimations$rotate(localPose, Axis.YP.rotationDegrees(rotation.y()));
                    overflowanimations$rotate(localPose, Axis.ZP.rotationDegrees(rotation.x()));
                    localPose.scale(0.9F, 0.9F, 0.9F);
                    localPose.scale(scale.x(), scale.y(), scale.z());
                    overflowanimations$doInverseTransformations(localPose, translation, rotation, scale);
                }
            }
        }
    }

    //? if <1.21.5 {
    /*@Unique
    private static void overflowanimations$rotate(final PoseStack localPose, final Quaternionf rotation) {
        localPose.mulPose(rotation);
    }

    @Unique
    private static void overflowanimations$doInverseTransformations(final PoseStack localPose, final Vector3fc translation, final Vector3fc rotation, final Vector3fc scale) {
    *///?} else {
    @Unique
    private static void overflowanimations$rotate(final PoseStack.Pose localPose, final Quaternionf rotation) {
        localPose.rotate(rotation);
    }

    @Unique
    private static void overflowanimations$doInverseTransformations(final PoseStack.Pose localPose, final Vector3fc translation, final Vector3fc rotation, final Vector3fc scale) {
    //?}
        localPose.scale(1 / scale.x(), 1 / scale.y(), 1 / scale.z());
        overflowanimations$rotate(localPose, Axis.ZP.rotationDegrees(-rotation.x()));
        overflowanimations$rotate(localPose, Axis.YP.rotationDegrees(-rotation.y()));
        overflowanimations$rotate(localPose, Axis.XP.rotationDegrees(-rotation.z()));
        localPose.translate(-translation.x(), -translation.y(), -translation.z());
    }

    @Unique
    private boolean overflowanimations$isTransformationModeValid() {
        final boolean itemDrops2D = OverflowAnimationsConfig.instance().items.droppedItems2D.isOld();
        final boolean itemFramed2D = OverflowAnimationsConfig.instance().items.itemFramed2D;
        return (itemDrops2D && this.itemStackRenderState.displayContext == ItemDisplayContext.GROUND) || (itemFramed2D && this.itemStackRenderState.displayContext == ItemDisplayContext.FIXED);
    }
}
