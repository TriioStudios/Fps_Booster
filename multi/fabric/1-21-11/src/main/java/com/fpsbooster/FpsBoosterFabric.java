package com.fpsbooster;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;

public final class FpsBoosterFabric implements ClientModInitializer {
    public static final String MODID = "fpsbooster";
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitializeClient() {
        Config.load();
        LOGGER.info("FPS Booster loaded (client-only) [Fabric]");
    }
}
