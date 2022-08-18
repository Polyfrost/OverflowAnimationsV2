package cc.polyfrost.overflowanimations.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Checkbox;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.oneconfig.config.migration.VigilanceMigrator;
import cc.polyfrost.oneconfig.config.migration.VigilanceName;
import cc.polyfrost.overflowanimations.OverflowAnimations;

@SuppressWarnings("unused")
public class OldAnimationsSettings extends Config {

    @Switch(
            name = "2D Items",
            subcategory = "2D Items"
    )
    public static boolean items2D = false;

    @Checkbox(
            name = "Microextent 2D Sprites",
            subcategory = "2D Items"
    )
    public static boolean itemSprites = true;

    @Switch(
            name = "Cleaner Enchantment Glint",
            subcategory = "Enchantment Glint"
    )
    public static boolean enchantmentGlint = true;

    @Switch(
            name = "1.15+ New Armor Enchantment Glint",
            subcategory = "Enchantment Glint"
    )
    public static boolean enchantmentGlintNew = true;

    @Switch(name = "1.7 Item Positions", subcategory = "Position")
    @VigilanceName(name = "oldModel", category = "Animations", subcategory = "Position")
    public static boolean oldModel = true;

    @Switch(name = "1.7 Bow Pullback", subcategory = "Position")
    @VigilanceName(name = "oldBow", category = "Animations", subcategory = "Position")
    public static boolean oldBow = true;

    @Switch(name = "1.7 Block Animation", subcategory = "Position")
    @VigilanceName(name = "oldSwordBlock", category = "Animations", subcategory = "Position")
    public static boolean oldSwordBlock = true;

    @Switch(name = "1.7 Rod Position", subcategory = "Position")
    @VigilanceName(name = "oldRod", category = "Animations", subcategory = "Position")
    public static boolean oldRod = true;

    @Switch(name = "1.7 3rd Person Block Animation", subcategory = "Position")
    @VigilanceName(name = "oldSwordBlock3", category = "Animations", subcategory = "Position")
    public static boolean oldSwordBlock3 = true;

    @Switch(
            name = "1.7 Mixces Item / Block Hand Positions",
            subcategory = "Position"
    )
    public static boolean mixcesAnimations = true;

    @Checkbox(
            name = "1.7 Mixces First Person Carpet Position",
            subcategory = "Position"
    )
    public static boolean firstPersonCarpetPosition = false;

    @Switch(
            name = "1.7 Projectiles",
            subcategory = "Position"
    )
    public static boolean oldProjectiles = true;

    @Checkbox(
            name = "Microextent Projectile Sprites",
            subcategory = "Position"
    )
    public static boolean projectileSprites = true;

    /*@Switch

    me = "Item Holding Position",
      ,
        subcategory = "Position"
    )
    public static boolean oldItemHeld = true;*/

    @Switch(name = "Consume Animation", subcategory = "Interaction")
    @VigilanceName(name = "oldEating", category = "Animations", subcategory = "Interaction")
    public static boolean oldEating = true;

    @Switch(name = "Block-Hitting Animation", subcategory = "Interaction")
    @VigilanceName(name = "oldBlockhitting", category = "Animations", subcategory = "Interaction")
    public static boolean oldBlockhitting = true;

    @Switch(name = "Smooth Sneaking", subcategory = "Interaction")
    @VigilanceName(name = "smoothSneaking", category = "Animations", subcategory = "Interaction")
    public static boolean smoothSneaking = true;


    @Switch(name = "Longer Unsneak", subcategory = "Interaction")
    @VigilanceName(name = "longSneaking", category = "Animations", subcategory = "Interaction")
    public static boolean longSneaking = true;

    @Switch(name = "Red Armor", subcategory = "Interaction")
    @VigilanceName(name = "redArmor", category = "Animations", subcategory = "Interaction")
    public static boolean redArmor = true;

    @Switch(name = "Punching During Usage", subcategory = "Interaction")
    @VigilanceName(name = "punching", category = "Animations", subcategory = "Interaction")
    public static boolean punching = true;

    @Switch(name = "Item Switching Animation", subcategory = "Interaction")
    @VigilanceName(name = "itemSwitch", category = "Animations", subcategory = "Interaction")
    public static boolean itemSwitch = true;

    @Switch(name = "Remove Health Bar Flashing", subcategory = "HUD")
    @VigilanceName(name = "oldHealth", category = "Animations", subcategory = "HUD")
    public static boolean oldHealth = true;

    @Switch(name = "Debug Screen", subcategory = "HUD")
    @VigilanceName(name = "oldDebugScreen", category = "Animations", subcategory = "HUD")
    public static boolean oldDebugScreen = true;

    @Switch(name = "Tab Overlay", subcategory = "HUD")
    @VigilanceName(name = "oldTab", category = "Animations", subcategory = "HUD")
    public static boolean oldTab;

    @Switch(name = "Debug Hitbox", subcategory = "World")
    @VigilanceName(name = "oldDebugHitbox", category = "Animations", subcategory = "World")
    public static boolean oldDebugHitbox = true;

    public OldAnimationsSettings() {
        super(new Mod(OverflowAnimations.NAME, ModType.PVP, new VigilanceMigrator("./config/sk1eroldanimations.toml")), "overflowanimations.json");
        initialize();
        addDependency("firstPersonCarpetPosition", "mixcesAnimations");
        addDependency("itemSprites", "items2D");
        addDependency("projectileSprites", "oldProjectiles");
    }
}
