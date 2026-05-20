package com.fpsbooster.mixin;

import com.fpsbooster.client.FpsBoosterConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
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
        PauseScreen self = (PauseScreen) (Object) this;
        this.addRenderableWidget(Button.builder(
                Component.literal("FPS Booster"),
                btn -> Minecraft.getInstance().setScreen(new FpsBoosterConfigScreen(self))
        ).bounds(x, y, 104, 20).build());
    }
}
