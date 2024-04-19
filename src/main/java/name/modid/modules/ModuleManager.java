package name.modid.modules;

import name.modid.modules.movement.FlightNew;
import name.modid.modules.movement.NoFall;
import name.modid.modules.movement.Sprint;
import name.modid.modules.world.FastUse;

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

    public Mod getModule(Class<? extends Mod> moduleClass) {
        for (Mod module : getModules()) {
            if (moduleClass.isInstance(module)) {
                return module;
            }
        }
        return null;
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

    public List<Mod> getModulesInCategory(Mod.Category category) {
        List<Mod> modulesInCategory = new ArrayList<>();

        for (Mod module : modules) {
            if (module.getCategory() == category) {
                modulesInCategory.add(module);
            }
        }

        return modulesInCategory;
    }

    private void addModules() {
        modules.add(new FlightNew());
        modules.add(new NoFall());
        modules.add(new Sprint());
        modules.add(new FastUse());
    }
}
