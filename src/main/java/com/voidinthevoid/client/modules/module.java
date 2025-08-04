package com.voidinthevoid.client.modules;

public abstract class Module {
    private String name;
    private boolean enabled;
    private String category;

    public Module(String name, String category) {
        this.name = name;
        this.category = category;
        this.enabled = false;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public String getCategory() {
        return category;
    }

    public void toggle() {
        setEnabled(!enabled);
    }

    public void onEnable() {
        // Override in subclasses
    }

    public void onDisable() {
        // Override in subclasses
    }

    public void onUpdate() {
        // Override in subclasses
    }
}
