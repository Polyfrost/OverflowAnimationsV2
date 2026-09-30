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

package org.polyfrost.overflowanimations.mixins.v1.entity.glint;

//? if <1.21.9 {
/*import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.polyfrost.overflowanimations.handler.rendering.pipeline.ArmorGlint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(EquipmentLayerRenderer.class)
public abstract class MixinEquipmentLayerRenderer_ArmorGlint {
    @Unique
    private static final String RENDER_LAYERS_TARGET = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/resources/Identifier;)V";

    @Unique
    private List<RenderType> overflowanimations$glint = List.of();

    @ModifyExpressionValue(method = RENDER_LAYERS_TARGET, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;hasFoil()Z"))
    private boolean overflowanimations$replaceGlint(final boolean original) {
        this.overflowanimations$glint = original ? ArmorGlint.layers() : List.of();
        return original && this.overflowanimations$glint.isEmpty();
    }

    @WrapOperation(method = RENDER_LAYERS_TARGET, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/Model;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V"))
    private void overflowanimations$renderGlint(final Model model, final PoseStack poseStack, final VertexConsumer buffer, final int light, final int overlay, final int color, final Operation<Void> original, @Local(argsOnly = true) final MultiBufferSource bufferSource) {
        original.call(model, poseStack, buffer, light, overlay, color);
        final int glintOverlay = ArmorGlint.tintsWithArmor() ? overlay : OverlayTexture.NO_OVERLAY;
        for (final RenderType renderType : this.overflowanimations$glint) {
            original.call(model, poseStack, bufferSource.getBuffer(renderType), light, glintOverlay, color);
        }

        this.overflowanimations$glint = List.of();
    }
}
*///?} else {
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.OrderedSubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
//? if <26.3 {
/*import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
*///?}
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
//? if <26.3 {
/*import net.minecraft.client.renderer.texture.TextureAtlasSprite;
*///?} else {
import net.minecraft.client.renderer.texture.UvMapping;
//?}
import org.polyfrost.overflowanimations.handler.rendering.pipeline.ArmorGlint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(EquipmentLayerRenderer.class)
public abstract class MixinEquipmentLayerRenderer_ArmorGlint {
    @Unique
    //? if <26.4 {
    private static final String RENDER_LAYERS_TARGET = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;II)V";
    //?} else {
    /*private static final String RENDER_LAYERS_TARGET = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;II)I";
    *///?}

    @Unique
    private List<RenderType> overflowanimations$glint = List.of();

    @ModifyExpressionValue(method = RENDER_LAYERS_TARGET, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;hasFoil()Z"))
    private boolean overflowanimations$replaceGlint(final boolean original) {
        this.overflowanimations$glint = original ? ArmorGlint.layers() : List.of();
        return original && this.overflowanimations$glint.isEmpty();
    }

    //? if <26.3 {
    /*@WrapOperation(method = RENDER_LAYERS_TARGET, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/OrderedSubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IIILnet/minecraft/client/renderer/texture/TextureAtlasSprite;ILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V", ordinal = 0))
    private <S> void overflowanimations$submitGlint(final OrderedSubmitNodeCollector instance, final Model<? super S> model, final S state, final PoseStack poseStack, final RenderType renderType, final int lightCoords, final int overlayCoords, final int color, final TextureAtlasSprite sprite, final int outlineColor, final ModelFeatureRenderer.CrumblingOverlay crumblingOverlay, final Operation<Void> original) {
        original.call(instance, model, state, poseStack, renderType, lightCoords, overlayCoords, color, sprite, outlineColor, crumblingOverlay);
        final int glintOverlay = ArmorGlint.tintsWithArmor() ? overlayCoords : OverlayTexture.NO_OVERLAY;
        for (final RenderType glint : this.overflowanimations$glint) {
            original.call(instance, model, state, poseStack, glint, lightCoords, glintOverlay, color, null, outlineColor, null);
        }

        this.overflowanimations$glint = List.of();
    }
    *///?} else {
    @WrapOperation(method = RENDER_LAYERS_TARGET, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/OrderedSubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IIILnet/minecraft/client/renderer/texture/UvMapping;I)V", ordinal = 0))
    private <S> void overflowanimations$submitGlint(final OrderedSubmitNodeCollector instance, final Model<? super S> model, final S state, final PoseStack poseStack, final RenderType renderType, final int lightCoords, final int overlayCoords, final int color, final UvMapping uvMapping, final int outlineColor, final Operation<Void> original) {
        original.call(instance, model, state, poseStack, renderType, lightCoords, overlayCoords, color, uvMapping, outlineColor);
        final int glintOverlay = ArmorGlint.tintsWithArmor() ? overlayCoords : OverlayTexture.NO_OVERLAY;
        for (final RenderType glint : this.overflowanimations$glint) {
            original.call(instance, model, state, poseStack, glint, lightCoords, glintOverlay, color, null, 0);
        }

        this.overflowanimations$glint = List.of();
    }
    //?}
}
//?}
