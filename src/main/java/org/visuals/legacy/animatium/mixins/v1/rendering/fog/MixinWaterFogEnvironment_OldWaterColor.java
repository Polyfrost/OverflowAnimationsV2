/**
 * Animatium
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

package org.visuals.legacy.animatium.mixins.v1.rendering.fog;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.environment.WaterFogEnvironment;
//? if <26.3 {
/*import net.minecraft.util.ARGB;
*///?}
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
//? if >=26.3 {
import org.joml.Vector3f;
import org.joml.Vector3fc;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(WaterFogEnvironment.class)
public abstract class MixinWaterFogEnvironment_OldWaterColor {
    @ModifyReturnValue(method = "getBaseColor", at = @At("RETURN"))
    //? if 1.21.11 {
    /*private int animatium$oldWaterFogColor(final int original, @Local(argsOnly = true, ordinal = 0) final Camera camera, @Local(argsOnly = true, ordinal = 0) final ClientLevel level) {
    *///?} elif >=26.1 <26.3 {
    /*private int animatium$oldWaterFogColor(final int original, @Local(argsOnly = true, name = "camera") final Camera camera, @Local(argsOnly = true, name = "level") final ClientLevel level) {
    *///?} else {
    private Vector3fc animatium$oldWaterFogColor(final Vector3fc original, @Local(argsOnly = true, name = "camera") final Camera camera, @Local(argsOnly = true, name = "level") final ClientLevel level) {
    //?}
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.oldWaterColorFog) {
            float value = 0.0F;
            if (camera.entity() instanceof LivingEntity livingEntity) {
                value = EnchantmentHelper.getEnchantmentLevel(level.registryAccess().getOrThrow(Enchantments.RESPIRATION), livingEntity) * 0.2F;
                if (livingEntity.hasEffect(MobEffects.WATER_BREATHING)) {
                    value *= 0.9F;
                }
            }

            //? if <26.3 {
            /*return ARGB.colorFromFloat(1.0F, 0.02F + value, 0.02F + value, 0.2F + value);
            *///?} else {
            return new Vector3f(0.02F + value, 0.02F + value, 0.2F + value);
            //?}
        } else {
            return original;
        }
    }
}
