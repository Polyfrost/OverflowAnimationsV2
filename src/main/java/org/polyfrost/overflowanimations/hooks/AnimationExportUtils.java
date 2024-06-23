package org.polyfrost.overflowanimations.hooks;

import cc.polyfrost.oneconfig.config.core.ConfigUtils;
import cc.polyfrost.oneconfig.utils.Notifications;
import com.google.gson.Gson;
import dulkirmod.config.Config;
import dulkirmod.config.DulkirConfig;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.config.ItemPositionSettings;
import org.polyfrost.overflowanimations.config.MainModSettings;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.io.File;
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
        OldAnimationsSettings settings = MainModSettings.INSTANCE.getOldSettings();
        ItemPositionSettings advanced = MainModSettings.INSTANCE.getPositionSettings();
        settings.setItemPositionX(importSettings.itemPositionX);
        settings.setItemPositionY(importSettings.itemPositionY);
        settings.setItemPositionZ(importSettings.itemPositionZ);
        settings.setItemRotationYaw(importSettings.itemRotationYaw);
        settings.setItemRotationPitch(importSettings.itemRotationPitch);
        settings.setItemRotationRoll(importSettings.itemRotationRoll);
        settings.setItemScale(importSettings.itemScale);
        advanced.setItemSwingPositionX(importSettings.itemSwingPositionX);
        advanced.setItemSwingPositionY(importSettings.itemSwingPositionY);
        advanced.setItemSwingPositionZ(importSettings.itemSwingPositionZ);
        settings.setItemSwingSpeed(importSettings.itemSwingSpeed);
        settings.setItemSwingSpeedHaste(importSettings.itemSwingSpeedHaste);
        settings.setItemSwingSpeedFatigue(importSettings.itemSwingSpeedFatigue);
        settings.setSwingSetting(importSettings.shouldScaleSwing ? 1 : 0);
        advanced.setConsumePositionX(importSettings.consumePositionX);
        advanced.setConsumePositionY(importSettings.consumePositionY);
        advanced.setConsumePositionZ(importSettings.consumePositionZ);
        advanced.setConsumeRotationYaw(importSettings.consumeRotationYaw);
        advanced.setConsumeRotationPitch(importSettings.consumeRotationPitch);
        advanced.setConsumeRotationRoll(importSettings.consumeRotationRoll);
        advanced.setConsumeScale(importSettings.consumeScale);
        advanced.setConsumeIntensity(importSettings.consumeIntensity);
        advanced.setConsumeSpeed(importSettings.consumeSpeed);
        advanced.setShouldScaleEat(importSettings.shouldScaleEat);
        advanced.setBlockedPositionX(importSettings.blockedPositionX);
        advanced.setBlockedPositionY(importSettings.blockedPositionY);
        advanced.setBlockedPositionZ(importSettings.blockedPositionZ);
        advanced.setBlockedRotationYaw(importSettings.blockedRotationYaw);
        advanced.setBlockedRotationPitch(importSettings.blockedRotationPitch);
        advanced.setBlockedRotationRoll(importSettings.blockedRotationRoll);
        advanced.setBlockedScale(importSettings.blockedScale);
        advanced.setProjectilePositionX(importSettings.projectilePositionX);
        advanced.setProjectilePositionY(importSettings.projectilePositionY);
        advanced.setProjectilePositionZ(importSettings.projectilePositionZ);
        advanced.setProjectileRotationYaw(importSettings.projectileRotationYaw);
        advanced.setProjectileRotationPitch(importSettings.projectileRotationPitch);
        advanced.setProjectileRotationRoll(importSettings.projectileRotationRoll);
        advanced.setProjectileScale(importSettings.projectileScale);

        settings.save();
    }

    public static void importDulkir(DulkirConfigData importSettings) {
        OldAnimationsSettings settings = MainModSettings.INSTANCE.getOldSettings();
        settings.setItemPositionX(importSettings.getX());
        settings.setItemPositionY(importSettings.getY());
        settings.setItemPositionZ(importSettings.getZ());
        settings.setItemRotationYaw(importSettings.getYaw());
        settings.setItemRotationPitch(importSettings.getPitch());
        settings.setItemRotationRoll(importSettings.getRoll());
        settings.setItemScale(importSettings.getSize());
        settings.setItemSwingSpeed(importSettings.getSpeed());
        settings.setItemSwingSpeedHaste(importSettings.getSpeed());
        settings.setItemSwingSpeedFatigue(importSettings.getSpeed());
        settings.setIgnoreHaste(importSettings.getIgnoreHaste());
        settings.setSwingSetting(importSettings.getScaleSwing() ? 1 : 0);
        MainModSettings.INSTANCE.getPositionSettings().setShouldScaleEat(importSettings.getDrinkingFix() == 2);

        settings.save();
    }

    public static void transferDulkirConfig() {
        try {
            File dulkirConfigFile = ConfigUtils.getProfileFile("dulkirmod-config.json");
            String dulkirConfig = FileUtils.readFileToString(dulkirConfigFile, Charsets.UTF_8);
            FileUtils.writeStringToFile(new File(dulkirConfigFile.getParentFile(), "dulkirmod-config_backup.json"), dulkirConfig, Charsets.UTF_8);
            File overflowConfigFile = ConfigUtils.getProfileFile("overflowanimations.json");
            String overflowConfig = FileUtils.readFileToString(overflowConfigFile, Charsets.UTF_8);
            FileUtils.writeStringToFile(new File(overflowConfigFile.getParentFile(), "overflowanimations_backup.json"), overflowConfig, Charsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            Notifications.INSTANCE.send("OverflowAnimations", "Failed to backup configs! Click here if you want to continue despite this error.", AnimationExportUtils::importDulkirConfig);
            return;
        }
        AnimationExportUtils.importDulkirConfig();
    }

    private static void importDulkirConfig() {
        DulkirConfigData data;
        if (!OverflowAnimations.oldDulkirMod) {
            data = new DulkirConfigData(
                    DulkirConfig.INSTANCE.getCustomSize(),
                    DulkirConfig.INSTANCE.getDoesScaleSwing(),
                    DulkirConfig.INSTANCE.getCustomX(),
                    DulkirConfig.INSTANCE.getCustomY(),
                    DulkirConfig.INSTANCE.getCustomZ(),
                    DulkirConfig.INSTANCE.getCustomYaw(),
                    DulkirConfig.INSTANCE.getCustomPitch(),
                    DulkirConfig.INSTANCE.getCustomRoll(),
                    DulkirConfig.INSTANCE.getCustomSpeed(),
                    DulkirConfig.INSTANCE.getIgnoreHaste(),
                    DulkirConfig.INSTANCE.getDrinkingSelector()
            );
        } else {
            data = new DulkirConfigData(
                    Config.INSTANCE.getCustomSize(),
                    Config.INSTANCE.getDoesScaleSwing(),
                    Config.INSTANCE.getCustomX(),
                    Config.INSTANCE.getCustomY(),
                    Config.INSTANCE.getCustomZ(),
                    Config.INSTANCE.getCustomYaw(),
                    Config.INSTANCE.getCustomPitch(),
                    Config.INSTANCE.getCustomRoll(),
                    Config.INSTANCE.getCustomSpeed(),
                    Config.INSTANCE.getIgnoreHaste(),
                    Config.INSTANCE.getDrinkingSelector()
            );
        }
        AnimationExportUtils.importDulkir(data);
        DulkirConfig.INSTANCE.setCustomAnimations(false);
        Notifications.INSTANCE.send("OverflowAnimations", "Successfully imported DulkirMod config!");
    }
}
