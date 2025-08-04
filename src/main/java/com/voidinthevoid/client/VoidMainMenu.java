mpackage com.voidinthevoid.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class VoidMainMenu extends Screen {

    private final Screen parent;

    public VoidMainMenu(Screen parent) {
        super(Text.literal("Into The Void"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int midX = width / 2;
        int midY = height / 2;

        addDrawableChild(new ButtonWidget(midX - 100, midY - 50, 200, 20, Text.literal("Start Void World"), button -> {
            // TODO: Implement Void World Start logic here
            this.client.setScreen(null);
        }));

        addDrawableChild(new ButtonWidget(midX - 100, midY - 25, 200, 20, Text.literal("Feature Manager"), button -> {
            this.client.setScreen(new FeatureManager.FeatureManagerScreen(this));
        }));

        addDrawableChild(new ButtonWidget(midX - 100, midY, 200, 20, Text.literal("Settings"), button -> {
            this.client.setScreen(new VoidSettingsScreen(this));
        }));

        addDrawableChild(new ButtonWidget(midX - 100, midY + 25, 200, 20, Text.literal("Back"), button -> {
            this.client.setScreen(parent);
        }));
    }
}
