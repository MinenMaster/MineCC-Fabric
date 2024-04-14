package name.modid.ui.screens.clickgui;

import name.modid.module.Mod;
import name.modid.module.Mod.Category;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;

public class Frame {

    public int x, y, width, height;
    public Category category;

    public boolean dragging;

    private MinecraftClient mc = MinecraftClient.getInstance();

    public Frame(Category category, int x, int y, int width, int height) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dragging = false;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(x, y, x + width, y + height, -1);
        context.drawText(mc.textRenderer, category.name , x + 2, y + 2, Color.RED.getRGB(), true);
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {

    }
}
