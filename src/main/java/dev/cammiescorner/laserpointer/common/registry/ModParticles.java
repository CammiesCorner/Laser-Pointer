package dev.cammiescorner.laserpointer.common.registry;

import dev.cammiescorner.laserpointer.LaserPointer;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.registry.Registry;

public class ModParticles {
	public static final DefaultParticleType RED_LASER_DOT = FabricParticleTypes.simple();
	public static final DefaultParticleType GREEN_LASER_DOT = FabricParticleTypes.simple();
	public static final DefaultParticleType BLUE_LASER_DOT = FabricParticleTypes.simple();
	public static final DefaultParticleType YELLOW_LASER_DOT = FabricParticleTypes.simple();
	public static final DefaultParticleType PURPLE_LASER_DOT = FabricParticleTypes.simple();

	public static void register() {
		Registry.register(Registry.PARTICLE_TYPE, LaserPointer.id("red_laser_dot"), RED_LASER_DOT);
		Registry.register(Registry.PARTICLE_TYPE, LaserPointer.id("green_laser_dot"), GREEN_LASER_DOT);
		Registry.register(Registry.PARTICLE_TYPE, LaserPointer.id("blue_laser_dot"), BLUE_LASER_DOT);
		Registry.register(Registry.PARTICLE_TYPE, LaserPointer.id("yellow_laser_dot"), YELLOW_LASER_DOT);
		Registry.register(Registry.PARTICLE_TYPE, LaserPointer.id("purple_laser_dot"), PURPLE_LASER_DOT);
	}
}
