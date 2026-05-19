package com.fpsbooster.mixin;

import com.fpsbooster.Config;
import com.fpsbooster.util.Culling;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleEngine.class)
public abstract class ParticleEngineMixin {

    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    private void fpsbooster$skipOccluded(Particle particle, CallbackInfo ci) {
        if (!Config.on(Config.CULL_PARTICLES)) return;
        Vec3 pos = new Vec3(particle.getBoundingBox().getCenter().x,
                            particle.getBoundingBox().getCenter().y,
                            particle.getBoundingBox().getCenter().z);
        if (Culling.behindWalls(pos)) {
            ci.cancel();
        }
    }
}
