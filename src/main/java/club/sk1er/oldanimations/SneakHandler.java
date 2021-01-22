package club.sk1er.oldanimations;

import club.sk1er.oldanimations.config.OldAnimationsSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SneakHandler {

    private static final float START_HEIGHT = 1.62f;
    private static final float END_HEIGHT = 1.54f;

    private static SneakHandler INSTANCE = new SneakHandler();

    private float eyeHeight;
    private float lastEyeHeight;

    public static SneakHandler getInstance() {
        return INSTANCE;
    }

    public float getEyeHeight(float partialTicks) {
        if(!OldAnimationsSettings.smoothSneaking) {
            return eyeHeight;
        }
        return lastEyeHeight + (eyeHeight - lastEyeHeight) * partialTicks;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if(event.phase == TickEvent.Phase.END) {
            lastEyeHeight = eyeHeight;

            EntityPlayerSP p = Minecraft.getMinecraft().thePlayer;
            if(p == null) {
                eyeHeight = START_HEIGHT;
                return;
            }

            if(p.isSneaking()) {
                eyeHeight = END_HEIGHT;
            } else if(!OldAnimationsSettings.longSneaking) {
                eyeHeight = START_HEIGHT;
            } else if(eyeHeight < START_HEIGHT) {
                float delta = START_HEIGHT - eyeHeight;
                delta *= 0.4;
                eyeHeight = START_HEIGHT - delta;
            }
        }
    }

}
