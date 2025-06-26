package org.polyfrost.overflowanimations.hooks;

import org.polyfrost.overflowanimations.config.ItemPositionAdvancedSettings;
import org.polyfrost.overflowanimations.config.OldAnimationsSettings;

public class OverflowConfigData {
    public float itemPositionX;

    public float itemPositionY;

    public float itemPositionZ;

    public float itemRotationYaw;

    public float itemRotationPitch;

    public float itemRotationRoll;

    public float itemScale;

    // Swing Position Customization
    public float itemSwingPositionX;

    public float itemSwingPositionY;

    public float itemSwingPositionZ;

    public float itemSwingSpeed;

    public float itemSwingSpeedHaste;

    public float itemSwingSpeedFatigue;

    public boolean shouldScaleSwing = false;

    // Eating/Drinking Position
    public float consumePositionX;

    public float consumePositionY;

    public float consumePositionZ;

    public float consumeRotationYaw;

    public float consumeRotationPitch;

    public float consumeRotationRoll;

    public float consumeScale;

    public float consumeIntensity;

    public float consumeSpeed;

    public boolean shouldScaleEat = false;

    // Sword Block Position
    public float blockedPositionX;

    public float blockedPositionY;

    public float blockedPositionZ;

    public float blockedRotationYaw;

    public float blockedRotationPitch;

    public float blockedRotationRoll;

    public float blockedScale;

    // Projectiles Position
    public float projectilePositionX;

    public float projectilePositionY;

    public float projectilePositionZ;

    public float projectileRotationYaw;

    public float projectileRotationPitch;

    public float projectileRotationRoll;

    public float projectileScale;

    public OverflowConfigData() {
        final OldAnimationsSettings settings = OldAnimationsSettings.INSTANCE;
        final ItemPositionAdvancedSettings advanced = OldAnimationsSettings.advancedSettings;
        itemPositionX = settings.itemPositionX;
        itemPositionY = settings.itemPositionY;
        itemPositionZ = settings.itemPositionZ;
        itemRotationYaw = settings.itemRotationYaw;
        itemRotationPitch = settings.itemRotationPitch;
        itemRotationRoll = settings.itemRotationRoll;
        itemScale = settings.itemScale;
        itemSwingPositionX = advanced.itemSwingPositionX;
        itemSwingPositionY = advanced.itemSwingPositionY;
        itemSwingPositionZ = advanced.itemSwingPositionZ;
        itemSwingSpeed = settings.itemSwingSpeed;
        itemSwingSpeedHaste = settings.itemSwingSpeedHaste;
        itemSwingSpeedFatigue = settings.itemSwingSpeedFatigue;
        shouldScaleSwing = settings.swingSetting == 1;
        consumePositionX = advanced.consumePositionX;
        consumePositionY = advanced.consumePositionY;
        consumePositionZ = advanced.consumePositionZ;
        consumeRotationYaw = advanced.consumeRotationYaw;
        consumeRotationPitch = advanced.consumeRotationPitch;
        consumeRotationRoll = advanced.consumeRotationRoll;
        consumeScale = advanced.consumeScale;
        consumeIntensity = advanced.consumeIntensity;
        consumeSpeed = advanced.consumeSpeed;
        shouldScaleEat = ItemPositionAdvancedSettings.shouldScaleEat;
        blockedPositionX = advanced.blockedPositionX;
        blockedPositionY = advanced.blockedPositionY;
        blockedPositionZ = advanced.blockedPositionZ;
        blockedRotationYaw = advanced.blockedRotationYaw;
        blockedRotationPitch = advanced.blockedRotationPitch;
        blockedRotationRoll = advanced.blockedRotationRoll;
        blockedScale = advanced.blockedScale;
        projectilePositionX = advanced.projectilePositionX;
        projectilePositionY = advanced.projectilePositionY;
        projectilePositionZ = advanced.projectilePositionZ;
        projectileRotationYaw = advanced.projectileRotationYaw;
        projectileRotationPitch = advanced.projectileRotationPitch;
        projectileRotationRoll = advanced.projectileRotationRoll;
        projectileScale = advanced.projectileScale;
    }
}