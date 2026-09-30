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

package org.polyfrost.overflowanimations.mixins.legacy.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.render.model.block.BakedModel;
import net.minecraft.client.render.platform.GlStateManager;
import net.minecraft.client.resource.model.ModelTransformations;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.config.category.ItemsConfigCategory;
import org.polyfrost.overflowanimations.legacy.entity.LegacyItemRendering;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntityRenderer.class)
public abstract class MixinItemEntityRenderer_DroppedItems extends EntityRenderer<ItemEntity> {
    protected MixinItemEntityRenderer_DroppedItems(final EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @WrapOperation(method = "render(Lnet/minecraft/entity/ItemEntity;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/ItemRenderer;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/block/BakedModel;)V"))
    private void overflowanimations$groundTransform(final ItemRenderer instance, final ItemStack item, final BakedModel model, final Operation<Void> original) {
        final ModelTransformations.Type previous = LegacyItemRendering.transform;
        LegacyItemRendering.transform = ModelTransformations.Type.GROUND;
        try {
            original.call(instance, item, model);
        } finally {
            LegacyItemRendering.transform = previous;
        }
    }

    @ModifyArg(method = "applyItemBobbing", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/platform/GlStateManager;rotatef(FFFF)V"), index = 0)
    private float overflowanimations$itemDropsFaceCamera(final float angle, @Local(ordinal = 0) final boolean gui3d) {
        return !gui3d && OverflowAnimationsConfig.instance().items.itemDropsFaceCamera ? 180.0F - this.dispatcher.cameraYaw : angle;
    }

    @Inject(method = "applyItemBobbing", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/platform/GlStateManager;rotatef(FFFF)V", shift = At.Shift.AFTER))
    private void overflowanimations$itemDropsFaceCameraPitch(final ItemEntity entity, final double dx, final double dy, final double dz, final float tickDelta, final BakedModel model, final CallbackInfoReturnable<Integer> cir) {
        final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
        if (!model.isGui3d() && items.itemDropsFaceCamera && items.itemDropsFaceCameraRotationFix) {
            GlStateManager.rotatef(-this.dispatcher.cameraPitch, 1.0F, 0.0F, 0.0F);
        }
    }

    @Inject(method = "applyItemBobbing", at = @At("TAIL"))
    private void overflowanimations$droppedTransforms(final ItemEntity entity, final double dx, final double dy, final double dz, final float tickDelta, final BakedModel model, final CallbackInfoReturnable<Integer> cir) {
        final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
        LegacyItemRendering.applyTransform(items.droppedPositionX, items.droppedPositionY, items.droppedPositionZ, items.droppedRotationX, items.droppedRotationY, items.droppedRotationZ, items.droppedScale);
    }
}
