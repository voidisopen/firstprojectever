package com.voidinthevoid.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

public class AutoUpdater {
    private static final String MODRINTH_API_URL = "https://api.modrinth.com/v2/project/voidinthevoid/version";
    private static final String MOD_ID = "voidinthevoid";
    private static AutoUpdater instance;
    private String currentVersion;
    private boolean updateAvailable = false;
    private String latestVersion;
    private String downloadUrl;

    private AutoUpdater() {
        // Get current mod version
        Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(MOD_ID);
        if (modContainer.isPresent()) {
            currentVersion = modContainer.get().getMetadata().getVersion().getFriendlyString();
        } else {
            currentVersion = "unknown";
        }
    }

    public static AutoUpdater getInstance() {
        if (instance == null) {
            instance = new AutoUpdater();
        }
        return instance;
    }

    public void checkForUpdates() {
        new Thread(() -> {
            try {
                // Fetch latest version info from Modrinth
                URL url = new URL(MODRINTH_API_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("User-Agent", "VoidInTheVoid Mod AutoUpdater");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse JSON response
                JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
                latestVersion = jsonResponse.get("version_number").getAsString();
                downloadUrl = jsonResponse.get("files").getAsJsonArray().get(0).getAsJsonObject().get("url").getAsString();

                // Compare versions
                if (!currentVersion.equals(latestVersion)) {
                    updateAvailable = true;
                    // Notify user about update
                    MinecraftClient.getInstance().execute(() -> {
                        SystemToast.add(
                            MinecraftClient.getInstance().getToastManager(),
                            SystemToast.Type.TUTORIAL_HINT,
                            Text.literal("VoidInTheVoid Update Available"),
                            Text.literal("Version " + latestVersion + " is available. Current version: " + currentVersion)
                        );
                    });
                }
            } catch (Exception e) {
                System.err.println("Failed to check for updates: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }

    public void downloadAndInstallUpdate() {
        if (!updateAvailable) {
            return;
        }

        new Thread(() -> {
            try {
                // Download the new version
                URL url = new URL(downloadUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                Path tempFile = Files.createTempFile("voidinthevoid-update-", ".jar");
                Files.copy(
                    connection.getInputStream(),
                    tempFile,
                    StandardCopyOption.REPLACE_EXISTING
                );

                // Get the mods directory
                Path modsDir = FabricLoader.getInstance().getGameDir().resolve("mods");
                Path newModFile = modsDir.resolve("voidinthevoid-" + latestVersion + ".jar");

                // Move the downloaded file to the mods directory
                Files.move(tempFile, newModFile, StandardCopyOption.REPLACE_EXISTING);

                // Delete the old mod file
                // This is tricky because we're running from the old mod file
                // A better approach would be to use a separate launcher or
                // to schedule the deletion for the next game restart
                // For now, we'll just notify the user to manually delete the old file
                MinecraftClient.getInstance().execute(() -> {
                    SystemToast.add(
                        MinecraftClient.getInstance().getToastManager(),
                        SystemToast.Type.NARRATOR_TOGGLE,
                        Text.literal("Update Downloaded"),
                        Text.literal("Please restart Minecraft to complete the update.")
                    );
                });
            } catch (Exception e) {
                System.err.println("Failed to download and install update: " + e.getMessage());
                e.printStackTrace();
                MinecraftClient.getInstance().execute(() -> {
                    SystemToast.add(
                        MinecraftClient.getInstance().getToastManager(),
                        SystemToast.Type.PERIODIC_NOTIFICATION,
                        Text.literal("Update Failed"),
                        Text.literal("Failed to download and install the update. Check the logs for details.")
                    );
                });
            }
        }).start();
    }

    public boolean isUpdateAvailable() {
        return updateAvailable;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }
}
