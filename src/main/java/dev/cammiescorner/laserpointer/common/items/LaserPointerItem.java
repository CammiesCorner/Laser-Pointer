package dev.cammiescorner.laserpointer.common.items;

import dev.cammiescorner.laserpointer.LaserPointer;
import dev.cammiescorner.laserpointer.common.registry.EntityTags;
import dev.cammiescorner.laserpointer.common.registry.ModParticles;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class LaserPointerItem extends Item {
	private final DyeColor colour;

	public LaserPointerItem(DyeColor colour) {
		super(new FabricItemSettings().maxCount(1));
		this.colour = colour;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		return ItemUsage.consumeHeldItem(world, user, hand);
	}

	@Override
	public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		HitResult hitResult = LaserPointer.raycast(user, 16, false, true);
		HitResult entityHit = LaserPointer.raycast(user, 16, true, false);

		if(entityHit instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity livingEntity && Math.abs(livingEntity.getEyeY() - entityHitResult.getPos().getY()) < 0.125)
			livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 40, 0, false, false));

		if(hitResult.getType() != HitResult.Type.MISS) {
			Vec3i vec = Vec3i.ZERO;
			double offset = 0.001;

			if(hitResult instanceof BlockHitResult blockHit)
				vec = blockHit.getSide().getVector();
			if(entityHit instanceof EntityHitResult entityHitResult && entityHitResult.getEntity().getType().isIn(EntityTags.THIN_ENTITIES))
				offset += 0.0694;

			Vec3d pos = hitResult.getPos().add(vec.getX() * offset, vec.getY() * offset, vec.getZ() * offset);
			ParticleEffect particleEffect = switch(colour) {
				case GREEN -> ModParticles.GREEN_LASER_DOT;
				case BLUE -> ModParticles.BLUE_LASER_DOT;
				case YELLOW -> ModParticles.YELLOW_LASER_DOT;
				case PURPLE -> ModParticles.PURPLE_LASER_DOT;
				default -> ModParticles.RED_LASER_DOT;
			};

			world.addParticle(particleEffect, true, pos.getX(), pos.getY(), pos.getZ(), vec.getX(), vec.getY(), vec.getZ());
		}
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return Integer.MAX_VALUE;
	}
}
