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

package org.polyfrost.overflowanimations.mixins.legacy.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.model.block.BakedModel;
import net.minecraft.client.resource.manager.ResourceManager;
import net.minecraft.client.resource.model.ModelIdentifier;
import net.minecraft.client.resource.model.ModelManager;
import net.minecraft.util.registry.MappedRegistry;
import net.minecraft.util.registry.Registry;
import org.polyfrost.overflowanimations.legacy.model.ItemTransformOverrides;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelManager.class)
public abstract class MixinModelManager_ItemTransformOverrides {
    @Shadow
    private Registry<ModelIdentifier, BakedModel> registry;

    @Inject(method = "reload", at = @At("TAIL"))
    private void overflowanimations$loadVanillaTransforms(final ResourceManager resourceManager, final CallbackInfo ci) {
        ItemTransformOverrides.load((MappedRegistry<ModelIdentifier, BakedModel>) this.registry, Minecraft.getInstance().getResourcePacks().defaultPack);
    }
}
