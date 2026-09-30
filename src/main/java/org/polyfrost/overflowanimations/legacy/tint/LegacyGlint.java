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

package org.polyfrost.overflowanimations.legacy.tint;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.model.block.BakedModel;
import net.minecraft.client.render.model.block.BakedQuad;
import net.minecraft.client.render.model.block.MiningQuad;
import net.minecraft.client.render.platform.GlStateManager;
import net.minecraft.client.render.texture.TextureAtlasSprite;
import net.minecraft.client.render.vertex.BufferBuilder;
import net.minecraft.client.render.vertex.DefaultVertexFormat;
import net.minecraft.client.render.vertex.Tesselator;
import net.minecraft.client.resource.model.ModelTransformations;
import net.minecraft.util.math.Direction;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Function;

public final class LegacyGlint {
    public static final int ITEM_COLOR = 0xFF61309B;

    private static final TextureAtlasSprite ITEM_SPACE = new TextureAtlasSprite("overflowanimations:legacy_glint") {
        @Override
        public float getU(final double u) {
            return (float) (-u / 16.0);
        }

        @Override
        public float getV(final double v) {
            return (float) (v / 16.0);
        }
    };

    private static final Map<BakedModel, BakedModel> ITEM_SPACE_MODELS = new WeakHashMap<>();
    private static final Map<BakedModel, BakedModel> POTION_LIQUID_MODELS = new WeakHashMap<>();

    public static boolean renderingGui = false;

    private LegacyGlint() {
    }

    public static BakedModel itemSpace(final BakedModel model) {
        return ITEM_SPACE_MODELS.computeIfAbsent(model, key -> transform(key, quad -> new MiningQuad(quad, ITEM_SPACE)));
    }

    public static BakedModel potionLiquid(final BakedModel model) {
        return POTION_LIQUID_MODELS.computeIfAbsent(model, key -> transform(key, quad -> quad.getTintIndex() == 0 ? quad : null));
    }

    private static BakedModel transform(final BakedModel model, final Function<BakedQuad, BakedQuad> mapper) {
        final List<BakedQuad> quads = new ArrayList<>();
        for (final Direction direction : Direction.values()) {
            map(model.getQuads(direction), mapper, quads);
        }

        map(model.getQuads(), mapper, quads);
        final boolean ambientOcclusion = model.useAmbientOcclusion();
        final boolean gui3d = model.isGui3d();
        final boolean customRenderer = model.isCustomRenderer();
        final TextureAtlasSprite particle = model.getParticleIcon();
        final ModelTransformations transformations = model.getTransformations();
        return new BakedModel() {
            @Override
            public List<BakedQuad> getQuads(final Direction face) {
                return Collections.emptyList();
            }

            @Override
            public List<BakedQuad> getQuads() {
                return quads;
            }

            @Override
            public boolean useAmbientOcclusion() {
                return ambientOcclusion;
            }

            @Override
            public boolean isGui3d() {
                return gui3d;
            }

            @Override
            public boolean isCustomRenderer() {
                return customRenderer;
            }

            @Override
            public TextureAtlasSprite getParticleIcon() {
                return particle;
            }

            @Override
            public ModelTransformations getTransformations() {
                return transformations;
            }
        };
    }

    private static void map(final List<BakedQuad> from, final Function<BakedQuad, BakedQuad> mapper, final List<BakedQuad> to) {
        for (final BakedQuad quad : from) {
            final BakedQuad mapped = mapper.apply(quad);
            if (mapped != null) {
                to.add(mapped);
            }
        }
    }

    public static void setupItemTexturing(final int layer) {
        final long period = layer == 0 ? 3000L : 4873L;
        final float offset = (Minecraft.getTime() % period) / (float) period * 8.0F;
        GlStateManager.scalef(0.125F, 0.125F, 0.125F);
        GlStateManager.translatef(layer == 0 ? offset : -offset, 0.0F, 0.0F);
        GlStateManager.rotatef(layer == 0 ? -50.0F : 10.0F, 0.0F, 0.0F, 1.0F);
    }

    public static void renderGuiGlint(final int x, final int y, final float z) {
        if (!GL11.glIsEnabled(GL11.GL_DEPTH_TEST)) {
            return;
        }

        GlStateManager.depthFunc(GL11.GL_GEQUAL);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.enableAlphaTest();
        GlStateManager.enableBlend();
        GlStateManager.color4f(0.5F, 0.25F, 0.8F, 1.0F);
        final Tesselator tesselator = Tesselator.getInstance();
        final BufferBuilder buffer = tesselator.getBuffer();
        final int left = x - 2;
        final int top = y - 2;
        final int size = 20;
        final float texel = 1.0F / 256.0F;
        for (int layer = 0; layer < 2; layer++) {
            GlStateManager.blendFuncSeparate(GL11.GL_DST_ALPHA, GL11.GL_ONE, GL11.GL_ZERO, GL11.GL_ZERO);
            final float period = 3000.0F + layer * 1873;
            final float offset = (Minecraft.getTime() % (long) period) / period * 256.0F;
            final float skew = layer == 0 ? 4.0F : -1.0F;
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormat.POSITION_TEX);
            buffer.vertex(left, top + size, z).texture((offset + size * skew) * texel, size * texel).nextVertex();
            buffer.vertex(left + size, top + size, z).texture((offset + size + size * skew) * texel, size * texel).nextVertex();
            buffer.vertex(left + size, top, z).texture((offset + size) * texel, 0.0).nextVertex();
            buffer.vertex(left, top, z).texture(offset * texel, 0.0).nextVertex();
            tesselator.end();
        }

        GlStateManager.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableAlphaTest();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(GL11.GL_LEQUAL);
    }
}
