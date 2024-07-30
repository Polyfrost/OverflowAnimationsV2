package org.polyfrost.overflowanimations.config;

import org.polyfrost.oneconfig.api.config.v1.Config;
import org.polyfrost.oneconfig.api.config.v1.annotations.*;
import org.polyfrost.overflowanimations.OverflowAnimations;
import org.polyfrost.overflowanimations.hooks.AnimationExportUtils;
import org.polyfrost.overflowanimations.hooks.PotionColors;

public class OldAnimationsSettings extends Config {

    // 2D Items
    @Switch(
            title = "2D Dropped Items",
            description = "Renders items as sprites rather than as models.",
            subcategory = "2D Dropped Items"
    )
    public static boolean itemSprites = false;

    @Checkbox(title = "1.7 Item Sprite Colors",
            description = "Changes the colors of the dropped item sprites to be brighter just like in 1.7.",
            subcategory = "2D Dropped Items"
    )
    public static boolean itemSpritesColor = false;

    @Checkbox(
            title = "Remove Glint From Sprites",
            description = "This will disable the enchantment glint for both dropped items and projectiles. Only works with 2D items enabled.",
            subcategory = "2D Dropped Items"
    )
    public static boolean spritesGlint = false;

    @Switch(
            title = "Rotation Fix",
            description = "Allows dropped items to face the player properly without being stuck on the Y-Axis.",
            subcategory = "2D Dropped Items"
    )
    public static boolean rotationFix = true;

    // Smooth Sneaking

    @Switch(
            title = "1.7 Smoother Sneaking",
            description = "Smoothens the player camera to appear just like the 1.7 smoother sneaking camera.",
            subcategory = "Smooth Sneaking"
    )
    @PreviousNames(
            value = {"Animations.Interaction.Smooth Sneaking"}

    )
    public static boolean smoothSneaking = true;

    @Switch(
            title = "1.7 Longer Unsneak",
            description = "Lengthens the player camera's speed to appear just like the 1.7 smoother sneaking camera.",
            subcategory = "Smooth Sneaking"
    )
    @PreviousNames(
            value = {"Animations.Interaction.Longer Unsneak"}
    )
    public static boolean longerUnsneak = true;

    @Switch(
            title = "1.7 Third Person Sneaking",
            description = "Synchronizes the player model's sneaking behavior to the eye height to replicate the same behavior in 1.7. Disable if incompatible with cosmetic mods",
            subcategory = "Smooth Sneaking"
    )
    public static boolean smoothModelSneak = true;

    // Interaction
    @Switch(
            title = "1.7 Block-Hitting Animation",
            description = "Re-enables the block-hitting animations.",
            subcategory = "Interaction"
    )
    @PreviousNames(
            value = {"Animations.Interaction.Block-Hitting Animation"}
    )
    public static boolean oldBlockhitting = true;

    @Dropdown(
            title = "Armor Damage Tint Style",
            description = "Applies a damage tint to armor. " +
                    "\"None\" will disable the effect on armor. " +
                    "\"1.7\" will apply the damage color using the 1.7 formula. " +
                    "\"1.8 (With Glint)\" will use the 1.8 formula AND account for the enchantment glint. " +
                    "\"1.8 (Without Glint)\" will use the 1.8 formula AND NOT account for the enchantment glint. ",
            options = {"None", "1.7", "1.8 (With Glint)", "1.8 (Without Glint)"},
            subcategory = "HUD"
    )
    @PreviousNames(
            value = {"Animations.Interaction.Red Armor"}
    )
    public int armorDamageTintStyle = 3;

    @Dropdown(
            title = "1.7 Item Switching Animation",
            description = "Applies a damage tint to armor. " +
                    "\"None\" will disable the re-equip animation completely. " +
                    "\"1.7\" will use the 1.7 logic to display the re-equip animation. " +
                    "\"1.8\" will use the 1.8 logic to display the re-equip animation.",
            options = {"Disabled", "1.7", "1.8"},
            subcategory = "Interaction"
    )
    @PreviousNames(
            value = {"Animations.Interaction.Item Switching Animation"}
    )
    public int itemSwitchMode = 1;

