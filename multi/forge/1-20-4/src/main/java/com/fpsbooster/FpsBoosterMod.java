package com.fpsbooster;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(FpsBoosterMod.MODID)
public final class FpsBoosterMod {
    public static final String MODID = "fpsbooster";
    public static final Logger LOGGER = LogUtils.getLogger();

    public FpsBoosterMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::onClientSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        LOGGER.info("FPS Booster loaded (client-only) [Forge]");
    }
}
