package name.modid.module;

import name.modid.module.movement.Flight;
import name.modid.module.movement.NoFall;
import name.modid.module.movement.Sprint;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    public static final ModuleManager INSTANCE = new ModuleManager();
    private List<Mod> modules = new ArrayList<>();

    public ModuleManager() {
        addModules();
    }

    public List<Mod> getModules() {
        return modules;
    }

    public List<Mod> getEnabledModules() {
        List<Mod> enabledModules = new ArrayList<>();

        for (Mod module : modules) {
            if (module.isEnabled()) {
                enabledModules.add(module);
            }
        }

        return enabledModules;
    }

    private void addModules() {
        modules.add(new Flight());
        modules.add(new Sprint());
        modules.add(new NoFall());
    }
}