    @Switch(
            title = "1.7 Miss Penalty Swing Animation",
            description = "This option is purely visual. During the miss penalty, the player's arm will still swing and show particles just like in 1.7.",
            subcategory = "Interaction"
    )
    public static boolean visualSwing = true;

    @Switch(
            title = "1.7 Punching During Usage",
            description = "Purely visual feature. Re-enables the ability to consume food or block a sword whilst punching a block.",
            subcategory = "Interaction")
    @PreviousNames(
            value = {"Animations.Interaction.Punching During Usage"}
    )
    public static boolean punching = true;

    @Checkbox(
            title = "1.7 Punch-During-Usage Particles",
            description = "Spawns Particles whilst Punching During Usage",
            subcategory = "Interaction"
    )
    public static boolean punchingParticles = true;

    // Positions
    @Switch(
            title = "1.7 First-Person Item Transformations",
            description = "Brings back the old item positions from 1.7.",
            subcategory = "Position"
    )
    public static boolean firstTransformations = true;

    @Switch(
            title = "1.7 Third-Person Item Transformations",
            description = "Brings back the old item positions from 1.7.",
            subcategory = "Position"
    )
    public static boolean thirdTransformations = true;

    @Checkbox(
            title = "1.7 First-Person Fishing Rod Position",
            description = "Brings back the old fishing rod position from 1.7.",
            subcategory = "Position"
    )
    public static boolean fishingRodPosition = true;

    @Checkbox(
            title = "1.7 First-Person Carpet Position",
            description = "Brings back the old carpet position from 1.7.",
            subcategory = "Position"
    )
    public static boolean firstPersonCarpetPosition = true;

    @Checkbox(
            title = "1.7 Third-Person Carpet Position",
            description = "Brings back the old carpet position from 1.7.",
            subcategory = "Position"
    )
    public static boolean thirdPersonCarpetPosition = true;

    @Switch(
            title = "1.7 Projectiles Transformations",
            description = "Mirrors and transforms projectiles so that they're facing the correct direction and in the same position as 1.7 or 1.9+.",
            subcategory = "Position"
    )
    public static boolean oldProjectiles = true;

    @Switch(
            title = "1.7 Third-Person Arm Block Position",
            description = "Brings back the old arm rotation while blocking from 1.7.",
            subcategory = "Position")
    public static boolean oldArmPosition = true;

    @Switch(
            title = "1.7 Third-Person Sword Block Position",
            description = "Brings back the old sword rotation while blocking from 1.7.",
            subcategory = "Position"
    )
    @PreviousNames(
            value = {"Animations.Position.1.7 3rd Person Block Animation"}
    )
    public static boolean thirdPersonBlock = true;

    @Switch(
            title = "1.7 XP Orbs Position",
            description = "Brings back the old XP Orbs position from 1.7.",
            subcategory = "Position"
    )
    public static boolean oldXPOrbs = true;

    @Switch(
            title = "1.7 Pickup Animation Position",
            description = "Brings back the old item pickup position from 1.7.",
            subcategory = "Position"
    )
    public static boolean oldPickup = true;

    // Enchantment Glint

    @Switch(
            title = "1.7 Enchantment Glint",
            description = "Brings back the old enchantment glint from 1.7.",
            subcategory = "Enchantment Glint"
    )
    @PreviousNames(
            value = {"General.Cleaner Enchantment Glint"}
    )
    public static boolean enchantmentGlint = true;

