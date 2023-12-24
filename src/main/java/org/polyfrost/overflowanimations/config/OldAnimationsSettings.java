package org.polyfrost.overflowanimations.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.*;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.oneconfig.config.migration.VigilanceMigrator;
import cc.polyfrost.oneconfig.config.migration.VigilanceName;
import net.minecraft.client.Minecraft;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.hooks.AnimationExportUtils;

@SuppressWarnings("unused")
public class OldAnimationsSettings extends Config {

    @Switch(
            name = "2D Dropped Items",
            description = "Renders items as sprites rather than as models.",
            subcategory = "2D Dropped Items"
    )
    public static boolean itemSprites = false;

    @Checkbox(name = "1.7 Item Sprite Colors",
            description = "Changes the colors of the dropped item sprites to be brighter just like in 1.7.",
            subcategory = "2D Dropped Items"
    )
    public static boolean itemSpritesColor = false;

    @Checkbox(
            name = "Remove Glint From Sprites",
            description = "This will disable the enchantment glint for both dropped items and projectiles. Only works with 2D items enabled.",
            subcategory = "2D Dropped Items"
    )
    public static boolean spritesGlint = false;

    @Switch(
            name = "Rotation Fix",
            description = "Allows dropped items to face the player properly without being stuck on the Y-Axis.",
            subcategory = "2D Dropped Items"
    )
    public static boolean rotationFix = true;

    // Interaction
    @Switch(
            name = "1.7 Block-Hitting Animation",
            description = "Re-enables the block-hitting animations.",
            subcategory = "Interaction"
    )
    @VigilanceName(
            name = "1.7 Block-Hitting Animation",
            category = "Animations",
            subcategory = "Interaction"
    )
    public static boolean oldBlockhitting = true;

    @Switch(
            name = "1.7 Smoother Sneaking",
            description = "Smoothens the player camera to appear just like the 1.7 smoother sneaking camera.",
            subcategory = "Interaction"
    )
    @VigilanceName(
            name = "1.7 Smoother Sneaking",
            category = "Animations",
            subcategory = "Interaction"
    )
    public static boolean smoothSneaking = true;

    @Switch(
            name = "1.7 Armor Damage Tint",
            description = "Allows the armor to also have the damage tint applied to it.",
            subcategory = "Interaction"
    )
    @VigilanceName(
            name = "1.7 Red Armor Tint",
            category = "Animations",
            subcategory = "Interaction"
    )
    public static boolean redArmor = true;

    @Switch(
            name = "1.7 Item Switching Animation",
            description = "Re-enables the item switch animation from 1.7.",
            subcategory = "Interaction"
    )
    @VigilanceName(
            name = "1.7 Item Switching Animation",
            category = "Animations",
            subcategory = "Interaction"
    )
    public static boolean itemSwitch = true;

    @Switch(
            name = "1.7 Punching During Usage",
            description = "Purely visual feature. Re-enables the ability to consume food or block a sword whilst punching a block.",
            subcategory = "Interaction")
    @VigilanceName(
            name = "1.7 Punching During Usage",
            category = "Animations",
            subcategory = "Interaction"
    )
    public static boolean punching = true;

    @Checkbox(
            name = "1.7 Punch-During-Usage Particles",
            description = "Spawns Particles whilst Punching During Usage",
            subcategory = "Interaction"
    )
    public static boolean punchingParticles = true;

    // Positions
    @Switch(
            name = "1.7 First-Person Item Transformations",
            description = "Brings back the old item positions from 1.7.",
            subcategory = "Position"
    )
    public static boolean firstTransformations = true;

    @Switch(
            name = "1.7 Third-Person Item Transformations",
            description = "Brings back the old item positions from 1.7.",
            subcategory = "Position"
    )
    public static boolean thirdTransformations = true;

    @Checkbox(
            name = "1.7 First-Person Fishing Rod Position",
            description = "Brings back the old fishing rod position from 1.7.",
            subcategory = "Position"
    )
    public static boolean fishingRodPosition = true;

    @Checkbox(
            name = "1.7 First-Person Carpet Position",
            description = "Brings back the old carpet position from 1.7.",
            subcategory = "Position"
    )
    public static boolean firstPersonCarpetPosition = true;

    @Checkbox(
            name = "1.7 Third-Person Carpet Position",
            description = "Brings back the old carpet position from 1.7.",
            subcategory = "Position"
    )
    public static boolean thirdPersonCarpetPosition = true;

    @Switch(
            name = "1.7 Projectiles Transformations",
            description = "Mirrors and transforms projectiles so that they're facing the correct direction and in the same position as 1.7 or 1.9+.",
            subcategory = "Position"
    )
    public static boolean oldProjectiles = true;

