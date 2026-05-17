package com.fpsbooster.client;

import com.fpsbooster.Config;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

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
        y = addToggle(centerX, y, "fpsbooster.config.enabled",
                () -> Config.enabled, v -> Config.enabled = v);
        y += 24;
        y = addToggle(centerX, y, "fpsbooster.config.cullEntities",
                () -> Config.cullEntities, v -> Config.cullEntities = v);
        y = addToggle(centerX, y, "fpsbooster.config.cullBlockEntities",
                () -> Config.cullBlockEntities, v -> Config.cullBlockEntities = v);
        y = addToggle(centerX, y, "fpsbooster.config.cullParticles",
                () -> Config.cullParticles, v -> Config.cullParticles = v);
        y = addToggle(centerX, y, "fpsbooster.config.skipOffscreenItemAnim",
                () -> Config.skipOffscreenItemAnim, v -> Config.skipOffscreenItemAnim = v);
        y = addToggle(centerX, y, "fpsbooster.config.batchChunkUpdates",
                () -> Config.batchChunkUpdates, v -> Config.batchChunkUpdates = v);
        y = addToggle(centerX, y, "fpsbooster.config.poolHotVectors",
                () -> Config.poolHotVectors, v -> Config.poolHotVectors = v);

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, b -> this.onClose())
                .bounds(centerX - 100, y + 16, 200, 20).build());
    }

    private int addToggle(int centerX, int y, String langKey, BooleanSupplier get, Consumer<Boolean> set) {
        Component label = Component.translatable(langKey);
        this.addRenderableWidget(
                CycleButton.onOffBuilder(get.getAsBoolean())
                        .create(centerX - 150, y, 300, 20, label, (btn, v) -> set.accept(v))
        );
        return y + 24;
    }

    @Override
    public void onClose() {
        Config.save();
        if (this.minecraft != null) this.minecraft.setScreen(this.parent);
    }
}
