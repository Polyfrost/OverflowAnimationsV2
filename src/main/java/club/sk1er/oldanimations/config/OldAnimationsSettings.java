package club.sk1er.oldanimations.config;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;

import java.io.File;

@SuppressWarnings("unused")
public class OldAnimationsSettings extends Vigilant {

    @Property(
        type = PropertyType.SWITCH, name = "1.7 Item Positions",
        description = "Change all item models to be in the same position as 1.7.",
        category = "Animations", subcategory = "Position"
    )
    public static boolean oldModel = true;

    @Property(
        type = PropertyType.SWITCH, name = "1.7 Bow Pullback",
        description = "Change the bow pullback animation to be like 1.7.",
        category = "Animations", subcategory = "Position"
    )
    public static boolean oldBow = true;

    @Property(
        type = PropertyType.SWITCH, name = "1.7 Block Animation",
        description = "Change the sword block animation to be like 1.7.",
        category = "Animations", subcategory = "Position"
    )
    public static boolean oldSwordBlock = true;

    @Property(
        type = PropertyType.SWITCH, name = "1.7 Rod Position",
        description = "Change the fishing rod model to be in the same position as 1.7.",
        category = "Animations", subcategory = "Position"
    )
    public static boolean oldRod = true;

    @Property(
        type = PropertyType.SWITCH, name = "1.7 3rd Person Block Animation",
        description = "Change the 3rd person blocking animation to be like 1.7.",
        category = "Animations", subcategory = "Position"
    )
    public static boolean oldSwordBlock3 = true;

    /*@Property(
        type = PropertyType.SWITCH, name = "Item Holding Position",
        description = "Brings the item a little bit closer to the player in third person, like in 1.7.",
        category = "Animations", subcategory = "Position"
    )
    public static boolean oldItemHeld = true;*/

    @Property(
        type = PropertyType.SWITCH, name = "Consume Animation",
        description = "Change the eating and drinking animation to look like 1.7.",
        category = "Animations", subcategory = "Interaction"
    )
    public static boolean oldEating = true;

    @Property(
        type = PropertyType.SWITCH, name = "Block-Hitting Animation",
        description = "Makes block hitting look much smoother, like it did in 1.7.",
        category = "Animations", subcategory = "Interaction"
    )
    public static boolean oldBlockhitting = true;

    /*@Property(
        type = PropertyType.SWITCH, name = "Swing Animation",
        description = "Makes swinging an item look like it did in 1.7.",
        category = "Animations", subcategory = "Interaction"
    )
    public static boolean oldSwing = true;*/

    @Property(
        type = PropertyType.SWITCH, name = "Smooth Sneaking",
        description = "Makes the transition between sneaking/not sneaking smooth.\n§eCombine with longer unsneak to match 1.7.",
        category = "Animations", subcategory = "Interaction"
    )
    public static boolean smoothSneaking = true;


    @Property(
        type = PropertyType.SWITCH, name = "Longer Unsneak",
        description = "Makes moving up take longer than moving down\n§eCombine with smooth sneaking to match 1.7.",
        category = "Animations", subcategory = "Interaction"
    )
    public static boolean longSneaking = true;

    @Property(
        type = PropertyType.SWITCH, name = "Red Armor",
        description = "Makes an entity's armor turn red when it's hit, like in 1.7.",
        category = "Animations", subcategory = "Interaction"
    )
    public static boolean redArmor = true;

    @Property(
        type = PropertyType.SWITCH, name = "Punching During Usage",
        description = "Allows you to punch blocks whilst using an item.\n§eVisual only.",
        category = "Animations", subcategory = "Interaction"
    )
    public static boolean punching = true;

    @Property(
        type = PropertyType.SWITCH, name = "Item Switching Animation",
        description = "Stop held items from playing the switching animation when right clicking on blocks.",
        category = "Animations", subcategory = "Interaction"
    )
    public static boolean itemSwitch = true;

    @Property(
        type = PropertyType.SWITCH, name = "Remove Health Bar Flashing",
        description = "Stops your health bar flashing when you take damage.",
        category = "Animations", subcategory = "HUD"
    )
    public static boolean oldHealth = true;

    @Property(
        type = PropertyType.SWITCH, name = "Debug Screen",
        description = "Remove the boxes in the debug screen.",
        category = "Animations", subcategory = "HUD"
    )
    public static boolean oldDebugScreen = true;

    @Property(
        type = PropertyType.SWITCH, name = "Tab Overlay",
        description = "Remove player heads in tab.\n§eCurrently disables rendering of Essential's Online Indicator while enabled.",
        category = "Animations", subcategory = "HUD"
    )
    public static boolean oldTab;

    @Property(
        type = PropertyType.SWITCH, name = "Debug Hitbox",
        description = "Remove the entity eye line and box from F3+B hitboxes.",
        category = "Animations", subcategory = "World"
    )
    public static boolean oldDebugHitbox = true;

    public OldAnimationsSettings() {
        super(new File("./config/sk1eroldanimations.toml"));
        initialize();
    }
}
