package cc.polyfrost.overflowanimations.handlers;

import cc.polyfrost.oneconfig.events.event.Stage;
import cc.polyfrost.oneconfig.events.event.TickEvent;
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe;
import cc.polyfrost.overflowanimations.config.OldAnimationsSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class SneakHandler { 

    private static final float START_HEIGHT = 1.62f;
    private static final float END_HEIGHT = 1.54f;

    private static final SneakHandler INSTANCE = new SneakHandler();

    private float eyeHeight;
    private float lastEyeHeight;

    public static SneakHandler getInstance() {
        return INSTANCE;
    }

    public float getEyeHeight(float partialTicks) {
        if (!OldAnimationsSettings.smoothSneaking) {
            return eyeHeight;
        }

        return lastEyeHeight + (eyeHeight - lastEyeHeight) * partialTicks;
    }

    @Subscribe
    public void onTick(TickEvent event) {
        if (event.stage == Stage.END) {
            lastEyeHeight = eyeHeight;

            final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            if (player == null) {
                eyeHeight = START_HEIGHT;
                return;
            }

            if (player.isSneaking()) {
                eyeHeight = END_HEIGHT;
            } else if (!OldAnimationsSettings.longSneaking) {
                eyeHeight = START_HEIGHT;
            } else if (eyeHeight < START_HEIGHT) {
                float delta = START_HEIGHT - eyeHeight;
                delta *= 0.4;
                eyeHeight = START_HEIGHT - delta;
            }
        }
    }
}
