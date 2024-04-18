package name.modid.ui;

import name.modid.module.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.util.Comparator;
import java.util.List;

import name.modid.module.Mod;

public class Hud {

    private static MinecraftClient mc = MinecraftClient.getInstance();

    public static void render(DrawContext context, float tickDelta) {
//        context.drawText(mc.textRenderer, "MineCC" , 10, 10, -1, true);
        renderArrayList(context);
    }

    public static void renderArrayList(DrawContext context) {
        int index = 0;
        int sWidth = mc.getWindow().getScaledWidth();

        List<Mod> enabledModules = ModuleManager.INSTANCE.getEnabledModules();

        enabledModules.sort(Comparator.comparingInt(m -> mc.textRenderer.getWidth(((Mod)m).getDisplayName())).reversed());

        for (Mod mod : enabledModules) {
            context.drawText(mc.textRenderer, mod.getDisplayName(), (sWidth - 4) - mc.textRenderer.getWidth(mod.getDisplayName()), 10 + (index * mc.textRenderer.fontHeight), -1, true);
            index++;
        }
    }
}
