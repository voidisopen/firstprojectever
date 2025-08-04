package com.voidinthevoid.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class VoidSettingsScreen extends Screen {

    private final Screen parent;
    private boolean voidUIEnabled = com.voidinthevoid.client.VoidInTheVoidClient.voidUIEnabled;
    private boolean hotbarCustomEnabled = false;
    
    // UI Customization variables
    private int hotbarX = 0;
    private int hotbarY = 0;
    private int inventoryX = 0;
    private int inventoryY = 0;
    
    // Username/Skin changer variables
    private String newUsername = "";
    private boolean skinCustomizationEnabled = false;

    public VoidSettingsScreen(Screen parent) {
        super(Text.literal("Void Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int midX = width / 2;
        int buttonY = 30;

        // Void UI toggle
        addDrawableChild(new ButtonWidget(midX - 100, buttonY, 200, 20, 
            Text.literal("Void UI: " + (voidUIEnabled ? "ON" : "OFF")), 
            button -> {
                voidUIEnabled = !voidUIEnabled;
                button.setMessage(Text.literal("Void UI: " + (voidUIEnabled ? "ON" : "OFF")));
                com.voidinthevoid.client.VoidInTheVoidClient.voidUIEnabled = voidUIEnabled;
            }));
        buttonY += 30;

        // Hotbar customization toggle
        addDrawableChild(new ButtonWidget(midX - 100, buttonY, 200, 20, 
            Text.literal(hotbarCustomEnabled ? "Disable Hotbar Customization" : "Enable Hotbar Customization"), 
            button -> {
                hotbarCustomEnabled = !hotbarCustomEnabled;
                button.setMessage(Text.literal(hotbarCustomEnabled ? "Disable Hotbar Customization" : "Enable Hotbar Customization"));
            }));
        buttonY += 30;

        // Add a simple button for now to represent the complex feature of customizing the UI
        addDrawableChild(new ButtonWidget(midX - 100, buttonY, 200, 20, 
            Text.literal("Customize UI Elements"), 
            button -> {
                // Placeholder for complex UI customization logic
            }));
        buttonY += 30;
            
        // Add a button for the username/skin changer
        addDrawableChild(new ButtonWidget(midX - 100, buttonY, 200, 20, 
            Text.literal("Open Skin/Username Changer"), 
            button -> {
                // Placeholder for the username/skin changer
            }));
        buttonY += 30;

        // Back button
        addDrawableChild(new ButtonWidget(midX - 100, buttonY, 200, 20, Text.literal("Back"), button -> this.client.setScreen(parent)));
    }
}
