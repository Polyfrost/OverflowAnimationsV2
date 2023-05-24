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

    @Checkbox(name = "1.7 Projectile Sprites", subcategory = "2D Items")
    public static boolean projectileSprites = true;

    @Switch(name = "1.7 Block-Hitting Animation", subcategory = "Interaction")
    @VigilanceName(name = "1.7 Block-Hitting Animation", category = "Animations", subcategory = "Interaction")
    public static boolean oldBlockhitting = true;

    @Switch(name = "1.7 Smoother Sneaking", subcategory = "Interaction")
    @VigilanceName(name = "1.7 Smoother Sneaking", category = "Animations", subcategory = "Interaction")
    public static boolean smoothSneaking = true;

    @Switch(name = "1.7 Armor Damage Tint", subcategory = "Interaction")
    @VigilanceName(name = "1.7 Red Armor Tint", category = "Animations", subcategory = "Interaction")
    public static boolean redArmor = true;

    @Switch(name = "1.7 Item Switching Animation", subcategory = "Interaction")
    @VigilanceName(name = "1.7 Item Switching Animation", category = "Animations", subcategory = "Interaction")
    public static boolean itemSwitch = true;

    @Switch(name = "1.7 Punching During Usage", subcategory = "Interaction")
    @VigilanceName(name = "1.7 Punching During Usage", category = "Animations", subcategory = "Interaction")
    public static boolean punching = true;

    @Checkbox(name = "1.7 Punch-During-Usage Particles", description = "Spawns Particles whilst Punching During Usage", subcategory = "Interaction")
    public static boolean punchingParticles = true;

    @Switch(name = "1.7 First-Person Item Transformations", description = "Turn off to use resource packs that modify item transformations.", subcategory = "Position")
    public static boolean itemTransformations = true;

    @Switch(name = "1.7 Third-Person Item Transformations", subcategory = "Position")
    public static boolean thirdTransformations = true;

    @Checkbox(name = "1.7 First-Person Carpet Position", subcategory = "Position")
    public static boolean firstPersonCarpetPosition = true;

    @Switch(name = "1.7 Third-Person Block Position", subcategory = "Position")
    @VigilanceName(name = "1.7 3rd Person Block Animation", category = "Animations", subcategory = "Position")
    public static boolean oldSwordBlock3 = true;

    @Switch(name = "1.7 Mirrored Projectiles", subcategory = "Position")
    public static boolean oldProjectiles = true;

    @Switch(name = "1.7 XP Orbs", subcategory = "Position")
    public static boolean oldXPOrbs = true;

    @Switch(name = "1.7 Pickup Animation", subcategory = "Position")
    public static boolean oldPickup = true;

    @Switch(name = "1.7 Enchantment Glint", subcategory = "HUD")
    public static boolean enchantmentGlint = true;

    @Switch(name = "1.7 Health Bar Flashing", subcategory = "HUD")
    @VigilanceName(name = "Remove Health Bar Flashing", category = "Animations", subcategory = "HUD")
    public static boolean oldHealth = true;

    @Switch(name = "1.7 Debug Screen Text Style", subcategory = "HUD")
    @VigilanceName(name = "Debug Screen", category = "Animations", subcategory = "HUD")
    public static boolean oldDebugScreen = true;

    @Switch(name = "1.7 Debug Screen Crosshair", subcategory = "HUD")
    public static boolean oldDebugCrosshair = true;

    @Dropdown(name = "Tab Menu Style", options = {"1.7", "1.8", "Disable Heads"})
    public int tabMode = 1;

    @Switch(name = "1.15+ Armor Enchantment Glint", subcategory = "Modern Features")
    public static boolean enchantmentGlintNew = false;

    @Switch(name = "1.15+ Item Throwing Animation", subcategory = "Modern Features")
    public static boolean itemThrow = false;

    @Switch(name = "Dropped Item Sprites Rotation Fix", subcategory = "Fixes")
    public static boolean rotationFix = true;

    @Checkbox(name = "Remove Glint From Sprites ", description = "This will disable the enchantment glint for both dropped items and projectiles.", subcategory = "Fixes")
    public static boolean spritesGlint = false;

    @Switch(name = "Rod Line Position Based on FOV", subcategory = "Fixes")
    public static boolean fixRod = true;

    @Switch(name = "Disable Entity/Mob Third-Person Item Transformations", subcategory = "Fixes")
    public static boolean entityTransforms = true;

    @Switch(name = "Disable Punching During Usage in Adventure Mode", subcategory = "Fixes")
    public static boolean adventurePunching = false;

    @Switch(name = "Disable Punch-During-Usage Particles in Adventure Mode", subcategory = "Fixes")
    public static boolean adventureParticles = false;

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
}
