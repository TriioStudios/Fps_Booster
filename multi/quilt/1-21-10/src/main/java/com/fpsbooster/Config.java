package com.fpsbooster;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Config {
    public static boolean enabled = true;
    public static boolean cullEntities = true;
    public static boolean cullBlockEntities = true;
    public static boolean cullParticles = true;
    public static boolean skipOffscreenItemAnim = true;
    public static boolean batchChunkUpdates = true;
    public static boolean poolHotVectors = true;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static Path path() {
        return FabricLoader.getInstance().getConfigDir().resolve("fpsbooster.json");
    }

    public static void load() {
        Path p = path();
        if (!Files.exists(p)) { save(); return; }
        try {
            Data d = GSON.fromJson(Files.readString(p), Data.class);
            if (d != null) d.copyTo();
        } catch (IOException e) {
            FpsBoosterFabric.LOGGER.warn("FPS Booster: failed to load config, using defaults", e);
        }
    }

    public static void save() {
        try {
            Files.createDirectories(path().getParent());
            Files.writeString(path(), GSON.toJson(Data.snapshot()));
        } catch (IOException e) {
            FpsBoosterFabric.LOGGER.warn("FPS Booster: failed to save config", e);
        }
    }

    public static boolean on(boolean flag) { return enabled && flag; }

    private static final class Data {
        boolean enabled, cullEntities, cullBlockEntities, cullParticles,
                skipOffscreenItemAnim, batchChunkUpdates, poolHotVectors;

        static Data snapshot() {
            Data d = new Data();
            d.enabled = Config.enabled;
            d.cullEntities = Config.cullEntities;
            d.cullBlockEntities = Config.cullBlockEntities;
            d.cullParticles = Config.cullParticles;
            d.skipOffscreenItemAnim = Config.skipOffscreenItemAnim;
            d.batchChunkUpdates = Config.batchChunkUpdates;
            d.poolHotVectors = Config.poolHotVectors;
            return d;
        }
        void copyTo() {
            Config.enabled = enabled;
            Config.cullEntities = cullEntities;
            Config.cullBlockEntities = cullBlockEntities;
            Config.cullParticles = cullParticles;
            Config.skipOffscreenItemAnim = skipOffscreenItemAnim;
            Config.batchChunkUpdates = batchChunkUpdates;
            Config.poolHotVectors = poolHotVectors;
        }
    }
}
