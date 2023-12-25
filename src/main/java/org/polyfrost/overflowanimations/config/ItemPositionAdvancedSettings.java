package org.polyfrost.overflowanimations.config;

import cc.polyfrost.oneconfig.config.annotations.Button;
import cc.polyfrost.oneconfig.config.annotations.Checkbox;
import cc.polyfrost.oneconfig.config.annotations.Slider;
import net.minecraft.client.Minecraft;

@SuppressWarnings("unused")
public class ItemPositionAdvancedSettings {

    // Swing Position Customization
    @Slider(
            name = "Item Swing X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Item Swing Position",
            instant = true
    )
    public float itemSwingPositionX = 0.0F;

    @Slider(
            name = "Item Swing Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Item Swing Position",
            instant = true
    )
    public float itemSwingPositionY = 0.0F;

    @Slider(
            name = "Item Swing Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Item Swing Position",
            instant = true
    )
    public float itemSwingPositionZ = 0.0F;

    @Slider(
            name = "Item Swing Speed",
            min = -2.5F, max = 2.5F,
            category = "Customize Item Positions", subcategory = "Item Swing Position",
            instant = true
    )
    public float itemSwingSpeed = 0.0F;

    @Slider(
            name = "Haste Swing Speed",
            min = -2.5F, max = 2.5F,
            category = "Customize Item Positions", subcategory = "Item Swing Position",
            instant = true
    )
    public float itemSwingSpeedHaste = 0.0F;

    @Slider(
            name = "Miner's Fatigue Swing Speed",
            min = -2.5F, max = 2.5F,
            category = "Customize Item Positions", subcategory = "Item Swing Position",
            instant = true
    )
    public float itemSwingSpeedFatigue = 0.0F;

    @Checkbox(
            name = "Scale Item Swing Based on Item Scale",
            description = "Scales the swing animation based on the scale of the item.",
            category = "Customize Item Positions", subcategory = "Item Swing Position"
    )
    public static boolean shouldScaleSwing = false;

    @Checkbox(
            name = "Disable Swing Translation",
            description = "Disables the swing translation.",
            category = "Customize Item Positions", subcategory = "Item Swing Position"
    )
    public static boolean disableSwing = false;

    @Button(
            name = "Reset Item Swing Transformations",
            text = "Reset",
            category = "Customize Item Positions", subcategory = "Item Swing Position"
    )
    Runnable resetSwing = (() -> {
        Minecraft.getMinecraft().displayGuiScreen(null);
        itemSwingPositionX = 0.0F;
        itemSwingPositionY = 0.0F;
        itemSwingPositionZ = 0.0F;
        itemSwingSpeed = 0.0F;
        itemSwingSpeedHaste = 0.0F;
        itemSwingSpeedFatigue = 0.0F;
        shouldScaleSwing = false;
        disableSwing = false;
        ignoreHaste = false;
        OldAnimationsSettings.INSTANCE.save();
        OldAnimationsSettings.INSTANCE.openGui();
    });

    @Checkbox(
            name = "Ignore Haste Speed",
            description = "Ignores the haste speed when setting a custom item swing speed.",
            category = "Customize Item Positions", subcategory = "Item Swing Position"
    )
    public static boolean ignoreHaste = false;

    // Eating/Drinking Position
    @Slider(
            name = "Eating/Drinking X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position",
            instant = true
    )
    public float consumePositionX = 0.0F;

    @Slider(
            name = "Eating/Drinking Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position",
            instant = true
    )
    public float consumePositionY = 0.0F;

    @Slider(
            name = "Eating/Drinking Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position",
            instant = true
    )
    public float consumePositionZ = 0.0F;

    @Slider(
            name = "Eating/Drinking Rotation Yaw",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position",
            instant = true
    )
    public float consumeRotationYaw = 0.0F;

    @Slider(
            name = "Eating/Drinking Rotation Pitch",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position",
            instant = true
    )
    public float consumeRotationPitch = 0.0F;

    @Slider(
            name = "Eating/Drinking Rotation Roll",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position",
            instant = true
    )
    public float consumeRotationRoll = 0.0F;

    @Slider(
            name = "Eating/Drinking Scale",
            min = -1.5f, max = 1.5f,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position",
            instant = true
    )
    public float consumeScale = 0.0F;

