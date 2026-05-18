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
        y = addToggle(centerX, y, "fpsbooster.config.enabled", "Master toggle",
                () -> Config.enabled, v -> Config.enabled = v);
        y += 24;
        y = addToggle(centerX, y, "fpsbooster.config.cullEntities", "Cull hidden entities",
                () -> Config.cullEntities, v -> Config.cullEntities = v);
        y = addToggle(centerX, y, "fpsbooster.config.cullBlockEntities", "Cull hidden block entities",
                () -> Config.cullBlockEntities, v -> Config.cullBlockEntities = v);
        y = addToggle(centerX, y, "fpsbooster.config.cullParticles", "Cull hidden particles",
                () -> Config.cullParticles, v -> Config.cullParticles = v);
        y = addToggle(centerX, y, "fpsbooster.config.skipOffscreenItemAnim", "Skip off-screen item animations",
                () -> Config.skipOffscreenItemAnim, v -> Config.skipOffscreenItemAnim = v);
        y = addToggle(centerX, y, "fpsbooster.config.batchChunkUpdates", "Batch chunk updates",
                () -> Config.batchChunkUpdates, v -> Config.batchChunkUpdates = v);
        y = addToggle(centerX, y, "fpsbooster.config.poolHotVectors", "Reuse hot-path vectors",
                () -> Config.poolHotVectors, v -> Config.poolHotVectors = v);

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, b -> this.onClose())
                .bounds(centerX - 100, y + 16, 200, 20).build());
    }

    private int addToggle(int centerX, int y, String langKey, String fallback,
                          BooleanSupplier get, Consumer<Boolean> set) {
        // Resource-pack registration is finicky on Quilt 26.1.x without QFAPI, so we
        // pass the human-readable string as fallback; lang file overrides when loaded.
        Component label = Component.translatableWithFallback(langKey, fallback);
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
