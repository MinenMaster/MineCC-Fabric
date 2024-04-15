package name.modid.ui.screens.clickgui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import name.modid.module.Mod;

import java.awt.*;

public class ModuleButton {

    public Mod module;
    public Frame parent;
    public int offset;

    private MinecraftClient mc = MinecraftClient.getInstance();

    public ModuleButton(Mod module, Frame parent, int offset) {
        this.module = module;
        this.parent = parent;
        this.offset = offset;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height, (new Color(0, 0, 0, 150)).getRGB());
        context.drawText(mc.textRenderer, module.getName(), parent.x + 2, parent.y + offset + 2, -1, true);
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {

    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX >= parent.x && mouseX <= parent.x + parent.width && mouseY >= parent.y + offset && mouseY <= parent.y + offset + parent.height;
    }

}
