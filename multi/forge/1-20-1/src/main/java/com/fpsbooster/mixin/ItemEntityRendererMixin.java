package com.fpsbooster.mixin;

import com.fpsbooster.Config;
import com.fpsbooster.util.Culling;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntityRenderer.class)
public abstract class ItemEntityRendererMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void fpsbooster$skipOffscreen(ItemEntity entity, float entityYaw, float partialTicks,
                                          PoseStack poseStack, MultiBufferSource buffer,
                                          int packedLight, CallbackInfo ci) {
        if (!Config.on(Config.SKIP_OFFSCREEN_ITEM_ANIM)) return;
        Vec3 pos = entity.position().add(0, entity.getBbHeight() * 0.5, 0);
        if (Culling.behindWalls(pos)) {
            ci.cancel();
        }
    }
}
