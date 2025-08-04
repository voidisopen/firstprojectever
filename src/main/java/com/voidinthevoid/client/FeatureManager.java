    }                                                                                                                                                                                                       package com.voidinthevoid.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorUtil;

import java.util.ArrayList;
import java.util.List;

public class FeatureManager {
    private static FeatureManager instance;
    private List<Feature> features;
    private MinecraftClient mc;

    private FeatureManager() {
        features = new ArrayList<>();
        mc = MinecraftClient.getInstance();
        initFeatures();
    }

    public static FeatureManager getInstance() {
        if (instance == null) {
            instance = new FeatureManager();
        }
        return instance;
    }

    private void initFeatures() {
        // UI & CUSTOMIZATION
        features.add(new Feature("Fully Custom Main Menu", "UI & CUSTOMIZATION", true) {
            @Override
            public void onEnable() {
                System.out.println("Fully Custom Main Menu enabled");
            }
        });

        features.add(new Feature("Module Manager GUI", "UI & CUSTOMIZATION", true) {
            @Override
            public void onEnable() {
                System.out.println("Module Manager GUI enabled");
            }
        });

        features.add(new Feature("Theme Selector", "UI & CUSTOMIZATION", true) {
            @Override
            public void onEnable() {
                System.out.println("Theme Selector enabled");
            }
        });

        features.add(new Feature("Color Editor", "UI & CUSTOMIZATION", true) {
            @Override
            public void onEnable() {
                System.out.println("Color Editor enabled");
            }
        });

        // PVP (NO AURAS, LEGIT CLIENT-SIDE)
        features.add(new Feature("Keystrokes HUD", "PVP", true) {
            @Override
            public void onRender() {
                if (mc.player != null && mc.currentScreen == null) {
                    // Render keystrokes HUD
                    System.out.println("Rendering Keystrokes HUD");
                }
            }
        });

        features.add(new Feature("CPS Counter", "PVP", true) {
            @Override
            public void onRender() {
                if (mc.player != null && mc.currentScreen == null) {
                    // Render CPS Counter
                    System.out.println("Rendering CPS Counter");
                }
            }
        });

        features.add(new Feature("FPS Counter", "PVP", true) {
            @Override
            public void onRender() {
                if (mc.player != null && mc.currentScreen == null) {
                    // Render FPS Counter
                    System.out.println("Rendering FPS Counter");
                }
            }
        });

        // QUALITY OF LIFE
        features.add(new Feature("Item Durability HUD", "QUALITY OF LIFE", true) {
            @Override
            public void onRender() {
                if (mc.player != null && mc.currentScreen == null) {
                    // Render Item Durability HUD
                    System.out.println("Rendering Item Durability HUD");
                }
            }
        });

        features.add(new Feature("AutoGG", "QUALITY OF LIFE", true) {
            @Override
            public void onEnable() {
                System.out.println("AutoGG enabled");
            }
        });

        features.add(new Feature("Zoom (OptiFine-style)", "QUALITY OF LIFE", true) {
            @Override
            public void onEnable() {
                System.out.println("Zoom enabled");
            }
        });

        // CREATOR / VISUAL STUFF
        features.add(new Feature("Freecam (Client-side only)", "CREATOR", true) {
            @Override
            public void onEnable() {
                System.out.println("Freecam enabled");
            }
        });

        features.add(new Feature("Cinematic Camera Mode", "CREATOR", true) {
            @Override
            public void onEnable() {
                System.out.println("Cinematic Camera Mode enabled");
            }
        });

        // PERFORMANCE / TECH
        features.add(new Feature("Memory Usage Bar", "PERFORMANCE", true) {
            @Override
            public void onRender() {
                if (mc.player != null && mc.currentScreen == null) {
                    // Render Memory Usage Bar
                    System.out.println("Rendering Memory Usage Bar");
                }
            }
        });

        features.add(new Feature("Lag Spike Graph", "PERFORMANCE", true) {
            @Override
            public void onRender() {
                if (mc.player != null && mc.currentScreen == null) {
                    // Render Lag Spike Graph
                    System.out.println("Rendering Lag Spike Graph");
                }
            }
        });

        // CHAOS COSMETICS (NO ADVANTAGE)
        features.add(new Feature("Void Trail Cosmetic", "CHAOS COSMETICS", true) {
            @Override
            public void onEnable() {
                System.out.println("Void Trail Cosmetic enabled");
            }
        });

        features.add(new Feature("Cape Selector", "CHAOS COSMETICS", true) {
            @Override
            public void onEnable() {
                System.out.println("Cape Selector enabled");
            }
        });

        // DEV / TESTER ONLY
        features.add(new Feature("Built-in Logger", "DEV", true) {
            @Override
            public void onEnable() {
                System.out.println("Built-in Logger enabled");
            }
        });

        features.add(new Feature("Live FPS Profiler", "DEV", true) {
            @Override
            public void onEnable() {
                System.out.println("Live FPS Profiler enabled");
            }
        });
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public Feature getFeatureByName(String name) {
        for (Feature feature : features) {
            if (feature.getName().equals(name)) {
                return feature;
            }
        }
        return null;
    }

    public List<Feature> getFeaturesByCategory(String category) {
        List<Feature> featuresInCategory = new ArrayList<>();
        for (Feature feature : features) {
            if (feature.getCategory().equals(category)) {
                featuresInCategory.add(feature);
            }
        }
        return featuresInCategory;
    }

    public void onUpdate() {
        for (Feature feature : features) {
            if (feature.isEnabled()) {
                feature.onUpdate();
            }
        }
    }

    public void onRender() {
        for (Feature feature : features) {
            if (feature.isEnabled()) {
                feature.onRender();
            }
        }
    }

    public static abstract class Feature {
        private String name;
        private String category;
        private boolean enabled;

        public Feature(String name, String category, boolean enabled) {
            this.name = name;
            this.category = category;
            this.enabled = enabled;
        }

        public String getName() {
            return name;
        }

        public String getCategory() {
            return category;
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

        public void onRender() {
            // Override in subclasses
        }
    }

    public static class FeatureManagerScreen extends Screen {
        private final Screen parent;
        private FeatureManager featureManager;
        private List<Feature> allFeatures;
        private List<Feature> displayedFeatures;
        private int scrollOffset = 0;
        private int selectedCategory = 0;
        private String[] categories = {"All", "UI & CUSTOMIZATION", "PVP", "QUALITY OF LIFE", "CREATOR", "PERFORMANCE", "CHAOS COSMETICS", "DEV"};

        public FeatureManagerScreen(Screen parent) {
            super(Text.literal("Feature Manager"));
            this.parent = parent;
            this.featureManager = FeatureManager.getInstance();
            this.allFeatures = new ArrayList<>(featureManager.getFeatures());
            this.displayedFeatures = new ArrayList<>(allFeatures);
        }

        @Override
        protected void init() {
            super.init();
            this.clearChildren();
            int x = 10;
            int y = 30;
            int i = 0;

            // Add category buttons
            for (String category : categories) {
                int finalI = i;
                this.addDrawableChild(new ButtonWidget(x, y, 100, 20, Text.literal(category), button -> {
                    this.selectedCategory = finalI;
                    this.updateFeatureList();
                }));
                x += 105;
                if (x > width - 100) {
                    x = 10;
                    y += 25;
                }
                i++;
            }

            // Add feature buttons
            updateFeatureList();
        }

        @Override
        public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
            this.renderBackground(matrices);
            super.render(matrices, mouseX, mouseY, delta);
            this.textRenderer.draw(matrices, "Feature Manager", 10, 10, 0xFFFFFF);

            // Render features
            int x = 10;
            int y = 60;
            int i = scrollOffset;
            while (i < displayedFeatures.size() && y < height - 30) {
                Feature feature = displayedFeatures.get(i);
                this.textRenderer.draw(matrices, feature.getName(), x, y, feature.isEnabled() ? 0x00FF00 : 0xFF0000);
                i++;
                y += 15;
            }
        }

        private void updateFeatureList() {
            this.displayedFeatures.clear();
            if (this.selectedCategory == 0) {
                this.displayedFeatures.addAll(this.allFeatures);
            } else {
                this.displayedFeatures.addAll(this.featureManager.getFeaturesByCategory(this.categories[this.selectedCategory]));
            }
        }
    }
}
