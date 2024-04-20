package name.modid;

import name.modid.modules.Mod;
import name.modid.modules.ModuleManager;
import name.modid.ui.clickgui.ClickGUI;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NovaClient implements ClientModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("nova-client");
	public static final NovaClient INSTANCE = new NovaClient();
	public static MinecraftClient mc = MinecraftClient.getInstance();

	@Override
	public void onInitializeClient() {
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