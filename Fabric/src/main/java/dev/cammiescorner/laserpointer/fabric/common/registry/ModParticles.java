package dev.cammiescorner.laserpointer.fabric.common.registry;

import dev.cammiescorner.laserpointer.LaserPointer;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModParticles {
	public static final SimpleParticleType RED_LASER_DOT = FabricParticleTypes.simple();
	public static final SimpleParticleType GREEN_LASER_DOT = FabricParticleTypes.simple();
	public static final SimpleParticleType BLUE_LASER_DOT = FabricParticleTypes.simple();
	public static final SimpleParticleType YELLOW_LASER_DOT = FabricParticleTypes.simple();
	public static final SimpleParticleType PURPLE_LASER_DOT = FabricParticleTypes.simple();

	public static void register() {
		Registry.register(BuiltInRegistries.PARTICLE_TYPE, LaserPointer.id("red_laser_dot"), RED_LASER_DOT);
		Registry.register(BuiltInRegistries.PARTICLE_TYPE, LaserPointer.id("green_laser_dot"), GREEN_LASER_DOT);
		Registry.register(BuiltInRegistries.PARTICLE_TYPE, LaserPointer.id("blue_laser_dot"), BLUE_LASER_DOT);
		Registry.register(BuiltInRegistries.PARTICLE_TYPE, LaserPointer.id("yellow_laser_dot"), YELLOW_LASER_DOT);
		Registry.register(BuiltInRegistries.PARTICLE_TYPE, LaserPointer.id("purple_laser_dot"), PURPLE_LASER_DOT);
	}
}
