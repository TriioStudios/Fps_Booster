package com.fpsbooster.mixin;

import com.fpsbooster.Config;
import com.fpsbooster.util.Culling;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {
    // MC 1.21.x moved entity rendering to a state-based pipeline; shouldRender() is the
    // right cull hook now -- return false to skip the entity entirely.
    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private <E extends Entity> void fpsbooster$cullOccluded(
            E entity, Frustum frustum, double camX, double camY, double camZ,
            CallbackInfoReturnable<Boolean> cir) {
        if (!Config.on(Config.CULL_ENTITIES)) return;
        if (entity instanceof Player) return;
        if (entity.tickCount < 2) return;
        Vec3 center = entity.position().add(0, entity.getBbHeight() * 0.5, 0);
        if (Culling.behindWalls(center)) {
            cir.setReturnValue(false);
        }
    }
}