    @Slider(
            name = "Eating/Drinking Intensity Animation",
            min = -6.5F, max = 6.5F,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position",
            instant = true
    )
    public float consumeIntensity = 0.0F;

    @Slider(
            name = "Eating/Drinking Rotation Speed",
            min = -1.0F, max = 1.0F,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position",
            instant = true
    )
    public float consumeSpeed = 0.0F;

    @Button(
            name = "Reset Eating/Drinking Transformations",
            text = "Reset",
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position"
    )
    Runnable resetConsume = (() -> {
        Minecraft.getMinecraft().displayGuiScreen(null);
        consumePositionX = 0.0F;
        consumePositionY = 0.0F;
        consumePositionZ = 0.0F;
        consumeRotationYaw = 0.0F;
        consumeRotationPitch = 0.0F;
        consumeRotationRoll = 0.0F;
        consumeScale = 0.0F;
        consumeIntensity = 0.0F;
        consumeSpeed = 0.0F;
        shouldScaleEat = false;
        OldAnimationsSettings.INSTANCE.save();
        OldAnimationsSettings.INSTANCE.openGui();
    });

    @Checkbox(
            name = "Scale Eating/Drinking Based on Item Position",
            description = "Scales the Eating/Drinking animation based on the position of the item.",
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position"
    )
    public static boolean shouldScaleEat = false;

    // Sword Block Position
    @Slider(
            name = "Sword Block X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Sword Block Position",
            instant = true
    )
    public float blockedPositionX = 0.0F;

    @Slider(
            name = "Sword Block Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Sword Block Position",
            instant = true
    )
    public float blockedPositionY = 0.0F;

    @Slider(
            name = "Sword Block Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Sword Block Position",
            instant = true
    )
    public float blockedPositionZ = 0.0F;

    @Slider(
            name = "Sword Block Rotation Yaw",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Sword Block Position",
            instant = true
    )
    public float blockedRotationYaw = 0.0F;

    @Slider(
            name = "Sword Block Rotation Pitch",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Sword Block Position",
            instant = true
    )
    public float blockedRotationPitch = 0.0F;

    @Slider(
            name = "Sword Block Rotation Roll",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Sword Block Position",
            instant = true
    )
    public float blockedRotationRoll = 0.0F;

    @Slider(
            name = "Sword Block Scale",
            min = -1.5f, max = 1.5f,
            category = "Customize Item Positions", subcategory = "Sword Block Position",
            instant = true
    )
    public float blockedScale = 0.0F;

    @Button(
            name = "Reset Sword Block Transformations",
            text = "Reset",
            category = "Customize Item Positions", subcategory = "Sword Block Position"
    )
    Runnable resetBlockItem = (() -> {
        Minecraft.getMinecraft().displayGuiScreen(null);
        blockedPositionX = 0.0F;
        blockedPositionY = 0.0F;
        blockedPositionZ = 0.0F;
        blockedRotationYaw = 0.0F;
        blockedRotationPitch = 0.0F;
        blockedRotationRoll = 0.0F;
        blockedScale = 0.0F;
        OldAnimationsSettings.INSTANCE.save();
        OldAnimationsSettings.INSTANCE.openGui();
    });

    // Dropped Item Position
    @Slider(
            name = "Dropped Item X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Dropped Item Position",
            instant = true
    )
    public float droppedPositionX = 0.0F;

    @Slider(
            name = "Dropped Item Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Dropped Item Position",
            instant = true
    )
    public float droppedPositionY = 0.0F;

    @Slider(
            name = "Dropped Item Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Dropped Item Position",
            instant = true
    )
    public float droppedPositionZ = 0.0F;

    @Slider(
            name = "Dropped Item Rotation Yaw",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Dropped Item Position",
            instant = true
    )
    public float droppedRotationYaw = 0.0F;

    @Slider(
            name = "Dropped Item Rotation Pitch",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Dropped Item Position",
            instant = true
    )
    public float droppedRotationPitch = 0.0F;

    @Slider(
            name = "Dropped Item Rotation Roll",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Dropped Item Position",
            instant = true
    )
    public float droppedRotationRoll = 0.0F;

    @Slider(
            name = "Dropped Item Scale",
            min = -1.5f, max = 1.5f,
            category = "Customize Item Positions", subcategory = "Dropped Item Position",
            instant = true
    )
    public float droppedScale = 0.0F;

