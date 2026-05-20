package com.fpsbooster;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(FpsBoosterMod.MODID)
public class FpsBoosterMod {
    public static final String MODID = "fpsbooster";
    public static final Logger LOGGER = LogUtils.getLogger();

    public FpsBoosterMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::onClientSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        LOGGER.info("FPS Booster loaded (client-only) [NeoForge]");
    }
}
