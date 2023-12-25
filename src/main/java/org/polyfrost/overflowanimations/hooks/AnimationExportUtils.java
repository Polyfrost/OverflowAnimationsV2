package org.polyfrost.overflowanimations.hooks;

import cc.polyfrost.oneconfig.utils.Notifications;
import com.google.gson.Gson;
import org.polyfrost.overflowanimations.config.ItemPositionAdvancedSettings;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.util.Base64;

public class AnimationExportUtils {

    private static final Gson GSON = new Gson();

    public static void exportItemPositions() {
        String string = Base64.getEncoder().encodeToString(GSON.toJson(new OverflowConfigData()).getBytes());
        StringSelection selection = new StringSelection(string);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
        Notifications.INSTANCE.send("OverflowAnimations", "Exported item positions to clipboard");
    }

    public static void importItemPositions() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
            String base64 = (String) clipboard.getData(DataFlavor.stringFlavor);
            String jsonString = new String(Base64.getDecoder().decode(base64));
            if (jsonString.contains("ignoreHaste")) {
                System.out.println("Detected DulkirMod config data, trying to import");
                try {
                    DulkirConfigData importSettings = GSON.fromJson(jsonString, DulkirConfigData.class);
                    importDulkir(importSettings);
                } catch (Exception e) {
                    e.printStackTrace();
                    Notifications.INSTANCE.send("OverflowAnimations", "Failed to detect clipboard data! Please make sure you have copied a valid config from either OverflowAnimations or DulkirMod!");
                }
            }
            System.out.println("Detected OverflowAnimations config data, trying to import");
            try {
                OverflowConfigData importSettings = GSON.fromJson(jsonString, OverflowConfigData.class);
                importOverflow(importSettings);
            } catch (Exception ignored) {
                Notifications.INSTANCE.send("OverflowAnimations", "Failed to detect clipboard data! Please make sure you have copied a valid config from either OverflowAnimations or DulkirMod!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Notifications.INSTANCE.send("OverflowAnimations", "Failed to decode clipboard data");
        }
    }

    public static void importOverflow(OverflowConfigData importSettings) {
        OldAnimationsSettings settings = OldAnimationsSettings.INSTANCE;
        ItemPositionAdvancedSettings advanced = OldAnimationsSettings.advancedSettings;
        settings.itemPositionX = importSettings.itemPositionX;
        settings.itemPositionY = importSettings.itemPositionY;
        settings.itemPositionZ = importSettings.itemPositionZ;
        settings.itemRotationYaw = importSettings.itemRotationYaw;
        settings.itemRotationPitch = importSettings.itemRotationPitch;
        settings.itemRotationRoll = importSettings.itemRotationRoll;
        settings.itemScale = importSettings.itemScale;
        advanced.itemSwingPositionX = importSettings.itemSwingPositionX;
        advanced.itemSwingPositionY = importSettings.itemSwingPositionY;
        advanced.itemSwingPositionZ = importSettings.itemSwingPositionZ;
        advanced.itemSwingSpeed = importSettings.itemSwingSpeed;
        advanced.itemSwingSpeedHaste = importSettings.itemSwingSpeedHaste;
        advanced.itemSwingSpeedFatigue = importSettings.itemSwingSpeedFatigue;
        ItemPositionAdvancedSettings.shouldScaleSwing = importSettings.shouldScaleSwing;
        advanced.consumePositionX = importSettings.consumePositionX;
        advanced.consumePositionY = importSettings.consumePositionY;
        advanced.consumePositionZ = importSettings.consumePositionZ;
        advanced.consumeRotationYaw = importSettings.consumeRotationYaw;
        advanced.consumeRotationPitch = importSettings.consumeRotationPitch;
        advanced.consumeRotationRoll = importSettings.consumeRotationRoll;
        advanced.consumeScale = importSettings.consumeScale;
        advanced.consumeIntensity = importSettings.consumeIntensity;
        advanced.consumeSpeed = importSettings.consumeSpeed;
        ItemPositionAdvancedSettings.shouldScaleEat = importSettings.shouldScaleEat;
        advanced.blockedPositionX = importSettings.blockedPositionX;
        advanced.blockedPositionY = importSettings.blockedPositionY;
        advanced.blockedPositionZ = importSettings.blockedPositionZ;
        advanced.blockedRotationYaw = importSettings.blockedRotationYaw;
        advanced.blockedRotationPitch = importSettings.blockedRotationPitch;
        advanced.blockedRotationRoll = importSettings.blockedRotationRoll;
        advanced.blockedScale = importSettings.blockedScale;
        advanced.projectilePositionX = importSettings.projectilePositionX;
        advanced.projectilePositionY = importSettings.projectilePositionY;
        advanced.projectilePositionZ = importSettings.projectilePositionZ;
        advanced.projectileRotationYaw = importSettings.projectileRotationYaw;
        advanced.projectileRotationPitch = importSettings.projectileRotationPitch;
        advanced.projectileRotationRoll = importSettings.projectileRotationRoll;
        advanced.projectileScale = importSettings.projectileScale;

        settings.save();
    }

    public static void importDulkir(DulkirConfigData importSettings) {
        OldAnimationsSettings settings = OldAnimationsSettings.INSTANCE;
        ItemPositionAdvancedSettings advanced = OldAnimationsSettings.advancedSettings;
        settings.itemPositionX = importSettings.getX();
        settings.itemPositionY = importSettings.getY();
        settings.itemPositionZ = importSettings.getZ();
        settings.itemRotationYaw = importSettings.getYaw();
        settings.itemRotationPitch = importSettings.getPitch();
        settings.itemRotationRoll = importSettings.getRoll();
        settings.itemScale = importSettings.getSize();
        advanced.itemSwingSpeed = importSettings.getSpeed();
        advanced.itemSwingSpeedHaste = importSettings.getSpeed();
        advanced.itemSwingSpeedFatigue = importSettings.getSpeed();
        ItemPositionAdvancedSettings.ignoreHaste = importSettings.getIgnoreHaste();
        ItemPositionAdvancedSettings.shouldScaleSwing = importSettings.getScaleSwing();
        ItemPositionAdvancedSettings.shouldScaleEat = importSettings.getDrinkingFix() == 2;

        settings.save();
    }
}
