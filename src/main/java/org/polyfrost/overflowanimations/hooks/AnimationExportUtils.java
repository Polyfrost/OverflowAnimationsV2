package org.polyfrost.overflowanimations.hooks;

import cc.polyfrost.oneconfig.utils.Notifications;
import com.google.gson.Gson;
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
        OldAnimationsSettings.INSTANCE.itemPositionX = importSettings.itemPositionX;
        OldAnimationsSettings.INSTANCE.itemPositionY = importSettings.itemPositionY;
        OldAnimationsSettings.INSTANCE.itemPositionZ = importSettings.itemPositionZ;
        OldAnimationsSettings.INSTANCE.itemRotationYaw = importSettings.itemRotationYaw;
        OldAnimationsSettings.INSTANCE.itemRotationPitch = importSettings.itemRotationPitch;
        OldAnimationsSettings.INSTANCE.itemRotationRoll = importSettings.itemRotationRoll;
        OldAnimationsSettings.INSTANCE.itemScale = importSettings.itemScale;
        OldAnimationsSettings.INSTANCE.itemSwingPositionX = importSettings.itemSwingPositionX;
        OldAnimationsSettings.INSTANCE.itemSwingPositionY = importSettings.itemSwingPositionY;
        OldAnimationsSettings.INSTANCE.itemSwingPositionZ = importSettings.itemSwingPositionZ;
        OldAnimationsSettings.INSTANCE.itemSwingSpeed = importSettings.itemSwingSpeed;
        OldAnimationsSettings.INSTANCE.itemSwingSpeedHaste = importSettings.itemSwingSpeedHaste;
        OldAnimationsSettings.INSTANCE.itemSwingSpeedFatigue = importSettings.itemSwingSpeedFatigue;
        OldAnimationsSettings.shouldScaleSwing = importSettings.shouldScaleSwing;
        OldAnimationsSettings.INSTANCE.consumePositionX = importSettings.consumePositionX;
        OldAnimationsSettings.INSTANCE.consumePositionY = importSettings.consumePositionY;
        OldAnimationsSettings.INSTANCE.consumePositionZ = importSettings.consumePositionZ;
        OldAnimationsSettings.INSTANCE.consumeRotationYaw = importSettings.consumeRotationYaw;
        OldAnimationsSettings.INSTANCE.consumeRotationPitch = importSettings.consumeRotationPitch;
        OldAnimationsSettings.INSTANCE.consumeRotationRoll = importSettings.consumeRotationRoll;
        OldAnimationsSettings.INSTANCE.consumeScale = importSettings.consumeScale;
        OldAnimationsSettings.INSTANCE.consumeIntensity = importSettings.consumeIntensity;
        OldAnimationsSettings.INSTANCE.consumeSpeed = importSettings.consumeSpeed;
        OldAnimationsSettings.shouldScaleEat = importSettings.shouldScaleEat;
        OldAnimationsSettings.INSTANCE.blockedPositionX = importSettings.blockedPositionX;
        OldAnimationsSettings.INSTANCE.blockedPositionY = importSettings.blockedPositionY;
        OldAnimationsSettings.INSTANCE.blockedPositionZ = importSettings.blockedPositionZ;
        OldAnimationsSettings.INSTANCE.blockedRotationYaw = importSettings.blockedRotationYaw;
        OldAnimationsSettings.INSTANCE.blockedRotationPitch = importSettings.blockedRotationPitch;
        OldAnimationsSettings.INSTANCE.blockedRotationRoll = importSettings.blockedRotationRoll;
        OldAnimationsSettings.INSTANCE.blockedScale = importSettings.blockedScale;
        OldAnimationsSettings.INSTANCE.projectilePositionX = importSettings.projectilePositionX;
        OldAnimationsSettings.INSTANCE.projectilePositionY = importSettings.projectilePositionY;
        OldAnimationsSettings.INSTANCE.projectilePositionZ = importSettings.projectilePositionZ;
        OldAnimationsSettings.INSTANCE.projectileRotationYaw = importSettings.projectileRotationYaw;
        OldAnimationsSettings.INSTANCE.projectileRotationPitch = importSettings.projectileRotationPitch;
        OldAnimationsSettings.INSTANCE.projectileRotationRoll = importSettings.projectileRotationRoll;
        OldAnimationsSettings.INSTANCE.projectileScale = importSettings.projectileScale;

        OldAnimationsSettings.INSTANCE.save();
    }

    public static void importDulkir(DulkirConfigData importSettings) {
        OldAnimationsSettings.INSTANCE.itemPositionX = importSettings.getX();
        OldAnimationsSettings.INSTANCE.itemPositionY = importSettings.getY();
        OldAnimationsSettings.INSTANCE.itemPositionZ = importSettings.getZ();
        OldAnimationsSettings.INSTANCE.itemRotationYaw = importSettings.getYaw();
        OldAnimationsSettings.INSTANCE.itemRotationPitch = importSettings.getPitch();
        OldAnimationsSettings.INSTANCE.itemRotationRoll = importSettings.getRoll();
        OldAnimationsSettings.INSTANCE.itemScale = importSettings.getSize();
        OldAnimationsSettings.INSTANCE.itemSwingSpeed = importSettings.getSpeed();
        OldAnimationsSettings.INSTANCE.itemSwingSpeedHaste = importSettings.getIgnoreHaste() ? importSettings.getSpeed() : 1.0F;
        OldAnimationsSettings.INSTANCE.itemSwingSpeedFatigue = importSettings.getIgnoreHaste() ? importSettings.getSpeed() : 1.0F;
        OldAnimationsSettings.shouldScaleSwing = importSettings.getScaleSwing();
        OldAnimationsSettings.shouldScaleEat = importSettings.getDrinkingFix() == 2;

        OldAnimationsSettings.INSTANCE.save();
    }
}
