package org.polyfrost.overflowanimations.config

import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.elements.SubConfig
import cc.polyfrost.oneconfig.config.migration.VigilanceName
import net.minecraft.client.Minecraft
import org.polyfrost.overflowanimations.hooks.AnimationExportUtils

class OldAnimationsSettings : SubConfig("Old Animations", "oldanimations.json") {

    // 2D Items
    @Switch(
        name = "2D Dropped Items",
        description = "Renders items as sprites rather than as models.",
        subcategory = "2D Dropped Items"
    )
    var itemSprites: Boolean = false

    @Checkbox(
        name = "1.7 Item Sprite Colors",
        description = "Changes the colors of the dropped item sprites to be brighter just like in 1.7.",
        subcategory = "2D Dropped Items"
    )
    var itemSpritesColor: Boolean = false

    @Checkbox(
        name = "Remove Glint From Sprites",
        description = "This will disable the enchantment glint for both dropped items and projectiles. Only works with 2D items enabled.",
        subcategory = "2D Dropped Items"
    )
    var spritesGlint: Boolean = false

    @Switch(
        name = "Rotation Fix",
        description = "Allows dropped items to face the player properly without being stuck on the Y-Axis.",
        subcategory = "2D Dropped Items"
    )
    var rotationFix: Boolean = true

    // Smooth Sneaking
    @Switch(
        name = "1.7 Smoother Sneaking",
        description = "Smoothens the player camera to appear just like the 1.7 smoother sneaking camera.",
        subcategory = "Smooth Sneaking"
    )
    @VigilanceName(name = "Smooth Sneaking", category = "Animations", subcategory = "Interaction")
    var smoothSneaking: Boolean = true

    @Switch(
        name = "1.7 Longer Unsneak",
        description = "Lengthens the player camera's speed to appear just like the 1.7 smoother sneaking camera.",
        subcategory = "Smooth Sneaking"
    )
    @VigilanceName(name = "Longer Unsneak", category = "Animations", subcategory = "Interaction")
    var longerUnsneak: Boolean = true

    @Switch(
        name = "1.7 Third Person Smooth Sneaking",
        description = "Smoothens the player model while sneaking to replicate the same behavior in 1.7. Disable if incompatible with cosmetic mods",
        subcategory = "Smooth Sneaking"
    )
    var smoothModelSneak: Boolean = false

    // Interaction
    @Switch(
        name = "1.7 Block-Hitting Animation",
        description = "Re-enables the block-hitting animations.",
        subcategory = "Interaction"
    )
    @VigilanceName(name = "Block-Hitting Animation", category = "Animations", subcategory = "Interaction")
    var oldBlockhitting: Boolean = true

    @Switch(
        name = "1.7 Armor Damage Tint",
        description = "Allows the armor to also have the damage tint applied to it.",
        subcategory = "Interaction"
    )
    @VigilanceName(name = "Red Armor", category = "Animations", subcategory = "Interaction")
    var redArmor: Boolean = true

    @Switch(
        name = "1.7 Item Switching Animation",
        description = "Re-enables the item switch animation from 1.7.",
        subcategory = "Interaction"
    )
    @VigilanceName(name = "Item Switching Animation", category = "Animations", subcategory = "Interaction")
    var itemSwitch: Boolean = true

    @Switch(
        name = "1.7 Miss Penalty Swing Animation",
        description = "This option is purely visual. During the miss penalty, the player's arm will still swing and show particles just like in 1.7.",
        subcategory = "Interaction"
    )
    var visualSwing: Boolean = true

    @Switch(
        name = "1.7 Punching During Usage",
        description = "Purely visual feature. Re-enables the ability to consume food or block a sword whilst punching a block.",
        subcategory = "Interaction"
    )
    @VigilanceName(name = "Punching During Usage", category = "Animations", subcategory = "Interaction")
    var punching: Boolean = true

    @Checkbox(
        name = "1.7 Punch-During-Usage Particles",
        description = "Spawns Particles whilst Punching During Usage",
        subcategory = "Interaction"
    )
    var punchingParticles: Boolean = true

    // Positions
    @Switch(
        name = "1.7 First-Person Item Transformations",
        description = "Brings back the old item positions from 1.7.",
        subcategory = "Position"
    )
    var firstTransformations: Boolean = true

    @Switch(
        name = "1.7 Third-Person Item Transformations",
        description = "Brings back the old item positions from 1.7.",
        subcategory = "Position"
    )
    var thirdTransformations: Boolean = true

