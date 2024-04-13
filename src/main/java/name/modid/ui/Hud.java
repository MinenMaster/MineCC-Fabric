package name.modid.ui;

import name.modid.module.Mod;
import name.modid.module.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class Hud {

    private static MinecraftClient mc = MinecraftClient.getInstance();

    public static void render(DrawContext context, float tickDelta) {
//        context.drawText(mc.textRenderer, "MineCC" , 10, 10, -1, true);
        renderArrayList(context);
    }

    public static void renderArrayList(DrawContext context) {
        int index = 0;
        int sWidth = mc.getWindow().getScaledWidth();

        for (Mod mod : ModuleManager.INSTANCE.getEnabledModules()) {
            context.drawText(mc.textRenderer, mod.getDisplayName(), (sWidth - 4) - mc.textRenderer.getWidth(mod.getDisplayName()), 10 + (index * mc.textRenderer.fontHeight), -1, true);
            index++;
        }
    }
}
