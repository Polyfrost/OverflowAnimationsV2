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

import net.minecraft.client.render.texture.TextureUtil;
import net.minecraft.client.resource.Resource;
import net.minecraft.client.resource.metadata.ResourceMetadataSection;
import net.minecraft.client.resource.model.BlockModel;
import net.minecraft.client.resource.model.ModelIdentifier;
import net.minecraft.resource.Identifier;
import org.polyfrost.overflowanimations.OverflowAnimationsConstants;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public final class MobHeadIcons {
    private static final String[] TYPES = {"skeleton", "wither", "zombie", "char", "creeper"};
    private static final String[] SKINS = {
            "textures/entity/skeleton/skeleton.png",
            "textures/entity/skeleton/wither_skeleton.png",
            "textures/entity/zombie/zombie.png",
            "textures/entity/steve.png",
            "textures/entity/creeper/creeper.png"
    };
    private static final String MODEL_PREFIX = "item/legacy_skull_";
    private static final String TEXTURE_PREFIX = "textures/legacy_skull/";
    private static final String MODEL_JSON = "{\"parent\":\"builtin/generated\",\"textures\":{\"layer0\":\"%1$s\",\"particle\":\"%1$s\"},\"display\":{"
            + "\"thirdperson\":{\"rotation\":[-90,0,0],\"translation\":[0,1,-3],\"scale\":[0.55,0.55,0.55]},"
            + "\"firstperson\":{\"rotation\":[0,-135,25],\"translation\":[0,4,2],\"scale\":[1.7,1.7,1.7]}}}";

    public static final List<String> VARIANTS = new ArrayList<>();
    private static final ModelIdentifier[] MODELS = new ModelIdentifier[TYPES.length];

    static {
        for (int i = 0; i < TYPES.length; i++) {
            final String variant = OverflowAnimationsConstants.MOD_ID + ":legacy_skull_" + TYPES[i];
            VARIANTS.add(variant);
            MODELS[i] = new ModelIdentifier(variant, "inventory");
        }
    }

    private MobHeadIcons() {
    }

    public static ModelIdentifier getModel(final int metadata) {
        return MODELS[metadata >= 0 && metadata < MODELS.length ? metadata : 3];
    }

    public static BlockModel loadItemModel(final Identifier location) {
        final int type = indexOf(location, MODEL_PREFIX, "");
        if (type < 0) {
            return null;
        }

        final BlockModel model = BlockModel.fromJson(String.format(MODEL_JSON, OverflowAnimationsConstants.MOD_ID + ":legacy_skull/" + TYPES[type]));
        model.name = location.toString();
        return model;
    }

    public static Identifier getSkin(final Identifier texture) {
        final int type = indexOf(texture, TEXTURE_PREFIX, ".png");
        return type < 0 ? null : new Identifier(SKINS[type]);
    }

    public static Resource createIcon(final Identifier texture, final Resource skin) throws IOException {
        final BufferedImage image;
        try (InputStream stream = skin.asStream()) {
            image = TextureUtil.readImage(stream);
        }

        final int unit = Math.max(1, image.getWidth() / 64);
        final int size = Math.max(16, 8 * unit);
        final BufferedImage icon = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D graphics = icon.createGraphics();
        drawFace(graphics, image, 8 * unit, 8 * unit, size);
        final String path = texture.getPath();
        if (path.endsWith("/zombie.png") || path.endsWith("/char.png")) {
            drawFace(graphics, image, 40 * unit, 8 * unit, size);
        }

        graphics.dispose();
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ImageIO.write(icon, "png", bytes);
        return new IconResource(texture, bytes.toByteArray());
    }

    private static void drawFace(final Graphics2D graphics, final BufferedImage skin, final int x, final int y, final int size) {
        final int length = skin.getWidth() / 8;
        graphics.drawImage(skin, 0, 0, size, size, x, y, x + length, y + length, null);
    }

    private static int indexOf(final Identifier location, final String prefix, final String suffix) {
        final String path = location.getPath();
        if (!OverflowAnimationsConstants.MOD_ID.equals(location.getNamespace()) || !path.startsWith(prefix) || !path.endsWith(suffix)) {
            return -1;
        }

        final String type = path.substring(prefix.length(), path.length() - suffix.length());
        for (int i = 0; i < TYPES.length; i++) {
            if (TYPES[i].equals(type)) {
                return i;
            }
        }

        return -1;
    }

    private static final class IconResource implements Resource {
        private final Identifier location;
        private final byte[] image;

        private IconResource(final Identifier location, final byte[] image) {
            this.location = location;
            this.image = image;
        }

        @Override
        public Identifier getLocation() {
            return this.location;
        }

        @Override
        public InputStream asStream() {
            return new ByteArrayInputStream(this.image);
        }

        @Override
        public boolean hasMetadata() {
            return false;
        }

        @Override
        public <T extends ResourceMetadataSection> T getMetadata(final String name) {
            return null;
        }

        @Override
        public String getSourceName() {
            return OverflowAnimationsConstants.MOD_ID;
        }
    }
}
