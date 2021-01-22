package club.sk1er.oldanimations;

import club.sk1er.oldanimations.command.OldAnimationsCommand;
import club.sk1er.oldanimations.config.OldAnimationsSettings;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.modcore.api.ModCoreAPI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = OldAnimations.MODID, name = "Sk1er Old Animations", version = OldAnimations.VERSION)
public class OldAnimations {
    public static final String MODID = "sk1er_old_animations";
    public static final String VERSION = "1.0";
    public static OldAnimationsSettings oldAnimationsSettings;
    public static final Logger LOGGER = LogManager.getLogger("Sk1erOldAnimations");

    @EventHandler
    public void init(FMLInitializationEvent event) {
        ModCoreAPI.getCommandRegistry().registerCommand(new OldAnimationsCommand());
        oldAnimationsSettings = new OldAnimationsSettings();
        oldAnimationsSettings.preload();

        MinecraftForge.EVENT_BUS.register(AnimationHandler.getInstance());
        MinecraftForge.EVENT_BUS.register(SneakHandler.getInstance());
    }
}
