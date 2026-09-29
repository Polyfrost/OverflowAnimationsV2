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

// Targets the item model rendering from 1.21.5 (quad lists, record item transforms)
//? if >=1.21.5 {
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
//? if <1.21.9 {
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
import org.polyfrost.overflowanimations.util.ItemUtilKt;
import org.polyfrost.overflowanimations.util.enums.FishingRodVersionSetting;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(ItemStackRenderState.LayerRenderState.class)
public abstract class MixinItemStackRenderLayerState {
    @Shadow
    //? if <26.1 {
    /*ItemTransform transform;
    *///?} else {
    private ItemTransform itemTransform;
    //?}

    //? if <26.1 {
    /*@Shadow(aliases = "field_55345")
    *///?} else {
    @Shadow(aliases = "this$0")
    //?}
    @Final
    ItemStackRenderState itemStackRenderState;

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

    //? if <1.21.9 {
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
        if (OverflowAnimations.isEnabled() && (
                (glintDropped && this.itemStackRenderState.displayContext == ItemDisplayContext.GROUND) ||
                (glintFramed && this.itemStackRenderState.displayContext == ItemDisplayContext.FIXED))) {
            return ItemStackRenderState.FoilType.NONE;
        } else {
            return foilType;
        }
    }

    // TODO/MOVE
    //? if <1.21.9 {
    /*@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/model/ItemTransform;apply(ZLcom/mojang/blaze3d/vertex/PoseStack$Pose;)V"))
    private void overflowanimations$itemPositions(final PoseStack poseStack, final MultiBufferSource bufferSource, final int packedLight, final int packedOverlay, final CallbackInfo ci) {
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
                //? if <26.1 {
                /*final PoseStack.Pose localPose = poseStack.last();
                *///?}
                final ItemDisplayContext itemDisplayContext = this.itemStackRenderState.displayContext;
                final boolean isGui = itemDisplayContext == ItemDisplayContext.GUI;
                final boolean isFirstPerson = itemDisplayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || itemDisplayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND;
                final boolean isThirdPerson = itemDisplayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND || itemDisplayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
                //? if <26.1 {
                /*final ItemTransform transform = this.transform;
                *///?} else {
                final ItemTransform transform = this.itemTransform;
                //?}
                final float x = transform.translation().x();
                final float y = transform.translation().y();
                final float z = transform.translation().z();
                if (OverflowAnimationsConfig.instance().items.fishingRodVersion != FishingRodVersionSetting.VANILLA && ItemUtilKt.isFishingRodItem(stack) && isFirstPerson) {
                    final int ordinal = OverflowAnimationsConfig.instance().items.fishingRodVersion.ordinal();
                    if (ordinal <= FishingRodVersionSetting.V1_8.ordinal()) {
                        localPose.translate(0.070625F, 0.1F, 0.020625F);
                    }

                    localPose.translate(x, y, z);
                    if (ordinal == FishingRodVersionSetting.V1_7.ordinal()) {
                        localPose.rotate(Axis.YP.rotationDegrees(180));
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
                if (OverflowAnimationsConfig.instance().items.skullPosition && ItemUtilKt.isSkullBlock(stack) && isGui && !OverflowAnimationsConfig.instance().items.mobHeadIcons) {
                    localPose.translate(x, y, z);
                    localPose.rotate(Axis.XP.rotationDegrees(transform.rotation().z()));
                    localPose.rotate(Axis.YP.rotationDegrees(transform.rotation().y()));
                    localPose.rotate(Axis.ZP.rotationDegrees(transform.rotation().x()));
                    localPose.scale(0.9F, 0.9F, 0.9F);
                    localPose.scale(transform.scale().x(), transform.scale().y(), transform.scale().z());
                    overflowanimations$doInverseTransformations(localPose, transform);
                }
            }
        }
    }

    @Unique
    private static void overflowanimations$doInverseTransformations(final PoseStack.Pose localPose, final ItemTransform transform) {
        localPose.scale(1 / transform.scale().x(), 1 / transform.scale().y(), 1 / transform.scale().z());
        localPose.rotate(Axis.ZP.rotationDegrees(-transform.rotation().x()));
        localPose.rotate(Axis.YP.rotationDegrees(-transform.rotation().y()));
        localPose.rotate(Axis.XP.rotationDegrees(-transform.rotation().z()));
        localPose.translate(-transform.translation().x(), -transform.translation().y(), -transform.translation().z());
    }

    @Unique
    private boolean overflowanimations$isTransformationModeValid() {
        final boolean itemDrops2D = OverflowAnimationsConfig.instance().items.itemDrops2D;
        final boolean itemFramed2D = OverflowAnimationsConfig.instance().items.itemFramed2D;
        return (itemDrops2D && this.itemStackRenderState.displayContext == ItemDisplayContext.GROUND) || (itemFramed2D && this.itemStackRenderState.displayContext == ItemDisplayContext.FIXED);
    }
}
//?}
