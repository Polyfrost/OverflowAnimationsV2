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

package org.polyfrost.overflowanimations.legacy.entity;

import net.minecraft.client.render.model.block.BakedModel;
import net.minecraft.client.render.model.block.BakedQuad;
import net.minecraft.client.render.texture.TextureAtlasSprite;
import net.minecraft.client.resource.model.ModelTransformations;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public final class FastGrassModel implements BakedModel {
    private static final Map<BakedModel, BakedModel> CACHE = Collections.synchronizedMap(new WeakHashMap<>());

    private final Map<Direction, List<BakedQuad>> faceQuads = new EnumMap<>(Direction.class);
    private final List<BakedQuad> quads;
    private final boolean ambientOcclusion;
    private final boolean gui3d;
    private final TextureAtlasSprite particleIcon;
    private final ModelTransformations transformations;

    private FastGrassModel(final BakedModel model) {
        for (final Direction direction : Direction.values()) {
            this.faceQuads.put(direction, withoutOverlay(model.getQuads(direction)));
        }

        this.quads = withoutOverlay(model.getQuads());
        this.ambientOcclusion = model.useAmbientOcclusion();
        this.gui3d = model.isGui3d();
        this.particleIcon = model.getParticleIcon();
        this.transformations = model.getTransformations();
    }

    public static BakedModel of(final BakedModel model) {
        return CACHE.computeIfAbsent(model, FastGrassModel::new);
    }

    private static List<BakedQuad> withoutOverlay(final List<BakedQuad> quads) {
        final List<BakedQuad> filtered = new ArrayList<>(quads.size());
        for (final BakedQuad quad : quads) {
            if (!quad.hasTint() || quad.getFace().getAxis() == Direction.Axis.Y) {
                filtered.add(quad);
            }
        }

        return filtered;
    }

    @Override
    public List<BakedQuad> getQuads(final Direction face) {
        return this.faceQuads.get(face);
    }

    @Override
    public List<BakedQuad> getQuads() {
        return this.quads;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return this.ambientOcclusion;
    }

    @Override
    public boolean isGui3d() {
        return this.gui3d;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return this.particleIcon;
    }

    @Override
    public ModelTransformations getTransformations() {
        return this.transformations;
    }
}