    @Checkbox(
        name = "1.7 First-Person Fishing Rod Position",
        description = "Brings back the old fishing rod position from 1.7.",
        subcategory = "Position"
    )
    var fishingRodPosition: Boolean = true

    @Checkbox(
        name = "1.7 First-Person Carpet Position",
        description = "Brings back the old carpet position from 1.7.",
        subcategory = "Position"
    )
    var firstPersonCarpetPosition: Boolean = true

    @Checkbox(
        name = "1.7 Third-Person Carpet Position",
        description = "Brings back the old carpet position from 1.7.",
        subcategory = "Position"
    )
    var thirdPersonCarpetPosition: Boolean = true

    @Switch(
        name = "1.7 Projectiles Transformations",
        description = "Mirrors and transforms projectiles so that they're facing the correct direction and in the same position as 1.7 or 1.9+.",
        subcategory = "Position"
    )
    var oldProjectiles: Boolean = true

    @Switch(
        name = "1.7 Third-Person Arm Block Position",
        description = "Brings back the old arm rotation while blocking from 1.7.",
        subcategory = "Position"
    )
    var oldArmPosition: Boolean = true

    @Switch(
        name = "1.7 Third-Person Sword Block Position",
        description = "Brings back the old sword rotation while blocking from 1.7.",
        subcategory = "Position"
    )
    @VigilanceName(name = "1.7 3rd Person Block Animation", category = "Animations", subcategory = "Position")
    var thirdPersonBlock: Boolean = true

    @Switch(
        name = "1.7 XP Orbs Position",
        description = "Brings back the old XP Orbs position from 1.7.",
        subcategory = "Position"
    )
    var oldXPOrbs: Boolean = true

    @Switch(
        name = "1.7 Pickup Animation Position",
        description = "Brings back the old item pickup position from 1.7.",
        subcategory = "Position"
    )
    var oldPickup: Boolean = true

    // Item Changes
    @Switch(
        name = "1.7 Third-Person Fishing Rod Cast Texture",
        description = "For some reason, in 1.7, when a fishing rod is cast, the third person texture becomes a stick rather than the fishing rod texture. This feature brings back that questionable feature.",
        subcategory = "Item Changes"
    )
    var fishingStick: Boolean = false

    // HUD
    @Switch(
        name = "1.7 Health Bar Flashing",
        description = "Disables the heart flashing texture while taking damage similar to 1.7.",
        subcategory = "HUD"
    )
    @VigilanceName(name = "Remove Health Bar Flashing", category = "Animations", subcategory = "HUD")
    var oldHealth: Boolean = true

    // Miscellaneous
    @Switch(
        name = "1.20+ Potion Colors",
        description = "Back-ports the 1.20 potion overlay colors.",
        category = "Misc",
        subcategory = "Modern"
    )
    var modernPotColors: Boolean = true

    @Switch(
        name = "1.15+ Armor Enchantment Glint",
        description = "Back-ports the 1.15 armor glint rendering.",
        category = "Misc",
        subcategory = "Modern"
    )
    var enchantmentGlintNew: Boolean = true

    @Switch(
        name = "1.9+ Bow Pullback / Fishing Cast GUI Animation",
        description = "Shows the Bow Pullback / Fishing Cast textures animating in GUIs.",
        category = "Misc",
        subcategory = "Modern"
    )
    var rodBowGuiFix: Boolean = true

    @Switch(
        name = "1.15+ Backwards Walk Animation",
        description = "Back-ports the 1.15 walking animation.",
        category = "Misc",
        subcategory = "Modern"
    )
    var modernMovement: Boolean = true

    @Switch(
        name = "1.14+ View Bobbing",
        description = "Disables view bobbing when the player is falling.",
        category = "Misc",
        subcategory = "Modern"
    )
    var modernBobbing: Boolean = true

    @Switch(
        name = "1.15+ Drop Item Arm Swing",
        description = "Adds an arm swinging animation upon dropping items.",
        category = "Misc",
        subcategory = "Modern"
    )
    var modernDropSwing: Boolean = true

    @Switch(
        name = "1.15+ Head Yaw Fix",
        description = "Smooths the rotation of mobs' heads when turning left or right.",
        category = "Misc",
        subcategory = "Modern"
    )
    var headYawFix: Boolean = true

    @Switch(
        name = "1.9+ Item Use Cooldown Animation",
        description = "Shows the item cooldown reset animation everytime you right click similar to 1.9+.",
        category = "Misc",
        subcategory = "Modern"
    )
    var funnyFidget: Boolean = false