    @Switch(
            name = "1.7 Third-Person Arm Block Position",
            description = "Brings back the old arm rotation while blocking from 1.7.",
            subcategory = "Position")
    public static boolean oldArmPosition = true;

    @Switch(
            name = "1.7 Third-Person Sword Block Position",
            description = "Brings back the old sword rotation while blocking from 1.7.",
            subcategory = "Position"
    )
    @VigilanceName(
            name = "1.7 3rd Person Block Animation",
            category = "Animations",
            subcategory = "Position"
    )
    public static boolean thirdPersonBlock = true;

    @Switch(
            name = "1.7 XP Orbs Position",
            description = "Brings back the old XP Orbs position from 1.7.",
            subcategory = "Position"
    )
    public static boolean oldXPOrbs = true;

    @Switch(
            name = "1.7 Pickup Animation Position",
            description = "Brings back the old item pickup position from 1.7.",
            subcategory = "Position"
    )
    public static boolean oldPickup = true;

    // HUD
    @Switch(
            name = "1.7 Health Bar Flashing",
            description = "Disables the heart flashing texture while taking damage similar to 1.7.",
            subcategory = "HUD"
    )
    @VigilanceName(
            name = "Remove Health Bar Flashing",
            category = "Animations",
            subcategory = "HUD"
    )
    public static boolean oldHealth = true;

    @Dropdown(
            name = "Debug Menu Crosshair Style",
            description = "Allows you to choose between the 1.7, the vanilla 1.8, and the 1.12+ debug screen crosshair. 1.12+ Debug Screen Crosshair fixes Patcher's Parallax Fix Feature!",
            subcategory = "HUD",
            options = {"1.7", "1.8", "1.12+"}
    )
    public int debugCrosshairMode = 2;

    @Dropdown(
            name = "Debug Menu Style",
            description = "Reverts the debug menu to be aesthetically similar to 1.7",
            options = {"1.7", "1.8", "Disable Background"},
            subcategory = "HUD"
    )
    public int debugScreenMode = 1;

    @Dropdown(
            name = "Tab Menu Style",
            description = "Allows you to choose between the 1.7 tab menu, the 1.8 tab menu, and disabling the player heads in the tab menu.",
            options = {"1.7", "1.8", "Disable Heads"},
            subcategory = "HUD"
    )
    public int tabMode = 1;

    // Miscellaneous
    @Switch(
            name = "1.15+ Armor Enchantment Glint",
            description = "Back-ports the 1.15 armor glint rendering.",
            category = "Misc", subcategory = "Modern"
    )
    public static boolean enchantmentGlintNew = true;

    @Switch(
            name = "1.15+ Backwards Walk Animation",
            description = "Back-ports the 1.15 walking animation.",
            category = "Misc", subcategory = "Modern"
    )
    public static boolean modernMovement = false;

    @Switch(
            name = "1.7 Held Item Lighting",
            description = "Modifies the held item lighting to resemble 1.7.",
            category = "Misc", subcategory = "Fixes, QOL, and Tweaks"
    )
    public static boolean oldItemLighting = true;

    @Switch(
            name = "1.7 Miss Penalty Swing Animation",
            description = "This option is purely visual. During the miss penalty, the player's arm will still swing and show particles just like in 1.7.",
            category = "Misc", subcategory = "Fixes, QOL, and Tweaks"
    )
    public static boolean visualSwing = true;

    @Switch(
            name = "1.7 Third-Person Fishing Rod Cast Texture",
            description = "For some reason, in 1.7, when a fishing rod is cast, the third person texture becomes a stick rather than the fishing rod texture. This feature brings back that questionable feature.",
            category = "Misc", subcategory = "Fixes, QOL, and Tweaks"
    )
    public static boolean fishingStick = false;

    @Switch(
            name = "Render Fireball Projectile as a Model",
            description = "Renders the thrown fireball projectiles as models rather than as sprites.",
            category = "Misc", subcategory = "Fixes, QOL, and Tweaks"
    )
    public static boolean fireballModel = false;

    @Switch(
            name = "Rod Line Position Based on FOV",
            description = "Includes the player's FOV when calculating the fishing rod cast line position.",
            category = "Misc", subcategory = "Fixes, QOL, and Tweaks"
    )
    public static boolean fixRod = true;

    @Switch(
            name = "Block Breaking Fixes",
            description = "Resets block removing while using an item or when the player is out of range of a block.",
            category = "Misc", subcategory = "Fixes, QOL, and Tweaks"
    )
    public static boolean breakFix = false;

    @Switch(
            name = "Disable Entity/Mob Third-Person Item Transformations",
            description = "Allows/Disallows mobs or entities to have third person item positions applied to them.",
            category = "Misc", subcategory = "Fixes, QOL, and Tweaks"
    )
    public static boolean entityTransforms = true;

