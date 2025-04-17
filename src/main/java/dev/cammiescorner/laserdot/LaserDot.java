package dev.cammiescorner.laserdot;

import dev.cammiescorner.laserdot.common.registry.ItemTags;
import dev.cammiescorner.laserdot.common.registry.ModItems;
import dev.cammiescorner.laserdot.common.registry.ModParticles;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LaserDot implements ModInitializer {
	public static final String MOD_ID = "laserdot";
	public static final Logger LOGGER = LoggerFactory.getLogger("Laser Dot");

	@Override
	public void onInitialize(ModContainer mod) {
		ModItems.register();
		ModParticles.register();

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			if(player.getStackInHand(hand).isIn(ItemTags.LASER_POINTERS) && !player.isSneaking()) {
				player.getStackInHand(hand).use(world, player, hand);
				return ActionResult.SUCCESS;
			}

			return ActionResult.PASS;
		});

		UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if(player.getStackInHand(hand).isIn(ItemTags.LASER_POINTERS) && !player.isSneaking()) {
				player.getStackInHand(hand).use(world, player, hand);
				return ActionResult.CONSUME;
			}

			return ActionResult.PASS;
		});
	}

	public static HitResult raycast(Entity origin, double maxDistance, boolean includeEntities, boolean includeFluids) {
		Vec3d startPos = origin.getCameraPosVec(1F);
		Vec3d rotation = origin.getRotationVec(1F);
		Vec3d endPos = startPos.add(rotation.multiply(maxDistance));
		HitResult hitResult = origin.world.raycast(new RaycastContext(startPos, endPos, RaycastContext.ShapeType.COLLIDER, includeFluids ? RaycastContext.FluidHandling.ANY : RaycastContext.FluidHandling.NONE, origin));

		if(hitResult.getType() != HitResult.Type.MISS)
			endPos = hitResult.getPos();

		maxDistance *= maxDistance;
		HitResult entityHitResult = ProjectileUtil.raycast(origin, startPos, endPos, origin.getBoundingBox().stretch(rotation.multiply(maxDistance)).expand(1.0D, 1D, 1D), entity -> !entity.isSpectator(), maxDistance);

		if(includeEntities && entityHitResult != null)
			hitResult = entityHitResult;

		return hitResult;
	}

	public static Identifier id(String name) {
		return new Identifier(MOD_ID, name);
	}
}
