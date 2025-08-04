package com.voidinthevoid.client.modules;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private static ModuleManager instance;
    private List<Module> modules;

    private ModuleManager() {
        modules = new ArrayList<>();
        initModules();
    }

    public static ModuleManager getInstance() {
        if (instance == null) {
            instance = new ModuleManager();
        }
        return instance;
    }

    private void initModules() {
        // Initialize modules here
        // For now, we'll just add a few placeholder modules
        modules.add(new Module("Click GUI", "Client") {
            @Override
            public void onEnable() {
                System.out.println("Click GUI enabled");
            }

            @Override
            public void onDisable() {
                System.out.println("Click GUI disabled");
            }
        });

        modules.add(new Module("HUD", "Client") {
            @Override
            public void onEnable() {
                System.out.println("HUD enabled");
            }

            @Override
            public void onDisable() {
                System.out.println("HUD disabled");
            }
        });

        modules.add(new Module("Freecam", "Client") {
            @Override
            public void onEnable() {
                System.out.println("Freecam enabled");
            }

            @Override
            public void onDisable() {
                System.out.println("Freecam disabled");
            }
        });
    }

    public List<Module> getModules() {
        return modules;
    }

    public Module getModuleByName(String name) {
        for (Module module : modules) {
            if (module.getName().equals(name)) {
                return module;
            }
        }
        return null;
    }

    public List<Module> getModulesByCategory(String category) {
        List<Module> modulesInCategory = new ArrayList<>();
        for (Module module : modules) {
            if (module.getCategory().equals(category)) {
                modulesInCategory.add(module);
            }
        }
        return modulesInCategory;
    }

    public void onUpdate() {
        for (Module module : modules) {
            if (module.isEnabled()) {
                module.onUpdate();
            }
        }
    }
}
