package com.fpsbooster.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PauseScreen.class)
public abstract class PauseScreenMixin extends Screen {
    protected PauseScreenMixin(Component title) { super(title); }

    @Inject(method = "init", at = @At("TAIL"))
    private void fpsbooster$addConfigButton(CallbackInfo ci) {
        int x = this.width - 110;
        int y = 6;
        this.addRenderableWidget(Button.builder(
                Component.literal("FPS Booster"),
                btn -> {
                    Minecraft mc = Minecraft.getInstance();
                    ModList.get().getModContainerById("fpsbooster").ifPresent(c ->
                            mc.setScreen(new ConfigurationScreen(c, this)));
                }
        ).bounds(x, y, 104, 20).build());
    }
}
