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

package org.visuals.legacy.animatium.config.category;

import dev.isxander.yacl3.api.ConfigCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;
import org.visuals.legacy.animatium.handler.compatibility.ModsKt;
import org.visuals.legacy.animatium.handler.config.bundle.EntryBundle;
import org.visuals.legacy.animatium.handler.config.bundle.GroupBundle;
import org.visuals.legacy.animatium.handler.config.category.Category;
import org.visuals.legacy.animatium.handler.server_features.ServerFeature;
import org.visuals.legacy.animatium.handler.server_features.ServerFeatures;

public final class ExtrasConfigCategory extends Category {
    public boolean minimalViewBobbing = false;
    public boolean showNameTagInThirdPerson = false;
    public boolean hideNameTagBackground = false;
    public boolean nameTagTextShadow = false;
    public boolean debugHudTextColor = false;
    public boolean disableRecipeAndTutorialToasts = false;
    public boolean showArmWhileInvisible = false;
    public boolean dontMoveBlueVoid = false;
    public boolean disableEntityDeathTopple = false;
    public boolean disableParticlePhysics = false;
    public boolean disableFirstPersonParticles = false;
    public boolean dontClearChat = false;
    public boolean dontCloseChat = false;
    public boolean oldWaterColorEffects = false;
    public boolean colorBoost = false;
    public boolean alwaysBlockingHeadCap = false;
    public boolean hideRecipeBook = false;
    public boolean legacyLoadingScreenProgressBar = false;
    public boolean alwaysSharpParticles = false;
    public boolean damageBloodParticles = false;
    public int bloodParticleMultiplier = 1;
    // Damage Tint
    public boolean damageTintItems = false;
    public boolean damageTintCape = false;
    // Item Swing
    public boolean customSwingSpeed = false;
    public float itemSwingSpeed = 0.0F;
    public float hasteSwingSpeed = 0.0F;
    public float miningFatigueSwingSpeed = 0.0F;
    public boolean ignoreHasteSpeed = false;
    public boolean ignoreMiningFatigueSpeed = false;
    public boolean offhandUsageSwinging = false;
    public boolean alwaysUsageSwing = false;
    public boolean fakeMissPenaltySwing = false;
    public boolean disableSwingTranslate = false;
    public boolean disableSwingPivot = false;
    public boolean legacySwingAnimation = false;
    // Item Modifications
    public float itemScaleX = 1.0F;
    public float itemScaleY = 1.0F;
    public float itemScaleZ = 1.0F;
    public float itemOffsetX = 0.0F;
    public float itemOffsetY = 0.0F;
    public float itemOffsetZ = 0.0F;
    public float itemRotationX = 0.0F;
    public float itemRotationY = 0.0F;
    public float itemRotationZ = 0.0F;
    public boolean applyCustomizationToBlockItems = true;
    // Server Features (Singleplayer Only)
    public boolean miss_penalty = false;
    public boolean left_click_item_usage = false;
    public boolean mining_item_usage = false;
    public boolean hide_rod_bobber = false;
    public boolean pick_inflation = false;
    public boolean old_sneak_height = false;
    public boolean clientside_entities = false;
    public boolean disable_sprint_item_use = false;
    public boolean disable_sprint_sneaking = false;

    public static ConfigCategory create(final ExtrasConfigCategory defaults, final ExtrasConfigCategory config) {
        final ConfigCategory.Builder category = ConfigCategory.createBuilder();
        category.name(Component.translatable("animatium.category.extras"));
        config.bundle().install(category, defaults, config);
        return category.build();
    }

    @Override
    public @NonNull EntryBundle bundle() {
        final EntryBundle bundle = new EntryBundle(this, "extras");

        bundle.booleanEntry("minimalViewBobbing")
                .booleanEntry("showNameTagInThirdPerson")
                .booleanEntry("hideNameTagBackground")
                .booleanEntry("nameTagTextShadow")
                .booleanEntry("debugHudTextColor");
        if (!ModsKt.HAS_SODIUM_EXTRAS) {
            bundle.booleanEntry("disableRecipeAndTutorialToasts");
        }

        final Minecraft minecraft = Minecraft.getInstance();
        bundle.booleanEntry("showArmWhileInvisible")
                .booleanEntry("dontMoveBlueVoid")
                .booleanEntry("disableEntityDeathTopple")
                .booleanEntry("disableParticlePhysics")
                .booleanEntry("disableFirstPersonParticles")
                .booleanEntry("dontClearChat")
                .booleanEntry("dontCloseChat")
                //? if <26.2 {
                /*.booleanEntry("oldWaterColorEffects", (option, event) -> minecraft.levelRenderer.allChanged())
                *///?} else {
                .booleanEntry("oldWaterColorEffects", (option, event) -> minecraft.levelExtractor.allChanged())
                //?}
                .booleanEntry("colorBoost")
                .booleanEntry("alwaysBlockingHeadCap")
                .booleanEntry("hideRecipeBook")
                .booleanEntry("legacyLoadingScreenProgressBar")
                .booleanEntry("alwaysSharpParticles")
                .booleanEntry("damageBloodParticles")
                .intRange("bloodParticleMultiplier", 1, 40);

        bundle.group("damage_tint")
                .booleanEntry("damageTintItems")
                .booleanEntry("damageTintCape");

        bundle.group("item_swing")
                .booleanEntry("customSwingSpeed")
                .floatRange("itemSwingSpeed", -2.0F, 1.0F, 0.1F)
                .floatRange("hasteSwingSpeed", -2.0F, 1.0F, 0.1F)
                .floatRange("miningFatigueSwingSpeed", -2.0F, 1.0F, 0.1F)
                .booleanEntry("ignoreHasteSpeed")
                .booleanEntry("ignoreMiningFatigueSpeed")
                .booleanEntry("offhandUsageSwinging")
                .booleanEntry("alwaysUsageSwing")
                .booleanEntry("fakeMissPenaltySwing")
                .booleanEntry("disableSwingTranslate")
                .booleanEntry("disableSwingPivot")
                .booleanEntry("legacySwingAnimation");

        bundle.group("item_modifications")
                .floatRange("itemScaleX", 0.2F, 2.0F, 0.1F)
                .floatRange("itemScaleY", 0.2F, 2.0F, 0.1F)
                .floatRange("itemScaleZ", 0.2F, 2.0F, 0.1F)
                .floatEntry("itemOffsetX")
                .floatEntry("itemOffsetY")
                .floatEntry("itemOffsetZ")
                .floatEntry("itemRotationX")
                .floatEntry("itemRotationY")
                .floatEntry("itemRotationZ")
                .booleanEntry("applyCustomizationToBlockItems");
        {
            final GroupBundle serverFeatureGroup = bundle.group("server_features");
            for (final ServerFeature feature : ServerFeatures.allFeatures()) {
                if (!ServerFeatures.ALL.equals(feature)) {
                    serverFeatureGroup.booleanEntry(feature.getIdentifier().getPath());
                }
            }
        }

        return bundle;
    }
}