    @Switch(
            title = "1.7 GUI Enchantment Glint",
            description = "Brings back the old GUI enchantment glint from 1.7.",
            subcategory = "Enchantment Glint"
    )
    public static boolean enchantmentGlintGui = false;

//    @Switch(
//            title = "1.7 Potion Models (Held)",
//            description = "Use the old potion models from 1.7, making the enchantment glint appear only on the colored part of the potion.",
//            subcategory = "Enchantment Glint"
//    )
//    public static boolean oldPotions = true;
//
//    @Switch(
//            title = "1.7 Potion Models (Dropped)",
//            description = "Use the old potion models from 1.7, making the enchantment glint appear only on the colored part of the potion for dropped items as well.",
//            subcategory = "Enchantment Glint"
//    )
//    public static boolean oldPotionsDropped = false;
//
//    @Switch(
//            title = "1.7 Potion Models (GUI)",
//            description = "Use the old potion models from 1.7, making the enchantment glint appear only on the colored part of the potion for gui items as well.",
//            subcategory = "Enchantment Glint"
//    )
//    public static boolean oldPotionsGui = false;

    // Item Changes

//    @Switch(
//            title = "1.7 Skulls",
//            description = "Displays skulls as a 2D sprite rather than a 3D model, like in 1.7.",
//            subcategory = "Item Changes"
//    )
//    public static boolean oldSkulls = false;

    @Switch(
            title = "1.7 Third-Person Fishing Rod Cast Texture",
            description = "For some reason, in 1.7, when a fishing rod is cast, the third person texture becomes a stick rather than the fishing rod texture. This feature brings back that questionable feature.",
            subcategory = "Item Changes"
    )
    public static boolean fishingStick = false;

    // HUD

    @Switch(
            title = "1.7 Health Bar Flashing",
            description = "Disables the heart flashing texture while taking damage similar to 1.7.",
            subcategory = "HUD"
    )
    @PreviousNames(
            value = {"Animations.HUD.Remove Health Bar Flashing"}
    )
    public static boolean oldHealth = true;

    @Dropdown(
            title = "Debug Menu Crosshair Style",
            description = "Allows you to choose between the 1.7, the vanilla 1.8, and the 1.12+ debug screen crosshair. 1.12+ Debug Screen Crosshair fixes Patcher's Parallax Fix Feature!",
            subcategory = "HUD",
            options = {"1.7", "1.8", "1.12+"}
    )
    public int debugCrosshairMode = 2;

    @Dropdown(
            title = "Debug Menu Style",
            description = "Reverts the debug menu to be aesthetically similar to 1.7",
            options = {"1.7", "1.8", "Disable Background"},
            subcategory = "HUD"
    )
    public int debugScreenMode = 1;

    @Dropdown(
            title = "Tab Menu Style",
            description = "Allows you to choose between the 1.7 tab menu, the 1.8 tab menu, and disabling the player heads in the tab menu.",
            options = {"1.7", "1.8", "Disable Heads"},
            subcategory = "HUD"
    )
    public int tabMode = 1;

    // Miscellaneous
    @Switch(
            title = "1.20+ Potion Colors",
            description = "Back-ports the 1.20 potion overlay colors.",
            category = "Misc", subcategory = "Modern"
    )
    public static boolean modernPotColors = true;

    @Switch(
            title = "1.15+ Armor Enchantment Glint",
            description = "Back-ports the 1.15 armor glint rendering.",
            category = "Misc", subcategory = "Modern"
    )
    public static boolean enchantmentGlintNew = true;

    @Switch(
            title = "1.9+ Bow Pullback / Fishing Cast GUI Animation",
            description = "Shows the Bow Pullback / Fishing Cast textures animating in GUIs.",
            category = "Misc", subcategory = "Modern"
    )
    public static boolean rodBowGuiFix = true;

    @Switch(
            title = "1.15+ Backwards Walk Animation",
            description = "Back-ports the 1.15 walking animation.",
            category = "Misc", subcategory = "Modern"
    )
    public static boolean modernMovement = true;

    @Switch(
            title = "1.14+ View Bobbing",
            description = "Disables view bobbing when the player is falling.",
            category = "Misc", subcategory = "Modern"
    )
    public static boolean modernBobbing = true;

    @Switch(
            title = "1.15+ Drop Item Arm Swing",
            description = "Adds an arm swinging animation upon dropping items.",
            category = "Misc", subcategory = "Modern"
    )
    public static boolean modernDropSwing = true;