    @Switch(
        name = "Disable Item Re-equip Animation",
        description = "Completely removed the the item re-equip animation.",
        category = "Misc",
        subcategory = "Re-quip Animation"
    )
    var disableReequip: Boolean = false

    @Switch(
        name = "Only Allow Re-equip Animation Upon Switching Slots",
        description = "Fixes the re-equip animation to only play when items slots are switched.",
        category = "Misc",
        subcategory = "Re-quip Animation"
    )
    var fixReequip: Boolean = true

    @Switch(
        name = "Disable Item Pickup Animation",
        description = "Removes the animation played when picking up items.",
        category = "Misc",
        subcategory = "Pickup Animation"
    )
    var disablePickup: Boolean = false

    @Switch(
        name = "Rod Line Position Based on FOV",
        description = "Includes the player's FOV when calculating the fishing rod cast line position.",
        category = "Misc",
        subcategory = "Fishing Rod Line"
    )
    var fixRod: Boolean = true

    @Switch(
        name = "Rod Line Thickness",
        description = "Enable to customize the rod line's thickness",
        category = "Misc",
        subcategory = "Fishing Rod Line"
    )
    var rodThickBool: Boolean = true

    @Switch(
        name = "Render Fireball Projectile as a Model",
        description = "Renders the thrown fireball projectiles as models rather than as sprites.",
        category = "Misc",
        subcategory = "Fixes, QOL, and Tweaks"
    )
    var fireballModel: Boolean = false

    // todo: xp bottle model + move both model options to modern category
    @Switch(
        name = "Block Breaking Fixes",
        description = "Resets block removing while using an item or when the player is out of range of a block.",
        category = "Misc",
        subcategory = "Fixes, QOL, and Tweaks"
    )
    var breakFix: Boolean = false

    @Switch(
        name = "Disable Hand View Sway",
        description = "Disables held item rotations/swaying while the player turns their head.",
        category = "Misc",
        subcategory = "Fixes, QOL, and Tweaks"
    )
    var oldItemRotations: Boolean = false

    @Switch(
        name = "Disable Potion Enchantment Glint",
        description = "Disables the enchantment glint from rendering on potions.",
        category = "Misc",
        subcategory = "Fixes, QOL, and Tweaks"
    )
    var potionGlint: Boolean = false

    @Switch(
        name = "Colored Potion Bottles",
        description = "Uses the potion overlay color as the color of the potion bottle.",
        category = "Misc",
        subcategory = "Fixes, QOL, and Tweaks"
    )
    var coloredBottles: Boolean = false

    @Switch(
        name = "Disable Entity/Mob Third-Person Item Transformations",
        description = "Allows/Disallows mobs or entities to have third person item positions applied to them.",
        category = "Misc",
        subcategory = "Fixes, QOL, and Tweaks"
    )
    var entityTransforms: Boolean = true

    @Switch(
        name = "Disable swinging at the ground in Adventure Mode",
        description = "Allows/Disallows swinging at the ground in Adventure Mode.",
        category = "Misc",
        subcategory = "Fixes, QOL, and Tweaks"
    )
    var adventurePunching: Boolean = false

    @Switch(
        name = "Disable Punching During Usage in Adventure Mode",
        description = "Allows/Disallows the punching during usage feature in Adventure Mode.",
        category = "Misc",
        subcategory = "Fixes, QOL, and Tweaks"
    )
    var adventureBlockHit: Boolean = false

    @Checkbox(
        name = "Disable Punch-During-Usage Particles in Adventure Mode",
        description = "Allows/Disallows the particles played while punching during usage to appear while in Adventure Mode.",
        category = "Misc",
        subcategory = "Fixes, QOL, and Tweaks"
    )
    var adventureParticles: Boolean = true

    @Switch(
        name = "Disable Hurt Camera Shake",
        description = "Disables the camera damage shake.",
        category = "Misc",
        subcategory = "Fixes, QOL, and Tweaks"
    )
    var noHurtCam: Boolean = false

    @Switch(
        name = "Old Lunar/CheatBreaker Block-Hit Position",
        description = "Brings back the weird block-hitting position from older versions of Lunar Client or CheatBreaker!",
        category = "Misc",
        subcategory = "Fun"
    )
    var lunarBlockhit: Boolean = false

