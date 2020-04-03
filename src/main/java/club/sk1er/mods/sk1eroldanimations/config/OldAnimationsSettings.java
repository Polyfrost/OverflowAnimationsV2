package club.sk1er.mods.sk1eroldanimations.config;

import club.sk1er.vigilance.Vigilant;
import club.sk1er.vigilance.data.Property;
import club.sk1er.vigilance.data.PropertyType;

import java.io.File;

public class OldAnimationsSettings extends Vigilant {

    @Property(
        type = PropertyType.SWITCH,
        category = "Animations",
        subcategory = "Position",
        description = "Brings back the zoomed-out look of the 1.7 fishing rod",
        name = "1.7 Rod Position"
    )
    public static boolean oldRodPosition = true;

    @Property(
        type = PropertyType.SWITCH,
        category = "Animations",
        subcategory = "Position",
        description = "Brings back the 1.7 look of the bow for improved visibility",
        name = "1.7 Bow Position"
    )
    public static boolean oldBowPosition = true;

    @Property(
            type = PropertyType.SWITCH,
            category = "Animations",
            subcategory = "Position",
            description = "Brings the sword more parallel to the player when they are blocking",
            name = "1.7 Blocking"
    )
    public static boolean oldBlocking = true;

    @Property(
            type = PropertyType.SWITCH,
            category = "Animations",
            subcategory = "Position",
            description = "Brings the item a little bit closer to the player in third person, like in 1.7",
            name = "1.7 Item Held"
    )
    public static boolean oldItemHeld = true;

    @Property(
        type = PropertyType.SWITCH,
        category = "Animations",
        subcategory = "Interaction",
        description = "Smoothly transitions between punching and eating, like in 1.7",
        name = "1.7 Eating"
    )
    public static boolean oldEating = true;

    @Property(
        type = PropertyType.SWITCH,
        category = "Animations",
        subcategory = "Interaction",
        description = "Makes blockhitting look much smoother, like it did in 1.7",
        name = "1.7 Blockhitting"
    )
    public static boolean oldBlockhitting = true;

    @Property(
        type = PropertyType.SWITCH,
        category = "Animations",
        subcategory = "Interaction",
        description = "Applies smoothing to the sneak animation",
        name = "1.7 Sneaking"
    )
    public static boolean oldSneaking = true;

    @Property(
            type = PropertyType.SWITCH,
            category = "Animations",
            subcategory = "Interaction",
            description = "Makes an entity's armor turn red when it's hit, like in 1.7",
            name = "Red Armor"
    )
    public static boolean redArmor = true;

    @Property(
            type = PropertyType.SWITCH,
            category = "Animations",
            subcategory = "HUD",
            description = "Stops your health bar flashing when you take damage",
            name = "1.7 Health Bar"
    )
    public static boolean oldHealth = true;

    public OldAnimationsSettings() {
        super(new File("./config/sk1eroldanimations.toml"));
        initialize();
    }
}
