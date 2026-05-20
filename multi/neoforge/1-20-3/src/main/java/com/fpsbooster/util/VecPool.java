package com.fpsbooster.util;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class VecPool {
    private static final ThreadLocal<MutableVec3> VEC = ThreadLocal.withInitial(MutableVec3::new);
    private static final ThreadLocal<MutableAABB> BOX = ThreadLocal.withInitial(MutableAABB::new);

    private VecPool() {}

    public static MutableVec3 vec(double x, double y, double z) {
        MutableVec3 v = VEC.get();
        v.x = x; v.y = y; v.z = z;
        return v;
    }

    public static MutableAABB box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        MutableAABB b = BOX.get();
        b.minX = minX; b.minY = minY; b.minZ = minZ;
        b.maxX = maxX; b.maxY = maxY; b.maxZ = maxZ;
        return b;
    }

    public static final class MutableVec3 {
        public double x, y, z;
        public double lengthSqr() { return x*x + y*y + z*z; }
        public Vec3 immutable() { return new Vec3(x, y, z); }
    }

    public static final class MutableAABB {
        public double minX, minY, minZ, maxX, maxY, maxZ;
        public AABB immutable() { return new AABB(minX, minY, minZ, maxX, maxY, maxZ); }
    }
}
