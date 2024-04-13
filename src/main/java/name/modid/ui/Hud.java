package name.modid.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class Hud {

    private static MinecraftClient mc = MinecraftClient.getInstance();

    public static void render(DrawContext context, float tickDelta) {
        context.drawText(mc.textRenderer, "MineCC" , 10, 10, -1, true);
    }
}
