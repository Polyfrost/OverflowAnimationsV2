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

package org.polyfrost.overflowanimations.legacy.model;

import net.minecraft.client.render.model.block.BakedModel;
import net.minecraft.client.resource.model.BlockModel;
import net.minecraft.client.resource.model.ModelIdentifier;
import net.minecraft.client.resource.model.ModelTransformations;
import net.minecraft.client.resource.pack.ResourcePack;
import net.minecraft.resource.Identifier;
import net.minecraft.util.registry.MappedRegistry;
import org.polyfrost.overflowanimations.config.OverflowAnimationsConfig;
import org.polyfrost.overflowanimations.config.category.ItemsConfigCategory;
import org.polyfrost.overflowanimations.util.enums.FirstPersonItemPositionsSetting;
import org.polyfrost.overflowanimations.util.enums.ThirdPersonItemPositionsSetting;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;

public final class ItemTransformOverrides {
    private static Map<BakedModel, ModelTransformations> vanillaTransforms = Collections.emptyMap();

    private ItemTransformOverrides() {
    }

    public static boolean usesLegacyFirstPerson(final BakedModel model) {
        final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
        return items.firstPersonItemPositions.isOld() &&
                !(items.firstPersonItemPositions == FirstPersonItemPositionsSetting.AUTO && isOverridden(model, ModelTransformations.Type.FIRST_PERSON));
    }

    public static boolean usesLegacyThirdPerson(final BakedModel model) {
        final ItemsConfigCategory items = OverflowAnimationsConfig.instance().items;
        return items.thirdPersonItemPositions.isOld() &&
                !(items.thirdPersonItemPositions == ThirdPersonItemPositionsSetting.AUTO && isOverridden(model, ModelTransformations.Type.THIRD_PERSON));
    }

    private static boolean isOverridden(final BakedModel model, final ModelTransformations.Type type) {
        final ModelTransformations vanilla = vanillaTransforms.get(model);
        return vanilla != null && !vanilla.get(type).equals(model.getTransformations().get(type));
    }

    public static void load(final MappedRegistry<ModelIdentifier, BakedModel> models, final ResourcePack defaultPack) {
        final Map<BakedModel, ModelTransformations> loaded = new IdentityHashMap<>();
        for (final ModelIdentifier id : models.keySet()) {
            if (!"inventory".equals(id.getVariant())) {
                continue;
            }
            final Identifier location = new Identifier(id.getNamespace(), "models/item/" + id.getPath() + ".json");
            try (Reader reader = new InputStreamReader(defaultPack.getResource(location), StandardCharsets.UTF_8)) {
                loaded.put(models.get(id), BlockModel.fromJson(reader).getTransformations());
            } catch (final IOException ignored) {
            }
        }
        vanillaTransforms = loaded;
    }
}
