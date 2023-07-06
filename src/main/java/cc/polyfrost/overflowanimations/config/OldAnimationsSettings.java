package cc.polyfrost.overflowanimations.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Checkbox;
import cc.polyfrost.oneconfig.config.annotations.Dropdown;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.oneconfig.config.migration.VigilanceMigrator;
import cc.polyfrost.oneconfig.config.migration.VigilanceName;
import cc.polyfrost.overflowanimations.OverflowAnimations;

@SuppressWarnings("unused")
public class OldAnimationsSettings extends Config {

    @Switch(name = "1.7 Dropped Items", description = "Dropped items face the camera.", subcategory = "2D Items")
    public static boolean items2D = true;

    @Checkbox(name = "1.7 Item Sprites", description = "This option will render items as sprites rather than as models.", subcategory = "2D Items")
    public static boolean itemSprites = true;

    @Checkbox(name = "1.7 Projectile Sprites", description = "Projectiles such as snowballs, ender pearls, and eggs render as sprites rather than models.", subcategory = "2D Items")
    public static boolean projectileSprites = true;

    @Switch(name = "1.7 Block-Hitting Animation", description = "Re-enables the block-hitting animations.", subcategory = "Interaction")
    @VigilanceName(name = "1.7 Block-Hitting Animation", category = "Animations", subcategory = "Interaction")
    public static boolean oldBlockhitting = true;

    @Switch(name = "1.7 Smoother Sneaking", description = "Smoothens the player camera to appear just like the 1.7 smoother sneaking camera.", subcategory = "Interaction")
    @VigilanceName(name = "1.7 Smoother Sneaking", category = "Animations", subcategory = "Interaction")
    public static boolean smoothSneaking = true;

    @Switch(name = "1.7 Armor Damage Tint", description = "Allows the armor to also have the damage tint applied to it.", subcategory = "Interaction")
    @VigilanceName(name = "1.7 Red Armor Tint", category = "Animations", subcategory = "Interaction")
    public static boolean redArmor = true;

    @Switch(name = "1.7 Item Switching Animation", description = "Re-enables the item switch animation from 1.7.", subcategory = "Interaction")
    @VigilanceName(name = "1.7 Item Switching Animation", category = "Animations", subcategory = "Interaction")
    public static boolean itemSwitch = true;

    @Switch(name = "1.7 Punching During Usage", description = "Purely visual feature. Re-enables the ability to consume food or block a sword whilst punching a block.", subcategory = "Interaction")
    @VigilanceName(name = "1.7 Punching During Usage", category = "Animations", subcategory = "Interaction")
    public static boolean punching = true;

    @Checkbox(name = "1.7 Punch-During-Usage Particles", description = "Spawns Particles whilst Punching During Usage", subcategory = "Interaction")
    public static boolean punchingParticles = true;

    @Switch(name = "1.7 First-Person Item Transformations", description = "Turn off to use resource packs that modify item transformations. Brings back the old item positions from 1.7.", subcategory = "Position")
    public static boolean itemTransformations = true;

    @Switch(name = "1.7 Third-Person Item Transformations", description = "Turn off to use resource packs that modify item transformations. Brings back the old item positions from 1.7.", subcategory = "Position")
    public static boolean thirdTransformations = true;

    @Checkbox(name = "1.7 First-Person Carpet Position", description = "Brings back the old carpet position from 1.7.", subcategory = "Position")
    public static boolean firstPersonCarpetPosition = true;

    @Switch(name = "1.7 Third-Person Arm Block Position", description = "Brings back the old arm rotation while blocking from 1.7.", subcategory = "Position")
    @VigilanceName(name = "1.7 3rd Person Block Animation", category = "Animations", subcategory = "Position")
    public static boolean oldSwordBlock3 = true;

    @Switch(name = "1.7 Mirrored Projectiles", description = "Mirrors projectiles so that they're facing the correct direction similar to 1.7 or 1.9+.", subcategory = "Position")
    public static boolean oldProjectiles = true;

    @Switch(name = "1.7 Third-Person Sword Block Position", description = "Brings back the old sword rotation while blocking from 1.7.", subcategory = "Position")
    @VigilanceName(name = "1.7 3rd Person Block Animation", category = "Animations", subcategory = "Position")
    public static boolean thirdPersonBlock = true;

    @Switch(name = "1.7 XP Orbs", description = "Brings back the old XP Orbs position from 1.7.", subcategory = "Position")
    public static boolean oldXPOrbs = true;

    @Switch(name = "1.7 Pickup Animation", description = "Brings back the old item pickup position from 1.7.", subcategory = "Position")
    public static boolean oldPickup = true;