    @Switch(
        name = "Dinnerbone Mode Player-Only",
        description = "Allows the player to be completely upside down, just like Dinnerbone.",
        category = "Misc",
        subcategory = "Fun"
    )
    var dinnerBoneMode: Boolean = false

    @Switch(
        name = "Dinnerbone Mode All Entities",
        description = "Makes all entities be upside down, just like Dinnerbone.",
        category = "Misc",
        subcategory = "Fun"
    )
    var dinnerBoneModeEntities: Boolean = false

    @Switch(
        name = "Alpha/Indev Wavy Arms",
        description = "Brings back the wavy arms from Indev.",
        category = "Misc",
        subcategory = "Fun"
    )
    var wackyArms: Boolean = false

    @Switch(
        name = "Allow Clicking While Using an Item",
        description = "This option is purely visual. Allows the player to swing while clicking and using an item.",
        category = "Misc",
        subcategory = "Fun"
    )
    var fakeBlockHit: Boolean = false

    @Switch(
        name = "Remove Potion Overlay Color",
        description = "Removes the overlay color of potions effectively making them all look like potions of milk. MOOOOOOOOO.",
        category = "Misc",
        subcategory = "Fun"
    )
    var milkMode: Boolean = false

    @Switch(
        name = "Global Toggle",
        description = "This option globally enables/disables custom item transformations",
        category = "Customize Item Positions"
    )
    var globalPositions: Boolean = true

    //    @Button(
    //            name = "Reset Item Swing Speed",
    //            text = "Reset",
    //            category = "Customize Item Positions", subcategory = "Item Swing"
    //    )
    //    Runnable resetSpeed = (() -> {
    //        Minecraft.getMinecraft().displayGuiScreen(null);
    //        itemSwingSpeed = 0.0F;
    //        itemSwingSpeedHaste = 0.0F;
    //        itemSwingSpeedFatigue = 0.0F;
    //        swingSetting = 0;
    //        ignoreHaste = false;
    //        ignoreFatigue = false;
    //        OldAnimationsSettings.INSTANCE.save();
    //        OldAnimationsSettings.INSTANCE.openGui();
    //    });
    @Checkbox(
        name = "Ignore Haste Speed",
        description = "Ignores the haste speed when setting a custom item swing speed.",
        category = "Customize Item Positions",
        subcategory = "Item Swing"
    )
    var ignoreHaste: Boolean = false

    @Checkbox(
        name = "Ignore Mining Fatigue Speed",
        description = "Ignores the mining fatigue speed when setting a custom item swing speed.",
        category = "Customize Item Positions",
        subcategory = "Item Swing"
    )
    var ignoreFatigue: Boolean = false

    var didTheFunnyDulkirThingElectricBoogaloo: Boolean = false

    @Dropdown(
        name = "Debug Menu Crosshair Style",
        description = "Allows you to choose between the 1.7, the vanilla 1.8, and the 1.12+ debug screen crosshair. 1.12+ Debug Screen Crosshair fixes Patcher's Parallax Fix Feature!",
        subcategory = "HUD",
        options = ["1.7", "1.8", "1.12+"]
    )
    var debugCrosshairMode: Int = 2

    @Dropdown(
        name = "Debug Menu Style",
        description = "Reverts the debug menu to be aesthetically similar to 1.7",
        options = ["1.7", "1.8", "Disable Background"],
        subcategory = "HUD"
    )
    var debugScreenMode: Int = 1

    @Dropdown(
        name = "Tab Menu Style",
        description = "Allows you to choose between the 1.7 tab menu, the 1.8 tab menu, and disabling the player heads in the tab menu.",
        options = ["1.7", "1.8", "Disable Heads"],
        subcategory = "HUD"
    )
    var tabMode: Int = 1

    @Slider(
        name = "Item Re-equip Animation Speed",
        min = 0.1f,
        max = 1.0f,
        category = "Misc",
        subcategory = "Re-quip Animation",
        instant = true
    )
    var reequipSpeed: Float = 0.4f

    @Slider(
        name = "Dropped Item Y Position",
        min = -1.5f,
        max = 2.5f,
        category = "Misc",
        subcategory = "Pickup Animation",
        instant = true
    )
    var pickupPosition: Float = if (oldPickup) 1.0f else -0.5f

