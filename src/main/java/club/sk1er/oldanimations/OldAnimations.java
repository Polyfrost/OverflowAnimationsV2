package club.sk1er.oldanimations;

import club.sk1er.oldanimations.command.OldAnimationsCommand;
import club.sk1er.oldanimations.config.OldAnimationsSettings;
import gg.essential.api.EssentialAPI;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = OldAnimations.MODID, name = "Sk1er Old Animations", version = OldAnimations.VERSION)
public class OldAnimations {

    public static final String MODID = "sk1er_old_animations";
    public static final String VERSION = "0.2.0";
    public static OldAnimationsSettings oldAnimationsSettings;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        EssentialAPI.getCommandRegistry().registerCommand(new OldAnimationsCommand());
        oldAnimationsSettings = new OldAnimationsSettings();
        oldAnimationsSettings.preload();

        MinecraftForge.EVENT_BUS.register(AnimationHandler.getInstance());
        MinecraftForge.EVENT_BUS.register(SneakHandler.getInstance());
    }
}
