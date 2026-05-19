package com.fpsbooster.mixin;

import com.fpsbooster.Config;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Set;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {

    private final Set<Long> fpsbooster$pendingUpdates = new HashSet<>();
    private long fpsbooster$lastFlushFrame;

    @Inject(method = "blockChanged", at = @At("HEAD"), cancellable = true)
    private void fpsbooster$batchBlockUpdates(net.minecraft.world.level.BlockGetter level,
                                              BlockPos pos, BlockState oldState, BlockState newState,
                                              int flags, CallbackInfo ci) {
        if (!Config.on(Config.BATCH_CHUNK_UPDATES)) return;
        long key = pos.asLong();
        if (!fpsbooster$pendingUpdates.add(key)) {
            ci.cancel();
        }
    }

    @Inject(method = "renderLevel", at = @At("RETURN"))
    private void fpsbooster$flushBatch(CallbackInfo ci) {
        fpsbooster$pendingUpdates.clear();
        fpsbooster$lastFlushFrame++;
    }
}
