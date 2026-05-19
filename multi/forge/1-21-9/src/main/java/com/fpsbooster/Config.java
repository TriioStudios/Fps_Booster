package com.fpsbooster;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    private static final String P = "fpsbooster.config.";
    private static final ForgeConfigSpec.Builder B = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue ENABLED =
            B.comment("Master toggle for all FPS Booster optimizations")
             .translation(P + "enabled")
             .define("enabled", true);

    public static final ForgeConfigSpec.BooleanValue CULL_ENTITIES =
            B.comment("Skip rendering entities outside the view frustum or fully occluded by terrain")
             .translation(P + "cullEntities")
             .define("cullEntities", true);

    public static final ForgeConfigSpec.BooleanValue CULL_BLOCK_ENTITIES =
            B.comment("Skip rendering block entities (chests/signs/etc.) outside frustum")
             .translation(P + "cullBlockEntities")
             .define("cullBlockEntities", true);

    public static final ForgeConfigSpec.BooleanValue CULL_PARTICLES =
            B.comment("Skip ticking/rendering particles outside frustum or behind walls")
             .translation(P + "cullParticles")
             .define("cullParticles", true);

    public static final ForgeConfigSpec.BooleanValue SKIP_OFFSCREEN_ITEM_ANIM =
            B.comment("Skip bob/rotation math for item entities the player cannot see")
             .translation(P + "skipOffscreenItemAnim")
             .define("skipOffscreenItemAnim", true);

    public static final ForgeConfigSpec.BooleanValue BATCH_CHUNK_UPDATES =
            B.comment("Coalesce multiple block updates in the same frame into one mesh rebuild")
             .translation(P + "batchChunkUpdates")
             .define("batchChunkUpdates", true);

    public static final ForgeConfigSpec.BooleanValue POOL_HOT_VECTORS =
            B.comment("Reuse Vec3/AABB instances in per-frame hot paths to reduce GC pressure")
             .translation(P + "poolHotVectors")
             .define("poolHotVectors", true);

    public static final ForgeConfigSpec SPEC = B.build();

    public static boolean on(ForgeConfigSpec.BooleanValue v) {
        return ENABLED.get() && v.get();
    }
}
