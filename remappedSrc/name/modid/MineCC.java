package name.modid;

import name.modid.module.Mod;
import name.modid.module.ModuleManager;
import name.modid.ui.screens.clickgui.ClickGUI;
import net.fabricmc.api.ModInitializer;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MineCC implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("minecc");
	public static final MineCC INSTANCE = new MineCC();

	private MinecraftClient mc = MinecraftClient.getInstance();

	@Override
	public void onInitialize() {
	}

	public void onKeyPress(int key, int action) {
		if (action == GLFW.GLFW_PRESS) {
			for (Mod module : ModuleManager.INSTANCE.getModules()) {
				if (key == module.getKey()) {
					module.toggle();
				}
			}

			if (key == GLFW.GLFW_KEY_RIGHT_SHIFT) {
				mc.setScreen(ClickGUI.INSTANCE);
			}
		}
	}

	public void onTick() {
		if (mc.player != null) {
			for (Mod module : ModuleManager.INSTANCE.getEnabledModules()) {
				module.onTick();
			}
		}
	}
}