    @Slider(
        name = "Rod Line Thickness Slider",
        min = -100.0f,
        max = 100.0f,
        category = "Misc",
        subcategory = "Fishing Rod Line",
        instant = true
    )
    var rodThickness: Float = 0.0f

//    @Button(
//            name = "Globally Reset ALL Item Transformations",
//            text = "Reset",
//            category = "Customize Item Positions"
//    )
//    Runnable resetGlobally = (() -> {
//        itemPositionX = 0.0F
//        itemPositionY = 0.0F
//        itemPositionZ = 0.0F
//        itemRotationYaw = 0.0F
//        itemRotationPitch = 0.0F
//        itemRotationRoll = 0.0F
//        itemScale = 0.0F
//
//        advancedSettings.itemSwingPositionX = 0.0F
//        advancedSettings.itemSwingPositionY = 0.0F
//        advancedSettings.itemSwingPositionZ = 0.0F
//        itemSwingSpeed = 0.0F
//        itemSwingSpeedHaste = 0.0F
//        itemSwingSpeedFatigue = 0.0F
//        swingSetting = 0
//        ignoreHaste = false
//
//        advancedSettings.consumePositionX = 0.0F
//        advancedSettings.consumePositionY = 0.0F
//        advancedSettings.consumePositionZ = 0.0F
//        advancedSettings.consumeRotationYaw = 0.0F
//        advancedSettings.consumeRotationPitch = 0.0F
//        advancedSettings.consumeRotationRoll = 0.0F
//        advancedSettings.consumeScale = 0.0F
//        advancedSettings.consumeIntensity = 0.0F
//        advancedSettings.consumeSpeed = 0.0F
//        ItemPositionSettings.shouldScaleEat = false;
//
//        advancedSettings.blockedPositionX = 0.0F
//        advancedSettings.blockedPositionY = 0.0F
//        advancedSettings.blockedPositionZ = 0.0F
//        advancedSettings.blockedRotationYaw = 0.0F
//        advancedSettings.blockedRotationPitch = 0.0F
//        advancedSettings.blockedRotationRoll = 0.0F
//        advancedSettings. blockedScale = 0.0F
//
//        advancedSettings.droppedPositionX = 0.0F
//        advancedSettings.droppedPositionY = 0.0F
//        advancedSettings.droppedPositionZ = 0.0F
//        advancedSettings.droppedRotationYaw = 0.0F
//        advancedSettings.droppedRotationPitch = 0.0F
//        advancedSettings.droppedRotationRoll = 0.0F
//        advancedSettings.droppedScale = 0.0F
//
//        advancedSettings.projectilePositionX = 0.0F
//        advancedSettings.projectilePositionY = 0.0F
//        advancedSettings.projectilePositionZ = 0.0F
//        advancedSettings.projectileRotationYaw = 0.0F
//        advancedSettings.projectileRotationPitch = 0.0F
//        advancedSettings.projectileRotationRoll = 0.0F
//        advancedSettings.projectileScale = 0.0F
//
//        advancedSettings.fireballPositionX = 0.0F
//        advancedSettings.fireballPositionY = 0.0F
//        advancedSettings.fireballPositionZ = 0.0F
//        advancedSettings.fireballRotationYaw = 0.0F
//        advancedSettings.fireballRotationPitch = 0.0F
//        advancedSettings.fireballRotationRoll = 0.0F
//        advancedSettings.fireballScale = 0.0F
//    });

    @Button(
        name = "Copy / Export Item Positions As String",
        text = "Export",
        description = "Exports the item positions as a Base64 string. Will be copied to your clipboard.",
        category = "Customize Item Positions"
    )
    fun exportItemPositions() {
        AnimationExportUtils.exportItemPositions()
    }

    @Button(
        name = "Import Overflow / Dulkir Item Positions As String",
        text = "Import",
        description = "Exports the item positions as a Base64 string. Will be copied to your clipboard.",
        category = "Customize Item Positions"
    )
    fun importItemPositions() {
        Minecraft.getMinecraft().displayGuiScreen(null)
        AnimationExportUtils.importItemPositions()
        openGui()
    }

    @Button(
        name = "Transfer Dulkir Item Positions",
        text = "Transfer",
        description = "Transfers your DulkirMod item positions to OverflowAnimations.",
        category = "Customize Item Positions"
    )
    fun transferDulkirItemPositions() {
        Minecraft.getMinecraft().displayGuiScreen(null)
        AnimationExportUtils.transferDulkirConfig()
        openGui()
    }

    // Item Positions Customization
    @Slider(
        name = "Item X Position",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Item Position",
        instant = true
    )
    var itemPositionX: Float = 0.0f

