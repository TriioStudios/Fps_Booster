package com.fpsbooster;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(FpsBoosterMod.MODID)
public final class FpsBoosterMod {
    public static final String MODID = "fpsbooster";
    public static final Logger LOGGER = LogUtils.getLogger();

    public FpsBoosterMod(FMLJavaModLoadingContext context) {
        var modBusGroup = context.getModBusGroup();
        FMLClientSetupEvent.getBus(modBusGroup).addListener(this::onClientSetup);
        context.registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        LOGGER.info("FPS Booster loaded (client-only) [Forge]");
    }
}
