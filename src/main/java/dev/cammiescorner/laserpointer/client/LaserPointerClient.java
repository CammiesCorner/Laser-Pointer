package dev.cammiescorner.laserpointer.client;

import dev.cammiescorner.laserpointer.LaserPointer;
import dev.cammiescorner.laserpointer.client.particles.LaserDotParticle;
import dev.cammiescorner.laserpointer.client.renderer.LaserItemRenderer;
import dev.cammiescorner.laserpointer.common.items.LaserPointerItem;
import dev.cammiescorner.laserpointer.common.registry.ModParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.resource.ResourceType;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class LaserPointerClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		for(Item item : Registry.ITEM) {
			if(item instanceof LaserPointerItem) {
				Identifier itemId = Registry.ITEM.getId(item);
				LaserItemRenderer itemRenderer = new LaserItemRenderer(itemId);
				ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(itemRenderer);
				BuiltinItemRendererRegistry.INSTANCE.register(item, itemRenderer);
				ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
					out.accept(new ModelIdentifier(LaserPointer.id(itemId.getPath() + "_gui"), "inventory"));
					out.accept(new ModelIdentifier(LaserPointer.id(itemId.getPath() + "_handheld"), "inventory"));
				});
			}
		}

		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			registry.register(LaserPointer.id("particle/red_laser_dot"));
			registry.register(LaserPointer.id("particle/green_laser_dot"));
			registry.register(LaserPointer.id("particle/blue_laser_dot"));
			registry.register(LaserPointer.id("particle/yellow_laser_dot"));
			registry.register(LaserPointer.id("particle/purple_laser_dot"));
		}));

		ParticleFactoryRegistry.getInstance().register(ModParticles.RED_LASER_DOT, LaserDotParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.GREEN_LASER_DOT, LaserDotParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.BLUE_LASER_DOT, LaserDotParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.YELLOW_LASER_DOT, LaserDotParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.PURPLE_LASER_DOT, LaserDotParticle.Factory::new);
	}
}