    @Switch(
            name = "Disable Punching During Usage in Adventure Mode",
            description = "Allows/Disallows the punching during usage feature in Adventure Mode.",
            category = "Misc", subcategory = "Fixes, QOL, and Tweaks"
    )
    public static boolean adventurePunching = false;

    @Checkbox(
            name = "Disable Punch-During-Usage Particles in Adventure Mode",
            description = "Allows/Disallows the particles played while punching during usage to appear while in Adventure Mode.",
            category = "Misc", subcategory = "Fixes, QOL, and Tweaks"
    )
    public static boolean adventureParticles = true;

    @Switch(
            name = "Disable Hurt Camera Shake",
            description = "Disables the camera damage shake.",
            category = "Misc", subcategory = "Fixes, QOL, and Tweaks"
    )
    public static boolean noHurtCam = false;

    @Switch(
            name = "Old Lunar/CheatBreaker Block-Hit Position",
            description = "Brings back the weird block-hitting position from older versions of Lunar Client or CheatBreaker!",
            category = "Misc", subcategory = "Fun"
    )
    public static boolean lunarBlockhit = false;

    @Switch(
            name = "Swing Arm While Using an Item (Fake KillAura)",
            description = "This option is purely visual. Swings the arm while clicking and using an item.)",
            category = "Misc", subcategory = "Fun"
    )
    public static boolean fakeBlockHit = false;

    // Item Positions Customization

    @Button(
            name = "Copy / Export Item Positions As String",
            text = "Export",
            description = "Exports the item positions as a Base64 string. Will be copied to your clipboard.",
            category = "Customize Item Positions", subcategory = "Item Position"
    )
    public void exportItemPositions() {
        AnimationExportUtils.exportItemPositions();
    }

    @Button(
            name = "Import Overflow / Dulkir Item Positions As String",
            text = "Import",
            description = "Exports the item positions as a Base64 string. Will be copied to your clipboard.",
            category = "Customize Item Positions", subcategory = "Item Position"
    )
    public void importItemPositions() {
        Minecraft.getMinecraft().displayGuiScreen(null);
        AnimationExportUtils.importItemPositions();
        openGui();
    }

    @Slider(
            name = "Item X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Item Position"
    )
    public float itemPositionX = 0.0F;

    @Slider(
            name = "Item Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Item Position"
    )
    public float itemPositionY = 0.0F;

    @Slider(
            name = "Item Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Item Position"
    )
    public float itemPositionZ = 0.0F;

    @Slider(
            name = "Item Rotation Yaw",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Item Position"
    )
    public float itemRotationYaw = 0.0F;

    @Slider(
            name = "Item Rotation Pitch",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Item Position"
    )
    public float itemRotationPitch = 0.0F;

    @Slider(
            name = "Item Rotation Roll",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Item Position"
    )
    public float itemRotationRoll = 0.0F;

    @Slider(
            name = "Item Scale",
            min = -1.5f, max = 1.5f,
            category = "Customize Item Positions", subcategory = "Item Position"
    )
    public float itemScale = 0.0F;

    @Button(
            name = "Reset Item Transformations",
            text = "Reset",
            category = "Customize Item Positions", subcategory = "Item Position"
    )
    Runnable resetItem = (() -> {
        Minecraft.getMinecraft().displayGuiScreen(null);
        itemPositionX = 0.0F;
        itemPositionY = 0.0F;
        itemPositionZ = 0.0F;
        itemRotationYaw = 0.0F;
        itemRotationPitch = 0.0F;
        itemRotationRoll = 0.0F;
        itemScale = 0.0F;
        save();
        openGui();
    });

    // Swing Position Customization
    @Slider(
            name = "Item Swing X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Item Swing Position"
    )
    public float itemSwingPositionX = 0.0F;

    @Slider(
            name = "Item Swing Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Item Swing Position"
    )
    public float itemSwingPositionY = 0.0F;

    @Slider(
            name = "Item Swing Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Item Swing Position"
    )
    public float itemSwingPositionZ = 0.0F;

    @Slider(
            name = "Item Swing Speed",
            min = -2.5F, max = 2.5F,
            category = "Customize Item Positions", subcategory = "Item Swing Position"
    )
    public float itemSwingSpeed = 0.0F;

    @Slider(
            name = "Haste Swing Speed",
            min = -2.5F, max = 2.5F,
            category = "Customize Item Positions", subcategory = "Item Swing Position"
    )
    public float itemSwingSpeedHaste = 0.0F;

