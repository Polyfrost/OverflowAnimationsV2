package club.sk1er.mods.sk1eroldanimations.config;

import club.sk1er.vigilance.Vigilant;
import club.sk1er.vigilance.data.Property;
import club.sk1er.vigilance.data.PropertyType;
import java.io.File;

public class OldAnimationsSettings extends Vigilant {
    @Property(type = PropertyType.SWITCH, category = "bruh", subcategory = "bruh", description = "Brings back the more zoomed-out look of the fishing rod so that rod pvp is easier", name = "1.7 Rod Position")
    public static boolean oldRodPosition = true;
    @Property(type = PropertyType.SWITCH, category = "bruh", subcategory = "bruh", description = "does a thing", name = "1.7 Bow Position")
    public static boolean oldBowPosition = true;
    @Property(type = PropertyType.SWITCH, category = "bruh", subcategory = "bruh", description = "does a thing", name = "1.7 Eating")
    public static boolean oldEating = true;
    public OldAnimationsSettings() {
        super(new File("./config/sk1eroldanimations.toml"));
        initialize();
    }
}