    @Switch(
            title = "1.15+ Head Yaw Fix",
            description = "Smooths the rotation of mobs' heads when turning left or right.",
            category = "Misc", subcategory = "Modern"
    )
    public static boolean headYawFix = true;

    @Switch(
            title = "1.9+ Item Use Cooldown Animation",
            description = "Shows the item cooldown reset animation everytime you right click similar to 1.9+.",
            category = "Misc", subcategory = "Modern"
    )
    public static boolean funnyFidget = false;

    @Switch(
            title = "1.19+ Block Breaking Animation",
            description = "Renders the block breaking animation as soon as you start mining similar to 1.19+.",
            category = "Misc", subcategory = "Modern"
    )
    public static boolean modernBreak = true;

    @Switch(
            title = "1.14+ Fireball Model",
            description = "Renders the thrown fireball projectiles as models rather than as sprites similar to 1.14+.",
            category = "Misc", subcategory = "Modern"
    )
    public static boolean fireballModel = false;

    @Switch(
            title = "1.19.4+ Damage Tilt",
            description = "Makes hurt camera shake directional.",
            category = "Misc", subcategory = "Modern"
    )
    public static boolean damageTilt = false;

    @Slider(
            title = "Item Re-equip Animation Speed",
            min = 0.1F, max = 1.0F,
            category = "Misc", subcategory = "Re-equip Animation"
    )
    public float reequipSpeed = 0.4F;

    @Switch(
            title = "Only Allow Re-equip Animation Upon Switching Slots",
            description = "Fixes the re-equip animation to only play when items slots are switched.",
            category = "Misc", subcategory = "Re-equip Animation"
    )
    public static boolean fixReequip = true;

    @Switch(
            title = "Disable Item Pickup Animation",
            description = "Removes the animation played when picking up items.",
            category = "Misc", subcategory = "Pickup Animation"
    )
    public static boolean disablePickup = false;

    @Slider(
            title = "Dropped Item Y Position",
            min = -1.5F, max = 2.5F,
            category = "Misc", subcategory = "Pickup Animation"
    )
    public float pickupPosition = 0.0F;

    @Switch(
            title = "Rod Line Position Based on FOV",
            description = "Includes the player's FOV when calculating the fishing rod cast line position.",
            category = "Misc", subcategory = "Fishing Rod Line"
    )
    public static boolean fixRod = true;

    @Slider(
            title = "Rod Line Thickness Slider",
            min = -100.0F,
            category = "Misc", subcategory = "Fishing Rod Line"
    )
    public float rodThickness = 0.0F;

    @Switch(
            title = "Block Breaking Fixes",
            description = "Resets block removing while using an item or when the player is out of range of a block.",
            category = "Misc", subcategory = "Fixes, QOL, and Tweaks"
    )
    public static boolean breakFix = false;

    @Switch(
            title = "Disable Hand View Sway",
            description = "Disables held item rotations/swaying while the player turns their head.",
            category = "Misc", subcategory = "Fixes, QOL, and Tweaks"
    )
    public static boolean oldItemRotations = false;

    @Switch(
            title = "Disable Potion Enchantment Glint",
            description = "Disables the enchantment glint from rendering on potions.",
            category = "Misc", subcategory = "Fixes, QOL, and Tweaks"
    )
    public static boolean potionGlint = false;

    @Switch(
            title = "Colored Potion Bottles",
            description = "Uses the potion overlay color as the color of the potion bottle.",
            category = "Misc", subcategory = "Fixes, QOL, and Tweaks"
    )
    public static boolean coloredBottles = false;

    @Switch(
            title = "Apply Damage Tint to Held Items",
            description = "Applies the damage tint to entity held items.",
            category = "Misc", subcategory = "Fixes, QOL, and Tweaks"
    )
    public static boolean damageHeldItems = false;

    @Switch(
            title = "Apply Damage Tint to Capes",
            description = "Applies the damage tint to capes.",
            category = "Misc", subcategory = "Fixes, QOL, and Tweaks"
    )
    public static boolean damageCape = false;

