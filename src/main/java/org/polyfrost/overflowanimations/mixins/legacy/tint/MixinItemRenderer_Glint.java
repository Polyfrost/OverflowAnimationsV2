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
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.config.category.ItemsConfigCategory;
import org.polyfrost.overflowanimations.legacy.compat.Argentum;
import org.polyfrost.overflowanimations.legacy.tint.LegacyGlint;
import org.polyfrost.overflowanimations.util.enums.ItemGlintSetting;
import org.polyfrost.overflowanimations.util.enums.PotionGlintSetting;
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

    @Unique
    private static ItemGlintSetting overflowanimations$style;

    @WrapOperation(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/ItemRenderer;renderEnchantmentGlint(Lnet/minecraft/client/render/model/block/BakedModel;)V"))
    private void overflowanimations$modifyGlint(final ItemRenderer instance, BakedModel model, final Operation<Void> original, @Local(argsOnly = true) final ItemStack item) {
        if (!OverflowAnimations.isEnabled()) {
            original.call(instance, model);
            return;
        }
        final ItemsConfigCategory config = OverflowAnimationsConfig.instance().items;
        if (LegacyGlint.renderingGui && config.itemGlint == ItemGlintSetting.V1_7 && GL11.glIsEnabled(GL11.GL_DEPTH_TEST)) {
            return;
        }

        if (item.getItem() instanceof PotionItem && config.potionGlint == PotionGlintSetting.V1_7) {
            model = LegacyGlint.potionLiquid(model);
        }

        final boolean custom = config.itemGlint == ItemGlintSetting.V1_15 || config.itemGlint == ItemGlintSetting.V1_7 && !LegacyGlint.renderingGui;
        overflowanimations$style = custom ? config.itemGlint : null;
        try {
            original.call(instance, model);
        } finally {
            overflowanimations$style = null;
        }
    }

    @Inject(method = "renderEnchantmentGlint", at = @At("HEAD"), cancellable = true)
    private void overflowanimations$customGlint(final BakedModel model, final CallbackInfo ci) {
        if (overflowanimations$style != null) {
            this.overflowanimations$renderGlint(model, overflowanimations$style == ItemGlintSetting.V1_15);
            ci.cancel();
        }
    }

    @Unique
    private void overflowanimations$renderGlint(final BakedModel model, final boolean modern) {
        GlStateManager.depthMask(false);
        GlStateManager.depthFunc(GL11.GL_EQUAL);
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
        this.textureManager.bind(modern ? LegacyGlint.MODERN_ITEM_TEXTURE : ENCHANTMENT_GLINT_LOCATION);
        GlStateManager.matrixMode(GL11.GL_TEXTURE);
        if (modern) {
            final int strength = (int) (LegacyGlint.modernStrength() * 255.0F);
            GlStateManager.pushMatrix();
            LegacyGlint.setupModernTexturing(8.0F, LegacyGlint.modernSpeed());
            this.render(model, 0xFF000000 | strength << 16 | strength << 8 | strength);
            GlStateManager.popMatrix();
        } else {
            final BakedModel glint = LegacyGlint.itemSpace(model);
            for (int layer = 0; layer < 2; layer++) {
                GlStateManager.pushMatrix();
                LegacyGlint.setupItemTexturing(layer);
                this.render(glint, LegacyGlint.ITEM_COLOR);
                GlStateManager.popMatrix();
            }
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
        if (Argentum.bakingGuiItem()) {
            return;
        }

        if (OverflowAnimations.isEnabled() && config.itemGlint == ItemGlintSetting.V1_7 && item.hasEnchantmentGlint()) {
            this.textureManager.bind(ENCHANTMENT_GLINT_LOCATION);
            LegacyGlint.renderGuiGlint(x, y, 100.0F + this.zOffset);
            this.textureManager.bind(TextureAtlas.BLOCKS_LOCATION);
        }
    }
}
