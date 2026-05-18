package com.fpsbooster.util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public final class Culling {
    private Culling() {}

    public static boolean behindWalls(Vec3 target) {
        Minecraft mc = Minecraft.getInstance();
        Level level = mc.level;
        Entity viewer = mc.player;
        if (level == null || viewer == null) return false;
        Vec3 eye = mc.gameRenderer.getMainCamera().getPosition();
        if (eye.distanceToSqr(target) < 16.0) return false;
        HitResult hit = level.clip(new ClipContext(eye, target,
                ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, viewer));
        return hit.getType() == HitResult.Type.BLOCK
                && hit.getLocation().distanceToSqr(target) > 1.5;
    }
}