    @Checkbox(
            title = "Disable Drop Item Arm Swing in Chests",
            description = "Disables the arm swinging animation upon dropping items while in Chests.",
            category = "Misc", subcategory = "Fixes, QOL, and Tweaks"
    )
    public static boolean modernDropSwingFix = true;

    @Switch(
            title = "Disable Entity/Mob Third-Person Item Transformations",
            description = "Allows/Disallows mobs or entities to have third person item positions applied to them.",
            category = "Misc", subcategory = "Fixes, QOL, and Tweaks"
    )
    public static boolean entityTransforms = true;

    @Switch(
            title = "Disable swinging at the ground in Adventure Mode",
            description = "Allows/Disallows swinging at the ground in Adventure Mode.",
            category = "Misc", subcategory = "Fixes, QOL, and Tweaks"
    )
    public static boolean adventurePunching = false;

    @Switch(
            title = "Disable Punching During Usage in Adventure Mode",
            description = "Allows/Disallows the punching during usage feature in Adventure Mode.",
            category = "Misc", subcategory = "Fixes, QOL, and Tweaks"
    )
    public static boolean adventureBlockHit = false;

    @Checkbox(
            title = "Disable Punch-During-Usage Particles in Adventure Mode",
            description = "Allows/Disallows the particles played while punching during usage to appear while in Adventure Mode.",
            category = "Misc", subcategory = "Fixes, QOL, and Tweaks"
    )
    public static boolean adventureParticles = true;

    @Switch(
            title = "Disable Hurt Camera Shake",
            description = "Disables the camera damage shake.",
            category = "Misc", subcategory = "Fixes, QOL, and Tweaks"
    )
    public static boolean noHurtCam = false;

    @Switch(
            title = "Old Lunar/CheatBreaker Block-Hit Position",
            description = "Brings back the weird block-hitting position from older versions of Lunar Client or CheatBreaker!",
            category = "Misc", subcategory = "Fun"
    )
    public static boolean lunarBlockhit = false;

    @Switch(
            title = "Old Lunar/CheatBreaker Item Positions",
            description = "Brings back the item positions from older versions of Lunar Client or CheatBreaker!",
            category = "Misc", subcategory = "Fun"
    )
    public static boolean lunarPositions = false;

    @Switch(
            title = "Dinnerbone Mode Player-Only",
            description = "Allows the player to be completely upside down, just like Dinnerbone.",
            category = "Misc", subcategory = "Fun"
    )
    public static boolean dinnerBoneMode = false;

    @Switch(
            title = "Dinnerbone Mode All Entities",
            description = "Makes all entities be upside down, just like Dinnerbone.",
            category = "Misc", subcategory = "Fun"
    )
    public static boolean dinnerBoneModeEntities = false;

    @Switch(
            title = "Alpha/Indev Wavy Arms",
            description = "Brings back the wavy arms from Indev.",
            category = "Misc", subcategory = "Fun"
    )
    public static boolean wackyArms = false;

    @Switch(
            title = "Allow Clicking While Using an Item",
            description = "This option is purely visual. Allows the player to swing while clicking and using an item.",
            category = "Misc", subcategory = "Fun"
    )
    public static boolean fakeBlockHit = false;

