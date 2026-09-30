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

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.ProjectileRenderer;
import net.minecraft.client.render.platform.GlStateManager;
import net.minecraft.client.resource.model.ModelTransformations;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.config.category.ItemsConfigCategory;
import org.polyfrost.overflowanimations.legacy.entity.LegacyItemRendering;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ProjectileRenderer.class)
public abstract class MixinProjectileRenderer_FireballModel extends EntityRenderer<ProjectileEntity> {
    protected MixinProjectileRenderer_FireballModel(final EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Inject(method = "render(Lnet/minecraft/entity/projectile/ProjectileEntity;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/platform/GlStateManager;scalef(FFF)V", shift = At.Shift.AFTER), cancellable = true)
    private void overflowanimations$fireballModel(final ProjectileEntity entity, final double dx, final double dy, final double dz, final float yaw, final float tickDelta, final CallbackInfo ci) {
        if (!OverflowAnimationsConfig.instance().items.fireballModel) {
            return;
        }

        GlStateManager.scalef(1.5F, 1.5F, 1.5F);
        GlStateManager.rotatef(180.0F - this.dispatcher.cameraYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotatef(-this.dispatcher.cameraPitch, 1.0F, 0.0F, 0.0F);
        GlStateManager.translatef(0.0F, 0.125F, 0.0F);
        GlStateManager.scalef(0.5F, 0.5F, 0.5F);
        overflowanimations$fireballTransforms();
        Minecraft.getInstance().getItemRenderer().renderItemInHand(new ItemStack(Items.FIRE_CHARGE), ModelTransformations.Type.GROUND);
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.render(entity, dx, dy, dz, yaw, tickDelta);
        ci.cancel();
    }

    @Inject(method = "render(Lnet/minecraft/entity/projectile/ProjectileEntity;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/vertex/BufferBuilder;begin(ILnet/minecraft/client/render/vertex/VertexFormat;)V"))
    private void overflowanimations$fireballSpriteTransforms(final CallbackInfo ci) {
        overflowanimations$fireballTransforms();
    }

    @Unique
    private static void overflowanimations$fireballTransforms() {
        final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
        LegacyItemRendering.applyTransform(items.fireballPositionX, items.fireballPositionY, items.fireballPositionZ, items.fireballRotationX, items.fireballRotationY, items.fireballRotationZ, items.fireballScale);
    }
}
