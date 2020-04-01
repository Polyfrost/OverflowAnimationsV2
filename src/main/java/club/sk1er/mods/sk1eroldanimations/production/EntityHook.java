package club.sk1er.mods.sk1eroldanimations.production;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class EntityHook {

    private static float currentHeight = 1.62F;
    private static long lastChangeTime = System.currentTimeMillis();

    public static float getEyeHeight() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        int timeDelay = 1000 / 100;
        if (player.isSneaking()) {
            float sneakingHeight = 1.54f;

            if (currentHeight > sneakingHeight) {
                long time = System.currentTimeMillis();
                long timeSinceLastChange = time - lastChangeTime;
                if (timeSinceLastChange > timeDelay) {
                    currentHeight -= 0.012f;
                    lastChangeTime = time;
                }
            }
        } else {
            float standingHeight = 1.62F;
            if (currentHeight < standingHeight && currentHeight > 0.2f) {
                long time = System.currentTimeMillis();
                long timeSinceLastChange = time - lastChangeTime;
                if (timeSinceLastChange > timeDelay) {
                    currentHeight += 0.012f;
                    lastChangeTime = time;
                }
            } else {
                currentHeight = 1.62f;
            }
        }

        if (player.isPlayerSleeping()) {
            currentHeight = 0.2f;
        }

        return currentHeight;
    }
}
