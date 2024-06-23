package org.polyfrost.overflowanimations.hooks;

import org.polyfrost.overflowanimations.config.ItemPositionSettings;
import org.polyfrost.overflowanimations.config.MainModSettings;
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
        OldAnimationsSettings settings = MainModSettings.INSTANCE.getOldSettings();
        ItemPositionSettings advanced = MainModSettings.INSTANCE.getPositionSettings();
        itemPositionX = settings.getItemPositionX();
        itemPositionY = settings.getItemPositionY();
        itemPositionZ = settings.getItemPositionZ();
        itemRotationYaw = settings.getItemRotationYaw();
        itemRotationPitch = settings.getItemRotationPitch();
        itemRotationRoll = settings.getItemRotationRoll();
        itemScale = settings.getItemScale();
        itemSwingPositionX = advanced.getItemSwingPositionX();
        itemSwingPositionY = advanced.getItemSwingPositionY();
        itemSwingPositionZ = advanced.getItemSwingPositionZ();
        itemSwingSpeed = settings.getItemSwingSpeed();
        itemSwingSpeedHaste = settings.getItemSwingSpeedHaste();
        itemSwingSpeedFatigue = settings.getItemSwingSpeedFatigue();
        shouldScaleSwing = settings.getSwingSetting() == 1;
        consumePositionX = advanced.getConsumePositionX();
        consumePositionY = advanced.getConsumePositionY();
        consumePositionZ = advanced.getConsumePositionZ();
        consumeRotationYaw = advanced.getConsumeRotationYaw();
        consumeRotationPitch = advanced.getConsumeRotationPitch();
        consumeRotationRoll = advanced.getConsumeRotationRoll();
        consumeScale = advanced.getConsumeScale();
        consumeIntensity = advanced.getConsumeIntensity();
        consumeSpeed = advanced.getConsumeSpeed();
        shouldScaleEat = advanced.getShouldScaleEat();
        blockedPositionX = advanced.getBlockedPositionX();
        blockedPositionY = advanced.getBlockedPositionY();
        blockedPositionZ = advanced.getBlockedPositionZ();
        blockedRotationYaw = advanced.getBlockedRotationYaw();
        blockedRotationPitch = advanced.getBlockedRotationPitch();
        blockedRotationRoll = advanced.getBlockedRotationRoll();
        blockedScale = advanced.getBlockedScale();
        projectilePositionX = advanced.getProjectilePositionX();
        projectilePositionY = advanced.getProjectilePositionY();
        projectilePositionZ = advanced.getProjectilePositionZ();
        projectileRotationYaw = advanced.getProjectileRotationYaw();
        projectileRotationPitch = advanced.getProjectileRotationPitch();
        projectileRotationRoll = advanced.getProjectileRotationRoll();
        projectileScale = advanced.getProjectileScale();
    }
}