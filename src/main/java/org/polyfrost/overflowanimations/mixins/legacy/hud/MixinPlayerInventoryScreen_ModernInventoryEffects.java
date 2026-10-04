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

package org.polyfrost.overflowanimations.mixins.legacy.hud;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.screen.game.inventory.InventoryMenuScreen;
import net.minecraft.client.gui.screen.game.inventory.PlayerInventoryScreen;
import net.minecraft.client.render.platform.GlStateManager;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.living.effect.StatusEffect;
import net.minecraft.entity.living.effect.StatusEffectInstance;
import net.minecraft.inventory.menu.InventoryMenu;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.util.enums.InventoryEffectsSetting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(PlayerInventoryScreen.class)
public abstract class MixinPlayerInventoryScreen_ModernInventoryEffects extends InventoryMenuScreen {
    private MixinPlayerInventoryScreen_ModernInventoryEffects(final InventoryMenu menu) {
        super(menu);
    }

    @ModifyExpressionValue(method = "checkStatusEffects", at = {@At(value = "CONSTANT", args = "intValue=160"), @At(value = "CONSTANT", args = "intValue=200")})
    private int overflowanimations$keepInventoryCentred(final int original) {
        return OverflowAnimationsConfig.instance().screen.inventoryEffects == InventoryEffectsSetting.V1_21_10 ? 0 : original;
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/game/inventory/PlayerInventoryScreen;drawStatusEffects()V"))
    private void overflowanimations$drawEffectsOnRight(final PlayerInventoryScreen instance, final Operation<Void> original, @Local(argsOnly = true, ordinal = 0) final int mouseX, @Local(argsOnly = true, ordinal = 1) final int mouseY) {
        if (OverflowAnimationsConfig.instance().screen.inventoryEffects != InventoryEffectsSetting.V1_21_10) {
            original.call(instance);
            return;
        }

        final int left = this.x + this.backgroundWidth + 2;
        final int available = this.width - left;
        final List<StatusEffectInstance> effects = new ArrayList<>(this.minecraft.player.getStatusEffects());
        if (effects.isEmpty() || available < 32) {
            return;
        }

        effects.sort((a, b) -> overflowanimations$compareEffects(a, b));

        final boolean wide = available >= 120;
        final int spacing = effects.size() > 5 ? 132 / (effects.size() - 1) : 33;
        StatusEffectInstance hovered = null;
        int top = this.y;

        GlStateManager.disableLighting();
        for (final StatusEffectInstance instanceEffect : effects) {
            final StatusEffect effect = StatusEffect.BY_ID[instanceEffect.getId()];
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.minecraft.getTextureManager().bind(MENU_LOCATION);
            final int half = wide ? 60 : 16;
            this.drawTexture(left, top, 0, 166, half, 32);
            this.drawTexture(left + half, top, 140 - half, 166, half, 32);

            if (effect.hasIcon()) {
                final int icon = effect.getIconIndex();
                this.drawTexture(left + (wide ? 6 : 7), top + 7, icon % 8 * 18, 198 + icon / 8 * 18, 18, 18);
            }

            if (wide) {
                this.textRenderer.drawWithShadow(overflowanimations$effectName(instanceEffect, effect), left + 10 + 18, top + 6, 0xFFFFFF);
                this.textRenderer.drawWithShadow(StatusEffect.getDurationString(instanceEffect), left + 10 + 18, top + 6 + 10, 0x7F7F7F);
            } else if (mouseX >= left && mouseX <= left + 33 && mouseY >= top && mouseY <= top + spacing) {
                hovered = instanceEffect;
            }

            top += spacing;
        }

        if (hovered != null) {
            final StatusEffect effect = StatusEffect.BY_ID[hovered.getId()];
            this.renderTooltip(Arrays.asList(overflowanimations$effectName(hovered, effect), StatusEffect.getDurationString(hovered)), mouseX, mouseY);
        }
    }

    @Unique
    private static int overflowanimations$compareEffects(final StatusEffectInstance a, final StatusEffectInstance b) {
        final int color = Integer.compare(StatusEffect.BY_ID[a.getId()].getPotionColor(), StatusEffect.BY_ID[b.getId()].getPotionColor());
        final int ambient = Boolean.compare(a.isAmbient(), b.isAmbient());
        if ((a.getDuration() > 32147 && b.getDuration() > 32147) || (a.isAmbient() && b.isAmbient())) {
            return ambient != 0 ? ambient : color;
        }

        final int duration = Integer.compare(a.getDuration(), b.getDuration());
        return ambient != 0 ? ambient : duration != 0 ? duration : color;
    }

    @Unique
    private static String overflowanimations$effectName(final StatusEffectInstance instance, final StatusEffect effect) {
        final String name = I18n.translate(effect.getTranslationKey());
        final int amplifier = instance.getAmplifier();
        return amplifier >= 1 && amplifier <= 3 ? name + " " + I18n.translate("enchantment.level." + (amplifier + 1)) : name;
    }
}
