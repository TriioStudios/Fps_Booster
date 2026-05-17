package com.fpsbooster.mixin;

import com.fpsbooster.Config;
import com.fpsbooster.util.Culling;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private <E extends Entity> void fpsbooster$cullOccluded(
            E entity, double x, double y, double z, float rotationYaw, float partialTicks,
            PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        if (!Config.on(Config.CULL_ENTITIES)) return;
        if (entity instanceof Player) return; // never cull players — they're always relevant
        if (entity.tickCount < 2) return;
        Vec3 center = new Vec3(x, y + entity.getBbHeight() * 0.5, z);
        if (Culling.behindWalls(center)) {
            ci.cancel();
        }
    }
}