    @Switch(
            title = "Global Toggle",
            description = "This option globally enables/disables custom item transformations",
            category = "Customize Item Positions"
    )
    public static boolean globalPositions = true;

//    @Button(
//            title = "Globally Reset ALL Item Transformations",
//            text = "Reset",
//            category = "Customize Item Positions"
//    )
//    Runnable resetGlobally = (() -> {
//        itemPositionX = 0.0F;
//        itemPositionY = 0.0F;
//        itemPositionZ = 0.0F;
//        itemRotationYaw = 0.0F;
//        itemRotationPitch = 0.0F;
//        itemRotationRoll = 0.0F;
//        itemScale = 0.0F;
//
//        advancedSettings.itemSwingPositionX = 0.0F;
//        advancedSettings.itemSwingPositionY = 0.0F;
//        advancedSettings.itemSwingPositionZ = 0.0F;
//        itemSwingSpeed = 0.0F;
//        itemSwingSpeedHaste = 0.0F;
//        itemSwingSpeedFatigue = 0.0F;
//        swingSetting = 0;
//        ignoreHaste = false;
//
//        advancedSettings.consumePositionX = 0.0F;
//        advancedSettings.consumePositionY = 0.0F;
//        advancedSettings.consumePositionZ = 0.0F;
//        advancedSettings.consumeRotationYaw = 0.0F;
//        advancedSettings.consumeRotationPitch = 0.0F;
//        advancedSettings.consumeRotationRoll = 0.0F;
//        advancedSettings.consumeScale = 0.0F;
//        advancedSettings.consumeIntensity = 0.0F;
//        advancedSettings.consumeSpeed = 0.0F;
//        ItemPositionAdvancedSettings.shouldScaleEat = false;
//
//        advancedSettings.blockedPositionX = 0.0F;
//        advancedSettings.blockedPositionY = 0.0F;
//        advancedSettings.blockedPositionZ = 0.0F;
//        advancedSettings.blockedRotationYaw = 0.0F;
//        advancedSettings.blockedRotationPitch = 0.0F;
//        advancedSettings.blockedRotationRoll = 0.0F;
//        advancedSettings. blockedScale = 0.0F;
//
//        advancedSettings.droppedPositionX = 0.0F;
//        advancedSettings.droppedPositionY = 0.0F;
//        advancedSettings.droppedPositionZ = 0.0F;
//        advancedSettings.droppedRotationYaw = 0.0F;
//        advancedSettings.droppedRotationPitch = 0.0F;
//        advancedSettings.droppedRotationRoll = 0.0F;
//        advancedSettings.droppedScale = 0.0F;
//
//        advancedSettings.projectilePositionX = 0.0F;
//        advancedSettings.projectilePositionY = 0.0F;
//        advancedSettings.projectilePositionZ = 0.0F;
//        advancedSettings.projectileRotationYaw = 0.0F;
//        advancedSettings.projectileRotationPitch = 0.0F;
//        advancedSettings.projectileRotationRoll = 0.0F;
//        advancedSettings.projectileScale = 0.0F;
//
//        advancedSettings.fireballPositionX = 0.0F;
//        advancedSettings.fireballPositionY = 0.0F;
//        advancedSettings.fireballPositionZ = 0.0F;
//        advancedSettings.fireballRotationYaw = 0.0F;
//        advancedSettings.fireballRotationPitch = 0.0F;
//        advancedSettings.fireballRotationRoll = 0.0F;
//        advancedSettings.fireballScale = 0.0F;
//    });

    @Button(
            title = "Copy / Export Item Positions As String",
            text = "Export",
            description = "Exports the item positions as a Base64 string. Will be copied to your clipboard.",
            category = "Customize Item Positions"
    )
    public void exportItemPositions() {
        AnimationExportUtils.exportItemPositions();
    }

    @Button(
            title = "Import Overflow / Dulkir Item Positions As String",
            text = "Import",
            description = "Exports the item positions as a Base64 string. Will be copied to your clipboard.",
            category = "Customize Item Positions"
    )
    public void importItemPositions() {
        AnimationExportUtils.importItemPositions();
    }

    @Button(
            title = "Transfer Dulkir Item Positions",
            text = "Transfer",
            description = "Transfers your DulkirMod item positions to OverflowAnimations.",
            category = "Customize Item Positions"
    )
    public void transferDulkirItemPositions() {
        AnimationExportUtils.transferDulkirConfig();
    }


    // Item Positions Customization
    @Slider(
            title = "Item X Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Item Position"
    )
    public float itemPositionX = 0.0F;

    @Slider(
            title = "Item Y Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Item Position"
    )
    public float itemPositionY = 0.0F;

    @Slider(
            title = "Item Z Position",
            min = -1.5F, max = 1.5F,
            category = "Customize Item Positions", subcategory = "Item Position"
    )
    public float itemPositionZ = 0.0F;

    @Slider(
            title = "Item Rotation Yaw",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Item Position"
    )
    public float itemRotationYaw = 0.0F;

