package dev.cammiescorner.laserpointer.fabric.client.particles;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class LaserDotParticle extends TextureSheetParticle {
	private final Direction direction;

	protected LaserDotParticle(ClientLevel clientWorld, double x, double y, double z, double directionX, double directionY, double directionZ) {
		super(clientWorld, x, y, z, 0, 0, 0);
		this.xd = 0;
		this.yd = 0;
		this.zd = 0;
		this.lifetime = 0;
		this.direction = Direction.fromDelta((int) directionX, (int) directionY, (int) directionZ);
		quadSize = 0.25f;
	}

	@Override
	public void render(VertexConsumer buffer, Camera camera, float partialTicks) {
		Quaternionf quaternionf = switch(direction) {
			case UP -> Axis.XP.rotationDegrees(90f);
			case DOWN -> Axis.XN.rotationDegrees(90f);
			case NORTH -> Axis.YP.rotationDegrees(0f);
			case EAST -> Axis.YN.rotationDegrees(90f);
			case SOUTH -> Axis.YP.rotationDegrees(180f);
			case WEST -> Axis.YP.rotationDegrees(90f);
		};

		this.renderRotatedQuad(buffer, camera, quaternionf, partialTicks);
	}

	@Override
	public float getQuadSize(float scaleFactor) {
		return 0.25f;
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.CUSTOM;
	}

	public static class Factory implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteProvider;

		public Factory(SpriteSet spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
			LaserDotParticle dotParticle = new LaserDotParticle(clientWorld, x, y, z, velocityX, velocityY, velocityZ);
			dotParticle.pickSprite(spriteProvider);
			return dotParticle;
		}
	}
}
