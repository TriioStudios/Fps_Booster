package com.fpsbooster.client;

import com.fpsbooster.Config;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.ModConfigSpec;

public class FpsBoosterConfigScreen extends Screen {
    private final Screen parent;

    public FpsBoosterConfigScreen(Screen parent) {
        super(Component.literal("FPS Booster"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int y = 40;
        y = addToggle(centerX, y, "fpsbooster.config.enabled", "Master toggle", Config.ENABLED);
        y += 24;
        y = addToggle(centerX, y, "fpsbooster.config.cullEntities", "Cull hidden entities", Config.CULL_ENTITIES);
        y = addToggle(centerX, y, "fpsbooster.config.cullBlockEntities", "Cull hidden block entities", Config.CULL_BLOCK_ENTITIES);
        y = addToggle(centerX, y, "fpsbooster.config.cullParticles", "Cull hidden particles", Config.CULL_PARTICLES);
        y = addToggle(centerX, y, "fpsbooster.config.skipOffscreenItemAnim", "Skip off-screen item animations", Config.SKIP_OFFSCREEN_ITEM_ANIM);
        y = addToggle(centerX, y, "fpsbooster.config.batchChunkUpdates", "Batch chunk updates", Config.BATCH_CHUNK_UPDATES);
        y = addToggle(centerX, y, "fpsbooster.config.poolHotVectors", "Reuse hot-path vectors", Config.POOL_HOT_VECTORS);

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, b -> this.onClose())
                .bounds(centerX - 100, y + 16, 200, 20).build());
    }

    private int addToggle(int centerX, int y, String langKey, String fallback, ModConfigSpec.BooleanValue value) {
        Component label = Component.translatableWithFallback(langKey, fallback);
        this.addRenderableWidget(
                CycleButton.onOffBuilder(value.get())
                        .create(centerX - 150, y, 300, 20, label, (btn, v) -> value.set(v))
        );
        return y + 24;
    }

    @Override
    public void onClose() {
        Config.SPEC.save();
        if (this.minecraft != null) this.minecraft.setScreen(this.parent);
    }
}