    @Slider(
            title = "Item Rotation Pitch",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Item Position"
    )
    public float itemRotationPitch = 0.0F;

    @Slider(
            title = "Item Rotation Roll",
            min = -180f, max = 180f, step = 1,
            category = "Customize Item Positions", subcategory = "Item Position"
    )
    public float itemRotationRoll = 0.0F;

    @Slider(
            title = "Item Scale",
            min = -1.5f, max = 1.5f,
            category = "Customize Item Positions", subcategory = "Item Position"
    )
    public float itemScale = 0.0F;

    @Slider(
            title = "Item Swing Speed",
            min = -2.0F, max = 1.0F,
            category = "Customize Item Positions", subcategory = "Item Swing"
    )
    public float itemSwingSpeed = 0.0F;

    @Slider(
            title = "Haste Swing Speed",
            min = -2.0F, max = 1.0F,
            category = "Customize Item Positions", subcategory = "Item Swing"
    )
    public float itemSwingSpeedHaste = 0.0F;

    @Slider(
            title = "Mining Fatigue Swing Speed",
            min = -2.0F, max = 1.0F,
            category = "Customize Item Positions", subcategory = "Item Swing"
    )
    public float itemSwingSpeedFatigue = 0.0F;

    @Dropdown(
            title = "Swing Behavior",
            description = "Allows you to choose between the regular swing behavior, scaled swing behavior, and no swing translation!",
            category = "Customize Item Positions", subcategory = "Item Swing",
            options = {"Default", "Smart Item Swing Scaling", "Disable Swing Translation"}
    )
    public int swingSetting = 0;

    @Button(
            title = "Reset Item Swing Speed",
            text = "Reset",
            category = "Customize Item Positions", subcategory = "Item Swing"
    )
    Runnable resetSpeed = (() -> {
        itemSwingSpeed = 0.0F;
        itemSwingSpeedHaste = 0.0F;
        itemSwingSpeedFatigue = 0.0F;
        swingSetting = 0;
        ignoreHaste = false;
        ignoreFatigue = false;
    });

    @Checkbox(
            title = "Ignore Haste Speed",
            description = "Ignores the haste speed when setting a custom item swing speed.",
            category = "Customize Item Positions", subcategory = "Item Swing"
    )
    public static boolean ignoreHaste = false;

    @Checkbox(
            title = "Ignore Mining Fatigue Speed",
            description = "Ignores the mining fatigue speed when setting a custom item swing speed.",
            category = "Customize Item Positions", subcategory = "Item Swing"
    )
    public static boolean ignoreFatigue = false;

    @Button(
            title = "Reset Item Transformations",
            text = "Reset",
            category = "Customize Item Positions", subcategory = "Item Position"
    )
    Runnable resetItem = (() -> {
        itemPositionX = 0.0F;
        itemPositionY = 0.0F;
        itemPositionZ = 0.0F;
        itemRotationYaw = 0.0F;
        itemRotationPitch = 0.0F;
        itemRotationRoll = 0.0F;
        itemScale = 0.0F;
    });

    //todo: wyvest help
//    @Page(
//            title = "Advanced Item Customization Settings",
//            description = "Customize all sorts of item positions!",
//            location = PageLocation.BOTTOM,
//            category = "Customize Item Positions", subcategory = "Advanced Settings"
//    )
    public static ItemPositionAdvancedSettings advancedSettings = new ItemPositionAdvancedSettings();

    @Include public static boolean didTheFunnyDulkirThingElectricBoogaloo = false;

    public static final OldAnimationsSettings INSTANCE = new OldAnimationsSettings();

    public OldAnimationsSettings() {
        //todo: wyvest help

        // new VigilanceMigrator("./config/sk1eroldanimations.toml")
        super("overflowanimations.json", OverflowAnimations.NAME, Category.COMBAT);
        //, "/overflowanimations_dark.svg"
//        initialize();

        addCallback("modernPotColors", PotionColors::reloadColor);

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
        // Sneaking
        addDependency("longerUnsneak", "smoothSneaking");
    }
}
