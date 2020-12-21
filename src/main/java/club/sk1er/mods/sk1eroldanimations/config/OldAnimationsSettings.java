package club.sk1er.mods.sk1eroldanimations.config;

import club.sk1er.vigilance.Vigilant;
import club.sk1er.vigilance.data.Property;
import club.sk1er.vigilance.data.PropertyType;

import java.io.File;

@SuppressWarnings("unused")
public class OldAnimationsSettings extends Vigilant {

    @Property(
        type = PropertyType.SWITCH, name = "Rod Position",
        description = "Brings back the zoomed-out look of the 1.7 fishing rod.",
        category = "Animations", subcategory = "Position"
    )
    public static boolean oldRodPosition = true;

    @Property(
        type = PropertyType.SWITCH, name = "Bow Position",
        description = "Brings back the 1.7 look of the bow for improved visibility.",
        category = "Animations", subcategory = "Position"
    )
    public static boolean oldBowPosition = true;

    @Property(
        type = PropertyType.SWITCH, name = "Blocking Position",
        description = "Brings the sword more parallel to the player when they are blocking.",
        category = "Animations", subcategory = "Position"
    )
    public static boolean oldBlocking = true;

    @Property(
        type = PropertyType.SWITCH, name = "Item Holding Position",
        description = "Brings the item a little bit closer to the player in third person, like in 1.7.",
        category = "Animations", subcategory = "Position"
    )
    public static boolean oldItemHeld = true;

    @Property(
        type = PropertyType.SWITCH, name = "Eating Animation",
        description = "Makes eating and drinking look like it did in 1.7.",
        category = "Animations", subcategory = "Interaction"
    )
    public static boolean oldEating = true;

    @Property(
        type = PropertyType.SWITCH, name = "Block Hitting Animation",
        description = "Makes block hitting look much smoother, like it did in 1.7.",
        category = "Animations", subcategory = "Interaction"
    )
    public static boolean oldBlockhitting = true;

    @Property(
        type = PropertyType.SWITCH, name = "Swing Animation",
        description = "Makes swinging an item look like it did in 1.7.",
        category = "Animations", subcategory = "Interaction"
    )
    public static boolean oldSwing = true;

    @Property(
        type = PropertyType.SWITCH, name = "Sneaking Animation",
        description = "Applies smoothing to the sneak animation.",
        category = "Animations", subcategory = "Interaction"
    )
    public static boolean oldSneaking = true;

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
            description = "1.7 behaviour for deciding when your hand should move when switching items.",
            category = "Animations", subcategory = "Interaction"
    )
    public static boolean itemSwitch = true;

    @Property(
        type = PropertyType.SWITCH, name = "Health Bar Flashing",
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
        description = "Remove player heads in tab.\n§eCurrently disables rendering of ModCore's Online Indicator while enabled.",
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
