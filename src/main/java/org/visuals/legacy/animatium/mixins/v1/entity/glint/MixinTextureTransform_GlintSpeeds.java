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

package org.visuals.legacy.animatium.mixins.v1.entity.glint;

//? if 1.21.11 {
/*import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
*///?}
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.rendertype.TextureTransform;
//? if >=26.1 {
import org.objectweb.asm.Opcodes;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.visuals.legacy.animatium.Animatium;
import org.visuals.legacy.animatium.config.AnimatiumConfig;

@Mixin(TextureTransform.class)
public abstract class MixinTextureTransform_GlintSpeeds {
    //? if 1.21.11 {
    /*@Definition(id = "Double", type = Double.class)
    @Definition(id = "getInstance", method = "Lnet/minecraft/client/Minecraft;getInstance()Lnet/minecraft/client/Minecraft;")
    @Definition(id = "options", field = "Lnet/minecraft/client/Minecraft;options:Lnet/minecraft/client/Options;")
    @Definition(id = "glintSpeed", method = "Lnet/minecraft/client/Options;glintSpeed()Lnet/minecraft/client/OptionInstance;")
    @Definition(id = "get", method = "Lnet/minecraft/client/OptionInstance;get()Ljava/lang/Object;")
    @Expression("(Double) (getInstance().options.glintSpeed()).get()")
    @ModifyExpressionValue(method = "setupGlintTexturing", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static Double animatium$forceMaxGlintSpeed(final Double original) {
    *///?} else {
    @ModifyExpressionValue(method = "setupGlintTexturing", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/state/OptionsRenderState;glintSpeed:D", opcode = Opcodes.GETFIELD))
    private static double animatium$forceMaxGlintSpeed(final double original) {
    //?}
        if (Animatium.isEnabled() && AnimatiumConfig.instance().other.maxGlintProperties) {
            // 100% glint speed
            return 1.0D;
        } else {
            return original;
        }
    }

    @ModifyExpressionValue(method = "setupGlintTexturing", at = @At(value = "CONSTANT", args = "doubleValue=8.0"))
    private static double animatium$glintSpeed(final double original, @Local(argsOnly = true, ordinal = 0) final float scale) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().items.legacyGlintSpeed && scale == 8.0F) {
            // Value taken from 1.8
            return 1.0D;
        } else {
            return original;
        }
    }

    @ModifyExpressionValue(method = "setupGlintTexturing", at = @At(value = "CONSTANT", args = "floatValue=110000.0"))
    private static float animatium$glintSpeed$horizontal(final float original, @Local(argsOnly = true, ordinal = 0) final float scale) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().items.legacyGlintSpeed && scale == 8.0F) {
            // Value taken from 1.7/1.8
            return 4873.0F;
        } else {
            return original;
        }
    }

    @ModifyExpressionValue(method = "setupGlintTexturing", at = @At(value = "CONSTANT", args = "floatValue=30000.0"))
    private static float animatium$glintSpeed$diagonal(final float original, @Local(argsOnly = true, ordinal = 0) final float scale) {
        if (Animatium.isEnabled() && AnimatiumConfig.instance().items.legacyGlintSpeed && scale == 8.0F) {
            // Value taken from 1.7/1.8
            return 3000.0F;
        } else {
            return original;
        }
    }
}
