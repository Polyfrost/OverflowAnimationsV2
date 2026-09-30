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

package org.polyfrost.overflowanimations.mixins.legacy.tint;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.render.model.block.BakedModel;
import net.minecraft.client.render.platform.GlStateManager;
import net.minecraft.client.render.texture.TextureAtlas;
import net.minecraft.client.render.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.resource.Identifier;
import org.lwjgl.opengl.GL11;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.config.category.ItemsConfigCategory;
import org.polyfrost.overflowanimations.legacy.tint.LegacyGlint;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer_Glint {
    @Shadow
    @Final
    private static Identifier ENCHANTMENT_GLINT_LOCATION;

    @Shadow
    @Final
    private TextureManager textureManager;

    @Shadow
    public float zOffset;

    @Shadow
    protected abstract void render(BakedModel model, int color);

    @WrapOperation(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/ItemRenderer;renderEnchantmentGlint(Lnet/minecraft/client/render/model/block/BakedModel;)V"))
    private void overflowanimations$modifyGlint(final ItemRenderer instance, BakedModel model, final Operation<Void> original, @Local(argsOnly = true) final ItemStack item) {
        final ItemsConfigCategory config = OverflowAnimationsConfig.instance().items;
        final boolean potion = item.getItem() instanceof PotionItem;
        if (potion && config.disablePotionGlint || LegacyGlint.renderingGui && config.legacyGuiGlint && GL11.glIsEnabled(GL11.GL_DEPTH_TEST)) {
            return;
        }

        if (potion && config.legacyPotionGlint) {
            model = LegacyGlint.potionLiquid(model);
        }

        if (config.legacyGlint && !LegacyGlint.renderingGui) {
            this.overflowanimations$renderLegacyGlint(model);
        } else {
            original.call(instance, model);
        }
    }

    @Unique
    private void overflowanimations$renderLegacyGlint(final BakedModel model) {
        final BakedModel glint = LegacyGlint.itemSpace(model);
        GlStateManager.depthMask(false);
        GlStateManager.depthFunc(GL11.GL_EQUAL);
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
        this.textureManager.bind(ENCHANTMENT_GLINT_LOCATION);
        GlStateManager.matrixMode(GL11.GL_TEXTURE);
        for (int layer = 0; layer < 2; layer++) {
            GlStateManager.pushMatrix();
            LegacyGlint.setupItemTexturing(layer);
            this.render(glint, LegacyGlint.ITEM_COLOR);
            GlStateManager.popMatrix();
        }

        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableLighting();
        GlStateManager.depthFunc(GL11.GL_LEQUAL);
        GlStateManager.depthMask(true);
        this.textureManager.bind(TextureAtlas.BLOCKS_LOCATION);
    }

    @WrapOperation(method = "renderGuiItemModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/ItemRenderer;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/block/BakedModel;)V"))
    private void overflowanimations$markGuiRendering(final ItemRenderer instance, final ItemStack item, final BakedModel model, final Operation<Void> original) {
        final boolean previous = LegacyGlint.renderingGui;
        LegacyGlint.renderingGui = true;
        try {
            original.call(instance, item, model);
        } finally {
            LegacyGlint.renderingGui = previous;
        }
    }

    @Inject(method = "renderGuiItemModel", at = @At("TAIL"))
    private void overflowanimations$renderLegacyGuiGlint(final ItemStack item, final int x, final int y, final CallbackInfo ci) {
        final ItemsConfigCategory config = OverflowAnimationsConfig.instance().items;
        if (config.legacyGuiGlint && item.hasEnchantmentGlint() && !(config.disablePotionGlint && item.getItem() instanceof PotionItem)) {
            this.textureManager.bind(ENCHANTMENT_GLINT_LOCATION);
            LegacyGlint.renderGuiGlint(x, y, 100.0F + this.zOffset);
            this.textureManager.bind(TextureAtlas.BLOCKS_LOCATION);
        }
    }
}
