package club.sk1er.mods.sk1eroldanimations;

import club.sk1er.mods.sk1eroldanimations.command.OldAnimationsCommand;
import club.sk1er.mods.sk1eroldanimations.config.OldAnimationsSettings;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Sk1erOldAnimations.MODID, name = "Sk1er Old Animations", version = Sk1erOldAnimations.VERSION)
public class Sk1erOldAnimations {
    public static final String MODID = "sk1er_old_animations";
    public static final String VERSION = "1.0";
    public static OldAnimationsSettings oldAnimationsSettings;
    public static final Logger LOGGER = LogManager.getLogger("Sk1erOldAnimations");

    @EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new OldAnimationsCommand());
        ModCoreInstaller.initializeModCore(Minecraft.getMinecraft().mcDataDir);
        oldAnimationsSettings = new OldAnimationsSettings();
        oldAnimationsSettings.preload();
    }

    public static String getConfigClass() {
        return "club/sk1er/mods/sk1eroldanimations/config/OldAnimationsSettings";
    }
}
