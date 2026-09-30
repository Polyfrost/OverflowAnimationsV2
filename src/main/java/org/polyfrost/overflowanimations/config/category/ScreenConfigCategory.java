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

package org.polyfrost.overflowanimations.config.category;

import org.jspecify.annotations.NonNull;
import org.polyfrost.overflowanimations.handler.config.bundle.EntryBundle;
import org.polyfrost.overflowanimations.handler.config.category.Category;
//? if >1.8.9
import org.polyfrost.overflowanimations.util.UtilsKt;
import org.polyfrost.overflowanimations.util.enums.TabListSetting;
import org.polyfrost.overflowanimations.util.enums.DebugCrosshairSetting;
import org.polyfrost.overflowanimations.util.enums.CameraVersionSetting;

public final class ScreenConfigCategory extends Category {
    public CameraVersionSetting cameraVersion = CameraVersionSetting.VANILLA;
    public boolean crosshairInThirdPerson = false;
    public boolean disableHeartFlash = false;
    public boolean centerScrollableListWidgets = false;
    public boolean listWidgetSelectedBorderColor = false;
    public boolean legacyWidgetHoverTextColor = false;
    public boolean disableDebugHudBackground = false;
    public boolean debugHudTextShadow = false;
    public boolean disableCameraTransparentPassthrough = false;
    public boolean tooltipStyleRendering = false;
    public boolean slotHoverStyleRendering = false;
    public boolean listBackgroundGradient = false;
    public boolean inventoryEffectsPosition = false;
    public boolean fullWidthInventoryEffects = false;
    public boolean panoramaRendering = false;
    public boolean legacyLoadingScreen = false;
    public boolean legacyLoadingScreenProgressBar = false;
    public boolean oldChatPosition = false;
    public boolean oldCrosshairPosition = false;
    public boolean disconnectServerToTitleScreen = false;
    public boolean oldCraftingSlotsPosition = false;

    //? if >1.8.9 {
    public DebugCrosshairSetting debugCrosshairStyle = DebugCrosshairSetting.V1_12;
    //?} else
    //public DebugCrosshairSetting debugCrosshairStyle = DebugCrosshairSetting.V1_8;
    public TabListSetting tabListStyle = TabListSetting.V1_8;
    public boolean legacyDebugScreen = false;
    public boolean hideCrosshairInThirdPerson = false;

    @Override
    public @NonNull EntryBundle bundle() {
        final EntryBundle bundle = new EntryBundle(this, "screen");

        bundle.enumEntry("cameraVersion", CameraVersionSetting.class);
        bundle.booleanEntry("crosshairInThirdPerson");
        bundle.booleanEntry("disableHeartFlash");
        bundle.booleanEntry("centerScrollableListWidgets");
        bundle.booleanEntry("listWidgetSelectedBorderColor");
        bundle.booleanEntry("legacyWidgetHoverTextColor");
        bundle.booleanEntry("disableDebugHudBackground");
        bundle.booleanEntry("debugHudTextShadow");
        bundle.booleanEntry("disableCameraTransparentPassthrough");
        bundle.booleanEntry("tooltipStyleRendering");
        bundle.booleanEntry("slotHoverStyleRendering");
        bundle.booleanEntry("listBackgroundGradient");
        bundle.booleanEntry("inventoryEffectsPosition");
        bundle.booleanEntry("fullWidthInventoryEffects");
        bundle.booleanEntry("panoramaRendering");
        bundle.booleanEntry("legacyLoadingScreen");
        bundle.booleanEntry("legacyLoadingScreenProgressBar");
        bundle.booleanEntry("oldChatPosition");
        bundle.booleanEntry("oldCrosshairPosition");
        bundle.booleanEntry("disconnectServerToTitleScreen");
        //? if >1.8.9 {
        bundle.booleanEntry("oldCraftingSlotsPosition", event -> UtilsKt.reinitializeInventorySlots());
        //?} else
        //bundle.booleanEntry("oldCraftingSlotsPosition");

        bundle.group("legacy_hud")
                .enumEntry("debugCrosshairStyle", DebugCrosshairSetting.class)
                .enumEntry("tabListStyle", TabListSetting.class)
                .booleanEntry("legacyDebugScreen")
                .booleanEntry("hideCrosshairInThirdPerson");

        return bundle;
    }
}