    @Slider(
            name = "Miner's Fatigue Swing Speed",
            min = -2.5F, max = 2.5F,
            category = "Customize Item Positions", subcategory = "Item Swing Position"
    )
    public float itemSwingSpeedFatigue = 0.0F;

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
        save();
        openGui();
    });

    @Checkbox(
            name = "Scale Item Swing Based on Item Scale",
            description = "Scales the swing animation based on the scale of the item.",
            category = "Customize Item Positions", subcategory = "Item Swing Position"
    )
    public static boolean shouldScaleSwing = false;

    // Eating/Drinking Position
    @Slider(
            name = "Eating/Drinking X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position"
    )
    public float consumePositionX = 0.0F;

    @Slider(
            name = "Eating/Drinking Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position"
    )
    public float consumePositionY = 0.0F;

    @Slider(
            name = "Eating/Drinking Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position"
    )
    public float consumePositionZ = 0.0F;

    @Slider(
            name = "Eating/Drinking Rotation Yaw",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position"
    )
    public float consumeRotationYaw = 0.0F;

    @Slider(
            name = "Eating/Drinking Rotation Pitch",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position"
    )
    public float consumeRotationPitch = 0.0F;

    @Slider(
            name = "Eating/Drinking Rotation Roll",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position"
    )
    public float consumeRotationRoll = 0.0F;

    @Slider(
            name = "Eating/Drinking Scale",
            min = -1.5f, max = 1.5f,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position"
    )
    public float consumeScale = 0.0F;

    @Slider(
            name = "Eating/Drinking Intensity Animation",
            min = -6.5F, max = 6.5F,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position"
    )
    public float consumeIntensity = 0.0F;

    @Slider(
            name = "Eating/Drinking Rotation Speed",
            min = -1.0F, max = 1.0F,
            category = "Customize Item Positions", subcategory = "Eating/Drinking Position"
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
        save();
        openGui();
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
            category = "Customize Item Positions", subcategory = "Sword Block Position"
    )
    public float blockedPositionX = 0.0F;

    @Slider(
            name = "Sword Block Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Sword Block Position"
    )
    public float blockedPositionY = 0.0F;

    @Slider(
            name = "Sword Block Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Sword Block Position"
    )
    public float blockedPositionZ = 0.0F;

    @Slider(
            name = "Sword Block Rotation Yaw",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Sword Block Position"
    )
    public float blockedRotationYaw = 0.0F;

    @Slider(
            name = "Sword Block Rotation Pitch",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Sword Block Position"
    )
    public float blockedRotationPitch = 0.0F;

    @Slider(
            name = "Sword Block Rotation Roll",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Sword Block Position"
    )
    public float blockedRotationRoll = 0.0F;

    @Slider(
            name = "Sword Block Scale",
            min = -1.5f, max = 1.5f,
            category = "Customize Item Positions", subcategory = "Sword Block Position"
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
        save();
        openGui();
    });

    // Projectiles Position
    @Slider(
            name = "Thrown Projectile X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position"
    )
    public float projectilePositionX = 0.0F;

    @Slider(
            name = "Thrown Projectile Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position"
    )
    public float projectilePositionY = 0.0F;

    @Slider(
            name = "Thrown Projectile Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position"
    )
    public float projectilePositionZ = 0.0F;

    @Slider(
            name = "Thrown Projectile Rotation Yaw",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position"
    )
    public float projectileRotationYaw = 0.0F;

    @Slider(
            name = "Thrown Projectile Rotation Pitch",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position"
    )
    public float projectileRotationPitch = 0.0F;

    @Slider(
            name = "Thrown Projectile Rotation Roll",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position"
    )
    public float projectileRotationRoll = 0.0F;

    @Slider(
            name = "Thrown Projectile Scale",
            min = -1.5f, max = 1.5f,
            category = "Customize Item Positions", subcategory = "Thrown Projectile Position"
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
        save();
        openGui();
    });

    public static boolean didTheFunnyDulkirThing = false;

    @Exclude public static final OldAnimationsSettings INSTANCE = new OldAnimationsSettings();

    public OldAnimationsSettings() {
        super(new Mod(OverflowAnimations.NAME, ModType.PVP, "/overflowanimations_dark.svg", new VigilanceMigrator("./config/sk1eroldanimations.toml")), "overflowanimations.json");
        initialize();

        // Sprites
        addDependency("rotationFix", "itemSprites");
        addDependency("spritesGlint", "itemSprites");
        addDependency("itemSpritesColor", "itemSprites");
        // Interactions
        addDependency("punching", "oldBlockhitting");
        addDependency("punchingParticles", "oldBlockhitting");
        addDependency("adventureParticles", "oldBlockhitting");
        addDependency("adventurePunching", "oldBlockhitting");
        addDependency("adventureParticles", "punchingParticles");
        addDependency("punchingParticles", "punching");
        addDependency("adventureParticles", "punching");
        addDependency("adventurePunching", "punching");
        // Transformations
        addDependency("firstPersonCarpetPosition", "itemTransformations");
        addDependency("fixRod", "itemTransformations");
        addDependency("entityTransforms", "thirdTransformations");
    }
}
