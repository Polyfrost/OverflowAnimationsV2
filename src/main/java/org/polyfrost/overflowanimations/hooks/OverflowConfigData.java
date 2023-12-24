package org.polyfrost.overflowanimations.hooks;

import org.polyfrost.overflowanimations.config.OldAnimationsSettings;

public class OverflowConfigData {

    public float itemPositionX = 0.0F;

    public float itemPositionY = 0.0F;

    public float itemPositionZ = 0.0F;

    public float itemRotationYaw = 0.0F;

    public float itemRotationPitch = 0.0F;

    public float itemRotationRoll = 0.0F;

    public float itemScale = 0.0F;

    // Swing Position Customization
    public float itemSwingPositionX = 0.0F;

    public float itemSwingPositionY = 0.0F;

    public float itemSwingPositionZ = 0.0F;

    public float itemSwingSpeed = 0.0F;

    public float itemSwingSpeedHaste = 0.0F;

    public float itemSwingSpeedFatigue = 0.0F;

    public boolean shouldScaleSwing = false;

    // Eating/Drinking Position
    public float consumePositionX = 0.0F;

    public float consumePositionY = 0.0F;

    public float consumePositionZ = 0.0F;

    public float consumeRotationYaw = 0.0F;

    public float consumeRotationPitch = 0.0F;

    public float consumeRotationRoll = 0.0F;

    public float consumeScale = 0.0F;

    public float consumeIntensity = 0.0F;

    public float consumeSpeed = 0.0F;

    public boolean shouldScaleEat = false;

    // Sword Block Position
    public float blockedPositionX = 0.0F;

    public float blockedPositionY = 0.0F;

    public float blockedPositionZ = 0.0F;

    public float blockedRotationYaw = 0.0F;

    public float blockedRotationPitch = 0.0F;

    public float blockedRotationRoll = 0.0F;

    public float blockedScale = 0.0F;

    // Projectiles Position
    public float projectilePositionX = 0.0F;

    public float projectilePositionY = 0.0F;

    public float projectilePositionZ = 0.0F;

    public float projectileRotationYaw = 0.0F;

    public float projectileRotationPitch = 0.0F;

    public float projectileRotationRoll = 0.0F;

    public float projectileScale = 0.0F;

    public OverflowConfigData() {
        itemPositionX = OldAnimationsSettings.INSTANCE.itemPositionX;
        itemPositionY = OldAnimationsSettings.INSTANCE.itemPositionY;
        itemPositionZ = OldAnimationsSettings.INSTANCE.itemPositionZ;
        itemRotationYaw = OldAnimationsSettings.INSTANCE.itemRotationYaw;
        itemRotationPitch = OldAnimationsSettings.INSTANCE.itemRotationPitch;
        itemRotationRoll = OldAnimationsSettings.INSTANCE.itemRotationRoll;
        itemScale = OldAnimationsSettings.INSTANCE.itemScale;
        itemSwingPositionX = OldAnimationsSettings.INSTANCE.itemSwingPositionX;
        itemSwingPositionY = OldAnimationsSettings.INSTANCE.itemSwingPositionY;
        itemSwingPositionZ = OldAnimationsSettings.INSTANCE.itemSwingPositionZ;
        itemSwingSpeed = OldAnimationsSettings.INSTANCE.itemSwingSpeed;
        itemSwingSpeedHaste = OldAnimationsSettings.INSTANCE.itemSwingSpeedHaste;
        itemSwingSpeedFatigue = OldAnimationsSettings.INSTANCE.itemSwingSpeedFatigue;
        shouldScaleSwing = OldAnimationsSettings.shouldScaleSwing;
        consumePositionX = OldAnimationsSettings.INSTANCE.consumePositionX;
        consumePositionY = OldAnimationsSettings.INSTANCE.consumePositionY;
        consumePositionZ = OldAnimationsSettings.INSTANCE.consumePositionZ;
        consumeRotationYaw = OldAnimationsSettings.INSTANCE.consumeRotationYaw;
        consumeRotationPitch = OldAnimationsSettings.INSTANCE.consumeRotationPitch;
        consumeRotationRoll = OldAnimationsSettings.INSTANCE.consumeRotationRoll;
        consumeScale = OldAnimationsSettings.INSTANCE.consumeScale;
        consumeIntensity = OldAnimationsSettings.INSTANCE.consumeIntensity;
        consumeSpeed = OldAnimationsSettings.INSTANCE.consumeSpeed;
        shouldScaleEat = OldAnimationsSettings.shouldScaleEat;
        blockedPositionX = OldAnimationsSettings.INSTANCE.blockedPositionX;
        blockedPositionY = OldAnimationsSettings.INSTANCE.blockedPositionY;
        blockedPositionZ = OldAnimationsSettings.INSTANCE.blockedPositionZ;
        blockedRotationYaw = OldAnimationsSettings.INSTANCE.blockedRotationYaw;
        blockedRotationPitch = OldAnimationsSettings.INSTANCE.blockedRotationPitch;
        blockedRotationRoll = OldAnimationsSettings.INSTANCE.blockedRotationRoll;
        blockedScale = OldAnimationsSettings.INSTANCE.blockedScale;
        projectilePositionX = OldAnimationsSettings.INSTANCE.projectilePositionX;
        projectilePositionY = OldAnimationsSettings.INSTANCE.projectilePositionY;
        projectilePositionZ = OldAnimationsSettings.INSTANCE.projectilePositionZ;
        projectileRotationYaw = OldAnimationsSettings.INSTANCE.projectileRotationYaw;
        projectileRotationPitch = OldAnimationsSettings.INSTANCE.projectileRotationPitch;
        projectileRotationRoll = OldAnimationsSettings.INSTANCE.projectileRotationRoll;
        projectileScale = OldAnimationsSettings.INSTANCE.projectileScale;
    }
}