    @Button(
            name = "Reset Dropped Item Transformations",
            text = "Reset",
            category = "Customize Item Positions", subcategory = "Dropped Item Position"
    )
    Runnable resetDropped = (() -> {
        Minecraft.getMinecraft().displayGuiScreen(null);
        droppedPositionX = 0.0F;
        droppedPositionY = 0.0F;
        droppedPositionZ = 0.0F;
        droppedRotationYaw = 0.0F;
        droppedRotationPitch = 0.0F;
        droppedRotationRoll = 0.0F;
        droppedScale = 0.0F;
        OldAnimationsSettings.INSTANCE.save();
        OldAnimationsSettings.INSTANCE.openGui();
    });

    // Projectiles Position
    @Slider(
            name = "Thrown Projectile X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position",
            instant = true
    )
    public float projectilePositionX = 0.0F;

    @Slider(
            name = "Thrown Projectile Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position",
            instant = true
    )
    public float projectilePositionY = 0.0F;

    @Slider(
            name = "Thrown Projectile Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position",
            instant = true
    )
    public float projectilePositionZ = 0.0F;

    @Slider(
            name = "Thrown Projectile Rotation Yaw",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position",
            instant = true
    )
    public float projectileRotationYaw = 0.0F;

    @Slider(
            name = "Thrown Projectile Rotation Pitch",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position",
            instant = true
    )
    public float projectileRotationPitch = 0.0F;

    @Slider(
            name = "Thrown Projectile Rotation Roll",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position",
            instant = true
    )
    public float projectileRotationRoll = 0.0F;

    @Slider(
            name = "Thrown Projectile Scale",
            min = -1.5f, max = 1.5f,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position",
            instant = true
    )
    public float projectileScale = 0.0F;

    @Button(
            name = "Reset Thrown Projectile Transformations",
            text = "Reset",
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position"
    )
    Runnable resetProjectile = (() -> {
        Minecraft.getMinecraft().displayGuiScreen(null);
        projectilePositionX = 0.0F;
        projectilePositionY = 0.0F;
        projectilePositionZ = 0.0F;
        projectileRotationYaw = 0.0F;
        projectileRotationPitch = 0.0F;
        projectileRotationRoll = 0.0F;
        projectileScale = 0.0F;
        OldAnimationsSettings.INSTANCE.save();
        OldAnimationsSettings.INSTANCE.openGui();
    });

    // Fireball Position
    @Slider(
            name = "Fireball Projectile X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Fireball Projectile Position",
            instant = true
    )
    public float fireballPositionX = 0.0F;

    @Slider(
            name = "Fireball Projectile Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Fireball Projectile Position",
            instant = true
    )
    public float fireballPositionY = 0.0F;

    @Slider(
            name = "Fireball Projectile Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Fireball Projectile Position",
            instant = true
    )
    public float fireballPositionZ = 0.0F;

    @Slider(
            name = "Fireball Projectile Rotation Yaw",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Fireball Projectile Position",
            instant = true
    )
    public float fireballRotationYaw = 0.0F;

    @Slider(
            name = "Fireball Projectile Rotation Pitch",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Fireball Projectile Position",
            instant = true
    )
    public float fireballRotationPitch = 0.0F;

    @Slider(
            name = "Fireball Projectile Rotation Roll",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Fireball Projectile Position",
            instant = true
    )
    public float fireballRotationRoll = 0.0F;

    @Slider(
            name = "Fireball Projectile Scale",
            min = -1.5f, max = 1.5f,
            category = "Customize Item Positions", subcategory = "Fireball Projectile Position",
            instant = true
    )
    public float fireballScale = 0.0F;

    @Button(
            name = "Reset Fireball Projectile Transformations",
            text = "Reset",
            category = "Customize Item Positions", subcategory = "Fireball Projectile Position"
    )
    Runnable resetFireball = (() -> {
        Minecraft.getMinecraft().displayGuiScreen(null);
        fireballPositionX = 0.0F;
        fireballPositionY = 0.0F;
        fireballPositionZ = 0.0F;
        fireballRotationYaw = 0.0F;
        fireballRotationPitch = 0.0F;
        fireballRotationRoll = 0.0F;
        fireballScale = 0.0F;
        OldAnimationsSettings.INSTANCE.save();
        OldAnimationsSettings.INSTANCE.openGui();
    });

}
