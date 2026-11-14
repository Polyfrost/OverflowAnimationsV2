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

package org.polyfrost.overflowanimations.mixins.v1.rendering.items;

//? if >=1.21.5 {
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.ModelRenderProperties;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.polyfrost.overflowanimations.util.states.ModelIdHolder;

@Mixin(ModelRenderProperties.class)
public abstract class MixinModelRenderProperties_ModelId implements ModelIdHolder {
    @Unique
    private Identifier overflowanimations$modelId;

    @Override
    public void overflowanimations$setModelId(final Identifier id) {
        this.overflowanimations$modelId = id;
    }

    @Inject(method = "fromResolvedModel", at = @At("RETURN"))
    private static void overflowanimations$storeModelId(final CallbackInfoReturnable<ModelRenderProperties> cir, @Local(argsOnly = true) final ResolvedModel model) {
        ((ModelIdHolder) (Object) cir.getReturnValue()).overflowanimations$setModelId(Identifier.tryParse(model.debugName()));
    }

    @Inject(method = "applyToLayer", at = @At("HEAD"))
    private void overflowanimations$tagLayer(final ItemStackRenderState.LayerRenderState layer, final ItemDisplayContext displayContext, final CallbackInfo ci) {
        ((ModelIdHolder) layer).overflowanimations$setModelId(this.overflowanimations$modelId);
    }
}
//?}
