package dev.cammiescorner.laserpointer.common.items;

import dev.cammiescorner.laserpointer.LaserPointer;
import dev.cammiescorner.laserpointer.common.registry.EntityTags;
import dev.cammiescorner.laserpointer.common.registry.ModParticles;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class LaserPointerItem extends Item {
	private final DyeColor colour;

	public LaserPointerItem(DyeColor colour) {
		super(new Properties().stacksTo(1));
		this.colour = colour;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		return ItemUtils.startUsingInstantly(world, user, hand);
	}

	@Override
	public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		HitResult hitResult = LaserPointer.raycast(user, 16, false, true);
		HitResult entityHit = LaserPointer.raycast(user, 16, true, false);

		if(entityHit instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity livingEntity && Math.abs(livingEntity.getEyeY() - entityHitResult.getLocation().y()) < 0.125)
			livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40, 0, false, false));

		if(hitResult.getType() != HitResult.Type.MISS) {
			Vec3i vec = Vec3i.ZERO;
			double offset = 0.001;

			if(hitResult instanceof BlockHitResult blockHit)
				vec = blockHit.getDirection().getNormal();
			if(entityHit instanceof EntityHitResult entityHitResult && entityHitResult.getEntity().getType().is(EntityTags.THIN_ENTITIES))
				offset += 0.0625;

			Vec3 pos = hitResult.getLocation().add(vec.getX() * offset, vec.getY() * offset, vec.getZ() * offset);
			ParticleOptions particleEffect = switch(colour) {
				case GREEN -> ModParticles.GREEN_LASER_DOT;
				case BLUE -> ModParticles.BLUE_LASER_DOT;
				case YELLOW -> ModParticles.YELLOW_LASER_DOT;
				case PURPLE -> ModParticles.PURPLE_LASER_DOT;
				default -> ModParticles.RED_LASER_DOT;
			};

			world.addParticle(particleEffect, true, pos.x(), pos.y(), pos.z(), vec.getX(), vec.getY(), vec.getZ());
		}
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.NONE;
	}

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity entity) {
		return Integer.MAX_VALUE;
	}
}
