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
        description = "Zooms out Fishing Rod's in order to make them more visible.",
        name = "1.7 Rod Position"
    )
    public static boolean oldRodPosition = true;

    @Property(
        type = PropertyType.SWITCH,
        category = "Animations",
        subcategory = "Position",
        description = "Zooms out Bow's in order to make them more visible.",
        name = "1.7 Bow Position"
    )
    public static boolean oldBowPosition = true;

    @Property(
        type = PropertyType.SWITCH,
        category = "Animations",
        subcategory = "Interaction",
        description = "what this do",
        name = "1.7 Eating"
    )
    public static boolean oldEating = true;

    @Property(
        type = PropertyType.SWITCH,
        category = "Animations",
        subcategory = "Interaction",
        description = "what this do",
        name = "1.7 Blockhitting"
    )
    public static boolean oldBlockhitting = true;

    @Property(
        type = PropertyType.SWITCH,
        category = "Animations",
        subcategory = "Interaction",
        description = "Apply smoothing to the sneak animation.",
        name = "1.7 Sneaking"
    )
    public static boolean oldSneaking = true;

    @Property(
            type = PropertyType.SWITCH,
            category = "Animations",
            subcategory = "idk",
            description = "Makes an entity's armor turn red when it's hit",
            name = "Red Armor"
    )
    public static boolean redArmor = true;

    public OldAnimationsSettings() {
        super(new File("./config/sk1eroldanimations.toml"));
        initialize();
    }
}
