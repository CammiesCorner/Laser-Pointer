package dev.cammiescorner.laserpointer;

import dev.upcraft.sparkweave.api.entrypoint.MainEntryPoint;
import dev.upcraft.sparkweave.api.platform.ModContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class LaserPointer implements MainEntryPoint {
    public static final String MOD_ID = "laserpointer";

    @Override
    public void onInitialize(ModContainer mod) {

    }

    public static HitResult raycast(Entity origin, double maxDistance, boolean includeEntities, boolean includeFluids) {
        Vec3 startPos = origin.getEyePosition(1f);
        Vec3 rotation = origin.getViewVector(1f);
        Vec3 endPos = startPos.add(rotation.scale(maxDistance));
        HitResult hitResult = origin.level().clip(new ClipContext(startPos, endPos, ClipContext.Block.COLLIDER, includeFluids ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE, origin));

        if(hitResult.getType() != HitResult.Type.MISS)
            endPos = hitResult.getLocation();

        maxDistance *= maxDistance;
        HitResult entityHitResult = ProjectileUtil.getEntityHitResult(origin, startPos, endPos, origin.getBoundingBox().expandTowards(rotation.scale(maxDistance)).inflate(1.0D, 1D, 1D), entity -> !entity.isSpectator(), maxDistance);

        if(includeEntities && entityHitResult != null)
            hitResult = entityHitResult;

        return hitResult;
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
