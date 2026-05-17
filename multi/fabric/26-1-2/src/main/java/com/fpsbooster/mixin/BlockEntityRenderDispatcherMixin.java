package com.fpsbooster.mixin;

import com.fpsbooster.Config;
import com.fpsbooster.util.Culling;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityRenderDispatcher.class)
public abstract class BlockEntityRenderDispatcherMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private <E extends BlockEntity> void fpsbooster$cullOccluded(
            E blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer,
            CallbackInfo ci) {
        if (!Config.on(Config.cullBlockEntities)) return;
        BlockPos pos = blockEntity.getBlockPos();
        Vec3 center = new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        if (Culling.behindWalls(center)) ci.cancel();
    }
}