    @Slider(
        name = "Item Y Position",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Item Position",
        instant = true
    )
    var itemPositionY: Float = 0.0f

    @Slider(
        name = "Item Z Position",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Item Position",
        instant = true
    )
    var itemPositionZ: Float = 0.0f

    @Slider(
        name = "Item Rotation Yaw",
        min = -180f,
        max = 180f,
        step = 1,
        category = "Customize Item Positions",
        subcategory = "Item Position",
        instant = true
    )
    var itemRotationYaw: Float = 0.0f

    @Slider(
        name = "Item Rotation Pitch",
        min = -180f,
        max = 180f,
        step = 1,
        category = "Customize Item Positions",
        subcategory = "Item Position",
        instant = true
    )
    var itemRotationPitch: Float = 0.0f

    @Slider(
        name = "Item Rotation Roll",
        min = -180f,
        max = 180f,
        step = 1,
        category = "Customize Item Positions",
        subcategory = "Item Position",
        instant = true
    )
    var itemRotationRoll: Float = 0.0f

    @Slider(
        name = "Item Scale",
        min = -1.5f,
        max = 1.5f,
        category = "Customize Item Positions",
        subcategory = "Item Position",
        instant = true
    )
    var itemScale: Float = 0.0f

    @Slider(
        name = "Item Swing Speed",
        min = -2.0f,
        max = 1.0f,
        category = "Customize Item Positions",
        subcategory = "Item Swing",
        instant = true
    )
    var itemSwingSpeed: Float = 0.0f

    @Slider(
        name = "Haste Swing Speed",
        min = -2.0f,
        max = 1.0f,
        category = "Customize Item Positions",
        subcategory = "Item Swing",
        instant = true
    )
    var itemSwingSpeedHaste: Float = 0.0f

    @Slider(
        name = "Mining Fatigue Swing Speed",
        min = -2.0f,
        max = 1.0f,
        category = "Customize Item Positions",
        subcategory = "Item Swing",
        instant = true
    )
    var itemSwingSpeedFatigue: Float = 0.0f

    @Dropdown(
        name = "Swing Behavior",
        description = "Allows you to choose between the regular swing behavior, scaled swing behavior, and no swing translation!",
        category = "Customize Item Positions",
        subcategory = "Item Swing",
        options = ["Default", "Smart Item Swing Scaling", "Disable Swing Translation"]
    )
    var swingSetting: Int = 0

    @Button(
        name = "Reset Item Transformations",
        text = "Reset",
        category = "Customize Item Positions",
        subcategory = "Item Position"
    )
    var resetItem: Runnable = (Runnable {
        Minecraft.getMinecraft().displayGuiScreen(null)
        itemPositionX = 0.0f
        itemPositionY = 0.0f
        itemPositionZ = 0.0f
        itemRotationYaw = 0.0f
        itemRotationPitch = 0.0f
        itemRotationRoll = 0.0f
        itemScale = 0.0f
        save()
        openGui()
    })

    //    @Exclude public static final OldAnimationsSettings INSTANCE = new OldAnimationsSettings();
    init {
        initialize()
    } //    public OldAnimationsSettings() {
    //        super(new Mod(OverflowAnimations.NAME, ModType.PVP, "/overflowanimations_dark.svg", new VigilanceMigrator("./config/sk1eroldanimations.toml")), "overflowanimations.json");
    //        initialize();
    //
    //        addListener("modernPotColors", PotionColors::reloadColor);
    //
    //        // Sprites
    //        addDependency("rotationFix", "itemSprites");
    //        addDependency("spritesGlint", "itemSprites");
    //        addDependency("itemSpritesColor", "itemSprites");
    //        // Interactions
    //        addDependency("punching", "oldBlockhitting");
    //        addDependency("punchingParticles", "oldBlockhitting");
    //        addDependency("adventureParticles", "oldBlockhitting");
    //        addDependency("adventurePunching", "oldBlockhitting");
    //        addDependency("adventureParticles", "punchingParticles");
    //        addDependency("punchingParticles", "punching");
    //        addDependency("adventureParticles", "punching");
    //        addDependency("adventurePunching", "punching");
    //        // Transformations
    //        addDependency("firstPersonCarpetPosition", "itemTransformations");
    //        addDependency("fixRod", "itemTransformations");
    //        addDependency("entityTransforms", "thirdTransformations");
    //        // Sneaking
    //        addDependency("longerUnsneak", "smoothSneaking");
    //        addDependency("smoothModelSneak", "smoothSneaking");
    //    }

}