    @Switch(name = "1.7 Enchantment Glint", description = "Reverts the glint rendering to be split up similar to 1.7.", subcategory = "HUD")
    public static boolean enchantmentGlint = true;

    @Switch(name = "1.7 Health Bar Flashing", description = "Disables the heart flashing texture while taking damage similar to 1.7.", subcategory = "HUD")
    @VigilanceName(name = "Remove Health Bar Flashing", category = "Animations", subcategory = "HUD")
    public static boolean oldHealth = true;

    @Dropdown(name = "Debug Menu Style", description = "Reverts the debug menu to be aesthetically similar to 1.7",
            options = {"1.7", "1.8", "Disable Background"})
    public int debugScreenMode = 1;

    @Switch(name = "1.7 Debug Screen Crosshair", description = "Disables the RGB cross-hair in the debug menu.", subcategory = "HUD")
    public static boolean oldDebugCrosshair = true;

    @Dropdown(name = "Tab Menu Style", description = "Allows you to choose between the 1.7 tab menu, the 1.8 tab menu, " +
            "and disabling the player heads in the tab menu.", options = {"1.7", "1.8", "Disable Heads"})
    public int tabMode = 1;

    @Switch(name = "1.15+ Armor Enchantment Glint", description = "Back-ports the 1.15 armor glint rendering.", subcategory = "Modern Features")
    public static boolean enchantmentGlintNew = false;

    @Switch(name = "1.15+ Item Throwing Animation", description = "Back-ports the 1.15 item throwing animation while dropping items or throwing projectiles.", subcategory = "Modern Features")
    public static boolean itemThrow = false;

    @Switch(name = "Miss Penalty Swing Animation", description = "This option is purely visual. During the miss penalty, the player's arm will still swing and show particles.",subcategory = "Fixes")
    public static boolean visualSwing = true;

    @Switch(name = "Dropped Item Sprites Rotation Fix", description = "Allows 2D items to face the player properly without being stuck on the Y-Axis.", subcategory = "Fixes")
    public static boolean rotationFix = true;

    @Checkbox(name = "Remove Glint From Sprites", description = "This will disable the enchantment glint for both dropped items and projectiles. Only works with 2D items enabled.", subcategory = "Fixes")
    public static boolean spritesGlint = false;

    @Switch(name = "Rod Line Position Based on FOV", description = "Includes the player's FOV when calculating the fishing rod cast line position.", subcategory = "Fixes")
    public static boolean fixRod = true;

    @Switch(name = "Disable Entity/Mob Third-Person Item Transformations", description = "Allows/Disallows mobs or entities to have third person item positions applied to them.", subcategory = "Fixes")
    public static boolean entityTransforms = true;

    @Switch(name = "Disable Punching During Usage in Adventure Mode", description = "Allows/Disallows the punching during usage feature in Adventure Mode.", subcategory = "Fixes")
    public static boolean adventurePunching = false;

    @Switch(name = "Disable Punch-During-Usage Particles in Adventure Mode", description = "Allows/Disallows the particles played while punching during usage to appear while in Adventure Mode.", subcategory = "Fixes")
    public static boolean adventureParticles = false;

    @Switch(name = "Disable Hurt Camera Shake", description = "Disables the camera damage shake.", subcategory = "Fun")
    public static boolean noHurtCam = false;

    @Switch(name = "Lunar Block-hitting Position", description = "Brings back the weird block-hitting position from older versions of Lunar Client. Compatible with overlay texture packs!", subcategory = "Fun")
    public static boolean lunarBlockhit = false;

    public static final OldAnimationsSettings INSTANCE = new OldAnimationsSettings();

    public OldAnimationsSettings() {
        super(new Mod(OverflowAnimations.NAME, ModType.PVP, "/overflowanimations_dark.svg", new VigilanceMigrator("./config/sk1eroldanimations.toml")), "overflowanimations.json");
        initialize();
        addDependency("itemSprites", "items2D");
        addDependency("rotationFix", "items2D");
        addDependency("spritesGlint", "items2D");
        addDependency("projectileSprites", "items2D");
        addDependency("projectileSprites", "itemSprites");
        addDependency("punching", "oldBlockhitting");
        addDependency("punchingParticles", "oldBlockhitting");
        addDependency("adventureParticles", "oldBlockhitting");
        addDependency("adventurePunching", "oldBlockhitting");
        addDependency("adventureParticles", "punchingParticles");
        addDependency("punchingParticles", "punching");
        addDependency("adventureParticles", "punching");
        addDependency("adventurePunching", "punching");
        addDependency("firstPersonCarpetPosition", "itemTransformations");
        addDependency("fixRod", "itemTransformations");
        addDependency("entityTransforms", "thirdTransformations");
    }

    public void preLoad() {
        // does nothing, used to call static initializers
    }
}
