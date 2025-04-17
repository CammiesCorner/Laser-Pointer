package dev.cammiescorner.laserpointer.fabric.entrypoints;

import dev.cammiescorner.laserpointer.client.particles.LaserDotParticle;
import dev.cammiescorner.laserpointer.client.renderer.LaserItemRenderer;
import dev.cammiescorner.laserpointer.common.items.LaserPointerItem;
import dev.cammiescorner.laserpointer.common.registry.ModParticles;
import dev.upcraft.sparkweave.api.annotation.CalledByReflection;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;

@CalledByReflection
public class Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        for(Item item : BuiltInRegistries.ITEM) {
            if(item instanceof LaserPointerItem) {
                ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item);
                LaserItemRenderer itemRenderer = new LaserItemRenderer(itemId);
                ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(itemRenderer);
                BuiltinItemRendererRegistry.INSTANCE.register(item, itemRenderer);
                ModelLoadingPlugin.register(ctx -> ctx.addModels(itemId.withSuffix("_gui"), itemId.withSuffix("_handheld")));
            }
        }

//        ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register(((atlasTexture, registry) -> {
//            registry.register(LaserPointer.id("particle/red_laser_dot"));
//            registry.register(LaserPointer.id("particle/green_laser_dot"));
//            registry.register(LaserPointer.id("particle/blue_laser_dot"));
//            registry.register(LaserPointer.id("particle/yellow_laser_dot"));
//            registry.register(LaserPointer.id("particle/purple_laser_dot"));
//        }));

        ParticleFactoryRegistry.getInstance().register(ModParticles.RED_LASER_DOT, LaserDotParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.GREEN_LASER_DOT, LaserDotParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.BLUE_LASER_DOT, LaserDotParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.YELLOW_LASER_DOT, LaserDotParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.PURPLE_LASER_DOT, LaserDotParticle.Factory::new);
    }
}
