package dev.cammiescorner.laserpointer.client.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.*;

public class LaserDotParticle extends SpriteBillboardParticle {
	private final Direction direction;

	protected LaserDotParticle(ClientWorld clientWorld, double x, double y, double z, double directionX, double directionY, double directionZ) {
		super(clientWorld, x, y, z, 0, 0, 0);
		this.velocityX = 0;
		this.velocityY = 0;
		this.velocityZ = 0;
		this.maxAge = 0;
		this.direction = Direction.fromVector((int) directionX, (int) directionY, (int) directionZ);
	}

	@Override
	public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
		Vec3d camPos = camera.getPos();
		float lerpX = (float) (MathHelper.lerp(tickDelta, prevPosX, x) - camPos.getX());
		float lerpY = (float) (MathHelper.lerp(tickDelta, prevPosY, y) - camPos.getY());
		float lerpZ = (float) (MathHelper.lerp(tickDelta, prevPosZ, z) - camPos.getZ());
		Quaternion quaternion = switch(direction) {
			case UP -> new Quaternion(Vec3f.POSITIVE_X, 90, true);
			case DOWN -> new Quaternion(Vec3f.POSITIVE_X, -90, true);
			case NORTH -> new Quaternion(Vec3f.POSITIVE_Y, 0, true);
			case EAST -> new Quaternion(Vec3f.POSITIVE_Y, -90, true);
			case SOUTH -> new Quaternion(Vec3f.POSITIVE_Y, 180, true);
			case WEST -> new Quaternion(Vec3f.POSITIVE_Y, 90, true);
		};

		Vec3f[] vec3fs = new Vec3f[]{new Vec3f(-1F, -1F, 0F), new Vec3f(-1F, 1F, 0F), new Vec3f(1F, 1F, 0F), new Vec3f(1F, -1F, 0F)};
		float size = 0.25F;

		for(int i = 0; i < 4; ++i) {
			Vec3f vec3f2 = vec3fs[i];
			vec3f2.rotate(quaternion);
			vec3f2.scale(size);
			vec3f2.add(lerpX, lerpY, lerpZ);
		}

		int light = 15728850;

		VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
		VertexConsumer vertex = immediate.getBuffer(RenderLayer.getEntityTranslucent(sprite.getAtlas().getId()));
		vertex.vertex(vec3fs[0].getX(), vec3fs[0].getY(), vec3fs[0].getZ()).color(1F, 1F, 1F, 1F).texture(getMaxU(), getMaxV()).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(0, 1, 0).next();
		vertex.vertex(vec3fs[1].getX(), vec3fs[1].getY(), vec3fs[1].getZ()).color(1F, 1F, 1F, 1F).texture(getMaxU(), getMinV()).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(0, 1, 0).next();
		vertex.vertex(vec3fs[2].getX(), vec3fs[2].getY(), vec3fs[2].getZ()).color(1F, 1F, 1F, 1F).texture(getMinU(), getMinV()).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(0, 1, 0).next();
		vertex.vertex(vec3fs[3].getX(), vec3fs[3].getY(), vec3fs[3].getZ()).color(1F, 1F, 1F, 1F).texture(getMinU(), getMaxV()).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(0, 1, 0).next();
		immediate.draw();
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.CUSTOM;
	}

	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public Factory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
			LaserDotParticle dotParticle = new LaserDotParticle(clientWorld, x, y, z, velocityX, velocityY, velocityZ);
			dotParticle.setSprite(spriteProvider);
			return dotParticle;
		}
	}
}
